package com.yskc.rs.service;

import com.yskc.common.exception.OwnerException;
import com.yskc.rs.models.UserInfo;
import com.yskc.rs.models.tech.ProjectListModel;
import com.yskc.rs.models.tech.TechAttachmentModel;
import com.yskc.rs.models.tech.TechUploadFileModel;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * Created by hck
 * on 2020/8/14 13:35
 * description:
 */
public interface TechAttachmentsService {

    List<TechAttachmentModel> getList(Integer projectId,UserInfo userInfo);

    Boolean add(UserInfo userInfo, TechAttachmentModel model);

    Boolean edit(UserInfo userInfo, TechAttachmentModel model) throws OwnerException;

    /**
     * 检查资料中是否存在该文件
     * @param id
     * @param filePath
     * @return
     */
    Boolean checkFileExist(Integer id, String filePath);

    /**
     *
     * @param filePath
     * @return
     * @throws OwnerException
     */
    Object preview(String filePath) throws OwnerException;

    /**
     * 上传资料（更新+添加）
     * @param model
     * @param userInfo
     * @return
     * @throws OwnerException
     */
    Map<String,String> upload(MultipartFile[] multipartFiles, TechAttachmentModel model, String dir,String fileName, UserInfo userInfo) throws OwnerException;

}
