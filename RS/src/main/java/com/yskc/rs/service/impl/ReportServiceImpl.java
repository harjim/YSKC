package com.yskc.rs.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.utils.JsonUtils;
import com.yskc.rs.dao.DeptDao;
import com.yskc.rs.dao.ProjectDao;
import com.yskc.rs.dao.ReportDao;
import com.yskc.rs.entity.Dept;
import com.yskc.rs.entity.ReportEntity;
import com.yskc.rs.models.UserInfo;
import com.yskc.rs.models.dept.DeptTree;
import com.yskc.rs.models.project.PlanInfo;
import com.yskc.rs.service.ReportService;
import com.yskc.rs.utils.ToolUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author zhangdingfu
 */
@Service
public class ReportServiceImpl extends ServiceImpl<ReportDao, ReportEntity> implements ReportService {

    @Autowired
    private ReportDao reportDao;
    @Autowired
    private DeptDao deptDao;
    @Autowired
    private ProjectDao projectDao;
    @Autowired
    private CommonService commonService;

    @Override
    public Boolean setCnt(ReportEntity entity, UserInfo userInfo)throws OwnerException {
        // commonService.checkAuditModify(userInfo.getCompanyId(),entity.getRyear(), AuditModeEnum.RD_PROJECT,userInfo.getUserSource());
        return saveCntOrTechIntro(entity, userInfo);
    }

    @Override
    public PlanInfo getPlanInfo(Integer companyId, Integer ryear) {
        PlanInfo planInfo = reportDao.getPlanInfo(companyId, ryear);
        return planInfo;
    }

    @Override
    public List<String> getDeptIds(Integer companyId,Integer year) {
        PlanInfo planInfo = reportDao.getPlanInfo(companyId, year);
        if (planInfo!=null){
            String deptIds = planInfo.getDeptIds();
            if (StringUtils.hasLength(deptIds)){
                List<String> list = JsonUtils.jsonToList(deptIds, String.class);
                return list;
            }else {
                return new ArrayList<>();
            }
        }else {
            return new ArrayList<>();
        }
    }

    @Override
    public Boolean saveTechIntro(ReportEntity report, UserInfo userInfo) throws OwnerException {
        return saveCntOrTechIntro(report, userInfo);
    }

    @Override
    public String getTechIntro(Integer companyId, Integer year) {
        return reportDao.getTechIntro(companyId, year);
    }

    @Override
    public Boolean saveEmployeeAmount(ReportEntity report, UserInfo userInfo) throws OwnerException {
        return saveCntOrTechIntro(report,userInfo);
    }

    private Boolean saveCntOrTechIntro(ReportEntity report, UserInfo userInfo) throws OwnerException {
        Date now = new Date();
        List<Dept> depts = projectDao.getDeptIds(userInfo.getCompanyId(), report.getRyear());
        List<Integer> list = JsonUtils.jsonToList(report.getDeptIds(), Integer.class);
        if (!CollectionUtils.isEmpty(depts)){
            List<Dept> child = deptDao.getDeptByDeptList(list, userInfo.getCompanyId());
            Map<Integer, String> deptMap = child.stream().collect(Collectors.toMap(Dept::getId, Dept::getDeptName));
            for (Dept dept : depts) {
                if (!deptMap.containsKey(dept.getId())){
                    throw new OwnerException("部门【"+dept.getDeptName()+"】已被项目使用，不能移除!");
                }
            }
        }
        ReportEntity queryEntity = reportDao.queryByYear(userInfo.getCompanyId(), report.getRyear());
        if (queryEntity == null) {
            report.setRname("");
            report.setCompanyId(userInfo.getCompanyId());
            report.setCreateTime(now);
            report.setLastUpdateTime(now);
            report.setCreatorId(userInfo.getUserId());
            report.setMsCreatorId(userInfo.getMsUserId());
            report.setLastUpdatorId(userInfo.getUserId());
            report.setMsLastUpdatorId(userInfo.getMsUserId());
            return reportDao.insert(report) > 0;
        } else {
            report.setId(queryEntity.getId());
            report.setMsLastUpdatorId(userInfo.getMsUserId());
            report.setLastUpdateTime(now);
            report.setLastUpdatorId(userInfo.getUserId());
            return reportDao.saveCntOrTechIntro(report) > 0;
        }
    }
}
