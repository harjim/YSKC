package com.yskc.rs.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yskc.rs.entity.ProductEntity;
import com.yskc.rs.models.product.ProductModel;
import com.yskc.rs.models.product.QueryPcodeModel;
import com.yskc.rs.models.product.QueryProductModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author wjy
 * @date 2022-06-09 09:01
 */
@Repository
public interface ProductDao extends BaseMapper<ProductEntity> {
    /**
     * 查询企业产品
     * @param query
     * @param companyId
     * @return
     */
    List<ProductModel> getProduct(@Param("page") Pagination page, @Param("query")QueryProductModel query, @Param("companyId")Integer companyId);

    /**
     * 查询产品总产值
     * @param companyId
     * @return
     */
    BigDecimal getOutputValues(@Param("companyId")Integer companyId, @Param("query")QueryProductModel query);
    /**
     * 检查产品编码
     * @param query
     * @param companyId
     * @return
     */
    List<ProductModel> checkPcode(@Param("query") QueryPcodeModel query, @Param("companyId")Integer companyId);
}
