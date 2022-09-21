package com.yskc.ms.dao.ms;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yskc.ms.entity.ms.FlowEntity;
import com.yskc.ms.models.flow.FlowModel;
import com.yskc.ms.models.flow.QueryFlowModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by hck
 * on 2020/12/15 11:00
 * description:
 */
@Repository
public interface FlowDao extends BaseMapper<FlowEntity> {
    /**
     *获取流程列表
     * @return
     */
    List<FlowModel> getList(@Param("page") Pagination page, @Param("query") QueryFlowModel query);
}
