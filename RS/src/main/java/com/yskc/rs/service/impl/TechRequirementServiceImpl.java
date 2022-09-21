package com.yskc.rs.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.common.utils.PageUtils;
import com.yskc.rs.dao.TechRequirementDao;
import com.yskc.rs.entity.TechRequirement;
import com.yskc.rs.enums.TechRequirementEnum;
import com.yskc.rs.models.BatchDeleteModel;
import com.yskc.rs.models.UserInfo;
import com.yskc.rs.models.techrequirement.QueryTechRequirement;
import com.yskc.rs.models.techrequirement.TechRequirementModel;
import com.yskc.rs.service.TechRequirementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.text.MessageFormat;
import java.util.Date;
import java.util.List;

/**
 * @BelongsProject: ms
 * @BelongsPackage: com.yskc.ms.service.impl.ms
 * @Author: zhangdingfu
 * @CreateTime: 2020-05-21 09:22
 * @Description: 技术需求业务实现层
 */
@Service
public class TechRequirementServiceImpl implements TechRequirementService {

    @Autowired
    private TechRequirementDao techRequirementDao;

    @Override
    public PageModel<List<TechRequirementModel>> getList(QueryTechRequirement query, Integer companyId) {
        Pagination page = query.getPagination();
        if (CollectionUtils.isEmpty(page.getDescs()) && CollectionUtils.isEmpty(page.getAscs())) {
            page.setDescs(CollUtil.newArrayList("lastUpdateTime"));
        }
        return PageUtils.build(page, techRequirementDao.getList(page, query, companyId));
    }

    @Override
    public Boolean add(TechRequirement techRequirement, UserInfo userInfo) {
        Date now = new Date();
        if (techRequirement.getDescription() == null) {
            techRequirement.setDescription("");
        }
        techRequirement.setCompanyId(userInfo.getCompanyId());
        techRequirement.setCreateTime(now);
        techRequirement.setCreatorId(userInfo.getUserId());
        techRequirement.setLastUpdatorId(userInfo.getUserId());
        techRequirement.setLastUpdateTime(now);
        techRequirement.setMsCreatorId(userInfo.getMsUserId());
        techRequirement.setMsLastUpdatorId(userInfo.getMsUserId());
        techRequirement.setStatus(TechRequirementEnum.NORMAL.getStatus());
        return techRequirementDao.insert(techRequirement) > 0;
    }

    @Override
    public Boolean update(TechRequirement requirement, UserInfo userInfo) throws OwnerException {
        Integer id = techRequirementDao.checkExistId(requirement.getId());
        if (id == null) {
            throw new OwnerException(MessageFormat.format("技术需求名称：[{0}]不存在，请稍后再试。", requirement.getTechName()));
        }
        if (requirement.getDescription() == null) {
            requirement.setDescription("");
        }
        requirement.setCompanyId(userInfo.getCompanyId());
        requirement.setLastUpdatorId(userInfo.getUserId());
        requirement.setMsLastUpdatorId(userInfo.getMsUserId());
        requirement.setStatus(TechRequirementEnum.NORMAL.getStatus());
        requirement.setLastUpdateTime(new Date());
        return techRequirementDao.updateById(requirement) > 0;
    }

    @Override
    public Boolean delete(BatchDeleteModel deleteModel) {
        return techRequirementDao.deleteBatchIds(deleteModel.getIds()) > 0;
    }

    @Override
    public String getFilePath(Integer id) {
        return techRequirementDao.getFilePath(id);
    }

    @Override
    public boolean invalid(UserInfo userInfo, BatchDeleteModel batchModel) {
        return techRequirementDao.updateStatus(
                batchModel.getIds(), userInfo.getUserId(), userInfo.getMsUserId(),
                new Date(), TechRequirementEnum.INVALID.getStatus()) > 0;
    }
}
