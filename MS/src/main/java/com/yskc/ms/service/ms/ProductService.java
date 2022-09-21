package com.yskc.ms.service.ms;

import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.ms.entity.ms.Product;
import com.yskc.ms.models.DataPermModel;
import com.yskc.ms.models.menu.AppMenuModel;
import com.yskc.ms.models.product.ProductModel;
import com.yskc.ms.models.product.QueryProductModel;

import java.util.List;

public interface ProductService {
    /**
     * 查询产品列表
     * @return
     */
    PageModel<List<ProductModel>> queryProduct(QueryProductModel query, DataPermModel perm);

    /**
     * 添加产品
     * @param id
     * @param model
     * @return
     */
    String addProduct(Integer id, ProductModel model);

    /**
     * 编辑产品
     * @param id
     * @param model
     * @return
     */
    String editProduct(Integer id, ProductModel model);

    /**
     * 删除产品
     * @param model
     * @return
     */
    boolean deleteProduct(ProductModel model) throws OwnerException;

    /**
     * 查询产品下拉框
     * @param year
     * @param addressCode
     * @return
     */
    List<ProductModel> productForSelect(Integer year, String addressCode, String productName);
}
