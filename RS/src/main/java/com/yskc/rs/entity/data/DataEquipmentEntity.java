package com.yskc.rs.entity.data;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.yskc.common.utils.DateUtil;
import com.yskc.rs.entity.tech.BaseEntity;
import com.yskc.rs.models.UserInfo;
import com.yskc.rs.models.excel.DataEquipmentExcel;
import com.yskc.rs.utils.ToolUtils;

import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author huronghua
 * @email 267267134@qq.com
 * @date 2019-07-12 08:24:37
 */
@TableName("d_equipment")
public class DataEquipmentEntity extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @TableId
    private Integer id;
    /**
     *
     */
    @Size(max = 100, message = "资产代码不能超过100个字")
    private String ecode;
    /**
     *
     */

    @Size(max = 200, message = "设备名称不能超过200个字")
    private String ename;
    /**
     *
     */
    @JsonFormat(pattern = "yyyy-MM", timezone = "GMT+8")
    private Date month;
    /**
     *
     */
    private String equData;
    /**
     *
     */
    private Integer companyId;
    /**
     *
     */
    private String remark;
    /**
     *
     */
    private String remainEquData;
    /**
     *
     */
    private BigDecimal workHours;
    private BigDecimal depreciation;
    private Integer accountTitleId;

    @TableField(exist = false)
    private Integer etype;

    private String deptName;

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setEcode(String ecode) {
        this.ecode = ecode;
    }

    public String getEcode() {
        return ecode;
    }

    public void setEname(String ename) {
        this.ename = ename;
    }

    public String getEname() {
        return ename;
    }

    public void setMonth(Date month) {
        this.month = month;
    }

    public Date getMonth() {
        return month;
    }

    public void setEquData(String equData) {
        this.equData = equData;
    }

    public String getEquData() {
        return equData;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getRemark() {
        return remark;
    }

    public String getRemainEquData() {
        return remainEquData;
    }

    public void setRemainEquData(String remainEquData) {
        this.remainEquData = remainEquData;
    }

    public BigDecimal getWorkHours() {
        return workHours;
    }

    public void setWorkHours(BigDecimal workHours) {
        this.workHours = workHours;
    }

    public BigDecimal getDepreciation() {
        return depreciation;
    }

    public void setDepreciation(BigDecimal depreciation) {
        this.depreciation = depreciation;
    }

    public Integer getEtype() {
        return etype;
    }

    public void setEtype(Integer etype) {
        this.etype = etype;
    }

    /**
     * 获取设备使用记录
     *
     * @param userInfo
     * @param now
     * @param equData
     * @param excel
     */
    public static DataEquipmentEntity build(UserInfo userInfo, Date now, String equData, DataEquipmentExcel excel) {
        DataEquipmentEntity result = new DataEquipmentEntity();
        result.companyId = userInfo.getCompanyId();
        result.equData = equData;
        result.ename = excel.getEname();
        result.ecode = excel.getEcode();
        result.month = DateUtil.getMonthFirstDay(excel.getMonth());
        result.remainEquData = equData;
        result.depreciation = excel.getDepreciation();
        BigDecimal totalHours = BigDecimal.valueOf(cn.hutool.core.date.DateUtil.dayOfMonth(DateUtil.getMonthLastDay(result.month)) * 24);
        result.workHours = excel.getWorkHours() == null || excel.getWorkHours().compareTo(totalHours) > 0 || excel.getWorkHours().compareTo(BigDecimal.ZERO) < 0 ? totalHours : excel.getWorkHours();
        result.remark = "设备使用记录报表导入";
        ToolUtils.entityCreate(result, userInfo.getUserId(), userInfo.getMsUserId(), now);
        result.deptName = excel.getDeptName();
        return result;
    }

    public Integer getAccountTitleId() {
        return accountTitleId;
    }

    public void setAccountTitleId(Integer accountTitleId) {
        this.accountTitleId = accountTitleId;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }
}
