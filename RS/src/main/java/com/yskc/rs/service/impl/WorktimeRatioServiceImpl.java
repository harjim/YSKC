package com.yskc.rs.service.impl;

import com.yskc.common.utils.DateUtil;
import com.yskc.rs.dao.WorktimeRatioDao;
import com.yskc.rs.entity.project.WorktimeRatioEntity;
import com.yskc.rs.models.UserInfo;
import com.yskc.rs.models.ratio.EquipmentRatioModel;
import com.yskc.rs.service.WorktimeRatioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.service.impl
 * @Author: zhangdingfu
 * @CreateTime: 2019-08-12 13:37
 * @Description: 研发比例业务实现层
 */
@Service
public class WorktimeRatioServiceImpl implements WorktimeRatioService {

    @Autowired
    private WorktimeRatioDao worktimeRatioDao;

    /**
     * @param userInfo
     * @param equipmentRatioModel
     * @return
     */
    @Override
    public Boolean setEquipmentRatio(UserInfo userInfo, EquipmentRatioModel equipmentRatioModel) {
        WorktimeRatioEntity worktimeRatioEntity = worktimeRatioDao.getWorktimeRatio(userInfo.getCompanyId(), equipmentRatioModel.getMonth(), equipmentRatioModel.getProjectId());
        Date now = new Date();
        BigDecimal one = new BigDecimal(1);
        if (worktimeRatioEntity == null) {
            worktimeRatioEntity = new WorktimeRatioEntity();
            worktimeRatioEntity.setCreateTime(now);
            worktimeRatioEntity.setAuxiliary(one);
            worktimeRatioEntity.setResearch(one);
            worktimeRatioEntity.setTechnical(one);
            worktimeRatioEntity.setCompanyId(userInfo.getCompanyId());
            worktimeRatioEntity.setProjectId(equipmentRatioModel.getProjectId());
            worktimeRatioEntity.setMonth(equipmentRatioModel.getMonth());
            worktimeRatioEntity.setMsCreatorId(userInfo.getUserSource() == 0 ? -1 : userInfo.getId());
            worktimeRatioEntity.setCreatorId(userInfo.getUserSource() == 0 ? userInfo.getId() : -1);
        }
        worktimeRatioEntity.setLastUpdateTime(now);
        worktimeRatioEntity.setLastUpdatorId(userInfo.getUserSource() == 0 ? userInfo.getId() : -1);
        worktimeRatioEntity.setMsLastUpdatorId(userInfo.getUserSource() == 0 ? -1 : userInfo.getId());
        worktimeRatioEntity.setLaboratory(equipmentRatioModel.getLab());
        worktimeRatioEntity.setProduction(equipmentRatioModel.getProd());
        if (worktimeRatioEntity.getId() == null) {
            return worktimeRatioDao.insert(worktimeRatioEntity) > 0;
        }
        return worktimeRatioDao.updateById(worktimeRatioEntity) > 0;
    }

    /**
     * 获取当前研发配置
     *
     * @param userId
     * @param companyId
     * @param projectId
     * @param month
     * @return
     */
    @Override
    public WorktimeRatioEntity getWorktimeRatioEntity(Integer userId, Integer companyId, Integer projectId, Date month) {
        WorktimeRatioEntity worktimeRatioEntity = worktimeRatioDao.getWorktimeRatio(companyId, month, projectId);
        return worktimeRatioEntity;
    }

    /**
     * 获取当前月份配置
     * 不存在时，获取上个月的配置
     * 再不存在，就生成新的记录
     * @param userId
     * @param companyId
     * @param projectId
     * @param month
     * @return
     */
    @Override
    public WorktimeRatioEntity getWorktimeRatioByMonth(Integer userId, Integer companyId, Integer projectId, Date month,UserInfo userInfo) {
        WorktimeRatioEntity worktimeRatioEntity= worktimeRatioDao.getWorktimeRatio(companyId,month,projectId);
        if (worktimeRatioEntity == null) {
             worktimeRatioEntity= worktimeRatioDao.getWorktimeRatio(companyId,DateUtil.addMonths(month,-1),projectId);
             if(worktimeRatioEntity==null){
                 worktimeRatioEntity = new WorktimeRatioEntity();
                 worktimeRatioEntity.setLaboratory(BigDecimal.valueOf(1));
                 worktimeRatioEntity.setProduction(BigDecimal.valueOf(1));
                 worktimeRatioEntity.setCreateTime(new Date());
                 worktimeRatioEntity.setCompanyId(companyId);
                 worktimeRatioEntity.setCreatorId(userId);
                 worktimeRatioEntity.setProjectId(projectId);
                 worktimeRatioEntity.setAuxiliary(BigDecimal.valueOf(1));
                 worktimeRatioEntity.setResearch(BigDecimal.valueOf(1));
                 worktimeRatioEntity.setTechnical(BigDecimal.valueOf(1));
                 worktimeRatioEntity.setLaboratory(BigDecimal.valueOf(1));
                 worktimeRatioEntity.setProduction(BigDecimal.valueOf(1));
             }
            worktimeRatioEntity.setId(null);
        }
        worktimeRatioEntity.setLastUpdatorId(userInfo.getUserSource()==0?userInfo.getId():-1);
        worktimeRatioEntity.setMsLastUpdatorId(userInfo.getUserSource()==1?userInfo.getId():-1);
        worktimeRatioEntity.setLastUpdateTime(new Date());
        worktimeRatioEntity.setMonth(month);
        return worktimeRatioEntity;
    }
}
