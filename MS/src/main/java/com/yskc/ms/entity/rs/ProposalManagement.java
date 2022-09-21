package com.yskc.ms.entity.rs;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.yskc.ms.entity.rs.models.RsBaseEntity;

/**
 * @BelongsProject: ms
 * @BelongsPackage: com.yskc.ms.entity.rs
 * @Author: zhangdingfu
 * @CreateTime: 2021-09-08 14:45
 * @Description: 提案管理
 */
@TableName("proposal_management")
public class ProposalManagement extends RsBaseEntity {

    @TableId
    private Integer id;
    private Integer companyId;
    private Integer year;
    private String title;
    private String filePath;
    private String remark;
    private Integer type;

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

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
