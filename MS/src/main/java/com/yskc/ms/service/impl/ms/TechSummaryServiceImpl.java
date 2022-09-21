package com.yskc.ms.service.impl.ms;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yskc.common.model.PageResult;
import com.yskc.common.utils.PageUtils;
import com.yskc.ms.dao.ms.ProjectMemberDao;
import com.yskc.ms.dao.ms.TechSummaryDao;
import com.yskc.ms.dao.rs.RsTProjectDao;
import com.yskc.ms.enums.MemberTypeEnum;
import com.yskc.ms.models.DataPermModel;
import com.yskc.ms.models.project.ProjectMemberModel;
import com.yskc.ms.models.techsummary.QueryTechSummaryModel;
import com.yskc.ms.models.techsummary.TechStageFilesModel;
import com.yskc.ms.models.techsummary.TechSummaryModel;
import com.yskc.ms.models.techsummary.TechTotalSummaryModel;
import com.yskc.ms.service.ms.TechSummaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.text.MessageFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @BelongsProject: ms
 * @BelongsPackage: com.yskc.ms.service.impl.ms
 * @Author: zhangdingfu
 * @CreateTime: 2020-10-15 09:04
 * @Description: 技改汇总业务实现层
 */
@Service
public class TechSummaryServiceImpl implements TechSummaryService {

    @Autowired
    private TechSummaryDao techSummaryDao;
    @Autowired
    private ProjectMemberDao projectMemberDao;
    @Autowired
    private RsTProjectDao rsTProjectDao;

    @Override
    public PageResult getList(QueryTechSummaryModel query, DataPermModel dataPerm) {
        TechTotalSummaryModel totalSummary = new TechTotalSummaryModel();
        Pagination page = query.getPagination();
        if (CollectionUtils.isEmpty(page.getDescs()) && CollectionUtils.isEmpty(page.getAscs())) {
            List<String> descs = new ArrayList<>();
            descs.add("operationTime");
            descs.add("year");
            page.setDescs(descs);
        }
        List<TechSummaryModel> list = techSummaryDao.getList(page, query, dataPerm);
        if (CollectionUtils.isEmpty(list)) {
            return PageUtils.buildPageResult(page, list, null, totalSummary);
        }
        List<Integer> projectIds = list.stream().map(a -> a.getProjectId()).collect(Collectors.toList());
        List<ProjectMemberModel> memberList = projectMemberDao.getMemberList(null, projectIds, CollUtil.newArrayList(MemberTypeEnum.Tech.getId()));
        Map<Integer, List<String>> projectMemberMap = new HashMap<>();
        memberList.forEach(item -> {
            Integer projectId = item.getProjectId();
            if (!projectMemberMap.containsKey(projectId)) {
                projectMemberMap.put(projectId, new ArrayList<>());
            }
            projectMemberMap.get(projectId).add(item.getRealName());
        });
        list.forEach(item -> {
            List<String> realNames = projectMemberMap.get(item.getProjectId());
            if (!CollectionUtils.isEmpty(realNames)) {
                item.setTechRealName(String.join(",", realNames));
            }
        });
        totalSummary = techSummaryDao.getTotal();
        return PageUtils.buildPageResult(page, list, null, totalSummary);
    }

    @Override
    public Map<String, Map<String, List<TechStageFilesModel>>> getStageFiles(Integer projectId) {
        List<TechStageFilesModel> stageFiles = rsTProjectDao.getStageFiles(projectId);
        Map<String, Map<String, List<TechStageFilesModel>>> data = new LinkedHashMap<>();
        String keyFormat = "{0}_{1}";
        Map<String, TechStageFilesModel> stageFilesMap = new LinkedHashMap<>();
        stageFiles.forEach(item -> {
            String key = MessageFormat.format(keyFormat, item.getStageKey(), item.getStageListId());
            if (!stageFilesMap.containsKey(key)) {
                stageFilesMap.put(key, item);
            }
            stageFilesMap.get(key).loadFile(item);
        });
        stageFilesMap.values().forEach(item -> {
            String stageKey = item.getStageKey();
            if (!data.containsKey(stageKey)) {
                Map<String, List<TechStageFilesModel>> filesMap = new HashMap<>();
                filesMap.put("deliverData", new ArrayList<>());
                filesMap.put("dockingData", new ArrayList<>());
                data.put(stageKey, filesMap);
            }
            if (item.getItemType() != null) {
                String type = item.getItemType() == 0 ? "deliverData" : "dockingData";
                data.get(stageKey).get(type).add(item);
            }
        });
        return data;
    }
}
