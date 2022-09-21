package com.yskc.ms.service.ms;

import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.ms.entity.ms.FlowInstanceLog;
import com.yskc.ms.models.DataPermModel;
import com.yskc.ms.models.UserInfo;
import com.yskc.ms.models.policy.PolicySubscriptionModel;
import com.yskc.ms.models.projectAudit.BatchAuditModel;
import com.yskc.ms.models.projectAudit.ProjectAuditModel;
import com.yskc.ms.models.serviceApply.*;

import java.util.List;

public interface ServiceApplyService {
    /**
     * 根据条件查询服务申请
     * @param query
     * @param info
     * @return
     */
    PageModel<List<ServiceApplyModel>> getList(QueryApplyModel query, UserInfo info, DataPermModel dataPerm);

    /**
     * 根据条件查询移动端服务申请
     * @param query
     * @param info
     * @return
     */
    PageModel<List<ServiceApplyModel>> getMobileList(QueryMobileApply query, UserInfo info, DataPermModel dataPerm);

    /**
     * 查询服务申请各类人员
     * @param model
     * @return
     */
    SelectMemberModel getMemberList(QueryUserModel model);

    /**
     * 查询移动端服务申请各类人员
     * @param model
     * @return
     */
    MobileMemberModel getMobileMemberList(QueryUserModel model);

    /**
     * 添加服务申请
     * @param model
     * @param info
     * @return
     * @throws OwnerException
     */
    Boolean addServiceApply(ServiceApplyModel model, UserInfo info) throws OwnerException;

    /**
     * 查询服务申请公司名称
     * @param model
     * @return
     */
    List<ServiceCustomerModel> getCustomerList(QueryCustomerModel model);

    /**
     * getServiceInfo
     * @param serviceNo
     * @param serviceId
     * @param info
     * @return
     */
    ServiceApplyModel getApplyInfo(String serviceNo,Integer serviceId,UserInfo info);

    /**
     * 修改服务申请
     * @param model
     * @param info
     * @return
     * @throws OwnerException
     */
    Boolean editServiceApply(ServiceApplyModel model, UserInfo info) throws OwnerException;

    /**
     * 审核时修改服务申请
     * @param model
     * @param info
     * @return
     * @throws OwnerException
     */
    Boolean editAuditApply(ApplyEditModel model,UserInfo info) throws OwnerException;
    /**
     * 删除服务申请
     * @param ids
     * @return
     * @throws OwnerException
     */
    Boolean delServiceApply(List<Integer> ids) throws OwnerException;

    /**
     * 提交服务申请
     * @param model
     * @param info
     * @return
     * @throws OwnerException
     */
    Boolean submit(ServiceApplyModel model, UserInfo info) throws OwnerException;
}
