package cn.own.boot.fundation.util;

import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;

import cn.own.boot.fundation.exception.OwnException;
import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.LogFactory;

import com.alibaba.fastjson.JSONObject;
import org.springframework.util.StringUtils;

/**
 * <p>
 * 分布式雪花算法高效有序ID生成器 <br>
 * </p>
 * @author xinYue
 * @since 2019-01-12
 */
class IdWorker {

    private static final Log logger = LogFactory.getLog(IdWorker.class);
    /**
     * 时间起始标记点，作为基准（一旦确定不能变动）
     * twepoch 为什么要等于1308888222657L 而不等于其他数？
     * 答：1308888222657L 是 (Fri Jun 24 12:03:42 CST 2011) 这一时刻到1970-01-01 00:00:00时刻所经过的毫秒数。
     * 	  项目开始时间（Sat Jan 12 23:27:23 CST 2019）减去1308888222657L 的值刚好在2^41 里，因此占41位。
     *    所以这个数是为了让时间戳占41位才特地算出来的。41位字节作为时间戳数值大约68年会用完，以便保证雪花id长度固定在19位
     */
    private long twepoch = 1308888222657L;
    /**
     * 机器标识位数
     */
    private long workerIdBits = 5L;
    private long datacenterIdBits = 5L;
    private long maxWorkerId = -1L ^ (-1L << workerIdBits);
    private long maxDatacenterId = -1L ^ (-1L << datacenterIdBits);
    /**
     * 毫秒内自增位
     */
    private long sequenceBits = 12L;
    private long workerIdShift = sequenceBits;
    private long datacenterIdShift = sequenceBits + workerIdBits;
    /**
     * 时间戳左移动位
     */
    private long timestampLeftShift = sequenceBits + workerIdBits + datacenterIdBits;
    private long sequenceMask = -1L ^ (-1L << sequenceBits);

    private long workerId;

    /**
     * 数据标识 ID 部分
     */
    private long datacenterId;
    /**
     * 并发控制
     */
    private long sequence = 0L;
    /**
     * 上次生产 ID 时间戳
     */
    private long lastTimestamp = -1L;

    public IdWorker() {
        this.datacenterId = getDatacenterId(maxDatacenterId);
        this.workerId = getMaxWorkerId(datacenterId, maxWorkerId);
    }

    /**
     * <p>
     * 有参构造器
     * </p>
     *
     * @param workerId     工作机器ID
     * @param datacenterId 序列号
     */
    public IdWorker(long workerId, long datacenterId) {
        if ((workerId > maxWorkerId) || (workerId < 0L)) {
        	throw new IllegalArgumentException(String.format("workerId 不能大于 %d 或小于 0", maxWorkerId));
        }
        if ((datacenterId > maxDatacenterId) || (datacenterId < 0L)) {
        	throw new IllegalArgumentException(String.format("datacenterId 不能大于 %d 或小于 0", maxDatacenterId));
        }
        this.workerId = workerId;
        this.datacenterId = datacenterId;
    }

    /**
     * <p>
     * 获取 maxWorkerId
     * </p>
     */
    protected static long getMaxWorkerId(long datacenterId, long maxWorkerId) {
        StringBuilder mpid = new StringBuilder();
        mpid.append(datacenterId);
        String name = ManagementFactory.getRuntimeMXBean().getName();
        if (!StringUtils.isEmpty(name)) {
            /*
             * GET jvmPid
             */
            mpid.append(name.split("@")[0]);
        }
        /*
         * MAC + PID 的 hashcode 获取16个低位
         */
        return (mpid.toString().hashCode() & 0xffff) % (maxWorkerId + 1);
    }

