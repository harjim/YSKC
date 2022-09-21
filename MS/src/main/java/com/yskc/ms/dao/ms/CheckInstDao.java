package com.yskc.ms.dao.ms;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yskc.ms.entity.ms.CheckInstEntity;
import com.yskc.ms.models.checkInst.CheckInstModel;
import com.yskc.ms.models.checkInst.CheckMiniModel;
import com.yskc.ms.models.checkInst.QueryCheckInstModel;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CheckInstDao extends BaseMapper<CheckInstEntity> {
    /**
     * 查询查新机构列
     * @param query
     * @param page
     * @return
     */
    List<CheckInstModel> getList(@Param("query") QueryCheckInstModel query, @Param("page")Pagination page);

    /**
     * 根据机构名称模糊查询
     * @param checkInstName
     * @return
     */
    List<CheckMiniModel> getMiniList(@Param("checkInstName")String checkInstName);

    /**
     * 查询重复机构名
     * @param model
     * @return
     */
    CheckInstModel getInstName(@Param("model")CheckInstModel model);

    /**
     * 添加
     * @param entity
     * @return
     */
    @Options(useGeneratedKeys = true)
    Integer addCheckInst(@Param("entity")CheckInstEntity entity);
}
