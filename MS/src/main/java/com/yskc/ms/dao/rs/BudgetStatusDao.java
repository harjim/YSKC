package com.yskc.ms.dao.rs;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yskc.ms.entity.rs.BudgetStatus;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @BelongsProject: ms
 * @BelongsPackage: com.yskc.ms.dao.rs
 * @Author: zhangdingfu
 * @CreateTime: 2020-07-15 17:54
 * @Description: 预算状态dao层
 */
@Repository
public interface BudgetStatusDao extends BaseMapper<BudgetStatus> {
    /**
     * 获取存在的数据
     *
     * @param projectId
     * @return
     */
    BudgetStatus getExist(@Param("projectId") Integer projectId);
}
