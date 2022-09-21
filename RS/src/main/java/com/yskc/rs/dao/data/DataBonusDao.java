package com.yskc.rs.dao.data;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yskc.rs.entity.data.DataBonusEntity;
import com.yskc.rs.models.attendance.UsedBonusModel;
import com.yskc.rs.models.data.DataBonusInfo;
import com.yskc.rs.models.data.DataBonusQuery;
import com.yskc.rs.models.excel.BonusExcel;
import com.yskc.rs.models.projectattendance.UsedQueryModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 员工奖金表
 * @author huronghua
 */
@Repository
public interface DataBonusDao  extends BaseMapper<DataBonusEntity> {
    /**
     * 批量添加
     * @param dataBonusEntityList
     * @return
     */
    boolean addBatch(@Param("dataBonusEntityList") List<DataBonusEntity> dataBonusEntityList);

    /**
     * 插入或者更新
     * @param dataBonusEntityList
     * @return
     */
    boolean insertOrUpdate(@Param("dataBonusEntityList") List<DataBonusEntity> dataBonusEntityList);
    /**
     * 获取员工奖金列表
     * @param page
     * @param dataBonusQuery
     * @return
     */
    List<DataBonusInfo> getDataBonusList(@Param("page") Pagination page, @Param("dataBonusQuery") DataBonusQuery dataBonusQuery);

    /**
     * 批量获取奖金信息
     * @param queryModels
     * @return
     */
    List<DataBonusEntity> getDataBonusEntityList(@Param("queryModels") List<UsedQueryModel> queryModels);

    /**
     * 获取正在使用的奖金记录
     * @param companyId
     * @param ids
     * @return
     */
    List<UsedBonusModel> getUsedBonusList(@Param("companyId") Integer companyId, @Param("ids") List<Integer> ids);

    /**
     * 导出员工奖金数据
     * @param dataBonusQuery
     * @return
     */
    List<BonusExcel> exportBonusData(@Param("dataBonusQuery") DataBonusQuery dataBonusQuery);
}
