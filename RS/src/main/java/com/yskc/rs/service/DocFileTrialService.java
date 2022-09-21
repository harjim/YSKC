package com.yskc.rs.service;

import com.yskc.rs.models.UserInfo;
import com.yskc.rs.models.trialprod.DocFileTrialModel;

import java.util.List;

/**
 * Created by hck
 * on 2020/7/3 14:38
 * description:
 */
public interface DocFileTrialService {
    /**
     * 添加已使用的试制
     * @param userInfo
     * @param model
     * @return
     */
    Boolean addDocFileTrial(UserInfo userInfo, List<DocFileTrialModel> model);

    /**
     * 删除文档试制
     * @param userInfo
     * @param trialId
     * @param docFileId
     * @return
     */
   Boolean delDocFileTrial(UserInfo userInfo,Integer trialId,Integer docFileId);
}
