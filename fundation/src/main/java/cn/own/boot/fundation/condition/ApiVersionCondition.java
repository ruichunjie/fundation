package cn.own.boot.fundation.condition;

import cn.own.boot.fundation.enums.OwnExceptionEnum;
import cn.own.boot.fundation.exception.OwnException;
import org.springframework.web.servlet.mvc.condition.RequestCondition;

import javax.servlet.http.HttpServletRequest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @description: 版本控制
 * @author: xinYue
 * @time: 2019/10/31 17:19
 */
public class ApiVersionCondition implements RequestCondition<ApiVersionCondition> {

    private int apiVersion;

    /**
     * 路径中版本前缀 格式/v[1-9]/
     */
    private final static Pattern VERSION_PREFIX_PATTERN =  Pattern.compile("v(\\d+)/");

    public ApiVersionCondition(int apiVersion){
        this.apiVersion = apiVersion;
    }

    public int getApiVersion() {
        return apiVersion;
    }

    @Override
    public ApiVersionCondition combine(ApiVersionCondition other) {
        return new ApiVersionCondition(other.getApiVersion());
    }

    @Override
    public ApiVersionCondition getMatchingCondition(HttpServletRequest request) {
        Matcher m = VERSION_PREFIX_PATTERN.matcher(request.getRequestURI());
        if(m.find()){
            Integer version = Integer.valueOf(m.group(1));
            if(version == this.apiVersion) {
                return this;
            }
        }
        //可以 return null 但是如果通过${version}访问也可以访问到
        throw new OwnException(OwnExceptionEnum.ApiVersion);
    }

    @Override
    public int compareTo(ApiVersionCondition other, HttpServletRequest request) {
        return other.getApiVersion() - this.apiVersion;
    }

}
