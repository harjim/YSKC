package com.yskc.ms.service.rs;


import com.yskc.ms.entity.rs.ProjectEntity;
import com.yskc.ms.models.rs.DataModel;

import java.util.Map;

/**
 * Created by hck
 * on 2020/6/15 14:10
 * description:导出过程文件接口
 */
public interface ExportDocFileService {

    Map<String,Object> exportDocFile(ProjectEntity projectEntity, Integer currentYear, String fileData, DataModel dataModel);

}
