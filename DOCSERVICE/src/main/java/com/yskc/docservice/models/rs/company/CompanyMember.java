package com.yskc.docservice.models.rs.company;

import com.yskc.docservice.config.Constant;
import org.springframework.util.StringUtils;

/**
 * 成员公司对象
 * @author huronghua
 */
public class CompanyMember {
    /**
     * 公司ID
     */
    private Integer companyId;
    /**
     * 公司名称
     */
    private String companyName;
    private String companyLogoPath;
    private Boolean selected;
    private Integer companyType;
    private Integer groupId;
    private String fullPath;

    public Boolean getSelected() {
        return selected;
    }

    public void setSelected(Boolean selected) {
        this.selected = selected;
    }

    public String getCompanyLogoPath() {
        return companyLogoPath;
    }

    public void setCompanyLogoPath(String companyLogoPath) {
        this.companyLogoPath = companyLogoPath;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Integer getCompanyType() {
        return companyType;
    }

    public void setCompanyType(Integer companyType) {
        this.companyType = companyType;
    }

    public CompanyMember() {
    }

    public CompanyMember(Integer companyId, String companyName) {
        this.companyId = companyId;
        this.companyName = companyName;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public String getFullPath() {
        return StringUtils.hasLength(fullPath) ? fullPath : companyId+ Constant.PATH_SEPARATOR;
    }

    public void setFullPath(String fullPath) {
        this.fullPath = fullPath;
    }
}
