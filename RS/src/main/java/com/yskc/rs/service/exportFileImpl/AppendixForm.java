package com.yskc.rs.service.exportFileImpl;

import com.yskc.common.utils.JsonUtils;
import com.yskc.rs.dao.project.DocFileAttachmentDao;
import com.yskc.rs.entity.project.ProjectEntity;
import com.yskc.rs.models.docFile.AttachmentModel;
import com.yskc.rs.models.document.DataModel;
import com.yskc.rs.service.ExportDocFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hck
 * on 2021/2/18 15:20
 * description:记录及附件
 */
@Component
public class AppendixForm implements ExportDocFileService {
    @Autowired
    private DocFileAttachmentDao docFileAttachmentDao;

    public static AppendixForm appendixForm;

    @PostConstruct
    public void init() {
        appendixForm = this;
        appendixForm.docFileAttachmentDao = this.docFileAttachmentDao;
    }

    @Override
    public Map<String, Object> exportDocFile(ProjectEntity projectEntity, Integer currentYear, String fileData, DataModel dataModel) {
        Map<String, Object> dataMap = new HashMap<>();
        if (!StringUtils.isEmpty(fileData)) {
            dataMap = JsonUtils.jsonToPojo(fileData, Map.class);
        }
        List<AttachmentModel> models = appendixForm.docFileAttachmentDao.getByDocFile(projectEntity.getId(), dataModel.getpDocFileId());
        if (!CollectionUtils.isEmpty(models)) {
            List<String> imgUrls = new ArrayList<>();
            for (AttachmentModel model : models) {
                imgUrls.add(model.getFilePath());
            }
            dataMap.put("imgUrls", imgUrls);
        }
        return dataMap;
    }
}
