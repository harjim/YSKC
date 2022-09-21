package com.yskc.ms.dao.rs;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yskc.ms.entity.rs.RdEmployeeEntity;
import com.yskc.ms.models.rs.EmployeeSelectModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author huronghua
 * @email 267267134@qq.com
 * @date 2019-11-18 14:21:04
 */
@Repository
public interface RdEmployeeDao extends BaseMapper<RdEmployeeEntity> {


    /**
     * 根据工号获取人员列表
     *
     * @param companyId
     * @param enumbers
     * @return
     */
    List<EmployeeSelectModel> getEmployeeByEnumber(@Param("companyId") Integer companyId, @Param("enumbers") List<String> enumbers);


}
