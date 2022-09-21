package com.yskc.rs.service;

import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.rs.entity.tech.TechProjectBasicEntity;
import com.yskc.rs.entity.tech.TechProjectEntity;
import com.yskc.rs.models.UserInfo;
import com.yskc.rs.models.tech.DeclarationInfo;
import com.yskc.rs.models.tech.ProductStageModel;
import com.yskc.rs.models.tech.QueryTechProjectModel;
import com.yskc.rs.models.tech.TechProjectModel;

import java.io.OutputStream;
import java.util.List;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.service
 * @Author: zhangdingfu
 * @CreateTime: 2019-09-23 11:08
 * @Description: 技改项目service层
 */
public interface TechProjectService {


    /**
     * 获取技改项目
     *
     * @param companyId
     * @param query
     * @return
     */
    PageModel<List<TechProjectModel>> getTechProjects(Integer companyId, QueryTechProjectModel query);

    /**
     * 获取年份
     *
     * @param companyId
     * @return
     * @throws OwnerException
     */
    List<Integer> getYears(Integer companyId) throws OwnerException;

    /**
     * 获取公司技改项目
     *
     * @param companyId
     * @param year
     * @return
     * @throws OwnerException
     */
    List<TechProjectEntity> getTechProjectsByYear(Integer companyId, Integer year) throws OwnerException;

    /**
     * 获取公司技改项目基本信息
     *
     * @param companyId
     * @param projectId
     * @return
     * @throws OwnerException
     */
    TechProjectBasicEntity getTechProjectBasic(Integer companyId, Integer projectId) throws OwnerException;

    /**
     * 保存技改项目基本信息
     *
     * @param userInfo
     * @param techProjectBasicEntity
     * @return
     * @throws OwnerException
     */
    boolean saveProjectBasic(UserInfo userInfo, TechProjectBasicEntity techProjectBasicEntity) throws OwnerException;

    /**
     * 获取技改项目
     *
     * @param userInfo
     * @param techProjectModel
     * @return
     */
    boolean add(UserInfo userInfo, TechProjectModel techProjectModel);

    /**
     * 更新技改项目
     *
     * @param userInfo
     * @param techProjectModel
     * @return
     */
    boolean update(UserInfo userInfo, TechProjectModel techProjectModel);

    /**
     * 通过id删除
     *
     * @param id
     * @return
     */
    boolean del(Integer id);

    /**
     * 批量删除
     *
     * @param techProjectEntityList
     * @return
     */
    boolean dels(List<TechProjectEntity> techProjectEntityList);

    /**
     * 根据标题获取技改申请报告
     *
     * @param projectId
     * @param key
     * @param info
     * @return
     * @throws OwnerException
     */
    List<DeclarationInfo> getDeclarationList(Integer projectId, String key, UserInfo info) throws OwnerException;

    /**
     * 保存技改项目申请报告
     *
     * @param info
     * @param declarationInfos
     * @return
     * @throws OwnerException
     */
    boolean saveDeclarationInfoList(UserInfo info, List<DeclarationInfo> declarationInfos) throws OwnerException;

    /**
     * 导出技改项目申请报告
     *
     * @param info
     * @param projectId
     * @param out
     * @throws Exception
     */
    void exportDeclarationInfo(UserInfo info, Integer projectId, OutputStream out) throws Exception;

}
