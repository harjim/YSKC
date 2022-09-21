package com.xxl.job.executor.utils;

import com.xxl.job.executor.core.config.Constant;
import com.xxl.job.executor.entity.ms.PatentDataEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * @BelongsProject: xxl-job
 * @BelongsPackage: com.xxl.job.executor.utils
 * @Author: zhangdingfu
 * @CreateTime: 2020-09-09 15:19
 * @Description: 专利工具类
 */
public class PatentUtils extends WebClientUtils {

    public String queryWord;

    public String getQueryWord() {
        return queryWord;
    }

    public void setQueryWord(String queryWord) {
        this.queryWord = queryWord;
    }

    public List<PatentDataEntity> getDataList() {
        return dataList;
    }

    public void setDataList(List<PatentDataEntity> dataList) {
        this.dataList = dataList;
    }

    public List<PatentDataEntity> dataList = new ArrayList<>();

    public Boolean checkSize() {
        return dataList.size() >= Constant.MAX_ROW_DATA;
    }

    public void clearDataList() {
        dataList.clear();
    }

    public PatentUtils(String queryWord) {
        super();
        this.queryWord = queryWord;
    }

    public PatentUtils(String queryWord, Boolean noJs) {
        super(noJs);
        this.queryWord = queryWord;
    }

}
