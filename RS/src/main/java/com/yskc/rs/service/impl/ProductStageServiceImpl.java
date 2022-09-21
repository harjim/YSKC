package com.yskc.rs.service.impl;

import com.yskc.common.enums.FileTypeEnum;
import com.yskc.common.exception.OwnerException;
import com.yskc.rs.config.RsConfig;
import com.yskc.rs.dao.tech.ProductStageDao;
import com.yskc.rs.dao.tech.ProductStageListDao;
import com.yskc.rs.dao.tech.ProjectListDao;
import com.yskc.rs.entity.tech.ProductStageListEntity;
import com.yskc.rs.entity.tech.ProjectListEntity;
import com.yskc.rs.models.UserInfo;
import com.yskc.rs.models.tech.ProductStageListModel;
import com.yskc.rs.models.tech.ProductStageModel;
import com.yskc.rs.models.tech.ProjectListModel;
import com.yskc.rs.service.DocumentService;
import com.yskc.rs.service.ProductStageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by hck
 * on 2020/10/19 14:42
 * description:
 */
@Service
public class ProductStageServiceImpl implements ProductStageService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ProductStageDao productStageDao;
    @Autowired
    private ProductStageListDao productStageListDao;
    @Autowired
    private ProjectListDao projectListDao;
    @Autowired
    private RsConfig rsConfig;

    @Override
    public List<ProductStageModel> getDirectionStage(Integer directionId) {
        List<ProductStageModel> list = productStageDao.getByDirectionId(directionId);
        if (!CollectionUtils.isEmpty(list)) {
            List<ProductStageListEntity> stageList = productStageListDao.getByDirectionIds(Arrays.asList(directionId));
            Map<Integer, List<ProductStageListModel>> dataMap = new HashMap<>();
            if (!CollectionUtils.isEmpty(stageList)) {
                List<Integer> stageListIds = stageList.stream().map(e -> e.getId()).collect(Collectors.toList());
                List<ProjectListEntity> projectList = projectListDao.getListByStage(stageListIds, null);
                Map<Integer, Boolean> projectMap = new HashMap<>();
                if (!CollectionUtils.isEmpty(projectList)) {
                    for (ProjectListEntity entity : projectList) {
                        if (!projectMap.containsKey(entity.getStageListId())) {
                            projectMap.put(entity.getStageListId(), true);
                        }
                    }
                }
                Map<Integer, Boolean> stageMap = new HashMap<>();
                for (ProductStageListEntity stage : stageList) {
                    ProductStageListModel model = new ProductStageListModel();
                    BeanUtils.copyProperties(stage, model);
                    if (projectMap.containsKey(stage.getId())) {
                        model.setLimitChange(false);
                        if (!stageMap.containsKey(stage.getStageId())) {
                            stageMap.put(stage.getStageId(), true);
                        }
                    }
                    if (!dataMap.containsKey(stage.getStageId())) {
                        List<ProductStageListModel> listModel = new ArrayList<>();
                        dataMap.put(stage.getStageId(), listModel);
                    }
                    dataMap.get(stage.getStageId()).add(model);
                }
                for (ProductStageModel productStage : list) {
                    if (dataMap.containsKey(productStage.getId())) {
                        productStage.setModels(dataMap.get(productStage.getId()));
                    }
                    if (stageMap.containsKey(productStage.getId())) {
                        productStage.setLimitChange(false);
                    }
                }
            }
        }
        return list;
    }

    @Override
    public ProjectListModel upload(MultipartFile file, ProjectListModel model, UserInfo userInfo) throws OwnerException {
        Date date = new Date();
        ProjectListModel fileModel = new ProjectListModel();
        String exportPath = rsConfig.getUploadConfig().getDocPath();
        String fileName = StringUtils.isEmpty(model.getFileName()) ? file.getOriginalFilename() : model.getFileName();
        String filePath = "/techProject/" + userInfo.getCompanyId() + "/" + System.currentTimeMillis() + fileName;
        File tempFile = new File(exportPath , filePath);
        if (!tempFile.getParentFile().exists()) {
            tempFile.getParentFile().mkdirs();
        }
        try {
            file.transferTo(tempFile);
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            throw new OwnerException("上传失败");
        }
        ProductStageListEntity proStage = productStageListDao.selectById(model.getStageListId());
        if (proStage == null) {
            throw new OwnerException("资料已删除或不存在，上传文件失败");
        }
        String typeName = fileName.substring(fileName.lastIndexOf("."), fileName.length());
        if (StringUtils.isEmpty(typeName)) {
            throw new OwnerException("不能解析文件类型，上传失败");
        }
        FileTypeEnum fileTypeEnum = FileTypeEnum.getByTypeName(proStage.getPattern());
        if (!fileTypeEnum.getValue().equals("")) {
            if (!fileTypeEnum.getValue().contains(typeName)) {
                throw new OwnerException("请上传" + proStage.getPattern() + "类型的文件");
            }
        }
        ProjectListEntity projectList = new ProjectListEntity();
        projectList.setCompanyId(userInfo.getCompanyId());
        projectList.setCreateTime(date);
        projectList.setCreatorId(userInfo.getUserId());
        projectList.setFileName(fileName);
        projectList.setFilePath(filePath);
        projectList.setLastUpdateTime(date);
        projectList.setLastUpdatorId(userInfo.getUserId());
        projectList.setMsCreatorId(userInfo.getMsUserId());
        projectList.setMsLastUpdatorId(userInfo.getMsUserId());
        projectList.setStageListId(model.getStageListId());
        projectList.setProjectId(model.getProjectId());
        if (projectListDao.insert(projectList) > 0) {
            fileModel.setFileName(fileName);
            fileModel.setFilePath(filePath);
            fileModel.setId(projectList.getId());
            return fileModel;
        }
        throw new OwnerException("上传文件失败，请联系管理员！");
    }

    @Override
    public boolean checkFileExist(ProjectListModel model) {
        ProjectListEntity entity = projectListDao.selectById(model.getId());
        if (entity != null) {
            return true;
        }
        return false;
    }

    @Override
    public boolean del(UserInfo userInfo, ProjectListModel model) {
        return projectListDao.deleteById(model.getId()) > 0;
    }

    @Override
    public Map<Integer, List<ProjectListModel>> getList(Integer stageId, Integer projectId, UserInfo userInfo) {
        List<ProductStageListEntity> stageList = productStageListDao.getByStageId(stageId);
        Map<Integer, List<ProjectListModel>> dataMap = new HashMap<>();
        if (CollectionUtils.isEmpty(stageList)) {
            return dataMap;
        }
        List<Integer> ids = stageList.stream().map(e -> e.getId()).collect(Collectors.toList());
        List<ProjectListEntity> projectList = projectListDao.getListByStage(ids, projectId);

        if (!CollectionUtils.isEmpty(projectList)) {
            for (ProjectListEntity entity : projectList) {
                ProjectListModel model = new ProjectListModel();
                BeanUtils.copyProperties(entity, model);
                if (!dataMap.containsKey(entity.getStageListId())) {
                    List<ProjectListModel> models = new ArrayList<>();
                    dataMap.put(entity.getStageListId(), models);
                }
                dataMap.get(entity.getStageListId()).add(model);
            }
        }
        return dataMap;
    }
}
