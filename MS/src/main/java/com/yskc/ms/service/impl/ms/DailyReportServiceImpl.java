package com.yskc.ms.service.impl.ms;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yskc.common.model.PageModel;
import com.yskc.common.utils.PageUtils;
import com.yskc.ms.dao.ms.DailyReportDetailDao;
import com.yskc.ms.enums.DailyReportEnum;
import com.yskc.ms.models.DataPermModel;
import com.yskc.ms.models.dailyreport.QueryDailyReportModel;
import com.yskc.ms.service.ms.DailyReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;

/**
 * @BelongsProject: ms
 * @BelongsPackage: com.yskc.ms.service.impl.ms
 * @Author: zhangdingfu
 * @CreateTime: 2021-08-06 14:31
 * @Description: 工作报表业务实现层
 */
@Service
public class DailyReportServiceImpl implements DailyReportService {
    @Autowired
    private DailyReportDetailDao dailyReportDetailDao;

    @Override
    public PageModel<List<Map<String, Object>>> getDayList(QueryDailyReportModel query, DataPermModel dataPerm) {
        Pagination page = query.getPagination();
        if(CollectionUtils.isEmpty(page.getDescs()) && CollectionUtils.isEmpty(page.getAscs())){
            page.setDescs(CollUtil.newArrayList("workDate"));
        }
        return PageUtils.build(page,dailyReportDetailDao.getDayList(page,query,dataPerm, DailyReportEnum.getList()));
    }
}
