package com.xxl.job.executor.dao.rs;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xxl.job.executor.entity.rs.RdEmployeeEntity;
import com.xxl.job.executor.models.ProjectInfo.EmployeeNumModel;
import com.xxl.job.executor.models.ProjectInfo.ProjectCustomerModel;
import com.xxl.job.executor.models.ProjectInfo.ProjectDataModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Created by hck
 * on 2020/11/4 14:48
 * description:
 */
@Repository
public interface RdEmployeeDao extends BaseMapper<RdEmployeeEntity> {
    /**
     * 获取公司研发人员数量
     * @param companyIds
     * @param countTime
     * @return
     */
    List<EmployeeNumModel> getRdEmployee(@Param("companyIds") List<Integer> companyIds, @Param("countTime") Date countTime);

    /**
     * 获取公司项目rdTitle列表
     * @param models
     * @param countTime
     * @return
     */
    List<ProjectDataModel> getRdTitle(@Param("models") List<ProjectCustomerModel> models, @Param("countTime") Date countTime);
}
