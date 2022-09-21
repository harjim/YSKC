package com.yskc.ms.entity.rs;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by hck
 * on 2020/5/20 13:58
 * 步骤状态
 */
@TableName("t_step_status")
public class RsStepStatusEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId
    private Integer id;
    /**
     * 状态
     */
    private Integer status;
    /**
     * 审核人
     */
    private Integer auditorId;
    /**
     * 审核时间
     */
    private Date auditTime;
    /**
     * 备注
     */
    private String remark;
    /**
     * 类型id
     */
    private Integer productId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getAuditorId() {
        return auditorId;
    }

    public void setAuditorId(Integer auditorId) {
        this.auditorId = auditorId;
    }

    public Date getAuditTime() {
        return auditTime;
    }

    public void setAuditTime(Date auditTime) {
        this.auditTime = auditTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }
}
