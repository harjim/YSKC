package com.yskc.ms.service.rs;

import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.ms.entity.rs.RsPatentEntity;
import com.yskc.ms.entity.rs.RsPatentPlanEntity;
import com.yskc.ms.models.DataPermModel;
import com.yskc.ms.models.UserInfo;
import com.yskc.ms.models.patentPlan.ChangePatentNoModel;
import com.yskc.ms.models.patentPlan.PlanToPatentModel;
import com.yskc.ms.models.patentPlan.QueryPatentPlanModel;
import com.yskc.ms.models.patentPlan.RsPatentPlanModel;

import java.util.List;

/**
 * Created by hck
 * on 2020/7/7 8:17
 * description:
 */
public interface RsPatentPlanService {
    /**
     * 根据条件查询专利立项列表
     *
     * @param model
     * @param userInfo
     * @return
     */
    PageModel<List<RsPatentPlanModel>> queryPatentPlans(QueryPatentPlanModel model, UserInfo userInfo, DataPermModel perm);

    /**
     * 分配负责人
     *
     * @param ids
     * @param masterId
     * @return
     */
    Boolean allocationMaster(List<Integer> ids, UserInfo userInfo, Integer masterId) throws OwnerException;

    /**
     * 更新申请状态为完成或拒绝
     *
     * @param model
     * @param userInfo
     * @return
     */
    Boolean updateStatus(PlanToPatentModel model, UserInfo userInfo) throws OwnerException;

    RsPatentPlanEntity getById(Integer id, String patentNo);

    /**
     * 驳回专利
     *
     * @param models
     * @param userInfo
     * @return
     */
    Boolean rejectPatent(List<RsPatentPlanModel> models, UserInfo userInfo) throws OwnerException;

    /**
     * 更新专利号
     *
     * @param model
     * @param userInfo
     * @return
     * @throws OwnerException
     */
    Boolean updatePatentNo(ChangePatentNoModel model, UserInfo userInfo) throws OwnerException;

    /**
     * 上传资料更新
     *
     * @param patent
     * @return
     */
    Boolean update(RsPatentPlanEntity patent);

    /**
     * 判断是否审核通过
     *
     * @param patentIds
     * @return
     */
    void judgeAuditPass(List<Integer> patentIds) throws OwnerException;

    /**
     * 更新上传资料路径
     *
     * @param model
     * @return
     */
    Boolean updateFilePath(RsPatentPlanModel model, UserInfo userInfo) throws OwnerException;

    /**
     * 获取专利申请信息
     *
     * @param patentPlanId
     * @return
     */
    RsPatentPlanModel getPatentInfo(Integer patentPlanId);

    /**
     * 获取专利
     *
     * @param patentNo
     * @return
     */
    RsPatentEntity getByPatentNo(String patentNo);

    /**
     * 设置定稿名称
     *
     * @param model
     * @param userInfo
     * @return
     */
    Boolean setName(RsPatentPlanModel model, UserInfo userInfo) throws OwnerException;

}
