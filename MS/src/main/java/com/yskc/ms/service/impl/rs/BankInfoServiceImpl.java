package com.yskc.ms.service.impl.rs;

import com.yskc.ms.dao.rs.BankInfoDao;
import com.yskc.ms.entity.rs.BankInfoEntity;
import com.yskc.ms.models.UserInfo;
import com.yskc.ms.models.company.BankInfoModel;
import com.yskc.ms.service.rs.BankInfoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by hck
 * on 2020/12/4 14:39
 * description:
 */
@Service
public class BankInfoServiceImpl implements BankInfoService {

    @Autowired
    private BankInfoDao bankInfoDao;

    @Override
    public BankInfoModel getBankInfo(Integer companyId) {
        return bankInfoDao.getByCompanyId(companyId);
    }

    @Override
    public Boolean editInfo(BankInfoModel model, UserInfo userInfo) {
        Date now = new Date();
        if (model == null) {
            return true;
        }
        BankInfoEntity bankInfo = bankInfoDao.getByYear(model.getCompanyId(), model.getYear());
        if (bankInfo != null) {
            model.setId(bankInfo.getId());
            BeanUtils.copyProperties(model, bankInfo);
            bankInfo.setLastUpdateTime(now);
            bankInfo.setLastUpdatorId(-1);
            bankInfo.setMsCreatorId(userInfo.getId());
            return bankInfoDao.updateById(bankInfo) > 0;
        } else {
            bankInfo = new BankInfoEntity();
            BeanUtils.copyProperties(model, bankInfo);
            bankInfo.setCreateTime(now);
            bankInfo.setLastUpdateTime(now);
            bankInfo.setCreatorId(-1);
            bankInfo.setLastUpdatorId(-1);
            bankInfo.setMsCreatorId(userInfo.getId());
            bankInfo.setMsLastUpdatorId(userInfo.getId());
            return bankInfoDao.insert(bankInfo) > 0;
        }
    }
}
