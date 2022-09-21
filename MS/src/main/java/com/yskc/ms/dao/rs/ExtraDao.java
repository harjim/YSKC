package com.yskc.ms.dao.rs;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yskc.ms.entity.rs.ExtraEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * Created by hck
 * on 2020/12/4 14:24
 * description:
 */
@Repository
public interface ExtraDao extends BaseMapper<ExtraEntity> {
    /**
     * 根据公司获取拓展信息
     * @param companyId
     * @return
     */
    ExtraEntity getByCompany(@Param("companyId") Integer companyId);
}
