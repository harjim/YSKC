package com.yskc.rs.dao.project;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yskc.rs.entity.project.ProjectYearFeeEntity;
import com.yskc.rs.models.projectyearfee.ProjectYearFeeInfoModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.dao.project
 * @Author: zhangdingfu
 * @CreateTime: 2022-05-07 08:55
 * @Description: 优惠明细表年费用dao层
 */
@Repository
public interface ProjectYearFeeDao extends BaseMapper<ProjectYearFeeEntity> {
    /**
     * 插入或保存
     *
     * @param entity
     * @return
     */
    int insertOrUpdate(@Param("e") ProjectYearFeeEntity entity);

    /**
     * 获取优惠明细表年费用
     *
     * @param companyId
     * @param year
     * @return
     */
    ProjectYearFeeInfoModel getYearFee(@Param("companyId") Integer companyId, @Param("year") Integer year);
}
