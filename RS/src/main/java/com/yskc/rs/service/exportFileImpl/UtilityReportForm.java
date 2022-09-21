package com.yskc.rs.service.exportFileImpl;

import com.yskc.common.utils.JsonUtils;
import com.yskc.rs.dao.SummaryDao;
import com.yskc.rs.entity.project.ProjectEntity;
import com.yskc.rs.models.document.DataModel;
import com.yskc.rs.service.ExportDocFileService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * @DateTime: 2022/4/24 16:25
 * @Description:
 * @author: hsx
 */

@Component
public class UtilityReportForm implements ExportDocFileService {

    @Autowired
    private SummaryDao summaryDao;

    public static UtilityReportForm utilityReportForm;

    @PostConstruct
    public void init() {
        utilityReportForm = this;
        utilityReportForm.summaryDao = this.summaryDao;
    }

    @Override
    public Map exportDocFile(ProjectEntity projectEntity, Integer currentYear, String fileData, DataModel dataModel) {
        Map map = new HashMap();
        if (!StringUtils.isEmpty(fileData)) {
            map = JsonUtils.jsonToPojo(fileData, Map.class);
        }
        BigDecimal projectRdFundsSum = summaryDao.getProjectRdFundsSum(projectEntity.getId());
        map.put("rdFundsSum",null == projectRdFundsSum?BigDecimal.ZERO:projectRdFundsSum);
        return map;
    }
}
