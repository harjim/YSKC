package com.yskc.ms.models.customer;

/**
 * @BelongsProject: ms
 * @BelongsPackage: com.yskc.ms.models.customer
 * @Author: zhangdingfu
 * @CreateTime: 2020-05-21 15:04
 * @Description: 公司
 */
public class MiniCompanyModel {

    private Integer id;

    private Integer companyId;

    private String title;

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
