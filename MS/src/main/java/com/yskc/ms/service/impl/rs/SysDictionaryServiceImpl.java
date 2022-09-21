package com.yskc.ms.service.impl.rs;

import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.common.utils.PageUtils;
import com.yskc.ms.dao.rs.SysDictionaryDao;
import com.yskc.ms.entity.rs.SysDictionaryEntity;
import com.yskc.ms.entity.rs.models.SysDictionaryEntityModel;
import com.yskc.ms.enums.DictionaryTypeEnum;
import com.yskc.ms.models.SysDictionaryModel;
import com.yskc.ms.models.SysDictionaryParams;
import com.yskc.ms.service.rs.SysDictionaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.sql.Array;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 字典服务
 *
 * @author huronghua
 */
@Service
public class SysDictionaryServiceImpl implements SysDictionaryService {
    @Autowired
    private SysDictionaryDao sysDictionaryDao;

    /**
     * 获取字典
     *
     * @param type
     * @return
     */
    @Override
    public List<SysDictionaryModel> getDictionaryByType(Integer type) {
        List<SysDictionaryModel> sysDictionaryEntityList = sysDictionaryDao.getDictionaryByType(type);
        Map<String, List<SysDictionaryModel>> childrenMap = new HashMap<>();
        for (SysDictionaryModel sysDictModel :
                sysDictionaryEntityList) {
            String cKey = sysDictModel.getKey();
            String pKey = sysDictModel.getParentKey();
            // 设置当前项的子列表
            List<SysDictionaryModel> children = childrenMap.computeIfAbsent(cKey, k -> new ArrayList<>());
            sysDictModel.setChildren(children);
            //将当前项加入父项的列表
            List<SysDictionaryModel> pChildren = childrenMap.computeIfAbsent(pKey, k -> new ArrayList<>());
            pChildren.add(sysDictModel);
        }
        // 返回顶级节点
        return childrenMap.get("0");
    }

    @Override
    public Map<String, List<SysDictionaryModel>> getCustomerDictionary() {
        List<Integer> types = new ArrayList<>();
        Map<String, List<SysDictionaryModel>> result = new HashMap<>();
        types.add(DictionaryTypeEnum.CURRENCY.getCode());
        types.add(DictionaryTypeEnum.INDUSTRY.getCode());
        types.add(DictionaryTypeEnum.AREA_CODE.getCode());
        List<SysDictionaryModel> list = sysDictionaryDao.getCustomerDictionary(types);
        Map<Integer, List<SysDictionaryModel>> map = list.stream().collect(Collectors.groupingBy(a -> a.getType()));
        Map<String, List<SysDictionaryModel>> childrenMap = new HashMap<>();
        for (SysDictionaryModel sysDictModel :
                map.get(DictionaryTypeEnum.CURRENCY.getCode())) {
            String cKey = sysDictModel.getKey();
            String pKey = sysDictModel.getParentKey();
            // 设置当前项的子列表
            List<SysDictionaryModel> children = childrenMap.computeIfAbsent(cKey, k -> new ArrayList<>());
            sysDictModel.setChildren(children);
            //将当前项加入父项的列表
            List<SysDictionaryModel> pChildren = childrenMap.computeIfAbsent(pKey, k -> new ArrayList<>());
            pChildren.add(sysDictModel);
        }
        result.put("currency",childrenMap.get("0"));
        childrenMap = new HashMap<>();
        for (SysDictionaryModel sysDictModel :
                map.get(DictionaryTypeEnum.INDUSTRY.getCode())) {
            String cKey = sysDictModel.getKey();
            String pKey = sysDictModel.getParentKey();
            // 设置当前项的子列表
            List<SysDictionaryModel> children = childrenMap.computeIfAbsent(cKey, k -> new ArrayList<>());
            sysDictModel.setChildren(children);
            //将当前项加入父项的列表
            List<SysDictionaryModel> pChildren = childrenMap.computeIfAbsent(pKey, k -> new ArrayList<>());
            pChildren.add(sysDictModel);
        }
        result.put("industry",childrenMap.get("0"));
        childrenMap = new HashMap<>();
        for (SysDictionaryModel sysDictModel :
                map.get(DictionaryTypeEnum.AREA_CODE.getCode())) {
            String cKey = sysDictModel.getKey();
            String pKey = sysDictModel.getParentKey();
            // 设置当前项的子列表
            List<SysDictionaryModel> children = childrenMap.computeIfAbsent(cKey, k -> new ArrayList<>());
            sysDictModel.setChildren(children);
            //将当前项加入父项的列表
            List<SysDictionaryModel> pChildren = childrenMap.computeIfAbsent(pKey, k -> new ArrayList<>());
            pChildren.add(sysDictModel);
        }

        result.put("areaCode",childrenMap.get("0"));
        return result;
    }

    @Override
    public List<SysDictionaryModel> getFinanceDictionary() {
        return sysDictionaryDao.getDictionaryByType(14);
    }

    @Override
    public PageModel<List<SysDictionaryEntityModel>> queryList(SysDictionaryParams params) {
        Pagination page = params.getPagination();
        if (CollectionUtils.isEmpty(page.getAscs()) && CollectionUtils.isEmpty(page.getDescs())) {
            List<String> asc = new ArrayList<>();
            asc.add("`type`");
            asc.add("`key`");
            page.setAscs(asc);
        }
        List<SysDictionaryEntityModel> list = sysDictionaryDao.queryList(page,params);

        return PageUtils.build(page, list);
    }

    @Override
    public List<SysDictionaryEntity> findKey(String key,Integer type) {
        if(StringUtils.isEmpty(key)){
            return null;
        }
        return sysDictionaryDao.findKey(key,type);
    }

    @Override
    public Integer insert(SysDictionaryEntity entity) throws OwnerException {
        Integer count = sysDictionaryDao.findBean(entity);
        if( count > 0 ){
            throw new OwnerException(DictionaryTypeEnum.getDictionaryTypeName(entity.getType())
            +"已存在key【"+entity.getKey()+"】,请勿重复添加");
        }
        return sysDictionaryDao.insert(entity);
    }

    @Override
    public Integer del(Integer id) throws OwnerException {
        SysDictionaryEntity entity = sysDictionaryDao.selectById(id);
        Integer count = sysDictionaryDao.findByKey(entity.getKey());
        if( count > 0 ){
            throw new OwnerException("该节点下有子节点不能删除");
        }
        return sysDictionaryDao.deleteById(id);
    }

    @Override
    public Integer edit(SysDictionaryEntity entity) throws OwnerException {
        Integer count = sysDictionaryDao.findBean(entity);
        if(count>0){
            throw new OwnerException("已有重复的键值不能修改为该键");
        }
        return sysDictionaryDao.updateById(entity);
    }

    @Override
    public Integer delAll(List<Integer> ids) throws OwnerException {
        List<SysDictionaryEntity> sysDictionaryEntities = sysDictionaryDao.selectBatchIds(ids);
        for (SysDictionaryEntity entity:sysDictionaryEntities) {
            Integer count = sysDictionaryDao.findByKey(entity.getKey());
            if( count > 0 ){
                throw new OwnerException("该节点下有子节点不能删除");
            }
        }
        return sysDictionaryDao.deleteBatchIds(ids);
    }


}
