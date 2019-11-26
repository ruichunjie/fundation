package cn.own.boot.fundation.util;

import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * @description: 日期转换
 * @author: xinYue
 * @time: 2019/10/11 10:51
 */
@Slf4j
public class DateUtil {

    /**
     * String->Date
     * @param dateStr
     * @return
     * @throws ParseException
     */
    public static Date stringToDate(String dateStr, String format) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Date date = sdf.parse(dateStr);
        return date;
    }

    /**
     * 将时间向前/后推移天数
     * @param date
     * @param day
     * @return
     */
    public static Date moveTime(Date date,int day){
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(Calendar.DATE,day);
        return calendar.getTime();
    }

}
