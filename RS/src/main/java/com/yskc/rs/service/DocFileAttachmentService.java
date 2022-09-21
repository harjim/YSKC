package com.yskc.rs.service;

import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.rs.models.UserInfo;
import com.yskc.rs.models.docFile.AttachmentModel;
import com.yskc.rs.models.project.DocFileAttachmentModel;
import com.yskc.rs.models.project.QueryAttachmentModel;
import com.yskc.rs.models.project.RdManageModel;

import java.util.List;

/**
 * @Author: hck
 * @DateTime: 2021/6/17 14:58
 * @Description:
 */
public interface DocFileAttachmentService {
    /**
     * 添加记录附件
     * @param model
     * @return
     */
    AttachmentModel save(UserInfo userInfo, AttachmentModel model) throws OwnerException;

    /**
     * 删除上传附件
     * @param id
     * @return
     */
    Boolean del(Integer id);

    /**
     * 获取附件列表
     * @param docFileId
     * @param projectId
     * @return
     */
    List<AttachmentModel> getAttachments(Integer docFileId, Integer projectId);

    /**
     * 获取多层级研发管理菜单列表
     * @param year
     * @param companyId
     * @return
     */
    List<RdManageModel> getRdManagerMenu(Integer year, Integer companyId);

    /**
     * 获取多层级研发管理附件列表
     * @param companyId
     * @param query
     * @return
     */
    PageModel<List<DocFileAttachmentModel>> getList(Integer companyId, QueryAttachmentModel query);
}
