package com.yskc.docservice.service.rd.doc;

import com.yskc.common.utils.DateUtil;
import com.yskc.common.utils.JsonUtils;
import com.yskc.docservice.dao.rs.StageDao;
import com.yskc.docservice.dao.rs.init.InitMemberDao;
import com.yskc.docservice.dao.rs.project.ProjectDao;
import com.yskc.docservice.entity.rs.StageEntity;
import com.yskc.docservice.entity.rs.project.ProjectEntity;
import com.yskc.docservice.models.rs.initmember.InitMemberModel;
import com.yskc.docservice.service.rd.RDDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by hck
 * on 2020/6/23 11:52
 * description:项目任务书
 */
@Component("TaskForm_Doc")
@Scope("prototype")
public class TaskFormDocument extends RDDocument {

  @Autowired
  private ProjectDao projectDao;
  @Autowired
  private StageDao stageDao;
  @Autowired
  private InitMemberDao initMemberDao;


  @Override
  protected Map getDocMap(ProjectEntity project, Integer currentYear) {
    Map<String, Object> map = new HashMap<>();
    String memberStr = getMemberStr(project.getId(),this.docFile.getId());
    if (!StringUtils.isEmpty(this.docFile.getData())) {
      map = JsonUtils.jsonToPojo(this.docFile.getData(), Map.class);
    }
    if (!map.containsKey("allData")) {
      map.put("allData", new ArrayList<>());
    }
    if (map.containsKey("beginDate")) {
      String startDate = DateUtil.getDateTime(project.getBeginDate(), "yyyy年MM月");
      map.put("beginDate",startDate);
    }
    if (map.containsKey("endDate")) {
      String endDate = DateUtil.getDateTime(project.getEndDate(), "yyyy年MM月");
      map.put("endDate",endDate);
    }
    map.put("memberStr", memberStr);
    return map;
  }

  private String getMemberStr(Integer projectId, Integer pDocFileId) {
    StringBuilder builder = new StringBuilder();
    ProjectEntity projectEntity = projectDao.selectById(projectId);
    StageEntity stage = stageDao.getStageInfo(projectId, pDocFileId);
    Integer year = cn.hutool.core.date.DateUtil.year(stage != null && stage.getBeginDate() != null ? stage.getBeginDate() : projectEntity.getBeginDate());
    List<InitMemberModel> memberList;
    if (projectEntity.getParentId() == 0 && projectEntity.getHasChild()) {
      //查询所有子项目成员列表
      List<ProjectEntity> projectEntityList = projectDao.getChildrenById(projectId);
      if (CollectionUtils.isEmpty(projectEntityList)) {
        return "";
      }
      List<Integer> projectIds = projectEntityList.stream().map(ProjectEntity::getId).collect(Collectors.toList());
      memberList = initMemberDao.getMemberEnames(projectIds, year);
    } else {
      memberList = initMemberDao.getMemberEname(projectId, year);
    }
    if (!CollectionUtils.isEmpty(memberList)) {
      memberList.forEach(item -> {
        builder.append(item.getEname()).append("、");
      });
      return builder.substring(0, builder.length() - 1);
    }
    return builder.toString();
  }

  @Override
  protected Map getDocMap() throws IOException {
    // 项目组成员 memberStr
    Map resultMap = new HashMap<>();
    String memberStr = getMemberStr(dataFactory.getProjectInfo().getId(),this.docFile.getId());
    resultMap.put("memberStr", memberStr);

    return resultMap;
  }
}
