package com.yskc.ms.dao.ms;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yskc.ms.entity.ms.DailyReport;
import org.springframework.stereotype.Repository;

/**
 * @BelongsProject: ms
 * @BelongsPackage: com.yskc.ms.dao.ms
 * @Author: zhangdingfu
 * @CreateTime: 2021-08-03 16:45
 * @Description: 用户审核汇总dao层
 */
@Repository
public interface DailyReportDao extends BaseMapper<DailyReport> {
}
