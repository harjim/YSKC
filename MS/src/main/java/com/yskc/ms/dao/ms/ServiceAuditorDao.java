package com.yskc.ms.dao.ms;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yskc.ms.entity.ms.ServiceAuditorEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * Created by hck
 * on 2020/12/18 17:06
 * description:
 */
@Repository
public interface ServiceAuditorDao extends BaseMapper<ServiceAuditorEntity> {
    Integer addOrUpdate(@Param("entity")ServiceAuditorEntity entity);

    Integer updateAuditor(@Param("entity")ServiceAuditorEntity entity);
}
