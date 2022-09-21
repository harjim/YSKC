package com.yskc.ms.service.ms;

import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.ms.entity.ms.FlowEntity;
import com.yskc.ms.models.DataPermModel;
import com.yskc.ms.models.UserInfo;
import com.yskc.ms.models.flow.*;
import com.yskc.ms.models.role.AppRoleModel;

import java.util.List;

/**
 * Created by hck
 * on 2020/12/15 11:31
 * description:
 */
public interface FlowService {

    PageModel<List<FlowModel>> getList(QueryFlowModel query);

    /**
     * 添加流程
     *
     * @param entity
     * @return
     */
    Boolean addFlow(FlowEntity entity);

    /**
     * 保存流程
     *
     * @param models
     * @param userInfo
     * @return
     */
    List<FlowNodeTreeModel> saveFlow(List<FlowNodeTreeModel> models, UserInfo userInfo) throws OwnerException;

    /**
     * 获取流程信息
     *
     * @param flowId
     * @return
     */
    List<FlowNodeTreeModel> getFlowInfo(Integer flowId);

    /**
     * 设置节点用户
     *
     * @param model
     * @param userInfo
     * @return
     */
    Boolean setFlowUser(SetNodeUserModel model, UserInfo userInfo) throws OwnerException;

    /**
     * 删除流程
     *
     * @param flowId
     * @return
     */
    Boolean delFlow(Integer flowId) throws OwnerException;

    /**
     * 获取角色列表
     *
     * @param roleName
     * @return
     */
    List<AppRoleModel> getRoleList(String roleName);
}
