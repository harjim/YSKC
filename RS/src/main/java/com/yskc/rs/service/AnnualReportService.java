package com.yskc.rs.service;

import com.yskc.common.model.PageModel;
import com.yskc.rs.entity.AnnualReportEntity;
import com.yskc.rs.models.sysDocument.SysDocumentModel;

import java.util.List;
/**
 * @author Administrator
 */
public interface AnnualReportService {
    /**
     *
     * @param entity
     * @return
     */
    boolean insertFile(AnnualReportEntity entity);

    /**
     *
     * @param fileName
     * @param fileType
     * @param year
     * @param companyId
     * @param pageNo
     * @param pageSize
     * @return
     */
    PageModel<List<SysDocumentModel>> queryDocument(String fileName, int fileType, String year, int companyId, int pageNo, int pageSize);

    /**
     *
     * @param model
     * @return
     */
    boolean delFile(AnnualReportEntity model);
}
