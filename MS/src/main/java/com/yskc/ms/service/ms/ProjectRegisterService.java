package com.yskc.ms.service.ms;

import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.ms.models.DataPermModel;
import com.yskc.ms.models.UserInfo;
import com.yskc.ms.models.projectRegister.ProjectRegisterDataModel;
import com.yskc.ms.models.projectRegister.ProjectRegisterModel;
import com.yskc.ms.models.projectRegister.QueryRegisterModel;
import com.yskc.ms.models.projectRegister.UploadRegisterFileModel;

import java.io.IOException;
import java.util.List;

/**
 * Created by hck
 * on 2020/8/6 11:59
 * description:
 */
public interface ProjectRegisterService {

    PageModel<List<ProjectRegisterDataModel>> getData(QueryRegisterModel model, DataPermModel dataPerm);

    /**
     * 根据项目id获取备案列表
     * @param projectId
     * @return
     */
    List<ProjectRegisterModel> getList(Integer projectId);

    Boolean addProjectRegister(UserInfo userInfo, ProjectRegisterModel model);

    Boolean delProjectRegister(UserInfo userInfo,ProjectRegisterModel model) throws OwnerException;

    Boolean updateProjectRegister(UserInfo userInfo,ProjectRegisterModel model) throws OwnerException;

    /**
     * 上传附件更新备案资料附件路径
     * @param userInfo
     * @param model
     * @return
     */
    Boolean uploadRegisterFile(UserInfo userInfo, UploadRegisterFileModel model) throws OwnerException;

    /**
     * 下载备案附件
     * @param id
     * @param filePath
    * @param sign 1:下载备案扫描证件 2：备案文件
     * @return
     */
    Boolean downloadFile(Integer id,String filePath,Integer sign) throws OwnerException;


}
