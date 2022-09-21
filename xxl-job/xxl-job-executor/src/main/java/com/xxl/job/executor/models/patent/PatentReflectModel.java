package com.xxl.job.executor.models.patent;

import com.xxl.job.executor.entity.ms.PatentDataEntity;

/**
 * @BelongsProject: xxl-job
 * @BelongsPackage: com.xxl.job.executor.models.patent
 * @Author: zhangdingfu
 * @CreateTime: 2020-09-19 09:31
 * @Description: 专利映射
 */
public class PatentReflectModel {

    public PatentDataEntity reflectEntity() {
        return new PatentDataEntity();
    }


    public void setCaseStatus(String caseStatus) {
    }

    public void setQueryWord(String queryWord) {
    }

}
