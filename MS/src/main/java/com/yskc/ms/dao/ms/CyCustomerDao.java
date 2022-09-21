package com.yskc.ms.dao.ms;

import java.util.Date;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yskc.ms.entity.ms.CyCustomer;

@Repository
public interface CyCustomerDao  extends BaseMapper<CyCustomer> {
    Integer addList(@Param("customerList") List<Map> customerList);
	Integer insertH3Agreement(@Param("agreeList") List<Map> agreeList);
	Integer insertH3Project(@Param("projectList") List<Map> projectList);
	Integer deleteH3Agreement();
	Integer deleteH3Project();
	Integer deleteH3Customer();

}
