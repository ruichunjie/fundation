package cn.tourism.back.entity;

import cn.tourism.back.enums.RouteStatusEnum;
import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@JsonIgnoreProperties({"createTime","updateTime","img"})
public class Route {

    /**ID*/
    private Integer id;

    /**UID*/
    private Integer uid;

    /**标题*/
    private String title;

    /**开始时间*/
    @JSONField(format = "yyyy-MM-dd")
    private Date start;

    /**结束时间*/
    @JSONField(format = "yyyy-MM-dd")
    private Date end;

    /**出行天数*/
    private Integer days;

    /**出行地点*/
    private String address;

    /**图片*/
    private String img;

    /**费用*/
    private BigDecimal fee;

    /**声明*/
    private String word;

    /**状态*/
    private RouteStatusEnum status;

    /**图片*/
    private List<String> imgUrlList;

    /**创建时间*/
    @JSONField(format = "yyyy-MM-dd")
    private Date createTime;

    /**更新时间*/
    @JSONField(format = "yyyy-MM-dd")
    private Date updateTime;
}
