package com.yskc.rs.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yskc.rs.entity.EnergyEntity;
import com.yskc.rs.models.energy.EnergyModel;
import com.yskc.rs.models.energy.EnergyModifyModel;
import com.yskc.rs.models.energy.QueryEnergy;
import com.yskc.rs.models.excel.EnergyExcel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.dao
 * @Author: blueToWhite
 * @CreateTime: 2019-07-09 09:18
 * @Description: 设备Dao层
 */
@Repository
public interface EnergyDao extends BaseMapper<EnergyEntity> {
    /**
     * 查询公司所有数据
     *
     * @param page
     * @param companyId
     * @param query
     * @return
     */
    List<EnergyModel> queryAll(@Param("page") Pagination page, @Param("companyId") Integer companyId, @Param("query") QueryEnergy query);

    /**
     * 获取导出数据
     *
     * @param companyId
     * @param type
     * @param ename
     * @return
     */
    List<EnergyModel> queryForExport(@Param("companyId") Integer companyId, @Param("type") int type, @Param("ename") String ename);

    /**
     * 获取导出报表内容
     *
     * @param companyId
     * @param query
     * @return
     */
    List<EnergyExcel> getExportEnergyList(@Param("companyId") Integer companyId, @Param("query") QueryEnergy query);

    /**
     * 日期条件查询能源
     *
     * @param companyId
     * @param type
     * @param ename
     * @param beginDate
     * @param endDate
     * @return
     */
    List<EnergyModel> queryEnergyByDate(@Param("companyId") Integer companyId,
                                        @Param("type") String type, @Param("ename") String ename,
                                        @Param("beginDate") Date beginDate, @Param("endDate") Date endDate);

    /**
     * 更新研发部门
     *
     * @param setModel
     * @return
     */
    int updateRdDept(@Param("setModel") EnergyModifyModel setModel);

    /**
     *  批量更新
     * @param energyList
     * @param now
     * @param userId
     * @param msUserId
     * @return
     */
    int updateList(@Param("energyList") List<EnergyEntity> energyList, @Param("now") Date now,
                   @Param("userId") Integer userId, @Param("msUserId") Integer msUserId);

    /**
     * 通过项目能源id 获取原始数据
     *
     * @param ids
     * @return
     */
    List<EnergyEntity> getByProjectEnergyIds(@Param("ids") List<Integer> ids);

    /**
     * 批量插入
     *
     * @param energys
     * @return
     */
    int addList(@Param("energys") List<EnergyEntity> energys);

    /**
     * 查询能源使用
     *
     * @param ids
     * @return
     */
    List<Integer> getProjectUsed(@Param("ids") List<Integer> ids);
}
