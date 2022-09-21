package com.yskc.docservice.service.impl.rs;

import cn.hutool.core.collection.CollUtil;
import com.yskc.common.enums.DataSourceEnum;
import com.yskc.common.exception.OwnerException;
import com.yskc.docservice.config.Constant;
import com.yskc.docservice.dao.rs.AccountTitleDao;
import com.yskc.docservice.entity.rs.AccountTitleEntity;
import com.yskc.docservice.enums.AccountEnum;
import com.yskc.docservice.models.rs.RsUserInfo;
import com.yskc.docservice.models.rs.excel.AccountExcel;
import com.yskc.docservice.service.rs.AccountTitleService;
import com.yskc.docservice.utils.TransactionUtils;
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
     * @param userInfo
     * @param data
     * @return
     * @throws OwnerException
     */
    @Override
    public String importAccount(RsUserInfo userInfo, List<AccountExcel> data) throws OwnerException {
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
            transactionStatus = TransactionUtils.newTransaction(DataSourceEnum.RS);
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
            TransactionUtils.commit(DataSourceEnum.RS,transactionStatus);
        } catch (Exception e) {
            logger.info(e.getMessage(), e);
            TransactionUtils.rollback(DataSourceEnum.RS,transactionStatus);
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
    private static AccountTitleEntity getAccount(RsUserInfo info, Date now, AccountExcel excel, Integer parentId, String fullName, int level) {
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
}

