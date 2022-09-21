package com.yskc.ms.models.contract;

import javax.validation.constraints.Size;
import java.math.BigDecimal;

/**
 * @program: ms
 * @description:
 * @author: wjy
 * @create: 2022-08-30 11:19
 **/
public class ContractMemberModel {
    private Integer id;
    private Integer contractId;
    private Integer userId;
    private String userName;
    private Integer deptId;
    private String deptName;
    private BigDecimal ratio;
    @Size(max=200,message="备注不能超过200个字")
    private String remark;

    public Integer getDeptId() {
        return deptId;
    }

    public void setDeptId(Integer deptId) {
        this.deptId = deptId;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

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

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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
