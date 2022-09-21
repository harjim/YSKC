package com.yskc.rs.models.projectenergy;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yskc.common.utils.DateUtil;
import com.yskc.rs.models.UsedModel;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.models.projectenergy
 * @Author: zhangdingfu
 * @CreateTime: 2019-08-28 16:02
 * @Description: 项目能源损耗
 */
public class ProjectEnergyModel implements Serializable {
    /**
     *
     */
    private Integer id;
    /**
     *
     */
    private Integer energyDataId;

    @NotBlank(message = "能源名称不能为空")
    private String ename;
    /**
     *
     */
    @NotNull(message = "能源类型不能为空")
    private Integer type;

    @NotNull(message = "发生日期不能为空")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date occDate;

    /**
     *
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @NotNull(message = "单价不能为空")
    private BigDecimal unitPrice;
    /**
     *
     */
    @NotNull(message = "数量不能为空")
    private BigDecimal quantity;

    /**
     *
     */
    @NotNull(message = "单位不能为空")
    private String unit;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal rdAmount;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal amount;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal remainAmount;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal totalHour;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal rdHour;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal totalYield;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal rdYield;

    private String deptName;

    private String accNumber;

    private List<UsedModel> usedList;

    private BigDecimal totalAmount;

    private Integer projectId;

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Date getOccDateMonth() {
        return occDate != null ? DateUtil.getMonthFirstDay(occDate) : null;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setEname(String ename) {
        this.ename = ename;
    }

    public String getEname() {
        return ename;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getType() {
        return type;
    }

    public void setOccDate(Date occDate) {
        this.occDate = occDate;
    }

    public Date getOccDate() {
        return occDate;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getUnit() {
        return unit;
    }

    public BigDecimal getRdAmount() {
        return rdAmount;
    }

    public void setRdAmount(BigDecimal rdAmount) {
        this.rdAmount = rdAmount;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Integer getEnergyDataId() {
        return energyDataId;
    }

    public void setEnergyDataId(Integer energyDataId) {
        this.energyDataId = energyDataId;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getAccNumber() {
        return accNumber;
    }

    public void setAccNumber(String accNumber) {
        this.accNumber = accNumber;
    }

    public BigDecimal getRemainAmount() {
        return remainAmount;
    }

    public void setRemainAmount(BigDecimal remainAmount) {
        this.remainAmount = remainAmount;
    }

    public BigDecimal getTotalHour() {
        return totalHour;
    }

    public void setTotalHour(BigDecimal totalHour) {
        this.totalHour = totalHour;
    }

    public BigDecimal getRdHour() {
        return rdHour;
    }

    public void setRdHour(BigDecimal rdHour) {
        this.rdHour = rdHour;
    }

    public BigDecimal getTotalYield() {
        return totalYield;
    }

    public void setTotalYield(BigDecimal totalYield) {
        this.totalYield = totalYield;
    }

    public BigDecimal getRdYield() {
        return rdYield;
    }

    public void setRdYield(BigDecimal rdYield) {
        this.rdYield = rdYield;
    }

    public List<UsedModel> getUsedList() {
        return usedList;
    }

    public void setUsedList(List<UsedModel> usedList) {
        this.usedList = usedList;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }
}
