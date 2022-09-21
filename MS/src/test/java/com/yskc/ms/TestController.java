package com.yskc.ms;


import com.yskc.common.exception.OwnerException;
import com.yskc.common.utils.DateUtil;
import com.yskc.ms.dao.rs.CompanyGroupDao;
import com.yskc.ms.entity.rs.CompanyGroupEntity;
import com.yskc.ms.models.customer.CustomerTraceModel;
import com.yskc.ms.models.flowInstance.FeeFlowInstanceLogModel;
import com.yskc.ms.models.projectsummary.QuerySummaryMonthModel;
import com.yskc.ms.models.projectsummary.SummaryDataMonthModel;
import com.yskc.ms.service.ms.CustomerService;
import com.yskc.ms.service.ms.FlowInstanceLogService;
import com.yskc.ms.service.rs.SummaryService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @program: ms
 * @description: 测试
 * @author: cyj
 * @create: 2022-04-22 17:22
 **/
@SpringBootTest
@ContextConfiguration
@RunWith(SpringRunner.class)
public class TestController {
    /*@Autowired
    private SummaryService summaryService;
    @Autowired
    private FlowInstanceLogService flowInstanceLogService;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private CompanyGroupDao companyGroupDao;
    @Test
    public void getMonthFee(){
        QuerySummaryMonthModel query = new QuerySummaryMonthModel();
        query.setMonth(DateUtil.parse("2019-06-01","yyyy-MM-dd"));
        query.setCompanyId(22);
        List<SummaryDataMonthModel> summaryDataMonthModelList = summaryService.getMonthFee(query);
        for (SummaryDataMonthModel s : summaryDataMonthModelList) {
            System.out.println(s.getType());
            System.out.println(s.getK10000());
            System.out.println(s.getK20000());
            System.out.println(s.getK20100());
            System.out.println(s.getK20200());
            System.out.println(s.getK20300());
            System.out.println(s.getK20500());
            System.out.println(s.getK20600());
            System.out.println(s.getK20700());
            System.out.println(s.getK40000());
            System.out.println(s.getK60400());
            System.out.println(s.getK69900()+"/n");
            System.out.println();
        }
    }
    @Test
    public void getAuditLog() throws Exception {
        Integer instanceId = 1;
        List<FeeFlowInstanceLogModel> flowInstanceLogModelList = flowInstanceLogService.getAuditLog(instanceId);
        for (FeeFlowInstanceLogModel model : flowInstanceLogModelList) {
            System.out.println(model.getAuditor());
            System.out.println(model.getCreateTime());
            System.out.println(model.getNodeName());
            System.out.println(model.getStatus());
            System.out.println(model.getSuggestion());
        }
    }*/
    @Test
    public void getTracList() throws Exception {
        Integer id = 6570;
        String s = ",1,2,3,1,";
        List<String> strings = Arrays.asList(StringUtils.split(s, ","));
        if (CollectionUtils.isEmpty(strings)){

        }
        /*List<CustomerTraceModel> traceList = customerService.getTraceList(id);
        if (CollectionUtils.isEmpty(traceList)){

        }*/
    }
    /*@Test
    public void getCompanyGroup() throws Exception{
        List<Integer> ids = new ArrayList<>();
        ids.add(null);
        ids.add(null);
        ids.add(null);
        ids.add(1);
        ids.add(113);
        List<CompanyGroupEntity> list = companyGroupDao.getByCompanyId(ids);
        if (CollectionUtils.isEmpty(list)){

        }

    }*/
}
