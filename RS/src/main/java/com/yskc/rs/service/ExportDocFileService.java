package com.yskc.rs.service;

import com.yskc.rs.entity.project.ProjectEntity;
import com.yskc.rs.models.document.DataModel;

import java.util.Map;

/**
 * Created by hck
 * on 2020/6/15 14:10
 * description:导出过程文件接口
 */
public interface ExportDocFileService {

    Map<String,Object> exportDocFile(ProjectEntity projectEntity, Integer currentYear, String fileData, DataModel dataModel);

}
