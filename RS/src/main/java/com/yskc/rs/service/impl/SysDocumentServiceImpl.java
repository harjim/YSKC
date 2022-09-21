package com.yskc.rs.service.impl;

import cn.afterturn.easypoi.entity.ImageEntity;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.io.FileUtil;
import com.alibaba.druid.sql.visitor.functions.Char;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yskc.common.enums.AuditModeEnum;
import com.yskc.common.enums.FlowInstanceStatusEnum;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.common.utils.DateUtil;
import com.yskc.common.utils.PageUtils;
import com.yskc.rs.config.RsConfig;
import com.yskc.rs.dao.*;
import com.yskc.rs.dao.project.AuditDocFileDao;
import com.yskc.rs.dao.project.AuditResultDao;
import com.yskc.rs.entity.RdDeptEntity;
import com.yskc.rs.entity.SysDocumentEntity;
import com.yskc.rs.models.UserInfo;
import com.yskc.rs.models.buildconfig.BuildConfigDocModel;
import com.yskc.rs.models.stage.StageModel;
import com.yskc.rs.models.sysDocument.*;
import com.yskc.rs.service.SysDocumentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class SysDocumentServiceImpl implements SysDocumentService {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RsConfig rsConfig;
    @Autowired
    SysDocumentDao sysDocumentDao;
    @Autowired
    private DocListDao docListDao;
    @Autowired
    private StageDao stageDao;
    @Autowired
    private AuditDocFileDao auditDocFileDao;
    @Autowired
    private AuditResultDao auditResultDao;
    @Autowired
    private BuildConfigDao buildConfigDao;
    @Autowired
    private EmployeeDao employeeDao;
    @Autowired
    private RdDeptDao rdDeptDao;
    @Override
    public PageModel<List<SysDocumentModel>> queryDocument(QuerySysDocumentModel query, Integer companyId) {
        Pagination pagination = query.getPagination();
        if (CollectionUtils.isEmpty(pagination.getAscs()) && CollectionUtils.isEmpty(pagination.getDescs())) {
            pagination.setDescs(CollUtil.newArrayList("year", "createTime"));
        }
        if (query.getDocMonth() != null) {
            query.setDocMonth(DateUtil.getMonthFirstDay(query.getDocMonth()));
        }
        return PageUtils.build(pagination, sysDocumentDao.queryDocument(pagination, query,companyId));
    }

    @Override
    public PageModel<List<SysDocumentModel>> queryAppendixList(String fileName, Integer[] fileType, int companyId, int pageNo, int pageSize, String year, String projectId) {
        Pagination pagination = new Pagination(pageNo, pageSize);
        return PageUtils.build(pagination, sysDocumentDao.queryAppendixList(pagination, fileName, fileType, companyId, year, projectId));
    }

    @Override
    public List<DocListModel> queryDocList(int listType, int companyId, Integer projectId) {

        List<DocListModel> docListModels = sysDocumentDao.queryDocList(listType, companyId);
        if (docListModels.size() == 0) {
            return null;
        }
        List<Integer> ids = docListModels.stream().filter(a -> !StringUtils.isEmpty(a.getId())).map(DocListModel::getId).collect(Collectors.toList());
        List<SysDocumentModel> sysDocumentModels = sysDocumentDao.queryDocForDocList(companyId, ids, projectId, "", 0);
        for (int i = 0; i < docListModels.size(); i++) {
            List<MonthModel> monthModels = new ArrayList<>();
            for (int j = 1; j < 13; j++) {
                List<SysDocumentModel> docList = new ArrayList<>();
                for (int k = 0; k < sysDocumentModels.size(); k++) {
                    SysDocumentModel sysDocumentModel = sysDocumentModels.get(k);
                    if (sysDocumentModel.getMonth() == j && sysDocumentModel.getListId().equals(docListModels.get(i).getId())) {
                        docList.add(sysDocumentModel);
                    }
                }
                MonthModel monthModel = new MonthModel();

                if (j < 10) {
                    monthModel.setMonth("0" + j);
                } else {
                    monthModel.setMonth("" + j);
                }
                monthModel.setDocList(docList);
                monthModels.add(monthModel);
            }
            docListModels.get(i).setMonthModelList(monthModels);
        }

        return docListModels;
    }

    @Override
    public List<DocListModel> queryAppendixDocList(int listType, int companyId, String patentNo, int year) {
        List<DocListModel> docListModels = sysDocumentDao.queryDocList(listType, companyId);
        if (docListModels.size() == 0) {
            return null;
        }

        List<Integer> ids = docListModels.stream().filter(a -> !StringUtils.isEmpty(a.getId())).map(DocListModel::getId).collect(Collectors.toList());
        List<SysDocumentModel> sysDocumentModels = sysDocumentDao.queryDocForDocList(companyId, ids, 0, patentNo, listType==2001?0:year);
        for (int i = 0; i < docListModels.size(); i++) {
            List<SysDocumentModel> docModels = new ArrayList<>();
            for (SysDocumentModel model : sysDocumentModels) {
                if (docListModels.get(i).getId().equals(model.getListId())) {
                    docModels.add(model);
                }
            }
            docListModels.get(i).setDocList(docModels);
        }
        return docListModels;
    }

    @Override
    public SysDocumentEntity getFileById(int id) {
        return sysDocumentDao.selectById(id);
    }

    @Override
    public Boolean updateFile(SysDocumentEntity entity) {
        return sysDocumentDao.updateById(entity) > 0;
    }

    @Override
    public boolean delFile(SysDocumentEntity model) throws OwnerException {
        SysDocumentEntity document=sysDocumentDao.selectById(model.getId());
        if(null!=document && 3==document.getListId()){
            //AuditReportEntity audit=auditReportDao.getByProject(document.getProjectId());
            if(!FlowInstanceStatusEnum.canModify(auditDocFileDao.getAuditStatus(document.getProjectId(), AuditModeEnum.RD_NOVELTY_SEARCH.getModuleId()))){
                throw new OwnerException("该文件处于审核中，不能删除！");
            }
        }
        return sysDocumentDao.deleteById(model.getId()) > 0;
    }


    public boolean insertOrUpdate(SysDocumentEntity entity) {
        SysDocumentModel model = sysDocumentDao.selectByParam(entity.getCompanyId(), entity.getFileType(), entity.getYear(), entity.getProjectId());
        if (model != null) {
            entity.setId(model.getId());
            entity.setCreatorId(model.getCreatorId());
            entity.setMsCreatorId(model.getMsCreatorId());
            entity.setCreateTime(model.getCreateTime());
            sysDocumentDao.updateById(entity);
        } else {
            sysDocumentDao.insert(entity);
        }
        return false;
    }
    @Override
    public SysDocumentModel uploadFile(UserInfo info, SysDocumentFile documentFile, MultipartFile file, Boolean needUpdate){
        SysDocumentModel docModel = null;
        Date date = new Date();
        String dirPath = MessageFormat.format("doc/{0}/{1}/{2}",info.getCompanyId(),documentFile.getYear().toString(),documentFile.getProjectType());
        String fileName = MessageFormat.format("{0}_{1}_{2}{3}",documentFile.getProjectId(),documentFile.getFileType(),date.getTime()+"",file.getOriginalFilename());

        File folder = new File(MessageFormat.format("{0}/{1}",rsConfig.getUploadConfig().getDocPath(),dirPath));
        if (!folder.exists() && !folder.isDirectory()) {
            folder.mkdirs();
        }
        documentFile.setFilePath(MessageFormat.format("{0}/{1}",dirPath,fileName));
        File tempFile = new File(MessageFormat.format("{0}/{1}/{2}",rsConfig.getUploadConfig().getDocPath(),dirPath,fileName));
        try {
            file.transferTo(tempFile);
            SysDocumentEntity entity = new SysDocumentEntity();
            if (documentFile.getDocumentName() != null && !documentFile.getDocumentName().equals("") && !documentFile.getDocumentName().equals("undefined")) {
                String[] strArray = file.getOriginalFilename().split("\\.");
                int suffixIndex = strArray.length - 1;
                entity.setFileName(documentFile.getDocumentName() + "." + strArray[suffixIndex]);
            } else {
                entity.setFileName(file.getOriginalFilename());
            }
            entity.setCompanyId(info.getCompanyId());
            entity.setCreatorId(info.getId());
            entity.setLastUpdatorId(info.getId());
            entity.setMsCreatorId(info.getMsUserId());
            entity.setMsLastUpdatorId(info.getMsUserId());
            entity.setCreateTime(date);
            entity.setLastUpdateTime(date);
            entity.setFileType(documentFile.getFileType());
            entity.setFilePath(documentFile.getFilePath());
            entity.setDownloadTimes(0);
            entity.setMonth(Integer.parseInt(documentFile.getMonth() != null ? documentFile.getMonth() : "0"));
            entity.setProjectId(documentFile.getProjectId());
            entity.setProjectType(documentFile.getProjectType());
            entity.setYear(documentFile.getYear());
            entity.setRemark(documentFile.getRemark());
            entity.setListId(documentFile.getListId() != null ? documentFile.getListId() : 0);
            entity.setPatentNo(documentFile.getPatentNo() != null ? documentFile.getPatentNo() : "");
            entity.setStageKey(documentFile.getStageKey()!=null?documentFile.getStageKey():"");
            entity.setDocDate(documentFile.getDocDate());
            entity.setWorkshop(documentFile.getWorkshop());
            entity.setBuildType(documentFile.getBuildType());
            Integer sign = 0;
            if (documentFile.getDocDate() != null) {
                entity.setDocMonth(DateUtil.getMonthFirstDay(documentFile.getDocDate()));
                sign = 1;
            }
            if (documentFile.getDocMonth() != null && sign == 0) {
                entity.setDocMonth(DateUtil.getMonthFirstDay(documentFile.getDocMonth()));
            }

            if (needUpdate) {
                docModel = sysDocumentDao.selectByParam(entity.getCompanyId(), entity.getFileType(), entity.getYear(), entity.getProjectId());
                if (docModel != null) {
                    entity.setId(docModel.getId());
                    entity.setCreatorId(docModel.getCreatorId());
                    entity.setMsCreatorId(docModel.getMsCreatorId());
                    entity.setCreateTime(docModel.getCreateTime());
                    sysDocumentDao.updateById(entity);
                } else {
                    docModel = new SysDocumentModel();
                    sysDocumentDao.insert(entity);
                }
            } else {
                docModel = new SysDocumentModel();
                sysDocumentDao.insert(entity);
            }

            docModel.setId(entity.getId());
            docModel.setFilePath(entity.getFilePath());
            docModel.setFileName(entity.getFileName());
            docModel.setUserName(info.getUserName());
            docModel.setCreateTime(new Date());
            docModel.setBuildType(entity.getBuildType());

            // insertLog(info, entity, "上传文档", "/api/sysDocument/upload");

        } catch (IOException e) {
            e.printStackTrace();
        }
        return docModel;
    }

    @Override
    public List<FileTypeModel> getFileType() {
        List<FileTypeModel> list=docListDao.getFileTypes();
        return list;
    }

    @Override
    public List<StageModel> queryStage(Integer companyId, Integer projectId) {
        return stageDao.queryStage(companyId,projectId);
    }

    @Override
    public List<SysDocumentEntity> getReportByProject(Integer projectId) {
        return sysDocumentDao.getReportByProject(projectId);
    }

    @Override
    public boolean delResultFile(SysDocumentEntity model) throws OwnerException {
        if(FlowInstanceStatusEnum.canModify(auditResultDao.getStatus(model.getId()))){
            return sysDocumentDao.deleteById(model.getId()) > 0;
        }
        throw new OwnerException("该文件处于审核中，不能删除！");
    }

    @Override
    public Map<String, Object> getBuildFileData(GenerateBuildFileModel model, UserInfo info) throws OwnerException {
        Map<String, Object> result = new HashMap<>();
        BuildConfigDocModel techConfig = new BuildConfigDocModel();
        BuildConfigDocModel financeConfig = new BuildConfigDocModel();
        //页眉换行字数控制,仍需手动微调
        Double nameLength = 0.0;
        String companyName = info.getCompanyName();
        char[] chars = companyName.toCharArray();
        for(int i=0;i<companyName.length();i++){
            String temp = String.valueOf(chars[i]);
            if (temp.matches("[^\\x00-\\xff]")) {
                nameLength++;
            }else {
                nameLength+=0.5;
            }
        }
        int length = model.getDocName().length();
        StringBuilder sb = new StringBuilder(info.getCompanyName());
        for(int i=0;i<(38.5-length-nameLength)*2;i++){
            sb.append(" ");
        }
        result.put("pageName",sb.toString());
        //公司名称
        result.put("companyName",companyName);
        RdDeptEntity parentNode = rdDeptDao.getParentNode(model.getYear(), info.getCompanyId());
        if (null != parentNode && !StringUtils.isEmpty(parentNode.getDeptName())) {
            result.put("deptName", parentNode.getDeptName());
        } else {
            result.put("deptName", "研发部门");
        }
        List<BuildConfigDocModel> list = buildConfigDao.getConfigList(info.getCompanyId(),model.getYear());
        if(list.size()>0){
            techConfig = list.get(0);
            financeConfig = list.get(1);
        }
        result.put("techConfig",techConfig);
        result.put("financeConfig",financeConfig);
        //公司logo
        String companyLogoPath = info.getCompanyLogoPath();
        String s = rsConfig.getUploadConfig().getBasicPath() + info.getCompanyLogoPath();
        result.put("logo", !StringUtils.isEmpty(info.getCompanyLogoPath()) && FileUtil.exist(rsConfig.getUploadConfig().getBasicPath() + info.getCompanyLogoPath()) ?
                new ImageEntity(rsConfig.getUploadConfig().getBasicPath() + info.getCompanyLogoPath(), 120, 120) : "");
        return result;
    }
}
