package com.yskc.ms.service.impl.ms;

import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yskc.common.model.PageModel;
import com.yskc.common.utils.PageUtils;
import com.yskc.ms.dao.ms.PolicyContentDao;
import com.yskc.ms.models.DataPermModel;
import com.yskc.ms.models.policy.PolicyContentModel;
import com.yskc.ms.models.policy.QueryPolicyContent;
import com.yskc.ms.service.ms.PolicyContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @BelongsProject: ms
 * @BelongsPackage: com.yskc.ms.service.impl.ms
 * @Author: zhangdingfu
 * @CreateTime: 2020-04-25 09:32
 * @Description: 政策内容业务层接口实现层
 */
@Service
public class PolicyContentServiceImpl implements PolicyContentService {

    @Autowired
    private PolicyContentDao policyContentDao;

    @Override
    public PageModel<List<PolicyContentModel>> getList(QueryPolicyContent query, DataPermModel dataPerm,Integer userId) {
        Pagination page = query.getPagination();
        if (CollectionUtils.isEmpty(page.getAscs()) && CollectionUtils.isEmpty(page.getDescs())) {
            List<String> desc = new ArrayList<>();
            desc.add("pc.createTime");
            page.setDescs(desc);
        }
        return PageUtils.build(page, policyContentDao.getList(page, query,dataPerm,userId));
    }
}
