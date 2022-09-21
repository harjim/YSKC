package com.yskc.ms.service.rs;

import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.ms.entity.rs.SysDictionaryEntity;
import com.yskc.ms.entity.rs.models.SysDictionaryEntityModel;
import com.yskc.ms.models.SysDictionaryModel;
import com.yskc.ms.models.SysDictionaryParams;

import java.util.List;
import java.util.Map;

/**
 * 字典服务
 * @author huronghua
 */
public interface SysDictionaryService {
    /**
     * 获取字典
     * @param type
     * @return
     */
    List<SysDictionaryModel> getDictionaryByType(Integer type);

    Map<String, List<SysDictionaryModel>> getCustomerDictionary();

    /**
     * 获取单位财务字典
     * @return
     */
    List<SysDictionaryModel> getFinanceDictionary();

    PageModel<List<SysDictionaryEntityModel >> queryList(SysDictionaryParams params);

    List<SysDictionaryEntity> findKey(String key,Integer type);

    Integer insert(SysDictionaryEntity entity) throws OwnerException;

    Integer del(Integer id) throws OwnerException;

    Integer edit(SysDictionaryEntity entity) throws OwnerException;

    Integer delAll(List<Integer> ids) throws OwnerException;
}
