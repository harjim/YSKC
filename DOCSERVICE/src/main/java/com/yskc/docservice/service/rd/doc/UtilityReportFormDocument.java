package com.yskc.docservice.service.rd.doc;

import com.yskc.docservice.dao.rs.SummaryDao;
import com.yskc.docservice.entity.rs.project.ProjectEntity;
import com.yskc.docservice.service.rd.RDDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * @DateTime: 2022/4/24 16:25
 * @Description:
 * @author: hsx
 */

@Component("UtilityReportForm_Doc")
@Scope("prototype")
public class UtilityReportFormDocument extends RDDocument {

    @Autowired
    private SummaryDao summaryDao;

    @Override
    protected Map getDocMap(ProjectEntity project, Integer currentYear) {
        Map map = new HashMap();
        BigDecimal projectRdFundsSum = summaryDao.getProjectRdFundsSum(project.getId());
        map.put("rdFundsSum",null == projectRdFundsSum?BigDecimal.ZERO:projectRdFundsSum);
        return map;
    }
}
