package com.yskc.rs.service;

import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.common.model.PageResult;
import com.yskc.rs.models.UserInfo;
import com.yskc.rs.models.material.AppMaterialModel;
import com.yskc.rs.models.material.ProjectMaterialModel;
import com.yskc.rs.models.material.QueryMaterialTrackModel;
import com.yskc.rs.models.project.DocMaterialModel;
import com.yskc.rs.models.project.QueryProjectMaterialModel;
import com.yskc.rs.models.projectmaterial.DepreciationRatioModel;
import com.yskc.rs.models.projectyieldconfig.ProjectYieldConfigModel;
import com.yskc.rs.models.projectyieldconfig.QueryYieldConfigModel;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author Administrator
 */
public interface ProjectMaterialService {
    /**
     * @param userInfo
     * @param model
     * @return
     * @throws OwnerException
     */
    boolean saveMaterial(UserInfo userInfo, ProjectMaterialModel model) throws OwnerException;

    /**
     * @param userInfo
     * @param model
     * @return
     * @throws OwnerException
     */
    boolean saveBillNo(UserInfo userInfo, ProjectMaterialModel model) throws OwnerException;


    /**
     * 查询项目使用物料
     *
     * @param companyId
     * @param query
     * @return
     */
    PageResult queryProjectMaterial(Integer companyId, QueryProjectMaterialModel query);

    /**
     * @param userInfo
     * @param projectId
     * @param materialDataId
     * @param acqMonth
     * @param rdType
     * @param used
     * @param id
     * @return
     * @throws OwnerException
     */
    boolean delMaterial(UserInfo userInfo, Integer projectId, Integer materialDataId, Date acqMonth, Integer rdType, BigDecimal used, int id) throws OwnerException;

    /**
     * 查询物料列表
     *
     * @param companyId
     * @param query
     * @return
     */
    PageResult queryMaterialAndQuantity(Integer companyId, QueryProjectMaterialModel query);

    /**
     * @param userInfo
     * @param model
     * @return
     * @throws OwnerException
     */
    boolean conditionalAddition(UserInfo userInfo, AppMaterialModel model) throws OwnerException;

    /**
     * @param userInfo
     * @param model
     * @return
     * @throws OwnerException
     */
    boolean conditionalAdditionBillNo(UserInfo userInfo, AppMaterialModel model) throws OwnerException;

//	ProjectMaterialModel countCost(AppMaterialModel model);

    /**
     * @param userInfo
     * @param model
     * @return
     * @throws OwnerException
     */
    boolean delSelect(UserInfo userInfo, ProjectMaterialModel model) throws OwnerException;

    /**
     * @param userInfo
     * @param model
     * @return
     * @throws OwnerException
     */
    boolean editMaterial(UserInfo userInfo, List<AppMaterialModel> model) throws OwnerException;

    /**
     * @param userInfo
     * @param query
     * @return
     */
    PageModel<List<DocMaterialModel>> getDocMaterialList(UserInfo userInfo, QueryProjectMaterialModel query);

    /**
     * @param companyId
     * @param query
     * @return
     */
    PageModel<List<AppMaterialModel>> queryMaterialTrack(Integer companyId, QueryMaterialTrackModel query);

    /**
     * @param userInfo
     * @param model
     * @return
     * @throws OwnerException
     */
    boolean editMaterialTrack(UserInfo userInfo, List<AppMaterialModel> model) throws OwnerException;

    /**
     * 设置损耗率
     *
     * @param userInfo
     * @param ratioModel
     * @return
     * @throws OwnerException
     */
    boolean setDepreciationRatio(UserInfo userInfo, DepreciationRatioModel ratioModel) throws OwnerException;

    /**
     * 设置试制量
     *
     * @param userInfo
     * @param yieldMap
     * @param ratioMap
     * @param config
     * @return
     * @throws OwnerException
     */
    boolean setYield(UserInfo userInfo, Map<String, ProjectYieldConfigModel> yieldMap, Map<String, BigDecimal> ratioMap, QueryYieldConfigModel config) throws OwnerException;
}
