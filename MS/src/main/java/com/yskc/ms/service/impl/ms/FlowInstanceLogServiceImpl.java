package com.yskc.ms.service.impl.ms;

import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yskc.common.enums.FlowInstanceStatusEnum;
import com.yskc.common.model.PageModel;
import com.yskc.common.utils.PageUtils;
import com.yskc.ms.dao.ms.FlowInstanceDao;
import com.yskc.ms.dao.ms.FlowInstanceLogDao;
import com.yskc.ms.models.flowInstance.FeeFlowInstanceLogModel;
import com.yskc.ms.models.params.FlowInstanceLogParams;
import com.yskc.ms.models.projectAudit.FlowInstanceLogModel;
import com.yskc.ms.service.ms.FlowInstanceLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class FlowInstanceLogServiceImpl implements FlowInstanceLogService {

    @Autowired
    private FlowInstanceLogDao flowInstanceLogDao;
    @Autowired
    private FlowInstanceDao flowInstanceDao;

    @Override
    public PageModel<List<FlowInstanceLogModel>> getLogList(FlowInstanceLogParams flowInstanceLogParams) {
        Pagination pagination = flowInstanceLogParams.getPagination();
        if (CollectionUtils.isEmpty(pagination.getDescs()) && CollectionUtils.isEmpty(pagination.getAscs())) {
            List<String> desc = new ArrayList<>();
            desc.add("createTime");
            pagination.setDescs(desc);
        }
        return PageUtils.build(pagination, flowInstanceLogDao.getLogList(pagination,flowInstanceLogParams));
    }

    @Override
    public List<FeeFlowInstanceLogModel> getAuditLog(Integer instanceId){
        List<FeeFlowInstanceLogModel> flowInstanceLogModelList = new ArrayList<>();
        flowInstanceLogModelList.addAll(flowInstanceLogDao.getAuditLog(instanceId));
        flowInstanceLogModelList.addAll(flowInstanceDao.getAuditFlow(instanceId,FlowInstanceStatusEnum.ONGOING.getStatus()));

        return flowInstanceLogModelList;
    }
}
