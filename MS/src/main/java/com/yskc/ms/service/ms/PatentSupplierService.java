package com.yskc.ms.service.ms;

import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.ms.models.DataPermModel;
import com.yskc.ms.models.UserInfo;
import com.yskc.ms.models.patentSupplier.QuerySupplierModel;
import com.yskc.ms.models.patentSupplier.SupplierInfoModel;

import java.util.List;

/**
 * @BelongsProject:
 * @BelogsPackage: src/main/java/com/yskc/ms/service/ms
 * @Author: hm
 * @CreateTime: 2022/9/3
 * @Description: 专利供应商对应Service
 */
public interface PatentSupplierService {
    /**
     * 获取供应商列表
     * @param params
     * @param userInfo
     * @param dataPerm
     * @return
     */
    PageModel< List< SupplierInfoModel > > getSupplierList(QuerySupplierModel params, UserInfo userInfo, DataPermModel dataPerm);

    /**
     * 保存、修改 供应商信息
     * @param infoModel
     * @param userInfo
     * @param isAdd
     * @return
     * @throws OwnerException
     */
    Boolean saveSupplierInfo(SupplierInfoModel infoModel, UserInfo userInfo, boolean isAdd) throws OwnerException;

    /**
     * 删除指定id的供应商信息
     * @param infoModel
     * @return
     * @throws OwnerException
     */
    Boolean delSupplierInfo(SupplierInfoModel infoModel) throws OwnerException;
}
