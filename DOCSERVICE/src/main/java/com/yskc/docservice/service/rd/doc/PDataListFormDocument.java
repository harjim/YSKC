package com.yskc.docservice.service.rd.doc;

import cn.hutool.core.util.StrUtil;
import com.yskc.common.utils.JsonUtils;
import com.yskc.docservice.dao.rs.company.CompanySettingDao;
import com.yskc.docservice.dao.rs.project.ProjectDocFileDao;
import com.yskc.docservice.entity.rs.company.CompanySettingEntity;
import com.yskc.docservice.entity.rs.project.ProjectEntity;
import com.yskc.docservice.models.rs.docfile.PDataListFormModel;
import com.yskc.docservice.service.rd.RDDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * Created by hck
 * on 2020/6/15 14:20
 * description:项目资料清单导出
 */
@Component("PDataListForm_Doc")
@Scope("prototype")
public class PDataListFormDocument extends RDDocument {

    @Autowired
    private ProjectDocFileDao projectDocFileDao;
    @Autowired
    private CompanySettingDao companySettingDao;

    @Override
    protected Map getDocMap(){
        ProjectEntity projectInfo = this.dataFactory.getProjectInfo();
        Map<String, Object> map = new HashMap<>();
        List<PDataListFormModel> stageModelList = getDocFileList();
        /*List<StageModel> stageModelList = projectDocFileDao.queryFileList(projectInfo.getId(),null);
        if (stageModelList.size()>0){
            CompanySettingEntity companySetting = companySettingDao.getSetting(projectInfo.getCompanyId());
            String rdIndex = String.format("%02d",projectInfo.getRdIndex()); // projectInfo.getRdIndex() > 9 ? projectInfo.getRdIndex().toString() : "0" + projectInfo.getRdIndex().toString();
            Map<String, Object> documentMap = null;
            if(companySetting!=null && StringUtils.hasText(companySetting.getDocumentNo())){
                documentMap = JsonUtils.jsonToPojo(companySetting.getDocumentNo(), HashMap.class);
            }
            boolean hasDocumentNo = !CollectionUtils.isEmpty(documentMap) && documentMap.containsKey("documentNo");
            for (int i = 0; i < stageModelList.size(); i++) {
                String documentNumber;
                if (hasDocumentNo) {
                    Map<String, Object> strMap = new HashMap<>();
                    strMap.put("year", projectInfo.getBeginYear());
                    strMap.put("stage", stageModelList.get(i).getStageType());
                    strMap.put("rdIndex", rdIndex);
                    //strMap.put("seq", stageModelList.get(i).getSeq());
                    strMap.put("seq", String.format("%04d", i + 1));
                    documentNumber = StrUtil.format(documentMap.get("documentNo").toString(), strMap);
                } else {
                    documentNumber = projectInfo.getBeginYear() + rdIndex + "-XM-" + String.format("%04d", i + 1);
                }
                stageModelList.get(i).setDocumentNumber(documentNumber);
                stageModelList.get(i).setDocSeq(i + 1);
            }
        }*/
        map.put("docCheckList", stageModelList);
        return map;
    }

    /*@Override
    protected Map getDocMap(ProjectEntity project, Integer currentYear) {
        Map<String, Object> map = new HashMap<>();
        List<StageModel> models= getDocFileList(project, null);
        if(!CollectionUtils.isEmpty(models)){
            map.put("docCheckList", models);
        }else {
            map.put("docCheckList", new ArrayList<>());
        }
        return map;
    }*/
    public List<PDataListFormModel> getDocFileList() {
        ProjectEntity projectEntity = dataFactory.getProjectInfo();
//        List<Integer> pDocFileIds = docParam.getpDocFileId() != null && docParam.getpDocFileId().length > 1 ? Arrays.asList(docParam.getpDocFileId()) : null;
        List<PDataListFormModel> stageModelList = dataFactory.getAllFileStageList();
        if (!CollectionUtils.isEmpty(stageModelList)) {
            CompanySettingEntity companySetting = companySettingDao.getSetting(projectEntity.getCompanyId());
            String rdIndex = projectEntity.getRdIndex() > 9 ? projectEntity.getRdIndex().toString() : "0" + projectEntity.getRdIndex().toString();
            Map<String, Object> documentMap = null;
            if(companySetting!=null && StringUtils.hasText(companySetting.getDocumentNo())){
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
        } else {
            stageModelList = new ArrayList<>();
        }
        return stageModelList;
    }
    /*
        List<StageModel> stageModelList = projectDocFileDao.queryFileList(projectEntity.getId(),pDocFileIds);
        if (!CollectionUtils.isEmpty(stageModelList)) {
            CompanySettingEntity companySetting = companySettingDao.getSetting(projectEntity.getCompanyId());
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
    }*/
}
