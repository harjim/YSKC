package com.yskc.rs.service.impl;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.common.utils.PageUtils;
import com.yskc.rs.dao.*;
import com.yskc.rs.dao.project.ReviewCommitteeDao;
import com.yskc.rs.entity.EmployeeEntity;
import com.yskc.rs.entity.StageEntity;
import com.yskc.rs.entity.project.ProjectDocFileEntity;
import com.yskc.rs.entity.project.ProjectEntity;
import com.yskc.rs.entity.project.ReviewCommitteeEntity;
import com.yskc.rs.models.UserInfo;
import com.yskc.rs.models.employee.EmployeeSelectModel;
import com.yskc.rs.models.employee.RdEmployeeModel;
import com.yskc.rs.models.project.ProjectEmployeeInfo;
import com.yskc.rs.models.reviewCommittee.QueryReviewModel;
import com.yskc.rs.models.reviewCommittee.ReviewAddModel;
import com.yskc.rs.models.reviewCommittee.ReviewModel;
import com.yskc.rs.service.ReviewCommitteeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by hck
 * on 2020/9/1 10:24
 * description:评审委员名单业务实现类
 */
@Service
public class ReviewCommitteeServiceImpl implements ReviewCommitteeService {
    @Autowired
    private EmployeeDao employeeDao;
    @Autowired
    private RdEmployeeDao rdEmployeeDao;
    @Autowired
    private ReviewCommitteeDao reviewCommitteeDao;
    @Autowired
    private StageDao stageDao;
    @Autowired
    private ProjectDocFileDao projectDocFileDao;
    @Autowired
    private ProjectDao projectDao;

    @Override
    public PageModel<List<ReviewModel>> getReviews(Integer companyId, QueryReviewModel model) {
        Pagination page = model.getPagination();
        if (CollectionUtils.isEmpty(page.getAscs()) && CollectionUtils.isEmpty(page.getDescs())) {
            List<String> ascs = new ArrayList<>();
            ascs.add("pr.seq");
            ascs.add("pr.enumber");
            page.setAscs(ascs);
        }
        //List<String> existReviews=reviewCommitteeDao.getExistList(companyId,model.getYear());
        return PageUtils.build(page, reviewCommitteeDao.getReviewList(companyId, page, model));
    }

    @Override
    public Boolean addReviews(UserInfo info, ReviewAddModel model) throws OwnerException {
        Date date = new Date();
        if (CollectionUtils.isEmpty(model.getEnumbers())) {
            throw new OwnerException("请选择要添加的人员");
        }
        Integer maxSeq = reviewCommitteeDao.getMaxSeq(info.getCompanyId(), model.getYear());
        maxSeq = (null == maxSeq ? 0 : maxSeq);
        List<String> existEnumbers = reviewCommitteeDao.getByEnumbers(model.getYear(), info.getCompanyId(), model.getEnumbers());
        Map<String, Object> enumberMap = new HashMap<>();
        existEnumbers.forEach(item -> {
            enumberMap.put(item, null);
        });
        Map<String,RdEmployeeModel> map = new HashMap<>();
        List<RdEmployeeModel> rdEmployeeModels = rdEmployeeDao.getDeptByEnumbers(info.getCompanyId(), model.getYear(), model.getEnumbers());
        if (!CollectionUtils.isEmpty(rdEmployeeModels)){
            map = rdEmployeeModels.stream().collect(Collectors.toMap(RdEmployeeModel::getEnumber, a->a));
        }
        List<EmployeeEntity> employees = employeeDao.getByNumbers(info.getCompanyId(), model.getEnumbers());
        List<ReviewCommitteeEntity> entityList = new ArrayList<>();
        for (EmployeeEntity entity : employees) {
            String enumber = entity.getEnumber();
            if (enumberMap.containsKey(enumber)) {
                continue;
            }
            ReviewCommitteeEntity review = new ReviewCommitteeEntity();
            if (map.containsKey(enumber)){
                review.setDeptName(map.get(enumber).getDeptName());
                review.setPosition(map.get(enumber).getPosition());
            }else {
                review.setDeptName(entity.getDeptName());
                review.setPosition(entity.getPosition());
            }
            review.setCompanyId(info.getCompanyId());
            review.setCreateTime(date);
            review.setCreatorId(info.getUserId());
            review.setEnumber(enumber);
            review.setLastUpdateTime(date);
            review.setLastUpdatorId(info.getUserId());
            review.setMsCreatorId(info.getMsUserId());
            review.setMsLastUpdatorId(info.getMsUserId());
            review.setYear(model.getYear());
            review.setSeq(++maxSeq);
            entityList.add(review);
        }
        if (!CollectionUtils.isEmpty(entityList)) {
            return reviewCommitteeDao.insertList(entityList) > 0;
        }
        return true;
    }

    @Override
    public boolean updateDeptPosition(UserInfo info, RdEmployeeModel model) throws OwnerException {
        if (!StringUtils.hasLength(model.getDeptName())||
                CollectionUtils.isEmpty(model.getIds())||!StringUtils.hasLength(model.getPosition())){
            return true;
        }
        return reviewCommitteeDao.setDeptPosition(model.getPosition(),model.getDeptName(), model.getIds(), info.getId(), info.getMsUserId(), new Date())>0;
    }


    @Override
    public Boolean delReview(Integer companyId, List<Integer> ids) throws OwnerException {
        if (CollectionUtils.isEmpty(ids)) {
            throw new OwnerException("请选择要删除的评审人员");
        }
        return reviewCommitteeDao.deleteBatchIds(ids) > 0;
    }


    @Override
    public List<EmployeeSelectModel> getReviewsSelect(Integer companyId, Integer year, String ename) {
        List<EmployeeSelectModel> models = reviewCommitteeDao.getSelectList(companyId, year, ename);
        return models;
    }

    @Override
    public PageModel<List<ProjectEmployeeInfo>> getReviewList(Integer companyId, QueryReviewModel query) {
        Pagination page = query.getPagination();
        if (CollectionUtils.isEmpty(page.getAscs()) && CollectionUtils.isEmpty(page.getDescs())) {
            List<String> ascs = new ArrayList<>();
            ascs.add("pr.seq");
            ascs.add("pr.enumber");
            page.setAscs(ascs);
        }
        Integer year;
        if (query.getDocDate() != null) {
            year = DateUtil.year(query.getDocDate());
        } else {
            ProjectDocFileEntity docFile = projectDocFileDao.selectById(query.getpDocFileId());
            StageEntity stage = stageDao.getStageInfo(docFile.getProjectId(), query.getpDocFileId());
            if (stage != null && stage.getBeginDate() != null) {
                year = DateUtil.year(stage.getBeginDate());
            } else {
                ProjectEntity project = projectDao.selectById(docFile.getProjectId());
                year = project.getBeginYear();
            }
        }
        List<ProjectEmployeeInfo> list = reviewCommitteeDao.getList(companyId, page, query, year);
        return PageUtils.build(page, list);
    }

    @Override
    public boolean editSeq(UserInfo userInfo, List<ReviewModel> models) {
        Date now = new Date();
        List<ReviewCommitteeEntity> list = new ArrayList<>();
        models.forEach(item->{
            ReviewCommitteeEntity review = new ReviewCommitteeEntity();
            review.setId(item.getId());
            review.setSeq(item.getSeq());
            review.setLastUpdateTime(now);
            review.setLastUpdatorId(userInfo.getUserId());
            review.setMsLastUpdatorId(userInfo.getMsUserId());
            list.add(review);
        });
        return reviewCommitteeDao.editSeq(list)>0;
    }
}
