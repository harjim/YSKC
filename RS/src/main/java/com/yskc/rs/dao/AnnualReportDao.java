package com.yskc.rs.dao;

import com.yskc.rs.entity.AnnualReportEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yskc.rs.models.sysDocument.SysDocumentModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 
 * 
 * @author huronghua
 * @email 267267134@qq.com
 * @date 2019-09-25 17:16:17
 */
@Repository
public interface AnnualReportDao extends BaseMapper<AnnualReportEntity> {

    List<SysDocumentModel> queryDocument(@Param("pagination") Pagination pagination, @Param("fileName") String fileName, @Param("fileType") int fileType, @Param("year") String year, @Param("companyId") int companyId);
}
