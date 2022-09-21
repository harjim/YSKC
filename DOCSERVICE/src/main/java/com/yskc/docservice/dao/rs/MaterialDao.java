package com.yskc.docservice.dao.rs;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yskc.docservice.entity.rs.MaterialEntity;
import com.yskc.docservice.models.rs.material.MaterialPlanModel;
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

    /**
     * 批量插入
     * @param list
     * @return
     */
    @Options(useGeneratedKeys = true)
    Integer addBatch(List<MaterialEntity> list);

    List<MaterialEntity> getByMcodes(@Param("companyId") Integer companyId, @Param("mcodeList") List<String> mcodeList, @Param("type") Integer type);

    /**
     * 获取用料计划表数据
     *
     * @param projectId
     * @return
     */
    List<MaterialPlanModel> getMaterialPlan(@Param("projectId") Integer projectId,
                                            @Param("beginDate") Date beginDate, @Param("endDate") Date endDate,
                                            @Param("costTypes") List<Integer> costTypes,@Param("type")Integer type);
}
