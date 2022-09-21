package com.yskc.rs.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yskc.rs.entity.ProductYearEntity;
import com.yskc.rs.models.product.ProductYearModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author wjy
 * @date 2022-06-09 09:01
 */
@Repository
public interface ProductYearDao extends BaseMapper<ProductYearEntity> {
    /**
     * 查询产品产值
     * @param productId
     * @param companyId
     * @return
     */
    List<ProductYearModel> getYearList(@Param("productId")Integer productId,@Param("companyId")Integer companyId);

    /**
     * 添加或修改产品产值
     * @param list
     * @return
     */
    Integer insertOrUpdate(@Param("list")List<ProductYearEntity> list,@Param("userId")Integer userId,
                           @Param("msUserId")Integer msUserId);

    /**
     * 删除产品产值
     * @param productId
     * @param companyId
     * @param list
     * @return
     */
    Boolean deleteYear(@Param("productId")Integer productId, @Param("companyId")Integer companyId, @Param("list")List<ProductYearEntity> list);
}
