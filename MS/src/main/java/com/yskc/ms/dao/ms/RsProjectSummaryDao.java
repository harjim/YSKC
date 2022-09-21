package com.yskc.ms.dao.ms;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yskc.ms.entity.ms.RsProjectSummary;
import com.yskc.ms.models.DataPermModel;
import com.yskc.ms.models.project.QueryProjectDetailModel;
import com.yskc.ms.models.project.RsProjectSummaryModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: hck
 * @DateTime: 2021/7/24 10:46
 * @Description:
 */
@Repository
public interface RsProjectSummaryDao extends BaseMapper<RsProjectSummary> {
    /**
     * 获取项目负责人列表
     *
     * @param pagination
     * @param query
     * @param dataPerm
     * @param asc
     * @param desc
     * @return
     */
    List<RsProjectSummaryModel> getList(@Param("page") Pagination pagination,
                                        @Param("query") QueryProjectDetailModel query,
                                        @Param("dataPerm")DataPermModel dataPerm,
                                        @Param("asc") List<String> asc,
                                        @Param("desc") List<String> desc);

    /**
     * 获取项目负责人列表不分页
     *
     * @param query
     * @param dataPerm
     * @return
     */
    List<RsProjectSummaryModel> getExportList(@Param("query") QueryProjectDetailModel query,
                                        @Param("dataPerm")DataPermModel dataPerm);

    /**
     * 获取汇总信息
     *
     * @param query
     * @return
     */
    RsProjectSummaryModel getTotal(@Param("query") QueryProjectDetailModel query,@Param("dataPerm")DataPermModel dataPerm);
}
