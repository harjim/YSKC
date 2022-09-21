package com.yskc.ms.service.ms;

import com.yskc.common.model.PageModel;
import com.yskc.ms.models.DataPermModel;
import com.yskc.ms.models.patent.PatentDemandModel;
import com.yskc.ms.models.patent.QueryPatentApplicationModel;
import com.yskc.ms.models.patentPlan.PatentPlanModel;
import com.yskc.ms.models.patentPlan.QueryPatentPlanModel;
import com.yskc.ms.models.project.BatchStaffModel;

import java.util.List;

/**
 * @program: ms
 * @description:
 * @author: cyj
 * @create: 2022-02-09 09:22
 **/
public interface PatentApplicationService {
    //获取需求列表
    PageModel<List<PatentDemandModel>> getList(QueryPatentApplicationModel query, DataPermModel dataPerm);
    //上传附件
    boolean upload(String type,int id,String path);
    //删除附件
    boolean del(String type,int id,String path);
    //分配工程师
    boolean setEngineer(BatchStaffModel model);
    //分配业务人员
    boolean setOwner(BatchStaffModel model);

    /**
     * 获取专利请求
     * @param query
     * @return
     */
    PageModel<List<PatentPlanModel>> getPlanList(QueryPatentPlanModel query);
}
