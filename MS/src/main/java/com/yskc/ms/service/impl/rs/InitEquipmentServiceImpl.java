package com.yskc.ms.service.impl.rs;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yskc.common.model.PageModel;
import com.yskc.common.utils.PageUtils;
import com.yskc.ms.dao.rs.InitEquipmentDao;
import com.yskc.ms.models.projectApproval.QueryProjectEquipmentModel;
import com.yskc.ms.models.rs.summary.InitEquipmentModel;
import com.yskc.ms.service.rs.InitEquipmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @DateTime: 2021/9/15 14:17
 * @Description:
 * @author: hsx
 */

@Service
public class InitEquipmentServiceImpl implements InitEquipmentService {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private InitEquipmentDao initEquipmentDao;

    @Override
    public PageModel<List<InitEquipmentModel>> getList(QueryProjectEquipmentModel query) {
        Pagination page = query.getPagination();
        if (CollectionUtils.isEmpty(page.getAscs()) && CollectionUtils.isEmpty(page.getDescs())) {
            List<String> asc = new ArrayList<>();
            asc.add("ie.id");
            page.setAscs(asc);
        }
        return PageUtils.build(page,initEquipmentDao.getList(page,query));
    }
}
