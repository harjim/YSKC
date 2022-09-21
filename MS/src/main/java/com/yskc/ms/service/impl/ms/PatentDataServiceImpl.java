package com.yskc.ms.service.impl.ms;

import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yskc.common.model.PageModel;
import com.yskc.common.utils.PageUtils;
import com.yskc.ms.dao.ms.PatentDataDao;
import com.yskc.ms.models.DataPermModel;
import com.yskc.ms.models.patent.PatentDataModel;
import com.yskc.ms.models.patent.QueryPatentDataModel;
import com.yskc.ms.service.ms.PatentDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hck
 * on 2020/9/11 8:34
 * description:专利数据接口实现类
 */
@Service
public class PatentDataServiceImpl implements PatentDataService {

    @Autowired
    private PatentDataDao patentDataDao;

    @Override
    public PageModel<List<PatentDataModel>> getList(QueryPatentDataModel query, DataPermModel perm) {
        Pagination page = query.getPagination();
        if (CollectionUtils.isEmpty(page.getAscs()) && CollectionUtils.isEmpty(page.getDescs())) {
            List<String> desc = new ArrayList<>();
            desc.add("publicDate");
            page.setDescs(desc);
        }
        return PageUtils.build(page, patentDataDao.getList(page, query,perm));
    }
}
