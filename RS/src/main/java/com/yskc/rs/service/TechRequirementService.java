package com.yskc.rs.service;

import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.rs.entity.TechRequirement;
import com.yskc.rs.models.BatchDeleteModel;
import com.yskc.rs.models.UserInfo;
import com.yskc.rs.models.techrequirement.QueryTechRequirement;
import com.yskc.rs.models.techrequirement.TechRequirementModel;

import java.util.List;

/**
 * @BelongsProject: ms
 * @BelongsPackage: com.yskc.ms.service.ms
 * @Author: zhangdingfu
 * @CreateTime: 2020-05-21 09:22
 * @Description: 技术需求业务接口层
 */
public interface TechRequirementService {
    /**
     * 获取技术需求列表
     *
     * @param query
     * @param companyId
     * @return
     */
    PageModel<List<TechRequirementModel>> getList(QueryTechRequirement query,Integer companyId);

    /**
     * 添加技术需求
     *
     * @param techRequirement
     * @param userInfo
     * @return
     */
    Boolean add(TechRequirement techRequirement, UserInfo userInfo);

    /**
     * 更新技术需求
     *
     * @param requirement
     * @param userInfo
     * @return
     * @throws OwnerException
     */
    Boolean update(TechRequirement requirement, UserInfo userInfo) throws OwnerException;

    /**
     * 删除技术需求
     *
     * @param deleteModel
     * @return
     */
    Boolean delete(BatchDeleteModel deleteModel);

    /**
     * 获取附件地址
     *
     * @param id
     * @return
     */
    String getFilePath(Integer id);

    /**
     * 作废技术需求
     * @param userInfo
     * @param batchModel
     * @return
     */
    boolean invalid(UserInfo userInfo, BatchDeleteModel batchModel);
}
