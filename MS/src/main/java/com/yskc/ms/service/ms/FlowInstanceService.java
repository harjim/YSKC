package com.yskc.ms.service.ms;

import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.ms.models.DataPermModel;
import com.yskc.ms.models.FormAuditModel;
import com.yskc.ms.models.UserInfo;
import com.yskc.ms.models.flow.FlowListModel;
import com.yskc.ms.models.flow.QueryFlowModel;
import com.yskc.ms.models.flow.SetFlowMasterModel;
import com.yskc.ms.models.flowInstance.FlowInstanceActivateModel;
import com.yskc.ms.models.flowInstance.FlowInstanceInfoModel;
import com.yskc.ms.models.flowInstance.FlowInstanceModel;
import com.yskc.ms.models.params.MyAuditParams;
import com.yskc.ms.models.projectAudit.BatchAuditModel;
import com.yskc.ms.models.projectAudit.ProjectAuditModel;

import java.util.List;

public interface FlowInstanceService {

    PageModel<List<FlowInstanceModel>> getList(MyAuditParams params, Integer userId);

    /**
     * 获取实例信息
     *
     * @param id
     * @param type
     * @return
     */
    FlowInstanceInfoModel getInstanceInfo(Integer id, Integer type);

    /**
     * 获取流程列表
     *
     * @param query
     * @param dataPerm
     * @return
     */
    PageModel<List<FlowListModel>> search(QueryFlowModel query, DataPermModel dataPerm) throws OwnerException;

    /**
     * 设置流程代理人
     *
     * @param model
     * @return
     */
    Boolean deliverMaster(SetFlowMasterModel model,UserInfo info) throws OwnerException;

    /**
     * 批量激活
     *
     * @param model
     * @param info
     * @return
     * @throws OwnerException
     */
    Boolean activates(FlowInstanceActivateModel model, UserInfo info) throws OwnerException;

    /**
     * 批量撤回或取消审核
     * @param userId
     * @param nodeStatus
     * @param constant
     * @param batchAudit
     * @return
     * @throws OwnerException
     */
    boolean cancelOrRecall(Integer userId, Integer nodeStatus, String constant, BatchAuditModel batchAudit) throws OwnerException;
    /**
     * 提交后返回flowInstance_Form.Id
     * @param modelId
     * @param userId
     * @param moduleId
     * @return
     */
    Integer submitForm(Integer modelId,Integer userId,Integer moduleId) throws OwnerException;

    /**
     * 审核服务流程
     * @param model
     * @param info
     * @return
     * @throws OwnerException
     */
    Boolean audit(FormAuditModel model, UserInfo info) throws OwnerException;
}
