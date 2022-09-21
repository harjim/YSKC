package com.yskc.rs.service.exportFileImpl;

import cn.hutool.core.util.StrUtil;
import com.yskc.common.utils.JsonUtils;
import com.yskc.rs.dao.ProjectDocFileDao;
import com.yskc.rs.dao.company.CompanySettingDao;
import com.yskc.rs.entity.company.CompanySettingEntity;
import com.yskc.rs.entity.project.ProjectEntity;
import com.yskc.rs.models.document.DataModel;
import com.yskc.rs.models.stage.StageModel;
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
 * on 2020/6/15 14:20
 * description:项目资料清单导出
 */
@Component("pDataListForm")
public class PDataListForm implements ExportDocFileService {

    @Autowired
    private ProjectDocFileDao projectDocFileDao;
    @Autowired
    private CompanySettingDao companySettingDao;

    public static PDataListForm pDataListForm;

    @PostConstruct
    public void init(){
        pDataListForm=this;
        pDataListForm.projectDocFileDao=this.projectDocFileDao;
        pDataListForm.companySettingDao=this.companySettingDao;
    }

    @Override
    public Map<String, Object> exportDocFile(ProjectEntity projectEntity, Integer currentYear, String fileData, DataModel dataModel) {
        Map<String, Object> dataMap = new HashMap<>();
        if (!StringUtils.isEmpty(fileData)) {
        dataMap= JsonUtils.jsonToPojo(fileData, Map.class);
        }
      List<StageModel> models= getDocFileList(projectEntity,dataModel.getpDocFileIds());
        if(!CollectionUtils.isEmpty(models)){
            dataMap.put("docCheckList", models);
        }else {
            dataMap.put("docCheckList", new ArrayList<>());
        }
        return dataMap;
    }

    public List<StageModel> getDocFileList(ProjectEntity projectEntity,List<Integer> pDocFileIds) {
        List<StageModel> stageModelList = pDataListForm.projectDocFileDao.queryFileList(projectEntity.getId(),pDocFileIds);
        if (!CollectionUtils.isEmpty(stageModelList)) {
            CompanySettingEntity companySetting = pDataListForm.companySettingDao.getSetting(projectEntity.getCompanyId());
            String rdIndex = projectEntity.getRdIndex() > 9 ? projectEntity.getRdIndex().toString() : "0" + projectEntity.getRdIndex().toString();
            Map<String, Object> documentMap = null;
            if(companySetting!=null && !StringUtils.isEmpty(companySetting.getDocumentNo())){
                documentMap = JsonUtils.jsonToPojo(companySetting.getDocumentNo(), HashMap.class);
            }
            boolean hasDocumentNo = !CollectionUtils.isEmpty(documentMap) && documentMap.containsKey("documentNo");
            for (int i = 0; i < stageModelList.size(); i++) {
                String documentNumber;
                if (hasDocumentNo) {
                    Map<String, Object> strMap = new HashMap<>();
                    strMap.put("year", projectEntity.getBeginYear());
                    strMap.put("stage", stageModelList.get(i).getStageType());
                    strMap.put("rdIndex", rdIndex);
                    //strMap.put("seq", stageModelList.get(i).getSeq());
                    strMap.put("seq", String.format("%04d", i + 1));
                    documentNumber = StrUtil.format(documentMap.get("documentNo").toString(), strMap);
                } else {
                    documentNumber = projectEntity.getBeginYear() + rdIndex + "-XM-" + String.format("%04d", i + 1);
                }
                stageModelList.get(i).setDocumentNumber(documentNumber);
                stageModelList.get(i).setDocSeq(i + 1);
            }
        }
        return stageModelList;
    }
}
