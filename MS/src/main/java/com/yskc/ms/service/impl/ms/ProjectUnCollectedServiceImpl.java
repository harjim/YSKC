package com.yskc.ms.service.impl.ms;

import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yskc.common.model.PageModel;
import com.yskc.common.utils.PageUtils;
import com.yskc.ms.dao.ms.ProjectUnCollectedDao;
import com.yskc.ms.models.params.PageParams;
import com.yskc.ms.models.projectuncollected.ProjectUnCollectedModel;
import com.yskc.ms.service.ms.ProjectUnCollectedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @program: ms
 * @description: 未归集项目服务实现类
 * @author: cyj
 * @create: 2021-12-13 08:04
 **/
@Service
public class ProjectUnCollectedServiceImpl implements ProjectUnCollectedService {
    @Autowired
    private ProjectUnCollectedDao projectUnCollectedDao;

    @Override
    public PageModel<List<ProjectUnCollectedModel>> getList(PageParams query) {
        Pagination page = query.getPagination();
        if (CollectionUtils.isEmpty(page.getAscs()) && CollectionUtils.isEmpty(page.getDescs())) {
            page.setAscs(Arrays.asList("r.createTime"));
        }
        List<ProjectUnCollectedModel> list = projectUnCollectedDao.getList(page);
        return PageUtils.build(page,list);
    }
}
