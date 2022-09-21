package com.yskc.rs.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.yskc.rs.entity.tech.BaseEntity;

/**
 * @Author: hck
 * @DateTime: 2021/8/26 17:08
 * @Description:
 */
@TableName("p_meet_member")
public class MeetMemberEntity extends BaseEntity {

    @TableId
    private Integer id;

    private Integer projectId;

    private String members;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public String getMembers() {
        return members;
    }

    public void setMembers(String members) {
        this.members = members;
    }
}
