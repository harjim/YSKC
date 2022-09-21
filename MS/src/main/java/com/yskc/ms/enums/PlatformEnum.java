package com.yskc.ms.enums;

/**
 * @BelongsProject: ms
 * @BelongsPackage: com.yskc.ms.enums
 * @Author: zhangdingfu
 * @CreateTime: 2022-06-08 10:13
 * @Description: 平台枚举
 */
public enum PlatformEnum {

    /**
     * 电脑端
     */
    PC(0, "电脑端"),
    MOBILE(1,"移动端");
    private int platform;
    private String name;

    PlatformEnum(int platform, String name) {
        this.platform = platform;
        this.name = name;
    }

    public static Boolean isMobile(Integer platform) {
        return platform !=null && platform == MOBILE.platform;
    }

    public int getPlatform() {
        return platform;
    }

    public String getName() {
        return name;
    }
}
