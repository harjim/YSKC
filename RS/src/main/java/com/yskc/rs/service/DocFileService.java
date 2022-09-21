package com.yskc.rs.service;

import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.rs.entity.DocFileTemplateEntity;
import com.yskc.rs.entity.MeetMemberEntity;
import com.yskc.rs.models.UserInfo;
import com.yskc.rs.models.docFile.DocFileModel;
import com.yskc.rs.models.docFile.NewReportsModel;
import com.yskc.rs.models.docFile.QueryReportsModel;
import com.yskc.rs.models.docFile.TissueFormModel;

import java.util.List;

public interface DocFileService {
    List<DocFileModel> queryDocFile(String stage);

    /**
     * 获取查新报告列表
     * @param model
     * @return
     */
    PageModel<List<NewReportsModel>> getNewReports(QueryReportsModel model);

    /**
     * 引入验证报告数据
     * @param projectId
     * @return
     */
    List<TissueFormModel> importReportData(Integer projectId,Integer companyId);

    List<NewReportsModel> getProjectReports(Integer projectId);

    /**
     * 获取会议纪要参会人员
     * @param projectId
     * @return
     */
    String getMeetMember(Integer projectId);

    /**
     * 保存参会人员
     * @param model
     * @param userInfo
     * @return
     */
    Boolean setMeetMember(MeetMemberEntity model, UserInfo userInfo) throws OwnerException;
}
