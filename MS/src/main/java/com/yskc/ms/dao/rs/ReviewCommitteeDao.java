package com.yskc.ms.dao.rs;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yskc.ms.entity.rs.ReviewCommitteeEntity;
import com.yskc.ms.models.rs.ProjectEmployeeInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by hck
 * on 2020/9/1 10:21
 * description:
 */
@Repository
public interface ReviewCommitteeDao extends BaseMapper<ReviewCommitteeEntity> {

    List<ProjectEmployeeInfo> getReviews(@Param("enumbers") List<String> enumbers, @Param("beginYear") Integer beginYear, @Param("companyId") Integer companyId);
    /**
     * 获取评审人员信息
     * @param enumbers
     * @param companyId
     * @return
     */
    List<ProjectEmployeeInfo> getEmployees(@Param("enumbers") List<String> enumbers,@Param("companyId") Integer companyId);

}
