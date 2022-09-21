package com.yskc.ms.models.patent;

import java.io.Serializable;
import java.util.List;

/**
 * @BelongsProject: ms
 * @BelongsPackage: com.yskc.ms.models.patent
 * @Author: zhangdingfu
 * @CreateTime: 2020-01-02 14:48
 * @Description: 验证心跳
 */
public class PatentValidHeartModel implements Serializable {

    private Boolean hasSearch;

    private List<Integer> ids;

    public Boolean getHasSearch() {
        return hasSearch;
    }

    public void setHasSearch(Boolean hasSearch) {
        this.hasSearch = hasSearch;
    }

    public List<Integer> getIds() {
        return ids;
    }

    public void setIds(List<Integer> ids) {
        this.ids = ids;
    }
}
