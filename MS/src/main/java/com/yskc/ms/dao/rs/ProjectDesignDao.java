package com.yskc.ms.dao.rs;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yskc.ms.entity.rs.ProjectDesignEntity;
import com.yskc.ms.models.projectAudit.QueryRdFeeModel;
import com.yskc.ms.models.rdfunds.DesignFeesModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author huronghua
 * @email 267267134@qq.com
 * @date 2019-07-15 11:06:55
 */
@Repository
public interface ProjectDesignDao extends BaseMapper<ProjectDesignEntity> {
    /**
     * 获取项目下的设计费用
     *
     * @param page
     * @param model
     * @param endDate
     * @return
     */
    List<DesignFeesModel> queryProjectDesign(@Param("page") Pagination page,
                                             @Param("model") QueryRdFeeModel model,
                                             @Param("endDate") Date endDate);
}
