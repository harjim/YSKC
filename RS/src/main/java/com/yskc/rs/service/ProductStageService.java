package com.yskc.rs.service;

import com.yskc.common.exception.OwnerException;
import com.yskc.rs.models.UserInfo;
import com.yskc.rs.models.tech.ProductStageModel;
import com.yskc.rs.models.tech.ProjectListModel;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * Created by hck
 * on 2020/10/19 14:41
 * description:
 */
public interface ProductStageService {
    /**
     * 获取技改项目的阶段及资料
     * @param directionId
     * @return
     */
    List<ProductStageModel> getDirectionStage(Integer directionId);

    /**
     * 资料上传文件
     * @param multipartFiles
     * @param model
     * @param userInfo
     * @return
     */
    ProjectListModel upload(MultipartFile multipartFiles, ProjectListModel model, UserInfo userInfo) throws OwnerException;

    /**
     * 查询文件是否存在
     * @param model
     * @return
     */
    boolean checkFileExist(ProjectListModel model);


    /**
     * 删除资料
     * @param userInfo
     * @param model
     * @return
     */
    boolean del(UserInfo userInfo, ProjectListModel model);

    /**
     * 获取阶段文件列表
     * @param stageId
     * @param userInfo
     * @return
     */
    Map<Integer, List<ProjectListModel>> getList(Integer stageId,Integer projectId, UserInfo userInfo);
}
