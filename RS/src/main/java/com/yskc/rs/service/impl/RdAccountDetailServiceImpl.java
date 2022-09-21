package com.yskc.rs.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.common.utils.DateUtil;
import com.yskc.common.utils.PageUtils;
import com.yskc.rs.dao.RdAccountDetailDao;
import com.yskc.rs.entity.RdAccountDetailEntity;
import com.yskc.rs.models.UserInfo;
import com.yskc.rs.models.excel.RdAccountDetailExcel;
import com.yskc.rs.models.rdaccountdetail.QueryRdAccountDetailModel;
import com.yskc.rs.models.rdaccountdetail.RdAccountDetailModel;
import com.yskc.rs.service.RdAccountDetailService;
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
    public PageModel<List<RdAccountDetailModel>> getList(Integer companyId, QueryRdAccountDetailModel query) {
        Pagination page = query.getPagination();
        if (CollectionUtils.isEmpty(page.getAscs()) && CollectionUtils.isEmpty(page.getDescs())) {
            List<String> descs = new ArrayList<>();
            descs.add("rd.rdDate");
            descs.add("rd.accNumber");
            page.setDescs(descs);
        }
        if (query.getRdDate() != null) {
            query.setRdDate(DateUtil.getDayBegin(query.getRdDate()));
        }
        return PageUtils.build(page, accountDetailDao.getList(page, companyId, query));
    }

    @Override
    public boolean add(UserInfo userInfo, RdAccountDetailModel rdAccountDetailModel) {
        RdAccountDetailEntity insertEntity = new RdAccountDetailEntity();
        Date now = new Date();
        BeanUtil.copyProperties(rdAccountDetailModel, insertEntity);
        insertEntity.setCompanyId(userInfo.getCompanyId());
        insertEntity.setCreatorId(userInfo.getId());
        insertEntity.setCreateTime(now);
        insertEntity.setLastUpdatorId(userInfo.getId());
        insertEntity.setLastUpdateTime(now);
        return accountDetailDao.insert(insertEntity) > 0;
    }

    @Override
    public boolean update(UserInfo userInfo, RdAccountDetailModel rdAccountDetailModel) {
        RdAccountDetailEntity updateEntity = new RdAccountDetailEntity();
        BeanUtil.copyProperties(rdAccountDetailModel, updateEntity);
        updateEntity.setLastUpdateTime(new Date());
        updateEntity.setLastUpdatorId(userInfo.getId());
        return accountDetailDao.updateById(updateEntity) > 0;
    }

    @Override
    public boolean del(RdAccountDetailModel rdAccountDetailModel) {
        return accountDetailDao.deleteById(rdAccountDetailModel.getId()) > 0;
    }

    @Override
    public boolean dels(List<RdAccountDetailModel> rdAccountDetailModels) {
        List<Integer> ids = new ArrayList<>(rdAccountDetailModels.size());
        rdAccountDetailModels.forEach(item -> {
            ids.add(item.getId());
        });
        return accountDetailDao.deleteBatchIds(ids) > 0;
    }

    @Override
    public boolean importInfo(UserInfo userInfo, List<RdAccountDetailExcel> rdAccountDetailExcels, int accType) throws OwnerException {
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

    private static RdAccountDetailEntity getRdAccountDetail(RdAccountDetailExcel item, UserInfo userInfo, Date now, int accType) {
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
