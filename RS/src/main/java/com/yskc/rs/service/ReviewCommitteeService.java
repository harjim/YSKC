package com.yskc.rs.service;

import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.rs.models.UserInfo;
import com.yskc.rs.models.employee.EmployeeSelectModel;
import com.yskc.rs.models.employee.RdEmployeeModel;
import com.yskc.rs.models.project.ProjectEmployeeInfo;
import com.yskc.rs.models.reviewCommittee.QueryReviewModel;
import com.yskc.rs.models.reviewCommittee.ReviewAddModel;
import com.yskc.rs.models.reviewCommittee.ReviewModel;

import java.util.List;

/**
 * Created by hck
 * on 2020/9/1 10:23
 * description:
 */
public interface ReviewCommitteeService {

    PageModel<List<ReviewModel>> getReviews(Integer companyId, QueryReviewModel model);
    /**
     * 添加评审人员
     * @param info
     * @param model
     * @return
     */
    Boolean addReviews(UserInfo info, ReviewAddModel model) throws OwnerException;
    /**
     * 批量设置部门职位
     *
     * @param info
     * @param model
     * @return
     * @throws OwnerException
     */
    boolean updateDeptPosition(UserInfo info, RdEmployeeModel model) throws OwnerException;

    /**
     * 删除评审人员
     * @param companyId
     * @param ids
     * @return
     */
    Boolean delReview(Integer companyId,List<Integer> ids) throws OwnerException;

    /**
     * 获取评审人员下拉列表
     * @param companyId
     * @param year
     * @param ename
     * @return
     */
    List<EmployeeSelectModel> getReviewsSelect(Integer companyId, Integer year,String ename);

    /**
     * 获取专家委员会名单
     * @param companyId
     * @param query
     * @return
     */
    PageModel<List<ProjectEmployeeInfo>> getReviewList(Integer companyId, QueryReviewModel query);

    /**
     * 编辑评审人员排序
     * @param userInfo
     * @param models
     * @return
     */
    boolean editSeq(UserInfo userInfo, List<ReviewModel> models);
}
