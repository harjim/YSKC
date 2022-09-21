package com.xxl.job.executor.models.patent;

import com.gargoylesoftware.htmlunit.util.NameValuePair;

import java.util.ArrayList;
import java.util.List;

/**
 * @BelongsProject: xxl-job
 * @BelongsPackage: com.xxl.job.executor.models.patent
 * @Author: zhangdingfu
 * @CreateTime: 2020-09-21 14:51
 * @Description: baiten
 */
public class BaiTenParamsModel {


    public final static List<NameValuePair> BAI_TEN_PARAMS = new ArrayList<>();

    static {
        BAI_TEN_PARAMS.add(new NameValuePair("sc", "35184372088831"));
        BAI_TEN_PARAMS.add(new NameValuePair("sortField", "ad"));
        BAI_TEN_PARAMS.add(new NameValuePair("fq", ""));
        BAI_TEN_PARAMS.add(new NameValuePair("pageSize", "100"));
        BAI_TEN_PARAMS.add(new NameValuePair("type", "s"));
        BAI_TEN_PARAMS.add(new NameValuePair("merge", "no-merge"));
        BAI_TEN_PARAMS.add(new NameValuePair("sort", "asc"));
    }

    public static List<NameValuePair> baiTenNextPage(String query, Integer page) {
        List<NameValuePair> params = new ArrayList<>();
        params.add(new NameValuePair("q", query));
        params.add(new NameValuePair("pageIndex", page.toString()));
        params.addAll(BAI_TEN_PARAMS);
        return params;
    }
}
