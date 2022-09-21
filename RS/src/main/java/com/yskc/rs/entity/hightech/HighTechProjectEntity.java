package com.yskc.rs.entity.hightech;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.yskc.rs.entity.tech.BaseEntity;
import com.yskc.rs.models.UserInfo;

import java.util.Date;

/**
 * @Author: hck
 * @DateTime: 2021/5/27 16:56
 * @Description: 高新技术关联RD
 */
@TableName("highTech_project")
public class HighTechProjectEntity extends BaseEntity {

    @TableId
    private Integer id;
    private Integer companyId;
    private Integer projectId;
    private Integer highTechId;

    public HighTechProjectEntity() {
    }

    public HighTechProjectEntity(Integer projectId, Integer highTechId, UserInfo userInfo, Date date) {
        this.companyId = userInfo.getCompanyId();
        this.projectId = projectId;
        this.highTechId = highTechId;
        create(userInfo.getUserId(),userInfo.getMsUserId(),date);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public Integer getHighTechId() {
        return highTechId;
    }

    public void setHighTechId(Integer highTechId) {
        this.highTechId = highTechId;
    }
}
