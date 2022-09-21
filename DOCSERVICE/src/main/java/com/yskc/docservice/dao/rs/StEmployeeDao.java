package com.yskc.docservice.dao.rs;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yskc.docservice.entity.rs.StEmployeeEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StEmployeeDao extends BaseMapper<StEmployeeEntity> {

    List<String> getByenumbers(@Param("companyId")Integer companyId,@Param("year")Integer year,
                               @Param("enumbers") List<String> enumbers);

    Integer addStEmployees(@Param("list")List<StEmployeeEntity> list);

}
