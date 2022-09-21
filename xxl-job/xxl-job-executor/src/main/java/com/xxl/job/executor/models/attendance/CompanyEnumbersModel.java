package com.xxl.job.executor.models.attendance;

import java.util.HashSet;
import java.util.Set;

/**
 * @BelongsProject: xxl-job
 * @BelongsPackage: com.xxl.job.executor.models.attendance
 * @Author: zhangdingfu
 * @CreateTime: 2020-12-29 08:48
 * @Description: 公司工号model
 */
public class CompanyEnumbersModel {
    private Integer companyId;
    private Set<String> enumbers;

    public static CompanyEnumbersModel build(Integer companyId) {
        CompanyEnumbersModel companyEnumbers = new CompanyEnumbersModel();
        companyEnumbers.companyId = companyId;
        companyEnumbers.enumbers = new HashSet<>();
        return companyEnumbers;
    }

    public Integer getCompanyId() {
        return companyId;
    }
    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }
    public Set<String> getEnumbers() {
        return enumbers;
    }
    public void setEnumbers(Set<String> enumbers) {
        this.enumbers = enumbers;
    }
}
