package com.yskc.ms.entity.ms;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.yskc.ms.entity.ms.models.MsBaseEntity;

import javax.validation.constraints.Size;
import java.math.BigDecimal;

/**
 * @program: ms
 * @description:
 * @author: wjy
 * @create: 2022-08-30 10:47
 **/
@TableName("contract_member")
public class ContractMemberEntity extends MsBaseEntity {
    @TableId
    private Integer id;
    private Integer contractId;
    private Integer mtype;
    private Integer userId;
    private BigDecimal ratio;
    @Size(max=200,message="备注不能超过200个字")
    private String remark;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getContractId() {
        return contractId;
    }

    public void setContractId(Integer contractId) {
        this.contractId = contractId;
    }

    public Integer getMtype() {
        return mtype;
    }

    public void setMtype(Integer mtype) {
        this.mtype = mtype;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public BigDecimal getRatio() {
        return ratio;
    }

    public void setRatio(BigDecimal ratio) {
        this.ratio = ratio;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
