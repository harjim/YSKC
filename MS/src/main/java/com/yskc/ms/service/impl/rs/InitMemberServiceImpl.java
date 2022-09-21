package com.yskc.ms.service.impl.rs;

import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yskc.common.model.PageModel;
import com.yskc.common.utils.PageUtils;
import com.yskc.ms.dao.rs.InitMemberDao;
import com.yskc.ms.models.rs.InitMemberModel;
import com.yskc.ms.models.rs.QueryProjectInitMemberModel;
import com.yskc.ms.service.rs.InitMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.service.impl
 * @Author: zhangdingfu
 * @CreateTime: 2019-07-23 17:18
 * @Description: 初始化项目人员列表业务实现层
 */
@Service
public class InitMemberServiceImpl implements InitMemberService {

    @Autowired
    private InitMemberDao initMemberDao;

    @Override
    public Map<Integer, Map<String,Long>> getMemberByProAndYear(List<Integer> projectIds, Integer year) {
        return initMemberDao.getMemberByProAndYear(projectIds, year);
    }

    @Override
    public PageModel<List<InitMemberModel>> getList(QueryProjectInitMemberModel query) {
        Pagination page = query.getPagination();
        if (CollectionUtils.isEmpty(page.getAscs()) && CollectionUtils.isEmpty(page.getDescs())) {
            List<String> asc = new ArrayList<>();
            List<String> desc = new ArrayList<>();
            asc.add("rde.etype");
            desc.add("im.enumber");
            page.setAscs(asc);
            page.setDescs(desc);
        }
        return PageUtils.build(page,initMemberDao.getMembers(page,query));
    }

}
