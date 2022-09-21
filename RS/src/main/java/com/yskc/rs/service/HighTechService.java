package com.yskc.rs.service;

import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.rs.entity.hightech.HighTechFileEntity;
import com.yskc.rs.models.UserInfo;
import com.yskc.rs.models.hightech.*;
import com.yskc.rs.models.project.ProjectInfoModel;

import java.util.List;
import java.util.Map;

/**
 * @Author: hck
 * @DateTime: 2021/5/28 8:16
 * @Description:
 */
public interface HighTechService {

    String getDescription(Integer companyId,String hcode);

    List<HighTechModel> getList(Integer companyId, QueryHighTechModel query);

    /**
     * 添加高品
     * @param userInfo
     * @param model
     * @return
     */
    boolean save(UserInfo userInfo, HighTechModel model) throws OwnerException;

    /**
     * 获取高品现存最大的代码
     * @param companyId
     * @param year
     * @return
     */
    Integer getMaxCodeNum(Integer companyId, Integer year);

    /**
     * 获取高品详细信息
     * @param companyId
     * @param highTechId
     * @return
     */
    HighTechInfoModel getTechInfo(Integer companyId, Integer highTechId,Integer year);

    /**
     * 保存高品信息
     * @param userInfo
     * @param model
     * @return
     */
    Integer saveDetail(UserInfo userInfo, HighTechInfoModel model);

    /**
     * 获取高品可绑定的项目列表
     * @param companyId
     * @param highTechId
     * @return
     */
    List<ProjectInfoModel> getProjects(Integer companyId, Integer highTechId);

    /**
     * 上传文档
     * @param fileEntity
     */
    void uploadFile(HighTechFileEntity fileEntity);

    /**
     * 删除上传文件
     * @param model
     * @return
     */
    boolean delTechFile(HighTechFileModel model);

    /**
     * 获取高品下拉列表
     * @param year
     * @param companyId
     * @return
     */
    List<HighTechSelectModel> getHighTechSelect(Integer year,Integer companyId); /**
     * 验证高品名称或代码唯一
     * @param companyId
     * @param hname
     * @param hcode
     * @return
     */
    Boolean verifyCodeAndName(Integer companyId, String hname, String hcode,Integer highTechId) throws OwnerException;

    /**
     * 删除高品项目
     * @param models
     * @param companyId
     * @return
     */
    Boolean delHighTech(List<HighTechModel> models, Integer companyId) throws OwnerException;

    /**
     * 获取高品导出数据map
     * @param companyId
     * @param highTechId
     * @param year
     * @return
     */
    Map<String, Object> getTechDataMap(Integer companyId, Integer highTechId, Integer year);

    /**
     * 获取高品上传列表
     * @param highTechId
     * @return
     */
    List<HighTechFileModel> getUploadFiles(Integer highTechId,Integer companyId);
}
