package com.yskc.ms.dao.rs;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yskc.ms.entity.rs.EmploymentInfoEntity;
import com.yskc.ms.models.company.EmploymentInfoModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * Created by hck
 * on 2020/12/4 14:29
 * description:
 */
@Repository
public interface EmploymentInfoDao extends BaseMapper<EmploymentInfoEntity> {

    EmploymentInfoModel getInfo(@Param("companyId") int companyId, @Param("year") int year);
}
