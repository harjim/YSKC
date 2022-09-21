package com.yskc.docservice.dao.rs;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yskc.docservice.entity.rs.WorkshopEntity;
import com.yskc.docservice.models.rs.CommonOrgModel;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author huronghua
 * @email 267267134@qq.com
 * @date 2019-11-21 09:07:37
 */
@Repository
public interface WorkshopDao extends BaseMapper<WorkshopEntity> {


    /**
     * 批量插入
     * @param insertList
     */
    @Options(useGeneratedKeys = true)
    void addBatch(List<WorkshopEntity> insertList);

    /**
     * 获取公司工艺线组织
     * @param companyId
     * @return
     */
    List<CommonOrgModel> getCompanyOrg(@Param("companyId") Integer companyId);
}
