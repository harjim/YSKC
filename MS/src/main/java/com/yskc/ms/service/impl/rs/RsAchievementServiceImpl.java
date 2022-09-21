package com.yskc.ms.service.impl.rs;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yskc.common.model.PageResult;
import com.yskc.common.utils.PageUtils;
import com.yskc.ms.dao.ms.FlowInstanceAchievementDao;
import com.yskc.ms.dao.rs.RsAchievementDao;
import com.yskc.ms.models.UserInfo;
import com.yskc.ms.models.rs.achievement.AchievementFileModel;
import com.yskc.ms.models.rs.achievement.AchievementModel;
import com.yskc.ms.models.rs.achievement.QueryAchievementModel;
import com.yskc.ms.service.rs.RsAchievementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @BelongsProject: ms
 * @BelongsPackage: com.yskc.ms.service.impl.rs
 * @Author: zhangdingfu
 * @CreateTime: 2021-11-19 16:45
 * @Description: rs成果业务实现层
 */
@Service
public class RsAchievementServiceImpl implements RsAchievementService {

    @Autowired
    private RsAchievementDao rsAchievementDao;
    @Autowired
    private FlowInstanceAchievementDao flowInstanceAchievementDao;

    @Override
    public PageResult getList(QueryAchievementModel query, UserInfo userInfo) {
        Pagination page = query.getPagination();
        if (CollectionUtils.isEmpty(page.getDescs()) && CollectionUtils.isEmpty(page.getAscs())) {
            page.setDescs(CollUtil.newArrayList("a.createTime", "lastUploadTime"));
        }
        List<AchievementModel> result = rsAchievementDao.getList(page, query);
        if (!CollectionUtils.isEmpty(result)) {
            List<Integer> achievementIds = result.stream().map(AchievementModel::getId).collect(Collectors.toList());
            List<Integer> auditAchievementIds = flowInstanceAchievementDao.getUserAudit(achievementIds, userInfo.getId());
            if (!CollectionUtils.isEmpty(auditAchievementIds)) {
                Map<Integer, Boolean> permissionMap = new HashMap<>();
                auditAchievementIds.forEach(aId -> permissionMap.put(aId, true));
                result.forEach(item -> item.setHasPermission(permissionMap.getOrDefault(item.getId(), false)));
            }
        }
        return PageUtils.buildPageResult(page, result);
    }

    @Override
    public List<AchievementFileModel> getFiles(Integer achievementId) {
        return rsAchievementDao.getFiles(achievementId);
    }

    @Override
    public Map<String, Object> getInfo(Integer id) {
        Map<String, Object> result = new HashMap<>(2);
        result.put("files", getFiles(id));
        result.put("info", rsAchievementDao.getInfo(id));
        return result;
    }

    @Override
    public Integer getAuditCnt(Integer year, Integer companyId, Integer userId) {
        List<Integer> achievementIds = rsAchievementDao.getAchievementIds(year, companyId);
        int result = 0;
        if (!CollectionUtils.isEmpty(achievementIds)) {
            List<Integer> auditAchievementIds = flowInstanceAchievementDao.getUserAudit(achievementIds, userId);
            if (!CollectionUtils.isEmpty(auditAchievementIds)) {
                result = auditAchievementIds.size();
            }
        }
        return result;
    }
}
