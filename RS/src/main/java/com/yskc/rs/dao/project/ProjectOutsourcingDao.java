package com.yskc.rs.dao.project;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yskc.rs.entity.project.ProjectOutsourcing;
import com.yskc.rs.models.outsourcing.BaseOutsourcingModel;
import com.yskc.rs.models.outsourcing.OutsourcingSummaryModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.dao.project
 * @Author: zhangdingfu
 * @CreateTime: 2021-06-08 14:59
 * @Description: 项目委外费用dao层
 */
@Repository
public interface ProjectOutsourcingDao extends BaseMapper<ProjectOutsourcing> {
    /**
     * 获取月份区间的费用
     *
     * @param beginMonth
     * @param endMonth
     * @param type
     * @param companyId
     * @return
     */
    List<BaseOutsourcingModel> getMonthFunds(@Param("beginMonth") Date beginMonth, @Param("endMonth") Date endMonth,
                                             @Param("type") Integer type, @Param("companyId") Integer companyId);

    /**
     * 插入或更新
     *
     * @param list"
     * @return
     */
    int insertOrUpdate(@Param("list") List<ProjectOutsourcing> list);

    /**
     * 获取已存在的数据id
     *
     * @param list
     * @return
     */
    List<Integer> getExistIds(@Param("list") List<ProjectOutsourcing> list);

    /**
     * 项目委托费用
     *
     * @param begin
     * @param end
     * @param companyId
     * @return
     */
    List<ProjectOutsourcing> getProjectOutsourcing(@Param("begin") Date begin, @Param("end") Date end,
                                                   @Param("companyId") Integer companyId);

    /**
     * 获取年委外费用
     * @param companyId
     * @param begin
     * @param end
     * @return
     */
    List<ProjectOutsourcing> getYearOutsourcing(@Param("begin") Date begin, @Param("end") Date end,
                                                @Param("companyId") Integer companyId);

    /**
     * 获取
     * @param begin
     * @param end
     * @param companyId
     * @return
     */
    List<OutsourcingSummaryModel> getMonthOutsourcing(@Param("begin") Date begin, @Param("end") Date end, Integer companyId);

    /**
     * 统计存在的费用
     * @param projectIds
     * @return
     */
    int countOutsourcing(@Param("projectIds") List<Integer> projectIds);

}
