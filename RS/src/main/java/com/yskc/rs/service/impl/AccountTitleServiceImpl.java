package com.yskc.rs.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import com.yskc.common.enums.ErrorEnum;
import com.yskc.common.exception.OwnerException;
import com.yskc.rs.config.Constant;
import com.yskc.rs.dao.AccountTitleDao;
import com.yskc.rs.entity.AccountTitleEntity;
import com.yskc.rs.enums.AccountEnum;
import com.yskc.rs.models.UserInfo;
import com.yskc.rs.models.accounttitle.AccountTitleModel;
import com.yskc.rs.models.accounttitle.AccountTree;
import com.yskc.rs.models.excel.AccountExcel;
import com.yskc.rs.service.AccountTitleService;
import com.yskc.rs.utils.TransactionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.text.MessageFormat;
import java.util.*;

/**
 * 菜单服务
 *
 * @author huronghua
 */
@Service
public class AccountTitleServiceImpl implements AccountTitleService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private AccountTitleDao accountTitleDao;

    /**
     * @param companyId
     * @param accountName
     * @return
     */
    @Override
    public List<AccountTitleModel> queryAccountTitle(int companyId, String accountName) {
        List<AccountTitleModel> accountTitleModels = accountTitleDao.queryAccountTitle(companyId, accountName);
        if (CollectionUtils.isEmpty(accountTitleModels)) {
            return accountTitleModels;
        }
        return recursiveTree(accountTitleModels);
    }

    /**
     * @param info
     * @param model
     * @return
     * @throws OwnerException
     */
    @Override
    public boolean addAccountTitle(UserInfo info, AccountTitleModel model) throws OwnerException {

        // 判断是否已存在数据
        if (accountTitleDao.getByAccountNumber(info.getCompanyId(), model.getAccountNumber()) != null) {
            throw new OwnerException(ErrorEnum.ACCOUNTTITLE_ACCOUNTNUMBER_EXSIT);
        }

        Date now = new Date();
        AccountTitleEntity accountTitleEntity = new AccountTitleEntity();
        BeanUtil.copyProperties(model, accountTitleEntity);
        String parentFullName = "";
        int level = 1;
        if (model.getParentId() > 0) {
            AccountTitleEntity parentAccount = accountTitleDao.selectById(model.getParentId());
            if (parentAccount != null) {
                level = parentAccount.getLevel() + 1;
                parentFullName = StringUtils.isEmpty(parentAccount.getFullAccountName()) ? "" :
                        parentAccount.getFullAccountName();
            }
        }
        if (accountTitleDao.getByAccountName(info.getCompanyId(), model.getParentId(), model.getAccountName()) != null) {
            throw new OwnerException("同级科目不能有相同的科目名称,请输入其他科目名称!");
        }
        accountTitleEntity.setFullAccountName(StringUtils.isEmpty(parentFullName) ? model.getAccountName() + Constant.PATH_SEPARATOR :
                parentFullName + model.getAccountName() + Constant.PATH_SEPARATOR);
        accountTitleEntity.setLevel(level);
        accountTitleEntity.setCompanyId(info.getCompanyId());
        accountTitleEntity.setCreatorId(info.getId());
        accountTitleEntity.setCreateTime(now);
        accountTitleEntity.setLastUpdatorId(info.getId());
        accountTitleEntity.setLastUpdateTime(now);
        return accountTitleDao.insert(accountTitleEntity) > 0;
    }

    /**
     * @param companyId
     * @return
     */
    @Override
    public List<AccountTree> queryAllAccountTitle(int companyId) {
        List<AccountTree> queryAll = accountTitleDao.queryAllAccountTitle(companyId);
        AccountTree accountTree = new AccountTree();
        accountTree.setKey(-1);
        accountTree.setTitle("根节点");
        accountTree.setParentId(-2);
        queryAll.add(accountTree);
        return queryAll;
    }

    /**
     * @param userInfo
     * @param data
     * @return
     * @throws OwnerException
     */
    @Override
    public String importAccount(UserInfo userInfo, List<AccountExcel> data) throws OwnerException {
        if (CollectionUtils.isEmpty(data)) {
            throw new OwnerException("未获取到任何数据，请检查表格是否存在数据。");
        }
        StringBuffer buffer = new StringBuffer();
        // 排序，按level正序
        CollUtil.sort(data, new Comparator<AccountExcel>() {
            @Override
            public int compare(AccountExcel o1, AccountExcel o2) {
                return o1.getLevel().compareTo(o2.getLevel());
            }
        });

        List<AccountExcel> accountQueryExcel = accountTitleDao.getLevelData(userInfo.getCompanyId());
        Map<String, AccountExcel> existModels = new HashMap<>();
        Map<Integer, Map<String, Integer>> existNameModels = new HashMap<>();
        accountQueryExcel.forEach(q -> {
            existModels.put(q.getAccountNumber(), q);
            if (!existNameModels.containsKey(q.getParentId())) {
                existNameModels.put(q.getParentId(), new HashMap<>());
            }
            existNameModels.get(q.getParentId()).put(q.getAccountName(), q.getId());
        });
        Date now = new Date();
        Integer exist = 1;
        TransactionStatus transactionStatus = null;
        try {
            transactionStatus = TransactionUtils.newTransaction();
            for (int i = 0; i < data.size(); i++) {
                AccountExcel excel = data.get(i);
                if (existModels.containsKey(excel.getAccountNumber())) {
                    buffer.append(MessageFormat.format(" 【科目编码：{0},科目名称：{1}】,科目编码已存在,", excel.getAccountNumber(), excel.getAccountName()));
                    continue;
                }

                AccountTitleEntity entity = null;
                for (AccountExcel exsitExcel : accountQueryExcel) {
                    // 是否父级  // 编码根级相同
                    boolean checked = exsitExcel.getLevel().equals(excel.getLevel() - 1)
                            && excel.getAccountNumber().trim().substring(0, exsitExcel.getAccountNumber().length()).equals(exsitExcel.getAccountNumber());
                    if (checked) {
                        if (!CollectionUtils.isEmpty(existNameModels.get(exsitExcel.getId())) &&
                                null != existNameModels.get(exsitExcel.getId()).get(excel.getAccountName())) {
                            buffer.append(MessageFormat.format("【科目编码：{0},科目名称：{1}】,同级已存在相同的科目名称", excel.getAccountNumber(), excel.getAccountName()));
                            entity = new AccountTitleEntity();
                            break;
                        }
                        entity = getAccount(userInfo, now, excel, exsitExcel.getId(), exsitExcel.getFullAccountName(), excel.getLevel());
                        accountTitleDao.insert(entity);
                        existModels.put(excel.getAccountNumber(), excel);

                        excel.setFullAccountName(entity.getFullAccountName());
                        excel.setParentId(entity.getParentId());
                        excel.setId(entity.getId());
                        if (CollectionUtils.isEmpty(existNameModels.get(entity.getParentId()))) {
                            existNameModels.put(entity.getParentId(), new HashMap<>());
                        }
                        existNameModels.get(entity.getParentId()).put(entity.getAccountName(), exist);
                        accountQueryExcel.add(excel);
                        break;
                    }
                }
                if (entity == null) {
                    entity = getAccount(userInfo, now, excel, -1, "", 1);
                    if (!CollectionUtils.isEmpty(existNameModels.get(entity.getParentId())) &&
                            null != existNameModels.get(entity.getParentId()).get(entity.getAccountName())) {
                        buffer.append(MessageFormat.format("【科目编码：{0},科目名称：{1}】,同级已存在相同的科目名称", entity.getAccountNumber(), entity.getAccountName()));
                        continue;
                    }
                    accountTitleDao.insert(entity);
                    if (CollectionUtils.isEmpty(existNameModels.get(entity.getParentId()))) {
                        existNameModels.put(entity.getParentId(), new HashMap<>());
                    }
                    existNameModels.get(entity.getParentId()).put(entity.getAccountName(), exist);
                    existModels.put(excel.getAccountNumber(), excel);
                    excel.setFullAccountName(entity.getFullAccountName());
                    excel.setParentId(entity.getParentId());
                    excel.setId(entity.getId());
                    accountQueryExcel.add(excel);
                }
            }
            TransactionUtils.commit(transactionStatus);
        } catch (Exception e) {
            logger.info(e.getMessage(), e);
            TransactionUtils.rollback(transactionStatus);
            throw new OwnerException("保存数据失败,请联系管理员");
        }
        if (buffer.length() > 1) {
            String result = buffer.substring(0, buffer.length() - 1);
            logger.info(result);
            return result;
        }
        return "";
    }

    /**
     * @param info
     * @param now
     * @param excel
     * @param parentId
     * @param fullName
     * @param level
     * @return
     */
    private static AccountTitleEntity getAccount(UserInfo info, Date now, AccountExcel excel, Integer parentId, String fullName, int level) {
        AccountTitleEntity entity = new AccountTitleEntity();
        entity.setCompanyId(info.getCompanyId());
        entity.setLastUpdateTime(now);
        entity.setCreateTime(now);
        entity.setLastUpdatorId(info.getId());
        entity.setCreatorId(info.getId());
        entity.setAccountName(excel.getAccountName());
        entity.setAccountNumber(excel.getAccountNumber());
        entity.setParentId(parentId);
        entity.setAccoutType(StringUtils.isEmpty(excel.getTypeName()) ? 0 : AccountEnum.getType(excel.getTypeName()));
        entity.setLevel(level);
        entity.setFullAccountName(fullName + entity.getAccountName() + Constant.PATH_SEPARATOR);
        return entity;
    }

    /**
     * @param info
     * @param model
     * @return
     * @throws OwnerException
     */
    @Override
    public boolean updateAccountTitle(UserInfo info, AccountTitleModel model) throws OwnerException {
        AccountTitleEntity oldAccount = accountTitleDao.getByAccountNumber(info.getCompanyId(), model.getAccountNumber());
        // 若修改了编码且已存在该编码，抛出异常
        if (oldAccount != null && !oldAccount.getId().equals(model.getId())) {
            throw new OwnerException(ErrorEnum.ACCOUNTTITLE_ACCOUNTNUMBER_EXSIT);
        }
        oldAccount = accountTitleDao.selectById(model.getId());
        String parentFullName = "";
        int level = 1;
        if (model.getParentId() > 0) {
            AccountTitleEntity parentAccount = accountTitleDao.selectById(model.getParentId());
            if (parentAccount != null) {
                parentFullName = StringUtils.isEmpty(parentAccount.getFullAccountName()) ? "" : parentAccount.getFullAccountName();
                level = parentAccount.getLevel() + 1;

            }
        }
        AccountTitleEntity existAccountTitle = accountTitleDao.getByAccountName(info.getCompanyId(), model.getParentId(), model.getAccountName());
        if (existAccountTitle != null && !existAccountTitle.getId().equals(model.getId())) {
            throw new OwnerException("同级科目不能有相同的科目名称,请输入其他科目名称!");
        }
        Date now = new Date();
        List<AccountTitleEntity> updateList = new ArrayList<>();
        AccountTitleEntity accountTitleEntity = new AccountTitleEntity();
        BeanUtil.copyProperties(model, accountTitleEntity);
        accountTitleEntity.setFullAccountName(StringUtils.isEmpty(parentFullName) ? accountTitleEntity.getAccountName() + Constant.PATH_SEPARATOR :
                parentFullName + accountTitleEntity.getAccountName() + Constant.PATH_SEPARATOR);
        accountTitleEntity.setLevel(level);
        accountTitleEntity.setLastUpdateTime(now);
        accountTitleEntity.setLastUpdatorId(info.getId());
        accountTitleEntity.setCompanyId(info.getCompanyId());
        updateList.add(accountTitleEntity);
        List<AccountTitleEntity> oldChildren = accountTitleDao.getChildren(info.getCompanyId(), oldAccount.getFullAccountName(), oldAccount.getId());
        if (!CollectionUtils.isEmpty(oldChildren)) {
            int step = level - oldAccount.getLevel();
            String oldFullName = oldAccount.getFullAccountName();
            oldChildren.forEach(old -> {
                old.setLevel(old.getLevel() + step);
                String fullName = old.getFullAccountName();
                old.setFullAccountName(fullName.replace(oldFullName, accountTitleEntity.getFullAccountName()));
                old.setLastUpdateTime(now);
                old.setLastUpdatorId(info.getId());
                old.setCompanyId(info.getCompanyId());
                updateList.add(old);
                ;
            });
        }
        return accountTitleDao.updateList(updateList) > 0;
    }

    /**
     * @param info
     * @param model
     * @return
     * @throws OwnerException
     */
    @Override
    public boolean delAccountTitle(UserInfo info, AccountTitleModel model) throws OwnerException {
        if (accountTitleDao.getByParentId(model.getId()) > 0) {
            throw new OwnerException("该科目有子科目,不能进行删除操作!");
        }
        return accountTitleDao.deleteById(model.getId()) > 0;
    }

    /**
     * 递归科目树
     *
     * @param list
     * @return
     */
    public List<AccountTitleModel> recursiveTree(List<AccountTitleModel> list) {
        Map<Integer, List<AccountTitleModel>> parentGroup = new HashMap<>();
        list.forEach(e -> {
            int parentId = e.getParentId();
            if (!parentGroup.containsKey(parentId)) {
                parentGroup.put(parentId, new ArrayList<>());
            }
            parentGroup.get(parentId).add(e);
        });
        List<AccountTitleModel> result = new ArrayList<>();
        Set<Integer> ids = new HashSet<>();
        for (Integer key : parentGroup.keySet()) {
            if (ids.contains(key)) {
                continue;
            }
            ids.add(key);
            List<AccountTitleModel> temp = recursiveTree(parentGroup, key, ids);
            if (!CollectionUtils.isEmpty(temp)) {
                result.addAll(temp);
            } else {
                result.addAll(parentGroup.get(key));
            }
        }
        return result;
        // 遍历子节点

//        for (AccountTitleModel atm : list) {
//            // 递归
//            if (atm.getParentId().equals(parentId)) {
//                List<AccountTitleModel> tempList = recursiveTree(list, atm.getId());
//                if (!CollectionUtils.isEmpty(tempList)) {
//                    atm.setChildren(tempList);
//                }
//                result.add(atm);
//            }
//        }
    }

    /**
     * @param parentGroup
     * @param parentId
     * @param ids
     * @return
     */
    private static List<AccountTitleModel> recursiveTree(Map<Integer, List<AccountTitleModel>> parentGroup, Integer parentId, Set<Integer> ids) {
        List<AccountTitleModel> group = parentGroup.get(parentId);
        if (CollectionUtils.isEmpty(group)) {
            return null;
        }
        List<AccountTitleModel> result = new ArrayList<>();
        for (AccountTitleModel model : group) {
            if (ids.contains(model.getId())) {
                continue;
            }
            ids.add(model.getId());
            List<AccountTitleModel> temp = recursiveTree(parentGroup, model.getId(), ids);
            if (!CollectionUtils.isEmpty(temp)) {
                model.setChildren(temp);
            }
            result.add(model);
        }
        return result;
    }
}

