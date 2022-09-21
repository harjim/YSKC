package com.yskc.ms.dao.rs;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yskc.ms.entity.rs.RsProductEntity;
import com.yskc.ms.models.rs.QueryRsProductModel;
import com.yskc.ms.models.rs.RsProductListModel;
import com.yskc.ms.models.rs.RsProductModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Created by hck
 * on 2020/10/13 8:26
 * description:
 */
@Repository
public interface RsProductDao extends BaseMapper<RsProductEntity> {
    /**
     * 查询申报项目列表
     *
     * @param query
     * @param page
     * @param msUserIds
     * @return
     */
    List<RsProductModel> queryList(@Param("query") QueryRsProductModel query, @Param("page") Pagination page,
                                   @Param("msUserIds") List<Integer> msUserIds);

    /**
     * 获取所有项目
     *
     * @return
     */
    List<RsProductListModel> getProductList(@Param("addressCode") String addressCode, @Param("year") Integer year);

    /**
     * 批量插入申报项目
     *
     * @param products
     * @return
     */
    boolean insertList(@Param("products") List<RsProductEntity> products);

    /**
     * 根据项目名称年份及地区获取申报项目
     *
     * @param productName
     * @param year
     * @param addressCode
     * @return
     */
    List<RsProductEntity> checkExist(@Param("productName") String productName, @Param("year") Integer year, @Param("addressCode") String addressCode, @Param("id") Integer id);

    /**
     * 批量更新负责人
     *
     * @param ids
     * @param managerId
     * @return
     */
    Integer updateManager(@Param("ids") List<Integer> ids, @Param("managerId") Integer managerId, @Param("date") Date date, @Param("userId") Integer userId);

    /**
     * 获取项目名称
     *
     * @param id
     * @return
     */
    String getProductName(@Param("id") Integer id);
}
