package com.yskc.ms.dao.ms;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yskc.ms.entity.ms.PatentSupplierEntity;
import com.yskc.ms.models.patentSupplier.QuerySupplierModel;
import com.yskc.ms.models.patentSupplier.SupplierInfoModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @BelongsProject:
 * @BelogsPackage: src/main/java/com/yskc/ms/dao/ms
 * @Author: hm
 * @CreateTime: 2022/9/3
 * @Description: 表 patent_supplier
 */
@Repository
public interface PatentSupplierDao extends BaseMapper< PatentSupplierEntity > {
//    List< SupplierInfoModel > getSupplierList(@Param("params") SupplierInfoModel supplierInfoModel);

    /**
     * 获取专利供应商 list
     * @param page
     * @param model
     * @return
     */
    List<SupplierInfoModel> getSupplierList(@Param("page")Pagination page, @Param("model") QuerySupplierModel model);

    /**
     * 获取指定供应商名称的记录
     * @param supplier
     * @return
     */
    SupplierInfoModel checkSupplier(@Param("supplier") String supplier);
}
