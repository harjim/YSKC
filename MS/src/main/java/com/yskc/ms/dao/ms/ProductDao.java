package com.yskc.ms.dao.ms;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yskc.ms.entity.ms.Product;
import com.yskc.ms.models.DataPermModel;
import com.yskc.ms.models.product.ProductModel;
import com.yskc.ms.models.product.QueryProductModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface ProductDao extends BaseMapper<Product> {

    List<ProductModel> queryProduct(@Param("page") Pagination page, @Param("query") QueryProductModel query, @Param("perm") DataPermModel perm);

    List<ProductModel> checkProduct(@Param("id") Integer id, @Param("productName") String productName, @Param("year") Integer year, @Param("address") String address);

    List<ProductModel> productForSelect(@Param("year") Integer year, @Param("addressCode") String addressCode, @Param("productName") String productName);

    /**
     * 获取项目类型
     *
     * @param id
     * @return
     */
    Integer getProductType(@Param("id") Integer id);

    /**
     * 获取项目类型
     *
     * @param ids
     * @return
     */
    List<Product> getProductNames(@Param("ids") Set<Integer> ids);
}
