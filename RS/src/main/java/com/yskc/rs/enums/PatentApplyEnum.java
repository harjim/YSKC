package com.yskc.rs.enums;

/**
 * Created by hck
 * on 2020/7/6 10:18
 * description:专利申请状态
 */
public enum PatentApplyEnum {

    WAITING(0,"待申请"),
    APPLYING(1,"申请中"),
    APPLIED(2,"已申请"),
    REFUSE(3,"已驳回");
    private Integer status;
    private String description;

    public Integer getStatus() {
        return status;
    }

    public String getDescription() {
        return description;
    }

    PatentApplyEnum(Integer status, String description){
        this.status=status;
        this.description=description;
    }

    public static int getType(String description) {
        description = description.trim();
        for (PatentApplyEnum patentApplyEnum : values()) {
            if (patentApplyEnum.description.equals(description)) {
                return patentApplyEnum.status;
            }
        }
        return WAITING.status;
    }
}
