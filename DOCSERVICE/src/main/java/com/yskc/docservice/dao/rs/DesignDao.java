package com.yskc.docservice.dao.rs;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yskc.docservice.entity.rs.DesignEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author huronghua
 * @email 267267134@qq.com
 * @date 2019-07-11 15:28:04
 */
@Repository
public interface DesignDao extends BaseMapper<DesignEntity> {

    /**
     * 批量添加
     *
     * @param designEntityList
     * @return
     */
    Integer addDesignBatch(@Param("designEntityList") List<DesignEntity> designEntityList);


    Integer updateList(@Param("designEntities") List<DesignEntity> designEntities);

}
