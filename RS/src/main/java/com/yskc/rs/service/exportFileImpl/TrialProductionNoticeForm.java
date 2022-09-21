package com.yskc.rs.service.exportFileImpl;

import com.yskc.common.utils.DateUtil;
import com.yskc.common.utils.JsonUtils;
import com.yskc.rs.dao.company.CompanyHolidayDao;
import com.yskc.rs.dao.project.ProjectDocFileDataDao;
import com.yskc.rs.dao.project.ProjectYieldConfigDao;
import com.yskc.rs.entity.project.ProjectEntity;
import com.yskc.rs.models.company.CompanyHolidayModel;
import com.yskc.rs.models.docFile.DocFileDataModel;
import com.yskc.rs.models.document.DataModel;
import com.yskc.rs.models.projectyieldconfig.ProjectYieldConfigModel;
import com.yskc.rs.models.projectyieldconfig.QueryYieldConfigModel;
import com.yskc.rs.service.ExportDocFileService;
import com.yskc.rs.utils.ToolUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by hck
 * on 2020/6/15 17:17
 * description:试验试制导出
 */
@Component
public class TrialProductionNoticeForm implements ExportDocFileService {

    @Autowired
    private ProjectYieldConfigDao projectYieldConfigDao;
    @Autowired
    private ProjectDocFileDataDao projectDocFileDataDao;
    @Autowired
    private CompanyHolidayDao companyHolidayDao;

    @Override
    public Map<String, Object> exportDocFile(ProjectEntity projectEntity, Integer currentYear, String fileData, DataModel dataModel) {
        Map<String, Object> dataMap = new HashMap<>();
        if (StringUtils.isEmpty(fileData)) {
            dataMap.put("receiveLists", new ArrayList<>());
        } else {
            dataMap = JsonUtils.jsonToPojo(fileData, Map.class);
            //试验试制接受人员处理数据
            List<Map<String, String>> recieverList = new ArrayList<>();
            List<Map<String, Object>> receivers = JsonUtils.jsonToPojo(JsonUtils.objectToJson(dataMap.get("receiveList")), List.class);
            for (Map<String, Object> reces : receivers) {
                Map<String, String> department = JsonUtils.jsonToPojo(JsonUtils.objectToJson(reces.get("department")), Map.class);
                Map<String, String> emitPerson = JsonUtils.jsonToPojo(JsonUtils.objectToJson(reces.get("emitPerson")), Map.class);
                Map<String, String> newRecieves = new HashMap<>();
                newRecieves.put("reciveDept", "接收部门");
                if (department != null) {
                    newRecieves.put("department", department.get("name") != null ? department.get("name") : "");
                }
                newRecieves.put("recivePerson", "接收人");
                if (emitPerson != null) {
                    newRecieves.put("emitPerson", emitPerson.get("ename") != null ? emitPerson.get("ename") : "");
                }
                newRecieves.put("reciveCase", "接收情况");
                newRecieves.put("reception", reces.get("reception") != null ? (String) reces.get("reception") : "");
                recieverList.add(newRecieves);
            }
            dataMap.put("receiveLists", CollectionUtils.isEmpty(recieverList) ? new ArrayList<>() : recieverList);
            //处理试验试制时间 有旧数据用旧数据yyyy-MM-dd至yyyy-MM-dd 新数据 日期数组
            if (!dataMap.containsKey("pilotTime") && !dataMap.containsKey("endPilotTime")) {
                DocFileDataModel model = projectDocFileDataDao.getData(dataModel.getpDocFileId());
                if (null != model.getMonth()) {
                    QueryYieldConfigModel queryModel = new QueryYieldConfigModel();
                    queryModel.setProjectId(model.getProjectId());
                    queryModel.setMonth(model.getMonth());
                    List<Date> list = projectYieldConfigDao.getTrialDate(queryModel);
                    if (!CollectionUtils.isEmpty(list)) {
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        List<String> dates = new ArrayList<>();
                        list.forEach(item -> {
                            String format = dateFormat.format(item);
                            dates.add(format);
                        });
                        dataMap.put("trialDates", org.apache.commons.lang3.StringUtils.join(dates, ","));
                        Date emitTime = DateUtil.addDays(list.get(0),-1);
                        Date firstDay = DateUtil.getMonthFirstDay(emitTime);
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTime(emitTime);
                        int day = calendar.get(Calendar.DAY_OF_MONTH);
                        while (true) {
                            CompanyHolidayModel holiday = companyHolidayDao.getMonthHoliday(firstDay, projectEntity.getCompanyId());
                            Map<Integer,Boolean> holidayMap = ToolUtils.getHolidayMap(holiday, false, firstDay);
                            Boolean hasWorkDay = getEmitDay(holidayMap, day,calendar);
                            if (hasWorkDay) {
                                break;
                            }
                            firstDay = DateUtil.addMonths(firstDay, -1);
                            calendar.setTime(DateUtil.getMonthLastDay(firstDay));
                            day = calendar.get(Calendar.DAY_OF_MONTH);
                        }
                        dataMap.put("emitTime", dateFormat.format(calendar.getTime()));
                    } else {
                        Object trialDates = dataMap.get("trialDates");
                        if (trialDates instanceof List) {
                            trialDates = String.join(",", (List) trialDates);
                        }
                        dataMap.put("trialDates", trialDates);
                    }
                } else {
                    Object trialDates = dataMap.get("trialDates");
                    if (trialDates instanceof List) {
                        trialDates = String.join(",", (List) trialDates);
                    }
                    dataMap.put("trialDates", trialDates);
                }
            }
        }
        return dataMap;
    }

    public Boolean getEmitDay(Map<Integer, Boolean> map, int day,Calendar calendar) {
        while (true) {
            if (map.containsKey(day)) {
                if (day > 1) {
                    day--;
                } else {
                    return false;
                }
            } else {
                calendar.set(Calendar.DAY_OF_MONTH,day);
                return true;
            }
        }
    }
}