    /**
     * <p>
     * 数据标识id部分
     * </p>
     */
    protected static long getDatacenterId(long maxDatacenterId) {
        long id = 0L;
        try {
            InetAddress ip = InetAddress.getLocalHost();
            NetworkInterface network = NetworkInterface.getByInetAddress(ip);
            if (network == null) {
                id = 1L;
            } else {
                byte[] mac = network.getHardwareAddress();
                if (null != mac) {
                    id = ((0x000000FF & (long) mac[mac.length - 1]) | (0x0000FF00 & (((long) mac[mac.length - 2]) << 8))) >> 6;
                    id = id % (maxDatacenterId + 1);
                }
            }
        } catch (Exception e) {
            logger.warn(" getDatacenterId: " + e.getMessage());
        }
        return id;
    }

    /**
     * 获取下一个ID
     *
     * @return
     */
    public synchronized long nextId() {
        long timestamp = timeGen();
        //闰秒
        if (timestamp < lastTimestamp) {
            long offset = lastTimestamp - timestamp;
            if (offset <= 5) {
                try {
                    wait(offset << 1);
                    timestamp = timeGen();
                    if (timestamp < lastTimestamp) {
                        throw new OwnException(String.format("Clock moved backwards.  Refusing to generate id for %d milliseconds", offset));
                    }
                } catch (Exception e) {
                    throw new OwnException(e.getMessage());
                }
            } else {
                throw new OwnException(String.format("Clock moved backwards.  Refusing to generate id for %d milliseconds", offset));
            }
        }

        if (lastTimestamp == timestamp) {
            // 相同毫秒内，序列号自增
            sequence = (sequence + 1) & sequenceMask;
            if (sequence == 0) {
                // 同一毫秒的序列数已经达到最大
                timestamp = tilNextMillis(lastTimestamp);
            }
        } else {
            // 不同毫秒内，序列号置为 1 - 3 随机数
            sequence = ThreadLocalRandom.current().nextLong(1, 3);
        }

        lastTimestamp = timestamp;

        // 时间戳部分 | 数据中心部分 | 机器标识部分 | 序列号部分
        return ((timestamp - twepoch) << timestampLeftShift)
            | (datacenterId << datacenterIdShift)
            | (workerId << workerIdShift)
            | sequence;
    }

    /**
	 * 解析雪花id中的时间、服务器id、数据中心id、序列id
	 * @param id
	 * @return
	 */
	public JSONObject parseId(long id) {
		// 将雪花id转换为二进制字符串
	    String id2Str = Long.toBinaryString(id);
	    int len = id2Str.length();
	    JSONObject jsonObject = new JSONObject();
	    int sequenceStart = (int) (len < workerIdShift ? 0 : len - workerIdShift);
	    int workerStart = (int) (len < datacenterIdShift ? 0 : len - datacenterIdShift);
	    int timeStart = (int) (len < timestampLeftShift ? 0 : len - timestampLeftShift);
	    String sequenceStr = id2Str.substring(sequenceStart, len);
	    String workerIdStr = sequenceStart == 0 ? "0" : id2Str.substring(workerStart, sequenceStart);
	    String dataCenterId = workerStart == 0 ? "0" : id2Str.substring(timeStart, workerStart);
	    String time = timeStart == 0 ? "0" : id2Str.substring(0, timeStart);
	    int sequenceInt = Integer.valueOf(sequenceStr, 2);
	    jsonObject.put("sequence", sequenceInt);
	    int workerIdInt = Integer.valueOf(workerIdStr, 2);
	    jsonObject.put("workerId", workerIdInt);
	    int dataCenterIdInt = Integer.valueOf(dataCenterId, 2);
	    jsonObject.put("dataCenterId", dataCenterIdInt);
	    long diffTime = Long.parseLong(time, 2);
	    long timeLong = diffTime + twepoch;
	    Date date = new Date(timeLong);
	    jsonObject.put("date", date);
	    return jsonObject;
	}

    protected long tilNextMillis(long lastTimestamp) {
        long timestamp = timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = timeGen();
        }
        return timestamp;
    }

    protected long timeGen() {
        return System.currentTimeMillis();
    }

}

