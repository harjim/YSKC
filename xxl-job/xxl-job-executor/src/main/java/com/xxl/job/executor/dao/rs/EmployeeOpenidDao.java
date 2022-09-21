package com.xxl.job.executor.dao.rs;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xxl.job.executor.entity.rs.EmployeeOpenidEntity;
import com.xxl.job.executor.models.attendance.CompanyEnumbersModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.dao
 * @Author: zhangdingfu
 * @CreateTime: 2020-10-27 10:18
 * @Description: 人员openid dao层
 */
@Repository
public interface EmployeeOpenidDao extends BaseMapper<EmployeeOpenidEntity> {
    /**
     * 获取微信openId
     *
     * @param list
     * @return
     */
    List<EmployeeOpenidEntity> getOpenid(@Param("list") List<CompanyEnumbersModel> list);
}
