package com.xxl.job.executor.dao.rs;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xxl.job.executor.entity.rs.RdDeptEntity;
import com.xxl.job.executor.models.ProjectInfo.ProjectCustomerModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Created by hck
 * on 2020/11/4 9:52
 * description:研发架构
 */
@Repository
public interface RdDeptDao extends BaseMapper<RdDeptEntity> {
    /**
     * 根据公司id及年份获取研发架构
     * @param companyModels
     * @return
     */
    List<RdDeptEntity> getByCompany(@Param("companyModels") List<ProjectCustomerModel> companyModels, @Param("beginDate") Date beginDate);
}
