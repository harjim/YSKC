package com.yskc.ms.dao.rs;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yskc.ms.entity.rs.Expert;
import java.util.Date;
import java.util.List;

import com.yskc.ms.models.DataPermModel;
import com.yskc.ms.models.expert.QueryExpertModel;
import com.yskc.ms.models.role.QueryRoleModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
/**
 * 
 * @author Administrator
 *
 */
@Repository
public interface ExpertDao  extends BaseMapper<Expert> {
	/**
	 * 查询专家库
	 * @param page
	 * @param query
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	List<Expert> queryExpert(@Param("page") Pagination page, @Param("query") QueryExpertModel query, @Param("perm") DataPermModel perm, @Param("beginDate") Date beginDate, @Param("endDate")Date endDate);

	/**
	 * 查询编号
	 * @return
	 */
	String getMaxExpertNumber();

	/**
	 * 修改状态
	 * @param expert
	 * @return
	 */
	int updateStatus(Expert expert);

	/**
	 * 根据UUID查询详情
	 * @param expertUuid
	 * @return
	 */
	Expert getExpertByUuid(@Param("expertUuid") String expertUuid);
}
