package com.yskc.ms.dao.ms;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yskc.ms.entity.ms.MonthlyReport;
import org.springframework.stereotype.Repository;

/**
 * @BelongsProject: ms
 * @BelongsPackage: com.yskc.ms.dao.ms
 * @Author: zhangdingfu
 * @CreateTime: 2021-08-10 09:39
 * @Description: 月统计
 */
@Repository
public interface MonthlyReportDao extends BaseMapper<MonthlyReport> {
}
