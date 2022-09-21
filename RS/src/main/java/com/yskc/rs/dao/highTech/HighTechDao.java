package com.yskc.rs.dao.highTech;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yskc.rs.entity.hightech.HighTechEntity;
import com.yskc.rs.models.hightech.HighTechIncomeModel;
import com.yskc.rs.models.hightech.HighTechModel;
import com.yskc.rs.models.hightech.HighTechSelectModel;
import com.yskc.rs.models.hightech.QueryHighTechModel;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: hck
 * @DateTime: 2021/5/27 16:53
 * @Description:
 */
@Repository
public interface HighTechDao extends BaseMapper<HighTechEntity> {

    List<HighTechModel> getList(@Param("companyId") Integer companyId, @Param("query") QueryHighTechModel query);

    List<String> getDescription(@Param("companyId") Integer companyId,@Param("hcode")String hcode);

    /**
     * 验证技术代码和名称唯一
     *
     * @param hcode
     * @param companyId
     * @param hname
     * @return
     */
    HighTechEntity verifyCodeName(@Param("hcode") String hcode,
                                  @Param("companyId") Integer companyId,
                                  @Param("hname") String hname,
                                  @Param("existId") Integer existId);

    /**
     * 获取高品现存最大的代码
     *
     * @param companyId
     * @param year
     * @return
     */
    Integer getMaxCode(@Param("companyId") Integer companyId, @Param("year") Integer year);

    /**
     * 获取高品下拉列表
     *
     * @param year
     * @param companyId
     * @return
     */
    List<HighTechSelectModel> getHighTechSelect(@Param("year") Integer year,@Param("companyId") Integer companyId);

    /**
     * 高品收入统计数据
     * @param companyId
     * @param year
     * @return
     */
    List<HighTechIncomeModel> countData(@Param("companyId") Integer companyId, @Param("year") Integer year);
}
