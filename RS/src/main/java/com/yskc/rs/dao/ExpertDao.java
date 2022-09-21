package com.yskc.rs.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yskc.rs.entity.Expert;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 
 * @author Administrator
 *
 */
@Repository
public interface ExpertDao extends BaseMapper<Expert> {

	/**
	 * 根据UUID查询详情
	 * @param expertUuid
	 * @return
	 */
	Expert getExpertByUuid(@Param("expertUuid") String expertUuid);
}
