package com.yskc.ms.service.rs;

import com.yskc.ms.models.rs.CheckModel;
import com.yskc.ms.models.rs.ReportCheckModel;
import com.yskc.ms.models.rs.RsProjectBaseModel;

import java.util.List;
import java.util.Map;

public interface ProjectCheckService {
    Map<Integer, List<CheckModel<RsProjectBaseModel>>> getDuplicateNameList(Integer[] ids, Double limitRate);

    Map<Integer, List<CheckModel<ReportCheckModel>>> getDuplicateReportList(Integer[] projectIds, Double limitRate);
}
