package com.yskc.rs.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yskc.rs.entity.CDocumentEntity;
import com.yskc.rs.models.cdocument.CDocumentModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @DateTime: 2022/1/14 14:21
 * @Description:
 * @author: hsx
 */
@Repository
public interface CDocumentDao extends BaseMapper<CDocumentEntity> {

    /**
     * 根据年份，公司Id，类型获取data
     * @param year
     * @param companyId
     * @param type
     * @return
     */
    String getData(@Param("year") Integer year, @Param("companyId") Integer companyId,@Param("type") Integer type);

    /**
     * 获取Id，判断新增或者编辑
     * @param model
     * @return
     */
    Integer getId(@Param("model") CDocumentModel model);
}
