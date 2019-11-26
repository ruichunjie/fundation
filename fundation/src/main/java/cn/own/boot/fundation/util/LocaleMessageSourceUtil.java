package cn.own.boot.fundation.util;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Locale;

/**
 * 国际化
 */
@Component
public class LocaleMessageSourceUtil {

    @Resource
    private MessageSource messageSource;

    private static LocaleMessageSourceUtil localeMessageSourceUtil;

    @PostConstruct
    public void init() {
        localeMessageSourceUtil = this;
        localeMessageSourceUtil.messageSource = this.messageSource;

    }


    /**
     * @param code ：对应messages配置的key.
     * @return
     */
    public static String getMessage(String code){
        return getMessage(code,null);
    }

    /**
     *
     * @param code ：对应messages配置的key.
     * @param args : 数组参数.
     * @return
     */
    public static String getMessage(String code,Object[] args){
        return getMessage(code, args,"");
    }


    /**
     *
     * @param code ：对应messages配置的key.
     * @param args : 数组参数.
     * @param defaultMessage : 没有设置key的时候的默认值.
     * @return
     */
    public static String getMessage(String code,Object[] args,String defaultMessage){
        Locale locale = LocaleContextHolder.getLocale();
        return localeMessageSourceUtil.messageSource.getMessage(code, args, defaultMessage, locale);
    }
}
