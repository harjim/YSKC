package com.yskc.docservice.enums;

import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.enums
 * @Author: zhangdingfu
 * @CreateTime: 2022-05-05 08:16
 * @Description: 审核研发类型枚举
 */
public enum AuditRdTypeEnum {
    /**
     * 人员费用
     */
    EMPLOYEE(10000, "人员费用", new Integer[]{10000, 10100}),
    EQUIPMENT(30000, "设备折旧", new Integer[]{30000, 30100}),
    MATERIAL(20000, "研发材料", new Integer[]{20000, 20001, 20002}),
    TRIAL(20300, "中间试制", new Integer[]{20301, 20303, 20304, 20302, 20300}),
    REPAIR(20600, "修理费用", new Integer[]{20601, 20600}),
    SAMPLE_MACHINE(20700, "样机费用", new Integer[]{20700}),
    STIMULUS(20100, "动力损耗", new Integer[]{20100, 20102, 20101}),
    FUEL(20200, "燃料损耗", new Integer[]{20200, 20201}),
    DESIGN(50000, "设计费用", new Integer[]{50000}),
    INSPECTION(20500, "检测费用", new Integer[]{20500}),
    TRAVEL(60400, "差旅费用", new Integer[]{60400}),
    AMORTIZATION(40000, "摊销费用", new Integer[]{40000, 40100, 40200, 40001}),
    OTHER(69900, "其他费用", new Integer[]{60000, 60100, 60200, 60300, 69900});

    private static final Map<Integer, AuditRdTypeEnum> childrenMap = new LinkedHashMap<>();

    static {
        for (AuditRdTypeEnum current : values()) {
            for (Integer child : current.children) {
                if (child != null) {
                    childrenMap.put(child, current);
                }

            }
        }
    }

    /**
     * 通过子类型获取rdType
     *
     * @param childType
     * @return
     */
    public static Integer getParent(Integer childType) {
        return childrenMap.containsKey(childType) ? childrenMap.get(childType).getRdType() : null;
    }

    /**
     * 通过多个子类获取rdType
     *
     * @param children
     * @return
     */
    public static Integer getParent(Collection<Integer> children) {
        if (CollectionUtils.isEmpty(children)) {
            return null;
        }
        for (Integer child : children) {
            if (childrenMap.containsKey(child)) {
                return childrenMap.get(child).getRdType();
            }
        }
        return null;
    }

    /**
     * 通过多个子类获取多个rdType
     *
     * @param children
     * @return
     */
    public static List<Integer> getParents(Collection<Integer> children) {
        if (CollectionUtils.isEmpty(children)) {
            return null;
        }
        return children.stream().filter(childrenMap::containsKey).map(a -> childrenMap.get(a).getRdType()).distinct().collect(Collectors.toList());
    }

    AuditRdTypeEnum(Integer rdType, String title, Integer[] children) {
        this.rdType = rdType;
        this.title = title;
        this.children = children;
    }

    private Integer rdType;

    private Integer[] children;

    private String title;

    public Integer getRdType() {
        return rdType;
    }

    public Integer[] getChildren() {
        return children;
    }

    public String getTitle() {
        return title;
    }

}

