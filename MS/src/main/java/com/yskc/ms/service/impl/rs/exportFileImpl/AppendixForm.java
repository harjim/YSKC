package com.yskc.ms.service.impl.rs.exportFileImpl;


import com.yskc.common.utils.JsonUtils;
import com.yskc.ms.dao.rs.DocFileAttachmentDao;
import com.yskc.ms.entity.rs.ProjectEntity;
import com.yskc.ms.entity.rs.models.AttachmentModel;
import com.yskc.ms.models.rs.DataModel;
import com.yskc.ms.service.rs.ExportDocFileService;
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
    //        Map<String,Object> dataMap=new HashMap<>();
//        if(!StringUtils.isEmpty(fileData)) {
//            dataMap= JsonUtils.jsonToPojo(fileData, Map.class);
//            List<Map<String, String>> imgUrlMaps = JsonUtils.jsonToPojo(JsonUtils.objectToJson(dataMap.get("list")), List.class);
//            List<String> imgUrls=new ArrayList<>();
//            if(!CollectionUtils.isEmpty(imgUrlMaps)){
//                for (Map<String,String> map:imgUrlMaps){
//                    imgUrls.add(map.get("filePath"));
//                }
//            }
//
//            dataMap.put("imgUrls",imgUrls);
//        }
//        return dataMap;
}
