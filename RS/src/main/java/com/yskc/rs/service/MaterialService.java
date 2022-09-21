package com.yskc.rs.service;

import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.rs.entity.MaterialEntity;
import com.yskc.rs.models.UserInfo;
import com.yskc.rs.models.excel.MaterialExcel;
import com.yskc.rs.models.material.*;

import java.util.Date;
import java.util.List;

/**
 * @author Administrator
 */
public interface MaterialService {

    /**
     * 查询物料清单
     *
     * @param userInfo
     * @param query
     * @return
     */
    PageModel<List<AppMaterialModel>> queryMaterial(UserInfo userInfo, QueryMaterialModel query);

    /**
     * 新增
     *
     * @param userInfo
     * @param model
     * @return
     */
    boolean addMaterial(UserInfo userInfo, MaterialEntity model) throws OwnerException;

    /**
     * 修改
     *
     * @param userInfo
     * @param model
     * @return
     */
    boolean editMaterial(UserInfo userInfo, MaterialEntity model) throws OwnerException;

    /**
     * 删除
     *
     * @param companyId
     * @param model
     * @return
     * @throws OwnerException
     */
    boolean delMaterial(Integer companyId, MaterialEntity model) throws OwnerException;

    /**
     * 导入物料清单
     *
     * @param userInfo
     * @param materialExcels
     * @param type
     * @param year
     * @param isRD
     * @return
     * @throws OwnerException
     */
    ImportMaterialModel importMaterial(UserInfo userInfo, List<MaterialExcel> materialExcels, Integer type,
                                       Integer year, Boolean isRD) throws OwnerException;

    /**
     * 确认导出
     *
     * @param info
     * @param data
     * @param type
     * @param year
     * @param isRD
     * @return
     * @throws OwnerException
     */
    boolean confirmImport(UserInfo info, List<MaterialExcel> data, Integer type, Integer year
            , Boolean isRD) throws OwnerException;

    /**
     * 导出物料清单
     *
     * @param model
     * @return
     */
    List<MaterialExcel> exportMaterialModel(AppMaterialModel model);

    /**
     * @param userInfo
     * @param model
     * @return
     */
    boolean editDept(UserInfo userInfo, AppMaterialModel model);

    /**
     * @param userInfo
     * @param model
     * @return
     */
    boolean setType(UserInfo userInfo, AppMaterialModel model);

    /**
     * @param userInfo
     * @param ids
     * @param acqMonth
     * @return
     * @throws OwnerException
     */
    boolean delSelect( List<Integer> ids) throws OwnerException;

    /**
     * 获取用料计划表数据
     * @param model
     * @return
     */
    PageModel<List<MaterialPlanModel>> getMaterialPlan(QueryMaterialTrackModel model);

    Boolean setPicker(SetMaterialPickerModel setPicker, UserInfo userInfo);

    /**
     * 查询试制产量计划
     * @param projectId
     * @return
     */
    //Map<String, List<MaterialModel>> getMaterial(Integer projectId);

}
