package com.yskc.rs.utils;

import cn.hutool.core.collection.CollectionUtil;
import com.yskc.common.utils.JsonUtils;
import com.yskc.rs.models.docFile.DocFileDataModel;
import com.yskc.rs.models.projectDocFile.ProjectAnalysisModel;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.utils
 * @Author: zhangdingfu
 * @CreateTime: 2021-10-13 11:45
 * @Description: 项目进度分析util
 */
public class ProjectAnalysisUtils {

    /**
     * 会议纪要
     */
    public static final int MEETING = 2;
    /**
     * 项目年度技术总结
     */
    public static final int ANNUAL_REPORT = 24;
    /**
     * 立项报告
     */
    public static final int PROJECT_REPORT = 27;
    /**
     * 科技成果转化报告
     */
    public static final int ACHIEVEMENT_REPORT = 31;
    /**
     * 项目验收报告
     */
    public static final int CHECK_REPORT = 33;


    public static List<Integer> getAnalysisDocFileIds() {
        return CollectionUtil.newArrayList(MEETING,
                ANNUAL_REPORT, PROJECT_REPORT, ACHIEVEMENT_REPORT, CHECK_REPORT);
    }

    /**
     * 根据对应的文档，填写对应的预计于实际数
     *
     * @param list
     * @return
     */
    public static List<ProjectAnalysisModel> getDocAnalysis(List<DocFileDataModel> list) {
        Map<Integer, ProjectAnalysisModel> map = new HashMap<>();

        ProjectAnalysisModel current;
        Map<String, Object> dataMap;
        for (DocFileDataModel doc : list) {
            Integer docId = doc.getDocFileId();
            switch (docId) {
                case MEETING:
                    current = map.computeIfAbsent(4, k -> new ProjectAnalysisModel(4, "0", "0"));
                    current.addActuality(false);
                    break;
                case ANNUAL_REPORT:
                    // evaluate
                    Date month = doc.getMonth();
                    current = map.computeIfAbsent(8, k -> new ProjectAnalysisModel(8, "", "", month));
                    // 当前月份都为空 或 当前文档月份大于上一个文档的月份，则复写 evaluate
                    if ((month == null && current.getMonth() == null) || (month != null && current.getMonth() != null && month.compareTo(current.getMonth()) > 0)) {
                        dataMap = JsonUtils.jsonToPojo(doc.getData(), Map.class);
                        Object evaluate = dataMap.get("evaluate");
                        current.setActuality(StringUtils.isEmpty(evaluate) ? "" : evaluate.toString());
                    }
                    break;
                case ACHIEVEMENT_REPORT:
                    current = map.computeIfAbsent(9, k -> new ProjectAnalysisModel(9, "", ""));
                    dataMap = JsonUtils.jsonToPojo(doc.getData(), Map.class);
                    Object innovation = dataMap.get("innovation");
                    current.setActuality(StringUtils.isEmpty(innovation) ? "" : innovation.toString());
                    break;
                case CHECK_REPORT:
                    current = map.computeIfAbsent(10, k -> new ProjectAnalysisModel(10, "", ""));
                    dataMap = JsonUtils.jsonToPojo(doc.getData(), Map.class);
                    Object verification = dataMap.get("verification");
                    current.setActuality(StringUtils.isEmpty(verification) ? "" : verification.toString());
                    break;
                case PROJECT_REPORT:
                    dataMap = JsonUtils.jsonToPojo(doc.getData(), Map.class);
                    Object researchContentsObj = dataMap.containsKey("researchContents") ? dataMap.get("researchContents") : dataMap.get("primaryContent"),
                            innovationObj =  dataMap.get("innovation"),
                            economicIndicatorsObj = dataMap.containsKey("economicIndicators") ? dataMap.get("economicIndicators") : dataMap.get("indicators");
                    ProjectAnalysisModel researchContentsModel = map.computeIfAbsent(8, k -> new ProjectAnalysisModel(8, "", ""));
                    ProjectAnalysisModel innovationModel = map.computeIfAbsent(9, k -> new ProjectAnalysisModel(9, "", ""));
                    ProjectAnalysisModel economicIndicatorsModel = map.computeIfAbsent(10, k -> new ProjectAnalysisModel(10, "", ""));
                    researchContentsModel.setExpectation(StringUtils.isEmpty(researchContentsObj) ? "" : researchContentsObj);
                    innovationModel.setExpectation(StringUtils.isEmpty(innovationObj) ? "" : innovationObj);
                    economicIndicatorsModel.setExpectation(StringUtils.isEmpty(economicIndicatorsObj) ? "" : economicIndicatorsObj);
                    break;
                default:
                    break;

            }
        }
        return new ArrayList<>(map.values());

    }


}
