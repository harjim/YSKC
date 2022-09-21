package com.yskc.rs.service.impl;

import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yskc.common.model.PageModel;
import com.yskc.common.utils.PageUtils;
import com.yskc.rs.dao.AnnualReportDao;
import com.yskc.rs.entity.AnnualReportEntity;
import com.yskc.rs.models.sysDocument.SysDocumentModel;
import com.yskc.rs.service.AnnualReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnnualReportServiceImpl implements AnnualReportService {
    @Autowired
    AnnualReportDao annualReportDao;

    /**
     *
     * @param entity
     * @return
     */
    @Override
    public boolean insertFile(AnnualReportEntity entity) {

        return annualReportDao.insert(entity)>0;
    }

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
    @Override
    public PageModel<List<SysDocumentModel>> queryDocument(String fileName, int fileType, String year, int companyId, int pageNo, int pageSize) {
        Pagination pagination = new Pagination(pageNo, pageSize);
        return PageUtils.build(pagination, annualReportDao.queryDocument(pagination, fileName, fileType, year, companyId));
    }

    /**
     *
     * @param model
     * @return
     */
    @Override
    public boolean delFile(AnnualReportEntity model) {
        return annualReportDao.deleteById(model.getId())>0;
    }
}
