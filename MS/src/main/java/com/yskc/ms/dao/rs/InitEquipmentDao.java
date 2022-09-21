package com.yskc.ms.dao.rs;

import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yskc.ms.models.projectApproval.QueryProjectEquipmentModel;
import com.yskc.ms.models.rs.summary.InitEquipmentModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @DateTime: 2021/9/15 14:19
 * @Description:
 * @author: hsx
 */

@Repository
public interface InitEquipmentDao {

    /**
     * 获取项目成员列表
     * @param query
     * @return
     */
    List<InitEquipmentModel> getList(@Param("page") Pagination page,@Param("query") QueryProjectEquipmentModel query);
    /**
     * 根据年和项目获取设备
     * @param companyId
     * @param year
     * @param projectId
     * @return
     */
    List<InitEquipmentModel> getEquList(@Param("companyId") Integer companyId, @Param("year") Integer year,@Param("projectId") Integer projectId);
}
