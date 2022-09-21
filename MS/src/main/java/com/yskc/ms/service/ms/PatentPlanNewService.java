package com.yskc.ms.service.ms;

import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.common.model.PageResult;
import com.yskc.ms.entity.ms.PatentPlanEntity;
import com.yskc.ms.models.DataPermModel;
import com.yskc.ms.models.UserInfo;
import com.yskc.ms.models.patent.PatentMasterModel;
import com.yskc.ms.models.patentPlan.*;
import com.yskc.ms.models.projectAudit.QueryAuditDataModel;
import com.yskc.ms.models.rs.RsProjectBaseModel;

import java.util.List;
import java.util.Map;

/**
 * @DateTime: 2022/2/22 8:10
 * @Description:
 * @author: hsx
 */
public interface PatentPlanNewService {

    /**
     * 获取专利申请相关信息
     *
     * @param patentPlanId
     * @param patentNo
     * @return
     */
    Map<String, Object> getInfo(Integer patentPlanId, String patentNo, Integer msUserId);

    /**
     * 保存专利
     *
     * @param model
     * @param userInfo
     * @return
     */
    Boolean savePatent(PatentInfoModel model, UserInfo userInfo) throws OwnerException;

    /**
     * 提交专利审核
     *
     * @param model
     * @param userInfo
     * @return
     */
    Boolean submitPatent(PatentPlanModel model, UserInfo userInfo) throws OwnerException;

    /**
     * 设置代理人
     *
     * @param models
     * @param userId
     * @return
     * @throws OwnerException
     */
    Boolean setMaster(List<PatentMasterModel> models, Integer userId, UserInfo userInfo) throws OwnerException;

    /**
     * 列表查询专利
     * @param query
     * @param dataPerm
     * @return
     */
    PageResult getList(QueryPatentPlanModel query, DataPermModel dataPerm);

    /**
     * 保存专利信息
     * @param model
     * @param userInfo
     * @return
     * @throws OwnerException
     */
    Boolean save(PatentPlanModel model, UserInfo userInfo) throws OwnerException;

    /**
     * 保存专利信息
     * @param model
     * @param userInfo
     * @return
     * @throws OwnerException
     */
    Boolean edit(PatentPlanModel model, UserInfo userInfo) throws OwnerException;

    /**
     * 删除专利
     * @param ids
     * @return
     * @throws OwnerException
     */
    Boolean del(List<Integer> ids) throws OwnerException;

    /**
     * 获取专利申请
     *
     * @param patentId
     * @param patentNo
     * @return
     */
    PatentPlanEntity getById(Integer patentId, String patentNo);

    /**
     * 获取专利审核列表
     *
     * @param query
     * @param userId
     * @return
     */
    PageModel<List<PatentPlanModel>> getPatentList(QueryAuditDataModel query, Integer userId);


    PatentPlanModel getPatentInfo(Integer patentPlanId) throws OwnerException;

    /**
     * 获取专利上传文件
     *
     * @param patentPlanId
     * @param patentNo
     * @return
     */
    Map<Integer, List<PatentFileModel>> getPatentFiles(Integer patentPlanId, String patentNo);

    /**
     * 分配专利申请工程师
     *
     * @param setEngineer
     * @param userId
     * @return
     */
    Boolean setEngineer(SetPatentEngineerModel setEngineer, Integer userId);

    /**
     * 检查专利号唯一
     *
     * @param patentNo
     * @param patentId
     * @return
     */
    Boolean checkPatentNo(String patentNo, Integer patentId) throws OwnerException;

    /**
     * 获取专利申请
     *
     * @param patentId
     * @return
     */
    PatentPlanModel getById(Integer patentId);

    /**
     * 获取专利申请项目下拉列表
     * @param companyId
     * @param year
     * @return
     */
    List<RsProjectBaseModel> getProjectSelect(Integer companyId, Integer year);
}
