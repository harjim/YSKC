package com.yskc.rs.enums;

/**
 * @BelongsProject: ms
 * @BelongsPackage: com.yskc.ms.enums
 * @Author: blueToWhite
 * @CreateTime: 2020-05-15 13:41
 * @Description: rs项目状态枚举
 */
@Deprecated
public enum RsProjectStatusEnum {
    /**
     * 草稿
     */
    DEFAULT(0, "草稿"),
    SUBMIT(1, "提交"),
    REFUSE(2, "拒绝"),
    FINALIZATION(3, "审核"),
    RECALL(4, "撤回");

    RsProjectStatusEnum(int status, String name) {
        this.status = status;
        this.name = name;
    }

    private int status;

    private String name;

    public int getStatus() {
        return status;
    }

    public String getName() {
        return name;
    }

    /**
     * 通过状态获取
     *
     * @param status
     * @return
     */
    public static RsProjectStatusEnum getByStatus(Integer status) {
        if (null == status) {
            return DEFAULT;
        }
        for (RsProjectStatusEnum statusEnum : values()) {
            if (statusEnum.getStatus() == status) {
                return statusEnum;
            }
        }
        return DEFAULT;
    }

    /**
     * 通过名称获取
     *
     * @param name
     * @return
     */
    public static RsProjectStatusEnum getByName(String name) {
        for (RsProjectStatusEnum statusEnum : values()) {
            if (statusEnum.getName().equals(name)) {
                return statusEnum;
            }
        }
        return DEFAULT;
    }
}
