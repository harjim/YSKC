package com.yskc.docservice.dao.rs;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yskc.docservice.entity.rs.MonthCostEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @BelongsProject:
 * @BelogsPackage: src/main/java/com/yskc/docservice/dao/rs
 * @Author: hm
 * @CreateTime: 2022/8/17
 * @Description: 月度成本相关数据库操作
 */
@Repository
public interface YearCostDao extends BaseMapper< MonthCostEntity > {
    /**
     * 导入(增加不为空数据部分)
     * @params list
     * @return
     */
    Integer insertOrUpdate(@Param("list")List<MonthCostEntity> list);

    /**
     * 导入(删除为空数据部分)
     * @params list
     * @return
     */
    Integer deleteCost(@Param("list")List< MonthCostEntity> list);
}
