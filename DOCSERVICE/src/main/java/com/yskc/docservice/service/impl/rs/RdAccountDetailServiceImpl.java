package com.yskc.docservice.service.impl.rs;

import cn.hutool.core.bean.BeanUtil;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.utils.DateUtil;
import com.yskc.docservice.dao.rs.RdAccountDetailDao;
import com.yskc.docservice.entity.rs.RdAccountDetailEntity;
import com.yskc.docservice.models.rs.RsUserInfo;
import com.yskc.docservice.models.rs.excel.RdAccountDetailExcel;
import com.yskc.docservice.service.rs.RdAccountDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.service.impl
 * @Author: zhangdingfu
 * @CreateTime: 2019-09-30 10:55
 * @Description: 研发费用明细业务实现层
 */
@Service
public class RdAccountDetailServiceImpl implements RdAccountDetailService {

    @Autowired
    private RdAccountDetailDao accountDetailDao;

    @Override
    public boolean importInfo(RsUserInfo userInfo, List<RdAccountDetailExcel> rdAccountDetailExcels, int accType) throws OwnerException {
        if (CollectionUtils.isEmpty(rdAccountDetailExcels)) {
            return true;
        }
        List<RdAccountDetailEntity> rdAccountDetailEntities = new ArrayList<>();
        Date now = new Date();
        rdAccountDetailExcels.forEach(item -> {
            if (StringUtils.isEmpty(item.getAccNumber())) {
                return;
            }
            rdAccountDetailEntities.add(getRdAccountDetail(item, userInfo, now, accType));
        });
        if (CollectionUtils.isEmpty(rdAccountDetailEntities)) {
            throw new OwnerException("未获取到数据，请检查是否导入了必要的[年,月,日,凭证号]");
        }
        return accountDetailDao.addList(rdAccountDetailEntities) > 0;
    }

    private static RdAccountDetailEntity getRdAccountDetail(RdAccountDetailExcel item, RsUserInfo userInfo, Date now, int accType) {
        RdAccountDetailEntity rdAccountDetailEntity = new RdAccountDetailEntity();
        BeanUtil.copyProperties(item, rdAccountDetailEntity);
        rdAccountDetailEntity.setRdDate(DateUtil.parse("" + item.getCyear() + '-' + item.getCmonth() + '-' + item.getCday(), "yyyy-MM-dd"));
        rdAccountDetailEntity.setAccType(accType);
        rdAccountDetailEntity.setLastUpdatorId(userInfo.getId());
        rdAccountDetailEntity.setCreatorId(userInfo.getId());
        rdAccountDetailEntity.setCompanyId(userInfo.getCompanyId());
        rdAccountDetailEntity.setLastUpdateTime(now);
        rdAccountDetailEntity.setCreateTime(now);
        return rdAccountDetailEntity;
    }

}
