package com.yskc.ms.dao.rs;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yskc.ms.entity.rs.MaterialEntity;
import com.yskc.ms.models.doc.MaterialTypeModel;
import com.yskc.ms.models.projectAudit.QueryRdFeeModel;
import com.yskc.ms.models.projectAudit.RdFeeMaterialModel;
import com.yskc.ms.models.rs.MaterialPlanModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Created by hck
 * on 2020/10/21 13:42
 * description:
 */
@Repository
public interface MaterialDao extends BaseMapper<MaterialEntity> {
    /**
     * 获取用料计划表数据
     *
     * @param projectId
     * @param beginDate
     * @param endDate
     * @param costTypes
     * @param type
     * @return
     */
    List<MaterialPlanModel> getMaterialPlanData(@Param("projectId") Integer projectId, @Param("beginDate") Date beginDate,
                                                @Param("endDate") Date endDate, @Param("costTypes") List<Integer> costTypes,
                                                @Param("type") Integer type);

    /**
     * 据项目月份返回材料数据
     * @param page
     * @param query
     * @param monthend
     * @return
     */
    List<RdFeeMaterialModel> getMaterialFeesData(@Param("page") Pagination page, @Param("query")QueryRdFeeModel query,
                                                 @Param("monthend")Date monthend);

    /**
     * 获取区间物料
     * @param projectId
     * @param begin
     * @param end
     * @return
     */
    List<MaterialTypeModel> getRangeMaterials(@Param("projectId") Integer projectId,@Param("begin") Date begin,@Param("end") Date end);
}
