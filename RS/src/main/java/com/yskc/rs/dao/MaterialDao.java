package com.yskc.rs.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yskc.rs.entity.MaterialEntity;
import com.yskc.rs.models.excel.MaterialExcel;
import com.yskc.rs.models.material.AppMaterialModel;
import com.yskc.rs.models.material.MaterialPlanModel;
import com.yskc.rs.models.material.QueryMaterialModel;
import com.yskc.rs.models.material.SetMaterialPickerModel;
import com.yskc.rs.models.project.QueryProjectMaterialModel;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @author huronghua
 * @email 267267134@qq.com
 * @date 2019-07-10 15:54:06
 */
@Repository
public interface MaterialDao extends BaseMapper<MaterialEntity> {

    List<AppMaterialModel> queryMaterial(@Param("companyId") Integer companyId, @Param("pagination") Pagination pagination, @Param("query") QueryMaterialModel query);

    //根据项目的计划时间,查询符合的物料
    List<AppMaterialModel> queryMaterialByDate(@Param("companyId") Integer companyId, @Param("mcode") String mcode,
                                               @Param("mname") String mname, @Param("beginDate") Date beginDate,
                                               @Param("endDate") Date endDate);

    MaterialEntity getMaterialbyMcode(@Param("companyId") Integer companyId, @Param("mcode") String mcode);

    MaterialEntity queryMaterialById(@Param("materialId") Integer materialId, @Param("mcode") String mcode, @Param("mname") String mname);

    /**
     * 查询可分配的库存量
     *
     * @param page
     * @param companyId
     * @param startDate
     * @param endDate
     * @param query
     * @return
     */
    List<AppMaterialModel> queryMaterialPage(@Param("page") Pagination page, @Param("companyId") Integer companyId,
                                             @Param("startDate") Date startDate, @Param("endDate") Date endDate,
                                             @Param("query") QueryProjectMaterialModel query);

    List<AppMaterialModel> queryMaterialRemoveDataId(@Param("model") AppMaterialModel model);

    List<MaterialExcel> getMaterialData(@Param("model") AppMaterialModel model);

    List<AppMaterialModel> queryMaterialByBillNoList(@Param("companyId") Integer companyId,
                                                     @Param("billNoList") List<String> billNoList,
                                                     @Param("rdType") Integer rdType);

    @Options(useGeneratedKeys = true)
    Integer addBatch(List<MaterialEntity> list);

    Integer updateBatch(@Param("materialEntitys") List<MaterialEntity> materialEntitys);

    Integer updateRemainQuantityById(@Param("materialRemainQuantiries") List<MaterialEntity> materialRemainQuantiries);

    List<MaterialEntity> getByMcodes(@Param("companyId") Integer companyId, @Param("mcodeList") List<String> mcodeList, @Param("type") Integer type);

    List<MaterialEntity> queryMaterialList(@Param("materialIdList") List<Integer> materialIdList);

    /**
     * 获取用料计划表数据
     *
     * @param projectId
     * @return
     */
    List<MaterialPlanModel> getMaterialPlanData(@Param("page") Pagination page,@Param("projectId") Integer projectId,
                                                @Param("beginDate") Date beginDate, @Param("endDate") Date endDate,
                                                @Param("costTypes") List<Integer> costTypes,@Param("type")Integer type);


    /**
     * 获取用料计划表数据
     *
     * @param projectId
     * @return
     */
    List<MaterialPlanModel> getMaterialPlan(@Param("projectId") Integer projectId,
                                                @Param("beginDate") Date beginDate, @Param("endDate") Date endDate,
                                                @Param("costTypes") List<Integer> costTypes,@Param("type")Integer type);

    /**
     * 获取旧实例
     *
     * @param id
     * @return
     */
    MaterialEntity getOldQuantity(@Param("id") Integer id);

    /**
     * 获取材料/备品/辅料数据
     * @param page
     * @param projectId
     * @param beginDate
     * @param endDate
     * @param type
     * @param rdType
     * @return
     */
    List<MaterialPlanModel> getMaterials(@Param("page") Pagination page, @Param("projectId") Integer projectId,
                                         @Param("beginDate") Date beginDate, @Param("endDate") Date endDate,
                                         @Param("type") Integer type,@Param("rdType")Integer rdType);

    /**
     * 设置领料信息
     * @param picker
     * @param msUserId
     * @param userId
     * @param now
     * @return
     */
    int setPicker(@Param("picker") SetMaterialPickerModel picker,@Param("msUserId") Integer msUserId,
                       @Param("userId") Integer userId,@Param("now") Date now);
}
