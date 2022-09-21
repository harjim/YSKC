package com.yskc.ms.service.impl.rs;

import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yskc.ms.dao.rs.RsStageDao;
import com.yskc.ms.models.QueryStageModel;
import com.yskc.ms.models.SysDictionaryModel;
import com.yskc.ms.models.rs.RsStageModel;
import com.yskc.ms.service.rs.StageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @DateTime: 2021/9/15 14:31
 * @Description:
 * @author: hsx
 */
@Service
public class StageServiceImpl implements StageService {

    @Autowired
    private RsStageDao rsStageDao;

    @Override
    public List<SysDictionaryModel> searchStage() {
        return rsStageDao.searchStage();
    }

    @Override
    public List<RsStageModel> queryStage(QueryStageModel queryStageModel) {
        return rsStageDao.queryStageList(queryStageModel);
    }
}
