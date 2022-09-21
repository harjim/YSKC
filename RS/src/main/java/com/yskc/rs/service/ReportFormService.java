package com.yskc.rs.service;

import com.yskc.rs.models.reportform.IndexModel;
import com.yskc.rs.models.reportform.MonthReportModel;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.List;

public interface ReportFormService {
    IndexModel getIndex(Integer year, Date month, Integer companyId) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException;
    List<MonthReportModel> getMonthReport(Integer year, Integer companyId);
}
