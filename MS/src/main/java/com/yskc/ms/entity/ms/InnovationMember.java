package com.yskc.ms.entity.ms;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.util.Date;

/**
 * @BelongsProject: ms
 * @BelongsPackage: com.yskc.ms.entity.ms
 * @Author: zhangdingfu
 * @CreateTime: 2021-07-26 11:32
 * @Description: 创新项目成员
 */
@TableName("innovation_member")
public class InnovationMember {

    @TableId
    private Integer id;
    /**
     * 类型(1.技术人员;2.财务人员;3.业务员；4.谈单经理)
     */
    private Integer mType;
    /**
     * 人员id
     */
    private Integer memberId;
    /**
     * 是否负责人(财务/技术只能有一个负责人，业务员没有负责人)
     */
    private Boolean isMaster;
    /**
     * 创新项目id
     */
    private Integer innovationId;
    private Integer creatorId;
    private Date createTime;

    public static InnovationMember build(Integer iId, Date now, Integer memberId, Integer mType,Integer creatorId,boolean isMaster) {
        InnovationMember member = new InnovationMember();
        member.innovationId = iId;
        member.createTime = now;
        member.memberId = memberId;
        member.mType = mType;
        member.creatorId = creatorId;
        member.isMaster = isMaster;
        return member;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getmType() {
        return mType;
    }

    public void setmType(Integer mType) {
        this.mType = mType;
    }

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    public Boolean getMaster() {
        return isMaster;
    }

    public void setMaster(Boolean master) {
        isMaster = master;
    }

    public Integer getInnovationId() {
        return innovationId;
    }

    public void setInnovationId(Integer innovationId) {
        this.innovationId = innovationId;
    }

    public Integer getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Integer creatorId) {
        this.creatorId = creatorId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
