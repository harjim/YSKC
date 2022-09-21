package com.yskc.rs.models.rddept;

import javax.validation.constraints.NotNull;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.models.rddept
 * @Author: zhangdingfu
 * @CreateTime: 2021-06-01 14:09
 * @Description: 研发部门排序
 */
public class RdDeptSortModel {

    @NotNull(message = "排序失败，无法获取当前排序部门")
    private Integer id;
    @NotNull(message = "排序失败，未指定排序方向")
    private Boolean left;

    public void setId(Integer id) {
        this.id = id;
    }

    public void setLeft(Boolean left) {
        this.left = left;
    }

    public Integer getId() {
        return id;
    }

    public Boolean getLeft() {
        return left;
    }
}
