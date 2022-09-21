package com.yskc.rs.dao.highTech;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yskc.rs.entity.hightech.HighTechIncomeEntity;
import com.yskc.rs.models.hightech.HighTechIncomeModel;
import com.yskc.rs.models.hightech.IncomeDataModel;
import com.yskc.rs.models.hightech.QueryHighTechIncomeModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Author: hck
 * @DateTime: 2021/5/27 17:30
 * @Description:
 */
@Repository
public interface HighTechIncomeDao extends BaseMapper<HighTechIncomeEntity> {
    /**
     * 获取高品收入
     *
     * @param page
     * @param companyId
     * @param query
     * @param begin
     * @param end
     * @return
     */
    List<HighTechIncomeModel> getList(@Param("page") Pagination page, @Param("companyId") Integer companyId,
                                      @Param("query") QueryHighTechIncomeModel query, @Param("begin") Date begin,
                                      @Param("end") Date end);

    /**
     * 批量插入
     *
     * @param list
     * @return
     */
    int addBatch(@Param("list") List<HighTechIncomeEntity> list);

    /**
     * 获取导出的高品收入
     *
     * @param companyId
     * @param query
     * @param begin
     * @param end
     * @return
     */
    List<HighTechIncomeModel> getExportIncome(@Param("companyId") Integer companyId,
                                              @Param("query") QueryHighTechIncomeModel query,
                                              @Param("begin") Date begin, @Param("end") Date end);

    /**
     * 更新高新产品id
     *
     * @param ids
     * @param highTechId
     * @param now
     * @param userId
     * @param msUserId
     * @return
     */
    int updateHighTechId(@Param("ids") List<Integer> ids, @Param("highTechId") Integer highTechId,
                         @Param("now") Date now, @Param("userId") Integer userId, @Param("msUserId") Integer msUserId);

    /**
     * 根据月份统计数据
     *
     * @param highTechId
     * @param begin
     * @param end
     * @return
     */
    List<Map<String, Object>> getDataByMonth(@Param("highTechId") Integer highTechId, @Param("begin") Date begin, @Param("end") Date end, @Param("companyId") Integer companyId);

    /**
     * 根据年获取当期收入
     *
     * @param year
     * @param companyId
     * @return
     */
    BigDecimal getCountData(@Param("companyId") Integer companyId, @Param("year") Integer year);

    /**
     * 获取高品收入
     *
     * @param highTechIds
     * @param companyId
     * @return
     */
    List<HighTechIncomeEntity> getByTechs(@Param("highTechIds") List<Integer> highTechIds, @Param("companyId") Integer companyId);

    /**
     * 获取高品收入根据年
     *
     * @param companyId
     * @param begin
     * @param end
     * @return
     */
    List<IncomeDataModel> countDataByYear(@Param("companyId") Integer companyId, @Param("begin") Date begin, @Param("end") Date end);
}
