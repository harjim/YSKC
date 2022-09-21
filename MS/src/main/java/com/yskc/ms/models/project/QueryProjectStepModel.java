package com.yskc.ms.models.project;

/**
 * Created by hck
 * on 2020/5/19 17:24
 */
public class QueryProjectStepModel {

    private Integer id;
    /**
     * 父节点id
     */
    private Integer parentId;
    /**
     * 步骤名称
     */
    private String  stepName;
    /**
     * 步骤类型
     */
    private Integer stepType;
    /**
     * 序号
     */
    private Integer seq;
    /**
     * 项目类型id
     */

    private Integer productId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getStepName() {
        return stepName;
    }

    public void setStepName(String stepName) {
        this.stepName = stepName;
    }

    public Integer getStepType() {
        return stepType;
    }

    public void setStepType(Integer stepType) {
        this.stepType = stepType;
    }

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }
}
