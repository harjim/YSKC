package com.yskc.rs.dao.tech;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yskc.rs.entity.tech.BeianEntity;
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
public interface BeianDao extends BaseMapper<BeianEntity> {
    /**
     * 获取备案管理列表
     * @param page
     * @param companyId
     * @param query
     * @return
     */
    List<BeianInfoModel> getList(@Param("page") Pagination page, @Param("companyId") Integer companyId, @Param("query") QueryBeianModel query);

    /**
     * 获取备案信息
     * @param beianId
     * @param companyId
     * @return
     */
    BeianInfoModel getInfo(@Param("beianId") Integer beianId, @Param("companyId") Integer companyId);
}
