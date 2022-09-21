package com.yskc.ms.dao.es;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yskc.ms.entity.es.ConfigDataEntity;
import com.yskc.ms.models.newexpert.index.ConfigDataModel;
import com.yskc.ms.models.newexpert.index.QueryConfigDataModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @DateTime: 2021/9/26 16:07
 * @Description:
 * @author: hsx
 */
@Repository
public interface ConfigDataDao extends BaseMapper<ConfigDataEntity> {

    /**
     * 查询专家库首页数据配置信息
     *
     * @param page
     * @param query
     * @return
     */
    List<ConfigDataModel> getDataList(@Param("page") Pagination page, @Param("query") QueryConfigDataModel query);


    /**
     * 启用或禁用
     *
     * @param id
     * @param disabled
     * @param userId
     * @param now
     * @return
     */
    int updateDisabled(@Param("id") Integer id, @Param("disabled") Boolean disabled, @Param("userId") Integer userId, @Param("now") Date now);
}
