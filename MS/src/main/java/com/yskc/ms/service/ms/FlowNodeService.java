package com.yskc.ms.service.ms;

import com.yskc.common.exception.OwnerException;
import com.yskc.ms.models.UserInfo;
import com.yskc.ms.models.flow.FlowNodeModel;
import com.yskc.ms.models.flow.FlowNodeUserModel;
import com.yskc.ms.models.flow.SetNodeUserModel;

import java.util.List;
import java.util.Map;

/**
 * Created by hck
 * on 2020/12/3 8:49
 * description:流程管理
 */
public interface FlowNodeService {
    /**
     * 获取流程节点
     * @param flowId
     * @return
     */
    List<FlowNodeModel> getNodes(Integer flowId);

    /**
     * 获取节点用户列表
     * @param flowId
     * @return
     */
    Map<Integer,List<FlowNodeUserModel>> getNodeUser(Integer flowId);


    /**
     * 添加流程节点
     * @param model
     * @return
     */
    Boolean addFlowNode(FlowNodeModel model,UserInfo userInfo) throws OwnerException;
}
