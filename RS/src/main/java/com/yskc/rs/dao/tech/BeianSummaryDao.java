package com.yskc.rs.dao.tech;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yskc.rs.entity.tech.BeianChangedEntity;
import com.yskc.rs.entity.tech.BeianEntity;
import com.yskc.rs.entity.tech.BeianSummaryEntity;
import com.yskc.rs.models.tech.BeianInfoModel;
import com.yskc.rs.models.tech.QueryBeianModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zdf-hck123
 * @since 2021-03-18
 */
@Repository
public interface BeianSummaryDao extends BaseMapper<BeianSummaryEntity> {

    Integer updateByBeian(@Param("entity")BeianSummaryEntity entity);

    /**
     * 根据beianId获取id
     * @param beianId
     * @param companyId
     * @return
     */
    Integer getByBeian(@Param("beianId") Integer beianId, @Param("companyId") Integer companyId);

}
