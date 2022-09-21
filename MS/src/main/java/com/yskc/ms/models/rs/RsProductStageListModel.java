package com.yskc.ms.models.rs;

import java.io.Serializable;

/**
 * Created by hck
 * on 2020/10/14 9:06
 * description:阶段文件类型配置model
 */
public class RsProductStageListModel implements Serializable {

    private Integer id;
    private Integer stageId;//阶段id
    private String itemName;//资料名
    private Integer itemType;// 资料类型 0: 交付，1： 对接
    private String pattern;//文件类型
    private String remark;//说明/备注
    private Boolean required;//是否必须
    private Integer directionId;
    private Integer seq;
    private Boolean limitChange;//true :可删除 false:不能删除

    public Boolean getLimitChange() {
        return limitChange;
    }

    public void setLimitChange(Boolean limitChange) {
        this.limitChange = limitChange;
    }

    public Integer getDirectionId() {
        return directionId;
    }

    public void setDirectionId(Integer directionId) {
        this.directionId = directionId;
    }

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getStageId() {
        return stageId;
    }

    public void setStageId(Integer stageId) {
        this.stageId = stageId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Integer getItemType() {
        return itemType;
    }

    public void setItemType(Integer itemType) {
        this.itemType = itemType;
    }

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Boolean getRequired() {
        return required;
    }

    public void setRequired(Boolean required) {
        this.required = required;
    }
}
