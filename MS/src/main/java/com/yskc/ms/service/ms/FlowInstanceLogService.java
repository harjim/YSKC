package com.yskc.ms.service.ms;

import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.ms.models.flowInstance.FeeFlowInstanceLogModel;
import com.yskc.ms.models.params.FlowInstanceLogParams;
import com.yskc.ms.models.params.PageParams;
import com.yskc.ms.models.projectAudit.FlowInstanceLogModel;

import java.util.List;

public interface FlowInstanceLogService {

    PageModel<List<FlowInstanceLogModel>> getLogList(FlowInstanceLogParams flowInstanceLogParams);

    /**
     * 审核日志查询
     * @param instanceId
     * @return
     */
    List<FeeFlowInstanceLogModel> getAuditLog(Integer instanceId);

}
