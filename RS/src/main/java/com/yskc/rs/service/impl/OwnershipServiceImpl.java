package com.yskc.rs.service.impl;

import com.yskc.rs.dao.company.CompanyDao;
import com.yskc.rs.dao.OwnershipDao;
import com.yskc.rs.entity.OwnershipEntity;
import com.yskc.rs.models.UserInfo;
import com.yskc.rs.service.OwnershipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OwnershipServiceImpl implements OwnershipService {
    @Autowired
    OwnershipDao ownershipDao;
    @Autowired
    CompanyDao companyDao;

    @Override
    public List<OwnershipEntity> queryOwnershipList(Integer companyId) {
        return ownershipDao.queryOwnershipList(companyId);
    }

    @Override
    public boolean addOwnership(UserInfo userInfo, OwnershipEntity entity) {
        Date date = new Date();
        entity.setCompanyId(userInfo.getCompanyId());
        entity.setCreatorId(userInfo.getId());
        entity.setCreateTime(date);
        entity.setLastUpdatorId(userInfo.getId());
        entity.setLastUpdateTime(date);
        return ownershipDao.insert(entity) > 0;
    }

    @Override
    public boolean editOwnership(UserInfo userInfo, OwnershipEntity entity) {
        entity.setLastUpdatorId(userInfo.getId());
        entity.setLastUpdateTime(new Date());
        return ownershipDao.updateById(entity) > 0;
    }

    @Override
    public boolean delOwnership(OwnershipEntity entity) {
        return ownershipDao.deleteById(entity.getId()) > 0;
    }

    @Override
    public BigDecimal checkProportion(UserInfo userInfo, BigDecimal proportion, BigDecimal oldProportion) {
        List<OwnershipEntity> ownershipEntities = ownershipDao.queryOwnershipList(userInfo.getCompanyId());
        if (!ownershipEntities.isEmpty()) {
            List<BigDecimal> proportionList = ownershipEntities.stream().filter(a -> !StringUtils.isEmpty(a.getProportion())).map(OwnershipEntity::getProportion).collect(Collectors.toList());
            BigDecimal proTotal = BigDecimal.ZERO;
            for (BigDecimal pro : proportionList) {
                proTotal = proTotal.add(pro);
            }
            BigDecimal residualProportion = new BigDecimal(100).add(oldProportion).subtract(proTotal);
            if (proportion.compareTo(residualProportion) == 1) {
                return residualProportion;
            }
        }
        return new BigDecimal(-1);
    }
}
