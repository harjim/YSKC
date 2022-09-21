package com.yskc.docservice.service.rd.doc;

import cn.hutool.core.date.DateUtil;
import com.yskc.docservice.dao.rs.project.ProjectYearInfoDao;
import com.yskc.docservice.entity.rs.project.ProjectEntity;
import com.yskc.docservice.entity.rs.project.ProjectYearInfoEntity;
import com.yskc.docservice.service.rd.RDDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @DateTime: 2022/4/20 15:28
 * @Description:立项报告模板
 * @author: hsx
 */

@Component("ResolutionForm_Doc")
@Scope("prototype")
public class ResolutionFormDocument extends RDDocument {

    @Autowired
    private ProjectYearInfoDao projectYearInfoDao;

    @Override
    protected Map getDocMap() throws IOException {
        Map resultMap = new HashMap<>();
        ProjectEntity projectInfo = dataFactory.getProjectInfo();
        String beginDate = MessageFormat.format("{0, date, yyyy年MM月}", projectInfo.getBeginDate());
        String endDate = MessageFormat.format("{0, date, yyyy年MM月}", projectInfo.getEndDate());
        resultMap.put("beginDate",beginDate);
        resultMap.put("endDate",endDate);
        resultMap.put("budget", dataFactory.getTotalBudget());
        return resultMap;
    }

    @Override
    protected Map getDocMap(ProjectEntity project, Integer currentYear) {
        Map<String,Object> map=new HashMap<>();
        String beginDate= DateUtil.format(project.getBeginDate(),"yyyy年MM月");
        String endDate= DateUtil.format(project.getEndDate(),"yyyy年MM月");
        map.put("beginDate",beginDate);
        map.put("endDate",endDate);
        List<ProjectYearInfoEntity> yearInfos=projectYearInfoDao.getInfoByYear(project.getId(),project.getEndYear());

        BigDecimal totalBudget=BigDecimal.ZERO;
        if(!CollectionUtils.isEmpty(yearInfos)){
            for (ProjectYearInfoEntity pyInfo:yearInfos){
                totalBudget=totalBudget.add(pyInfo.getBudget());
            }
        }
        map.put("budget",totalBudget);
        return map;
    }
}
