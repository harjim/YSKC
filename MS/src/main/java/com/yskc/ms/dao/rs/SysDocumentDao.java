package com.yskc.ms.dao.rs;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yskc.ms.entity.rs.SysDocumentEntity;
import com.yskc.ms.entity.rs.models.DocListModel;
import com.yskc.ms.models.SysDocumentModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @DateTime: 2021/9/6 15:40
 * @Description:
 */
@Repository
public interface SysDocumentDao extends BaseMapper<SysDocumentEntity> {

    List<DocListModel> queryDocList(@Param("listType") int listType, @Param("companyId") int companyId);

    List<SysDocumentModel> queryDocForDocList(@Param("companyId") int companyId, @Param("ids") List<Integer> ids, @Param("projectId") Integer projectId, @Param("patentNo") String patentNo, @Param("year") int year);

    /**
     * 获取项目查新报告列表
     *
     * @param projectId
     * @return
     */
    List<SysDocumentEntity> getReportByProject(@Param("projectId") Integer projectId);
}
