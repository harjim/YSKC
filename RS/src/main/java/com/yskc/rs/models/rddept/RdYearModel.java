package com.yskc.rs.models.rddept;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.models.rddept
 * @Author: zhangdingfu
 * @CreateTime: 2019-12-10 15:44
 * @Description: 年份model
 */
public class RdYearModel implements Serializable {

    @NotNull(message = "无法获取当前操作年份")
    private Integer currentYear;
    @NotNull(message = "导入年份不能为空")
    private Integer chYear;

    public Integer getCurrentYear() {
        return currentYear;
    }

    public void setCurrentYear(Integer currentYear) {
        this.currentYear = currentYear;
    }

    public Integer getChYear() {
        return chYear;
    }

    public void setChYear(Integer chYear) {
        this.chYear = chYear;
    }
}
