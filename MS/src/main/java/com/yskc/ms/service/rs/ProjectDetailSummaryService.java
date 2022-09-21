package com.yskc.ms.service.rs;

import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageResult;
import com.yskc.ms.models.DataPermModel;
import com.yskc.ms.models.UserInfo;
import com.yskc.ms.models.innovationproject.InnovationMemberSelectModel;
import com.yskc.ms.models.project.QueryProjectDetailModel;
import com.yskc.ms.models.project.RsProjectMasterModel;

import java.io.OutputStream;
import java.util.List;

/**
 * @Author: hck
 * @DateTime: 2021/7/24 10:48
 * @Description:
 */
public interface ProjectDetailSummaryService {

    PageResult getList(QueryProjectDetailModel query, DataPermModel perm);

    Boolean saveMaster(RsProjectMasterModel model);

    /**
     * 获取创新成员
     *
     * @param companyId
     * @param year
     * @param mType
     * @return
     */
    List<InnovationMemberSelectModel> getMemberList(Integer companyId, Integer year,Integer mType);

    /**
     * 导出立项管理数据表
     * @param query
     * @param info
     * @param out
     * @param dataPermModel
     */
    void exportPlan(QueryProjectDetailModel query, UserInfo info, OutputStream out,DataPermModel dataPermModel) throws OwnerException;
}
