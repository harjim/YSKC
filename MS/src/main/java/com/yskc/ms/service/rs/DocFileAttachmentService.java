package com.yskc.ms.service.rs;

import com.yskc.ms.entity.rs.models.AttachmentModel;

import java.util.List;

/**
 * @BelongsProject: ms
 * @BelongsPackage: com.yskc.ms.service.rs
 * @Author: zhangdingfu
 * @CreateTime: 2021-09-02 13:51
 * @Description: 记录及附件业务接口层
 */
public interface DocFileAttachmentService {

    /**
     * 获取记录及附件
     *
     * @param docFileId
     * @param projectId
     * @return
     */
    List<AttachmentModel> getAttachments(Integer docFileId, Integer projectId);
}
