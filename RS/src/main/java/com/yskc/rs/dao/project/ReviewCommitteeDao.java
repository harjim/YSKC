package com.yskc.rs.dao.project;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yskc.rs.entity.project.ReviewCommitteeEntity;
import com.yskc.rs.models.employee.EmployeeSelectModel;
import com.yskc.rs.models.project.ProjectEmployeeInfo;
import com.yskc.rs.models.reviewCommittee.QueryReviewModel;
import com.yskc.rs.models.reviewCommittee.ReviewModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Created by hck
 * on 2020/9/1 10:21
 * description:
 */
@Repository
public interface ReviewCommitteeDao extends BaseMapper<ReviewCommitteeEntity> {
    /**
     * 获取已存在的评审人员
     * @param year
     * @param companyId
     * @param enumbers
     * @return
     */
    List<String> getByEnumbers(@Param("year") Integer year, @Param("companyId") Integer companyId, @Param("enumbers") List<String> enumbers);

    /**
     * 批量插入
     * @param entityList
     * @return
     */
    Integer insertList(@Param("entityList") List<ReviewCommitteeEntity> entityList);


    Integer setDeptPosition(@Param("position") String position,@Param("deptName") String deptName, @Param("ids") List<Integer> ids,
                            @Param("userId") Integer userId, @Param("msUserId") Integer msUserId,
                            @Param("now") Date now);

    List<ReviewModel> getReviewList(@Param("companyId") Integer companyId, @Param("page") Pagination page, @Param("model") QueryReviewModel model);

    /**
     * 公司和年份获取评审成员列表
     * @param companyId
     * @param year
     * @return
     */
    List<String> getExistList(@Param("companyId") Integer companyId, @Param("year") Integer year);

    List<EmployeeSelectModel> getSelectList(@Param("companyId") Integer companyId, @Param("year") Integer year, @Param("ename") String ename);


    List<ProjectEmployeeInfo> getList(@Param("companyId") Integer companyId,
                                      @Param("page") Pagination page,
                                      @Param("query") QueryReviewModel query,
                                      @Param("year") Integer year);

    /**
     * 获取评审人员信息
     * @param enumbers
     * @param beginYear
     * @param companyId
     * @return
     */
    List<ProjectEmployeeInfo> getReviews(@Param("enumbers") List<String> enumbers, @Param("beginYear") Integer beginYear, @Param("companyId") Integer companyId);

    /**
     * 获取评审人员信息
     * @param enumbers
     * @param companyId
     * @return
     */
    List<ProjectEmployeeInfo> getEmployees(@Param("enumbers") List<String> enumbers,@Param("companyId") Integer companyId);

    /**
     * 获取评审人员最大排序
     * @param companyId
     * @param year
     * @return
     */
    Integer getMaxSeq(@Param("companyId") Integer companyId, @Param("year") Integer year);

    /**
     * 编辑评审人员排序
     * @param list
     * @return
     */
    int editSeq(@Param("list") List<ReviewCommitteeEntity> list);
}
