package com.yskc.ms.dao.rs;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yskc.ms.entity.rs.RsStepStatusEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * Created by hck
 * on 2020/5/20 14:09
 */
@Repository
public interface RsStepStatusDao extends BaseMapper<RsStepStatusEntity> {

    RsStepStatusEntity getStepStatus(@Param("productId") Integer productId);

}
