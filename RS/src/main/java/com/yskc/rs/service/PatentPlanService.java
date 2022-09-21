package com.yskc.rs.service;

import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.rs.entity.PatentPlanEntity;
import com.yskc.rs.models.PatentPlan.AddPatentPlanModel;
import com.yskc.rs.models.PatentPlan.PatentOpinionModel;
import com.yskc.rs.models.PatentPlan.PatentPlanModel;
import com.yskc.rs.models.PatentPlan.QueryPatentPlanModel;
import com.yskc.rs.models.UserInfo;

import java.util.List;

/**
 * Created by hck
 * on 2020/7/6 10:46
 * description:
 */
public interface PatentPlanService {

    PageModel<List<PatentPlanModel>> queryPatentPlan(QueryPatentPlanModel model, UserInfo userInfo);

    Boolean addPatentPlan(AddPatentPlanModel model, UserInfo userInfo) throws OwnerException;

    Boolean delPatentPlan(PatentPlanModel model,UserInfo userInfo) throws OwnerException;

    Boolean editPatentPlan(AddPatentPlanModel model,UserInfo userInfo) throws OwnerException;

    PatentPlanEntity getFilePath(Integer id);

    /**
     * 专利交底书提交审核
     * @param info
     * @param ids
     * @return
     */
    Boolean submitPatents(UserInfo info, List<Integer> ids) throws OwnerException;

    /**
     * 获取专利意见列表
     * @param patentPlanId
     * @return
     */
    List<PatentOpinionModel> getPatentOpinions(Integer patentPlanId,String patentNo) throws OwnerException;

    /**
     * 保存专利意见
     * @param model
     * @param userInfo
     * @return
     */
    Boolean saveOpinion(PatentOpinionModel model, UserInfo userInfo) throws OwnerException;

    /**
     * 删除专利资料意见
     * @param opinionIds
     * @return
     */
    Boolean delOpinions(List<Integer> opinionIds);
    /**
     * 获取专利申请
     * @param id
     * @param patentNo
     * @return
     */
    PatentPlanEntity getPatent(Integer id, String patentNo);
}
