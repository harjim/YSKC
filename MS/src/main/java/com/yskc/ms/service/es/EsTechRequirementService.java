package com.yskc.ms.service.es;

import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.ms.models.newexpert.techrequirement.EsQueryTechRequirementModel;
import com.yskc.ms.models.newexpert.techrequirement.EsTechRequirementModel;

import java.util.List;

/**
 * @author cyj
 */
public interface EsTechRequirementService {
    /**
     * 查询技术需求列表
     * @param query
     * @return
     */
    PageModel<List<EsTechRequirementModel>> getList(EsQueryTechRequirementModel query);

    /**
     * 获取技术需求详情
     * @param id
     * @return
     */
    EsTechRequirementModel getData(Integer id);

    /**
     * 编辑技术需求
     * @param model
     * @param id
     * @return
     */
    Boolean save(EsTechRequirementModel model,Integer id) throws OwnerException;

    /**
     * 修改技术需求状态
     * @param model
     * @param id
     * @return
     */
    Boolean editStatus(EsTechRequirementModel model,Integer id);

    /**
     * 删除技术需求
     * @param ids
     * @return
     */
    Boolean del(Integer[] ids) throws OwnerException;
}
