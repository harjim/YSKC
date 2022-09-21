package com.yskc.ms.dao.ms;

import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yskc.ms.models.DataPermModel;
import com.yskc.ms.models.project.ProjectProgressModel;
import com.yskc.ms.models.project.QueryProjectProgressModel;
import com.yskc.ms.models.projectAudit.FinaAuditModelGroup;
import com.yskc.ms.models.projectAudit.FinaAuditMonthModel;
import com.yskc.ms.models.projectAudit.FinaAuditcIdYearModel;
import com.yskc.ms.models.projectAudit.QueryFinaAuditModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface FinaAuditDao {



    /**
     * 获取项目进度列表
     *
     * @param page
     * @param query
     * @return
     */
    List<FinaAuditModelGroup> getList(@Param("page") Pagination page, @Param("query") QueryFinaAuditModel query, @Param("dataPerm") DataPermModel dataPerm);
    List<FinaAuditModelGroup> getListNom(@Param("page") Pagination page, @Param("query") QueryFinaAuditModel query, @Param("dataPerm") DataPermModel dataPerm);
    List<FinaAuditMonthModel> getMonthBycIdYear(@Param("fammList") List<FinaAuditcIdYearModel> fammList);
}