package com.yskc.ms.service.impl.rs.exportFileImpl;

import com.yskc.ms.dao.rs.DocListDao;
import com.yskc.ms.entity.rs.ProjectEntity;
import com.yskc.ms.models.rs.DataModel;
import com.yskc.ms.models.rs.NewReportsModel;
import com.yskc.ms.service.rs.ExportDocFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: hck
 * @DateTime: 2021/5/10 11:28
 * @Description:查新报告模板
 */
@Component
public class NewReportForm implements ExportDocFileService {

    @Autowired
    private DocListDao docListDao;

    public static NewReportForm newReportForm;

    @PostConstruct
    public void init() {
        newReportForm = this;
        newReportForm.docListDao = this.docListDao;
    }


    @Override
    public Map<String, Object> exportDocFile(ProjectEntity projectEntity, Integer currentYear, String fileData, DataModel dataModel) {
        Map<String, Object> dataMap = new HashMap<>();
        List<NewReportsModel> models = docListDao.getReports(projectEntity.getId());
        dataMap.put("newReports", models);
        return dataMap;
    }
}
