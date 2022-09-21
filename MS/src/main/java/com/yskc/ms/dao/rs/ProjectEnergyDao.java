package com.yskc.ms.dao.rs;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yskc.ms.entity.rs.ProjectEnergyEntity;
import com.yskc.ms.models.projectAudit.QueryRdFeeModel;
import com.yskc.ms.models.rdfunds.EnergyFeesModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @author huronghua
 * @email 267267134@qq.com
 * @date 2019-07-17 15:32:14
 */
@Repository
public interface ProjectEnergyDao extends BaseMapper<ProjectEnergyEntity> {

    /**
     * 获取项目能源列表 分页
     *
     * @param page
     * @param model
     * @param monthEnd
     * @return
     */
    List<EnergyFeesModel> getCollectionList(@Param("page") Pagination page,
                                  @Param("model") QueryRdFeeModel model,
                                  @Param("monthEnd") Date monthEnd);

}
