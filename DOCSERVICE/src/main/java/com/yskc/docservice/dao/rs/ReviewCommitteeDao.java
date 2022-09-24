package com.yskc.docservice.dao.rs;

import com.yskc.docservice.models.rs.project.ProjectEmployeeInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by hck
 * on 2020/9/1 10:21
 * description:
 */
@Repository
public interface ReviewCommitteeDao {
    /**
     * 获取评审人员信息
     * @param enumbers
     * @param beginYear
     * @param companyId
     * @return
     */
    List<ProjectEmployeeInfo> getReviews(@Param("enumbers") List<String> enumbers, @Param("beginYear") Integer beginYear, @Param("companyId") Integer companyId);

    /**
     * 获取评审人员信息 过程文档
     * @param enumbers
     * @param companyId
     * @return
     */
    List<ProjectEmployeeInfo> getEmployees(@Param("enumbers") List<String> enumbers,@Param("companyId") Integer companyId);
}
