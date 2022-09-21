package com.yskc.docservice.dao.rs;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yskc.docservice.entity.rs.tech.TechProjectCostEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.dao.tech
 * @Author: zhangdingfu
 * @CreateTime: 2019-09-24 17:33
 * @Description: 项目支出dao层
 */
@Repository
public interface TechProjectCostDao extends BaseMapper<TechProjectCostEntity> {

    /**
     * 批量插入数据
     *
     * @param costList
     * @return
     */
    int addList(@Param("costList") List<TechProjectCostEntity> costList);
}
