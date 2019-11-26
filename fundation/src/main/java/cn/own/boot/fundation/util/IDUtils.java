package cn.own.boot.fundation.util;

import java.math.BigDecimal;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class IDUtils {

	/**
	 * 系统自动构造唯一id生成器
	 */
	private static final IdWorker idWorker = new IdWorker();

	/**
	 * 从生成器中获取Long型主建
	 * @return
	 */
	public static Long genId(){
		return idWorker.nextId();
	}

	/**
	 * 从生成器中获取BigDecimal型主建
	 * @return
	 */
	public static BigDecimal genIdForBigDecimal(){
		long id = idWorker.nextId();
		return new BigDecimal(id);
	}

	/**
	 * 解析雪花id中的时间、服务器id、数据中心id、序列id
	 * @param id
	 * @return
	 */
	public static JSONObject parseId(long id) {
		return idWorker.parseId(id);
	}

	/**
	 * 测试
	 * @param args
	 */
	public static void main(String[] args) {
		for(int i=0; i<10; i++){
			long id = IDUtils.genId();
			log.info("id=" + id + "，生成日期："+IDUtils.parseId(id).get("date").toString());
			log.info("，数据中心id:"+IDUtils.parseId(id).get("dataCenterId").toString());
			log.info("，服务id:"+IDUtils.parseId(id).get("workerId").toString());
		}
	}
}
