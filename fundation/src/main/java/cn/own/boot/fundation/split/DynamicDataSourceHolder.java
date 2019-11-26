package cn.own.boot.fundation.split;

import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

/**
 * @description: 多数据源配置
 * @author: xinYue
 * @time: 2019/11/19 16:21
 */
@Slf4j
public class DynamicDataSourceHolder {

    private static ThreadLocal<String> contextHolder = new ThreadLocal<>();
    public static final String DB_MASTER = "master";
    public static final String DB_SLAVE="slave";

    /**
     * 获取数据源
     * @return
     */
    public static String getDbType(){
        String db = contextHolder.get();
        if(Objects.isNull(db)){
            db = DB_MASTER;
        }
        return db;
    }

    /**
     * 设置数据源
     * @param str
     */
    public static void setDbType(String str){
        log.debug("当前使用的数据库是:{}",str);
        contextHolder.set(str);
    }

    /**
     * 清除数据源
     */
    public static void clearDbType(){
        contextHolder.remove();
    }

}
