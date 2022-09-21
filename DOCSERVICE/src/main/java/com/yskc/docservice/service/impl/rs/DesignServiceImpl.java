package com.yskc.docservice.service.impl.rs;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yskc.common.enums.DataSourceEnum;
import com.yskc.common.enums.ErrorEnum;
import com.yskc.common.exception.OwnerException;
import com.yskc.docservice.config.Constant;
import com.yskc.docservice.dao.rs.AccountTitleDao;
import com.yskc.docservice.dao.rs.DesignDao;
import com.yskc.docservice.entity.rs.AccountTitleEntity;
import com.yskc.docservice.entity.rs.DesignEntity;
import com.yskc.docservice.models.rs.DesignModel;
import com.yskc.docservice.models.rs.RsUserInfo;
import com.yskc.docservice.models.rs.excel.DesginExcel;
import com.yskc.docservice.service.rs.DesignService;
import com.yskc.docservice.utils.ListUtils;
import com.yskc.docservice.utils.TransactionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Administrator
 */
@Service
public class DesignServiceImpl extends ServiceImpl<DesignDao, DesignEntity> implements DesignService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private DesignDao designDao;

    @Autowired
    private AccountTitleDao accountTitleDao;

    /**
     * @param info
     * @param desginExcels
     * @return
     * @throws OwnerException
     */
    @Override
    public boolean importDesign(RsUserInfo info, List<DesginExcel> desginExcels, Integer year) throws OwnerException {
        if (desginExcels.size() == 0) {
            return true;
        }
        Set<String> fullAccountNameList = new HashSet<>();
        desginExcels.forEach(item -> {
            if (!StringUtils.isEmpty(item.getFullAccountName())) {
                if (!item.getFullAccountName().endsWith(Constant.PATH_SEPARATOR)) {
                    item.setFullAccountName(item.getFullAccountName() + Constant.PATH_SEPARATOR);
                }
                fullAccountNameList.add(item.getFullAccountName());
            }
        });
        List<AccountTitleEntity> accountTitleEntityList = fullAccountNameList.size() == 0 ? new ArrayList<>() : accountTitleDao.getByFullAccountName(info.getCompanyId(), fullAccountNameList);
        Map<String, AccountTitleEntity> accountTitleEntityMap = accountTitleEntityList.stream().collect(Collectors.toMap(AccountTitleEntity::getFullAccountName, b -> b));
        List<DesignEntity> designEntities = new ArrayList<>();
        for (int i = 0; i < desginExcels.size(); i++) {
            DesginExcel desginExcel = desginExcels.get(i);
            int accountTitleId = 0;
            if (accountTitleEntityMap.containsKey(desginExcel.getFullAccountName())) {
                accountTitleId = accountTitleEntityMap.get(desginExcel.getFullAccountName()).getId();
            }
            DesignModel model = new DesignModel();
            BeanUtil.copyProperties(desginExcel, model);
            // 默认为0
            model.setAccountTitleId(accountTitleId);
            DesignEntity designEntity=setValue(info.getId(), info, model);
            designEntity.setRemainDFee(model.getdFee());
            designEntities.add(designEntity);
        }
        TransactionStatus transactionStatus = TransactionUtils.newTransaction(DataSourceEnum.RS);
        try {
            if (designEntities.size() > 0) {
                //Integer num= designDao.addDesignBatch(designEntities);
                List<List<DesignEntity>> insertList = ListUtils.subList(designEntities, Constant.MAX_INSERT_OR_UPDATE_COUNT);
                for (List<DesignEntity> items : insertList) {
                    designDao.addDesignBatch(items);
                }

            }
            TransactionUtils.commit(DataSourceEnum.RS,transactionStatus);
        } catch (Exception ex) {
            TransactionUtils.rollback(DataSourceEnum.RS,transactionStatus);
            logger.error("importDesgin", ex);
            throw new OwnerException(ErrorEnum.FAIL);
        }
        return true;
    }

    /**
     * @param currentUserId
     * @param userInfo
     * @param dModel
     * @return
     */
    public DesignEntity setValue(int currentUserId, RsUserInfo userInfo, DesignModel dModel) {
        DesignEntity dEntity = new DesignEntity();
        if (dModel.getId() > 0 || null != dModel.getId()) {
            dEntity.setId(dModel.getId());
        }
        dEntity.setCompanyId(userInfo.getCompanyId());
        dEntity.setDname(dModel.getDname());
        dEntity.setMsCreatorId(userInfo.getUserSource() == 1 ? userInfo.getId() : -1);
        dEntity.setMsLastUpdatorId(userInfo.getUserSource() == 1 ? userInfo.getId() : -1);
        dEntity.setCreatorId(userInfo.getUserSource() == 0 ? userInfo.getId() : -1);
        dEntity.setLastUpdatorId(userInfo.getUserSource() == 0 ? userInfo.getId() : -1);
        dEntity.setCreateTime(new Date());
        dEntity.setLastUpdateTime(new Date());
        dEntity.setCreatorId(currentUserId);
        dEntity.setDesignDate(dModel.getDesignDate());
        dEntity.setDfee(dModel.getdFee());
        dEntity.setDeptName(dModel.getDeptName());
        dEntity.setAccountTitleId(dModel.getAccountTitleId());
        dEntity.setRemark(dModel.getRemark());
        return dEntity;
    }
}
