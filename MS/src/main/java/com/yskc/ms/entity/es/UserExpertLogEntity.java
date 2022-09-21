package com.yskc.ms.entity.es;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * @DateTime: 2021/10/19 15:09
 * @Description:专家库用户审核日志记录实体类
 * @author: hsx
 */
@TableName("user_expert_log")
public class UserExpertLogEntity implements Serializable {

    private static final long serialVersionUID = 5172116001372348615L;

    @TableId
    private Integer id;

    private Integer userExpertId;

    private Integer status;

    private String suggestion;   //审核意见

    private Date createTime;

    private Integer msCreatorId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserExpertId() {
        return userExpertId;
    }

    public void setUserExpertId(Integer userExpertId) {
        this.userExpertId = userExpertId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getSuggestion() {
        return suggestion;
    }

    public void setSuggestion(String suggestion) {
        this.suggestion = suggestion;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getMsCreatorId() {
        return msCreatorId;
    }

    public void setMsCreatorId(Integer msCreatorId) {
        this.msCreatorId = msCreatorId;
    }
}
