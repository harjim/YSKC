package com.yskc.ms.models;

import java.util.List;

/**
 * @BelongsProject: ms
 * @BelongsPackage: com.yskc.ms.models
 * @Author: zhangdingfu
 * @CreateTime: 2020-05-07 11:44
 * @Description: 批量删除id model
 */
public class BatchDeleteModel {
    private List<Integer> ids;

    public List<Integer> getIds() {
        return ids;
    }

    public void setIds(List<Integer> ids) {
        this.ids = ids;
    }
}
