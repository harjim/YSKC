package com.yskc.rs.service;

import com.github.pagehelper.Page;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.common.model.PageResult;
import com.yskc.rs.models.UserInfo;
import com.yskc.rs.models.product.ProductModel;
import com.yskc.rs.models.product.ProductYearModel;
import com.yskc.rs.models.product.QueryPcodeModel;
import com.yskc.rs.models.product.QueryProductModel;

import java.util.List;

/**
 * @Author: wjy
 * @create: 2022-06-09 09:03
 *
 */
public interface ProductService {
    /**
     * 查询企业产品
     * @param query
     * @param companyId
     * @return
     */
    PageResult getList(QueryProductModel query, Integer companyId);

    /**
     * 检查产品编码
     * @param query
     * @param companyId
     * @return
     */
    Boolean checkPcode(QueryPcodeModel query, Integer companyId) throws OwnerException;

    /**
     * 添加企业产品
     * @param model
     * @return
     */
    Boolean addProduct(ProductModel model, Integer companyId, UserInfo userInfo) throws OwnerException;

    /**
     * 编辑企业产品
     * @param model
     * @return
     */
    Boolean updateProduct(ProductModel model, UserInfo userInfo);

    /**
     * 删除企业产品
     * @param id
     * @return
     */
    Boolean deleteProduct(Integer id, Integer companyId) throws OwnerException;

    /**
     * 获取产品产值
     * @param productId
     * @param companyId
     * @return
     */
    List<ProductYearModel> getYearList(Integer productId, Integer companyId);


    /**
     * 编辑企业产品产值
     * @param models
     * @param userInfo
     * @return
     */
    Boolean updateProductYear(List<ProductYearModel> models, UserInfo userInfo,Integer productId) throws OwnerException;
}
