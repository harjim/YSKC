package com.yskc.rs.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yskc.rs.entity.EmployeeOpenidEntity;
import com.yskc.rs.models.wechat.EmployeeOpenidModel;
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
     * 获取绑定列表
     *
     * @param employeeIds
     * @return
     */
    List<EmployeeOpenidModel> getBindList(@Param("employeeIds") List<Integer> employeeIds);
}
