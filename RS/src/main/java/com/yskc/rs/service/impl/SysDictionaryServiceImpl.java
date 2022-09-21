package com.yskc.rs.service.impl;

import com.yskc.common.exception.OwnerException;
import com.yskc.rs.dao.SysDictionaryDao;
import com.yskc.rs.dao.init.TableFieldDao;
import com.yskc.rs.entity.init.TableField;
import com.yskc.rs.models.HighTecIndustryModel;
import com.yskc.rs.models.SysDictionaryModel;
import com.yskc.rs.models.UserInfo;
import com.yskc.rs.models.excel.TableFieldInfo;
import com.yskc.rs.service.SysDictionaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 字典服务
 *
 * @author huronghua
 */
@Service
public class SysDictionaryServiceImpl implements SysDictionaryService {
    @Autowired
    private SysDictionaryDao sysDictionaryDao;
    @Autowired
    private TableFieldDao tableFieldDao;

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
        for (SysDictionaryModel sysDictModel : sysDictionaryEntityList) {
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

    /**
     * 获取高新领域树
     *
     * @return
     */
    @Override
    public List<HighTecIndustryModel> getHighTecIndustryModels() {
        return sysDictionaryDao.getHighTecIndustryModels();
    }

    /**
     * 获取表格头字段配置
     *
     * @param userInfo
     * @param tableId
     * @return
     */
    @Override
    public TableFieldInfo getTableField(UserInfo userInfo, String tableId) {
        return tableFieldDao.getTableFieldInfo(userInfo.getCompanyId(), tableId);
    }

    /**
     * 保存表格头字段配置
     *
     * @param userInfo
     * @param tableField
     * @return
     * @throws OwnerException
     */
    @Override
    public boolean saveTableField(UserInfo userInfo, TableField tableField) throws OwnerException {
        tableField.setLastUpdateTime(new Date());
        tableField.setLastUpdatorId(userInfo.getUserSource() == 0 ? userInfo.getId() : -1);
        tableField.setMsLastUpdatorId(userInfo.getUserSource() == 0 ? -1 : userInfo.getId());
        if (tableField.getId() != null) {
            tableField.setCompanyId(userInfo.getCompanyId());
            return tableFieldDao.updateById(tableField) > 0;
        } else {
            tableField.setCompanyId(userInfo.getCompanyId());
            tableField.setCreateTime(new Date());
            tableField.setCreatorId(userInfo.getUserSource() == 0 ? userInfo.getId() : -1);
            tableField.setMsLastUpdatorId(userInfo.getUserSource() == 0 ? -1 : userInfo.getId());
            return tableFieldDao.insert(tableField) > 0;
        }
    }

    /**
     * 获取当前节点的所有子节点
     *
     * @param nodeKey
     * @param nodes
     * @return
     */
    private static List<SysDictionaryModel> getChildNodes(String nodeKey, Map<String, SysDictionaryModel> nodes) {
        List<SysDictionaryModel> list = new ArrayList<>();
        for (String key : nodes.keySet()) {
            if (nodes.get(key).getParentKey().equals(nodeKey)) {
                list.add(nodes.get(key));
            }
        }
        return list;
    }
}
