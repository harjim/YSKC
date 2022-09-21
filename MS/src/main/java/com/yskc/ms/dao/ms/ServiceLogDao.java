package com.yskc.ms.dao.ms;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yskc.ms.entity.ms.QueryServiceModel;
import com.yskc.ms.entity.ms.ServiceLog;
import com.yskc.ms.models.DataPermModel;
import com.yskc.ms.models.servicelog.ExportServiceInfoModel;
import com.yskc.ms.models.servicelog.ServiceInfoModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by hck
 * on 2020/5/5 14:41
 * 客户服务记录
 */
@Repository
public interface ServiceLogDao extends BaseMapper<ServiceLog> {

    List<ServiceInfoModel> queryByParams(@Param("page") Pagination page, @Param("query") QueryServiceModel query, @Param("dataPerm") DataPermModel dataPerm);


    Map<String, Integer> getStat(@Param("query") QueryServiceModel model, @Param("dataPerm") DataPermModel dataPerm);

    /**
     * 获取导出数据
     *
     * @param query
     * @param dataPerm
     * @return
     */
    List<ExportServiceInfoModel> getExportData(@Param("query") QueryServiceModel query, @Param("dataPerm") DataPermModel dataPerm);
}
