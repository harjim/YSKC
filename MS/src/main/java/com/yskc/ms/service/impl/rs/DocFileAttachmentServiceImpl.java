package com.yskc.ms.service.impl.rs;

import com.yskc.ms.dao.rs.DocFileAttachmentDao;
import com.yskc.ms.entity.rs.models.AttachmentModel;
import com.yskc.ms.service.rs.DocFileAttachmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @BelongsProject: ms
 * @BelongsPackage: com.yskc.ms.service.impl.rs
 * @Author: zhangdingfu
 * @CreateTime: 2021-09-02 13:52
 * @Description: 记录及附件业务实现层
 */
@Service
public class DocFileAttachmentServiceImpl implements DocFileAttachmentService {

    @Autowired
    private DocFileAttachmentDao docFileAttachmentDao;

    @Override
    public List<AttachmentModel> getAttachments(Integer docFileId, Integer projectId) {
        return docFileAttachmentDao.getByDocFile( projectId, docFileId);
    }
}
