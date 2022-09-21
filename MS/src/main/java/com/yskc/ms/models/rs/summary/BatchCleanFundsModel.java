package com.yskc.ms.models.rs.summary;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @BelongsProject: ms
 * @BelongsPackage: com.yskc.ms.models.rs.summary
 * @Author: zhangdingfu
 * @CreateTime: 2021-04-24 10:28
 * @Description: 批量清除费用
 */
public class BatchCleanFundsModel {

    private Integer projectId;

    @NotNull(message = "请先选择数据。")
    @NotEmpty(message = "请先选择数据。")
    private List<Integer> ids;

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public List<Integer> getIds() {
        return ids;
    }

    public void setIds(List<Integer> ids) {
        this.ids = ids;
    }
}
