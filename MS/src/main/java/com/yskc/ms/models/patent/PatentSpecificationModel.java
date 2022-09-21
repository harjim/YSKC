package com.yskc.ms.models.patent;

/**
 * @BelongsProject: ms
 * @BelongsPackage: com.yskc.ms.models.patent
 * @Author: zhangdingfu
 * @CreateTime: 2022-08-29 11:28
 * @Description: 专利权利要求内容
 */
public class PatentSpecificationModel {
    private String claimContent;
    private String specification;
    private Integer id;

    public String getClaimContent() {
        return claimContent;
    }

    public void setClaimContent(String claimContent) {
        this.claimContent = claimContent;
    }

    public String getSpecification() {
        return specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
