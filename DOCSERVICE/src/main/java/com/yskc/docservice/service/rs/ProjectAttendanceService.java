package com.yskc.docservice.service.rs;

import com.yskc.common.exception.OwnerException;
import com.yskc.docservice.models.rs.RsUserInfo;
import com.yskc.docservice.models.rs.excel.ProjectAttendanceExcel;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * 研发人员费用
 *
 * @author huronghua
 */
public interface ProjectAttendanceService {
    /**
     * 删除已使用工时
     *
     * @param info
     * @param enumbers
     * @param monthBegin
     * @param monthEnd
     * @param projectId
     */
    void deleteUsed(RsUserInfo info, Set<String> enumbers, Date monthBegin, Date monthEnd, Integer projectId);

    /**
     * 导入研发考勤记录
     *
     * @param userInfo
     * @param data
     * @param month
     * @throws OwnerException
     */
    void importRdAttendance(RsUserInfo userInfo, List<ProjectAttendanceExcel> data,Date month)throws OwnerException;
}
