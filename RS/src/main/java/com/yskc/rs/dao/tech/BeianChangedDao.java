package com.yskc.rs.dao.tech;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yskc.rs.entity.tech.BeianChangedEntity;
import com.yskc.rs.entity.tech.BeianEntity;
import com.yskc.rs.models.tech.BeianChangedModel;
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
 * @author wjy
 * @since 2022-09-21
 */
@Repository
public interface BeianChangedDao extends BaseMapper<BeianChangedEntity> {
    /**
     * 根据beianId获取数据
     * @param beianId
     * @param companyId
     * @return
     */
    List<BeianChangedModel> getByBeianId(@Param("beianId") Integer beianId, @Param("companyId") Integer companyId);

    /**
     * 批量插入
     * @param list
     * @return
     */
    Integer insertList(@Param("list") List<BeianChangedEntity> list);

    /**
     * 批量更新
     * @param list
     * @return
     */
    Integer updateList(@Param("list") List<BeianChangedEntity> list);
}
