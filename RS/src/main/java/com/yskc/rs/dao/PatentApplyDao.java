package com.yskc.rs.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yskc.rs.entity.PatentApplyEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by hck
 * on 2020/6/29 13:45
 * description:
 */
@Repository
public interface PatentApplyDao extends BaseMapper<PatentApplyEntity> {

    PatentApplyEntity getPojoByPatentNo(@Param("patentNo") String patentNo);

    Integer insertPatentApplys(@Param("patentApplys") List<PatentApplyEntity> patentApplys);

    Integer updatePatentApplys(@Param("patentApplys") List<PatentApplyEntity> patentApplys);

    List<PatentApplyEntity> getListByPatentNos(@Param("patentNos") List<String> patentNos);
}
