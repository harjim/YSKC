package com.yskc.docservice.dao.rs;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yskc.docservice.entity.rs.PatentDetailEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by hck
 * on 2020/6/29 11:32
 * description:
 */
@Repository
public interface PatentDetailDao extends BaseMapper<PatentDetailEntity> {

    List<PatentDetailEntity> getListByPatentNos(@Param("companyId") Integer companyId);

    Integer insertPatents(@Param("patentDetails") List<PatentDetailEntity> patentDetails, @Param("companyId") Integer companyId);

    Integer updatePatents(@Param("patentDetails") List<PatentDetailEntity> patentDetails, @Param("companyId") Integer companyId);

}
