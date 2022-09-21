package com.yskc.ms.models.finaDaily;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

/**
 * @BelongsProject:
 * @BelogsPackage: src/main/java/com/yskc/ms/models/finaDaily
 * @Author: hm
 * @CreateTime: 2022/9/7
 * @Description: 保存、暂存接受模板
 */
public class SaveFinaDailyModel implements Serializable {

    /**
     * 日报id
     */
    private Integer id;

    /**
     * 客户id
     */
    @NotNull(message = "客户不可为空")
    private Integer customerId;

    /**
     * 客户名称
     */
    private String companyName;

    /**
     * 业务员id
     */
    @NotNull(message = "业务员不可为空")
    private Integer ownerId;

    /**
     * 所属部门id
     */
    private Integer deptId;

    /**
     * 工作日期
     */
    @NotNull(message = "工作日期不可为空")
    private Date workDate;

    /**
     * 事项
     */
    @NotNull(message = "事项不可为空")
    private Integer itemType;

    /**
     * 事项内容
     */
    private String itemTypeValue;

    /**
     * 内容
     */
    @NotBlank(message = "内容不可为空")
    @Size(max = 1000, message = "内容最大长度为1000")
    private String content;

    /**
     * 附件
     */
    private String filepath;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Integer getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Integer ownerId) {
        this.ownerId = ownerId;
    }

    public Integer getDeptId() {
        return deptId;
    }

    public void setDeptId(Integer deptId) {
        this.deptId = deptId;
    }

    public Date getWorkDate() {
        return workDate;
    }

    public void setWorkDate(Date workDate) {
        this.workDate = workDate;
    }

    public Integer getItemType() {
        return itemType;
    }

    public void setItemType(Integer itemType) {
        this.itemType = itemType;
    }

    public String getItemTypeValue() {
        return itemTypeValue;
    }

    public void setItemTypeValue(String itemTypeValue) {
        this.itemTypeValue = itemTypeValue;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }
}
