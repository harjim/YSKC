package com.yskc.ms.service.impl.ms;

import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.common.utils.PageUtils;
import com.yskc.ms.dao.ms.PatentSupplierDao;
import com.yskc.ms.entity.ms.PatentSupplierEntity;
import com.yskc.ms.models.DataPermModel;
import com.yskc.ms.models.UserInfo;
import com.yskc.ms.models.patentSupplier.QuerySupplierModel;
import com.yskc.ms.models.patentSupplier.SupplierInfoModel;
import com.yskc.ms.service.ms.PatentSupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @BelongsProject:
 * @BelogsPackage: src/main/java/com/yskc/ms/service/impl/ms
 * @Author: hm
 * @CreateTime: 2022/9/3
 * @Description: 专利供应商
 */
@Service
public class PatentSupplierServiceImpl implements PatentSupplierService {

    @Autowired
    private PatentSupplierDao patentSupplierDao;

    @Override
    public PageModel< List< SupplierInfoModel > > getSupplierList(QuerySupplierModel params, UserInfo userInfo, DataPermModel dataPerm) {
        Pagination page = params.getPagination();
        if (CollectionUtils.isEmpty(page.getAscs()) && CollectionUtils.isEmpty(page.getDescs())) {
            List<String> desc = new ArrayList<>();
            desc.add("createTime");
            page.setDescs(desc);
        }
        return PageUtils.build(page, patentSupplierDao.getSupplierList(page, params));
    }

    @Override
    public Boolean saveSupplierInfo(SupplierInfoModel infoModel, UserInfo userInfo, boolean isAdd) throws OwnerException {
        // 判断是否存在重复供应商
        SupplierInfoModel checkSupplier = patentSupplierDao.checkSupplier(infoModel.getSupplier());
        Integer saveInfoId = infoModel.getId();
        if (saveInfoId == null && !isAdd) {
            throw new OwnerException("请选择需要修改的供应商记录");
        }
        if (checkSupplier != null && saveInfoId != checkSupplier.getId()) {
            throw new OwnerException("当前已存在该供应商");
        }
        PatentSupplierEntity supplierEntity = new PatentSupplierEntity();
        supplierEntity.setSupplier(infoModel.getSupplier());
        supplierEntity.setAccountName(infoModel.getAccountName());
        supplierEntity.setAccountNumber(infoModel.getAccountNumber());
        supplierEntity.setLinkman(infoModel.getLinkman());
        supplierEntity.setLinkTel(infoModel.getLinkTel());
        supplierEntity.setRemark(infoModel.getRemark());
        Date now = new Date();
        supplierEntity.setLastUpdateTime(now);
        supplierEntity.setLastUpdatorId(userInfo.getId());
        if (isAdd) {
            /* 添加 */
            supplierEntity.setCreatorId(userInfo.getId());
            supplierEntity.setCreateTime(now);
            patentSupplierDao.insert(supplierEntity);
        } else {
            supplierEntity.setId(infoModel.getId());
            patentSupplierDao.updateById(supplierEntity);
        }
        return true;
    }

    @Override
    public Boolean delSupplierInfo(SupplierInfoModel infoModel) throws OwnerException {
        Integer infoId = infoModel.getId();
        if (infoId == null) {
            throw new OwnerException("请选择需要删除的供应商记录!");
        }
        return patentSupplierDao.deleteById(infoId) > 0;
    }
}
