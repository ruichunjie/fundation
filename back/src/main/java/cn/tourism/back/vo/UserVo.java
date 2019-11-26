package cn.tourism.back.vo;

import cn.tourism.back.enums.FocusEnum;
import cn.tourism.back.enums.SexEnum;
import cn.tourism.back.enums.UserStatusEnum;
import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @description: 返回前端VO数据
 * @author: xinYue
 * @time: 2019/10/9 11:32
 */
@Data
public class UserVo {

    /**UID*/
    private Integer uid;

    /**用户名*/
    private String phone;

    /**等级*/
    private Integer level;

    /**等级剩余时间*/
    private Integer levelnvq;

    /**体验资格*/
    private Integer levelday;

    /**昵称*/
    private String nickname;

    /**声明*/
    private String declaration;

    /**粉丝数*/
    private Integer fans;

    /**关注*/
    private Integer concern;

    /**头像*/
    private String icon;

    /**最近次数*/
    private Integer num;

    /**最近时间*/
    @JSONField(format = "yyyy-MM-dd")
    private Date lastTime;

    /**性别*/
    private SexEnum sex;

    /**年龄*/
    private Integer age;

    /**出生日期*/
    @JSONField(format = "yyyy-MM-dd")
    private Date birth;

    /**职业*/
    private String profession;

    /**地址*/
    private String address;

    /**状态*/
    private UserStatusEnum status;

    /**时区*/
    private String areaCode;

    /**直推*/
    private Integer direct;

    /**间推*/
    private Integer indirect;

    /**互关*/
    private FocusEnum focus;

    /**子集*/
    private List<UserVo> children;

    /**等级*/
    private Integer clevel;

    /**路径*/
    private String path;

    @JSONField(format = "yyyy-MM-dd")
    private Date createTime;

}
