package com.yskc.docservice.service.rd.doc;

import com.yskc.common.utils.DateUtil;
import com.yskc.common.utils.JsonUtils;
import com.yskc.docservice.dao.rs.company.CompanyHolidayDao;
import com.yskc.docservice.dao.rs.project.ProjectDocFileDataDao;
import com.yskc.docservice.dao.rs.project.ProjectYieldConfigDao;
import com.yskc.docservice.entity.rs.project.ProjectEntity;
import com.yskc.docservice.models.rs.DocFileDataModel;
import com.yskc.docservice.models.rs.QueryYieldConfigModel;
import com.yskc.docservice.models.rs.company.CompanyHolidayModel;
import com.yskc.docservice.service.rd.RDDocument;
import com.yskc.docservice.utils.ToolUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by hck
 * on 2020/6/15 17:17
 * description:试验试制导出
 */
@Component( "TrialProductionNoticeForm_Doc" )
@Scope( "prototype" )
public class TrialProductionNoticeFormDocument extends RDDocument {

    @Autowired
    private ProjectYieldConfigDao projectYieldConfigDao;
    @Autowired
    private ProjectDocFileDataDao projectDocFileDataDao;
    @Autowired
    private CompanyHolidayDao companyHolidayDao;

    @Override
    protected Map getDocMap(ProjectEntity project, Integer currentYear) {
        Map<String, Object> map = new HashMap<>();
        if (StringUtils.isEmpty(this.docFile.getData())) {
            map.put("receiveLists", new ArrayList<>());
        } else {
            map = JsonUtils.jsonToPojo(this.docFile.getData(), Map.class);
            //试验试制接受人员处理数据
            List<Map<String, String>> recieverList = new ArrayList<>();
            List<Map<String, Object>> receivers = JsonUtils.jsonToPojo(JsonUtils.objectToJson(map.get("receiveList")), List.class);
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
                newRecieves.put("reception", reces.get("reception") != null ? (String)reces.get("reception") : "");
                recieverList.add(newRecieves);
            }
            map.put("receiveLists", CollectionUtils.isEmpty(recieverList) ? new ArrayList<>() : recieverList);
            //处理试验试制时间 有旧数据用旧数据yyyy-MM-dd 至 yyyy-MM-dd 新数据 日期数组
            if (!map.containsKey("pilotTime") && !map.containsKey("endPilotTime")) {
                DocFileDataModel model = projectDocFileDataDao.getData(this.docFile.getId());
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
                        map.put("trialDates", String.join(",", dates));
                        Date emitTime = DateUtil.addDays(list.get(0), -1);
                        Date firstDay = DateUtil.getMonthFirstDay(emitTime);
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTime(emitTime);
                        int day = calendar.get(Calendar.DAY_OF_MONTH);
                        while (true) {
                            CompanyHolidayModel holiday = companyHolidayDao.getMonthHoliday(firstDay, project.getCompanyId());
                            Map<Integer, Boolean> holidayMap = ToolUtils.getHolidayMap(holiday, false, firstDay);
                            Boolean hasWorkDay = getEmitDay(holidayMap, day, calendar);
                            if (hasWorkDay) {
                                break;
                            }
                            firstDay = DateUtil.addMonths(firstDay, -1);
                            calendar.setTime(DateUtil.getMonthLastDay(firstDay));
                            day = calendar.get(Calendar.DAY_OF_MONTH);
                        }
                        dataMap.put("emitTime", dateFormat.format(calendar.getTime()));
                    } else {
                        Object trialDates = map.get("trialDates");
                        if (trialDates instanceof List) {
                            trialDates = String.join(",", (List)trialDates);
                        }
                        map.put("trialDates", trialDates);
                    }
                } else {
                    Object trialDates = map.get("trialDates");
                    if (trialDates instanceof List) {
                        trialDates = String.join(",", (List)trialDates);
                    }
                    map.put("trialDates", trialDates);
                }
            }
        }
        return map;
    }

    public Boolean getEmitDay(Map<Integer, Boolean> map, int day, Calendar calendar) {
        while (true) {
            if (map.containsKey(day)) {
                if (day > 1) {
                    day--;
                } else {
                    return false;
                }
            } else {
                calendar.set(Calendar.DAY_OF_MONTH, day);
                return true;
            }
        }
    }

    @Override
    protected Map getDocMap() throws IOException {
        Map jsonMap = this.getJsonMap();
        Map resultMap = new HashMap<>();
        // 处理接受人员相关数据 receiveLists
        List<Map<String, Object>> receiveList = (List<Map<String, Object>>)jsonMap.get("receiveList");
        List<Map<String, Object>> receiveLists = new ArrayList<>();
        for (Map<String, Object> receive: receiveList){
            Map<String, Object> newReceive = new HashMap<>();
            HashMap<String, String> department = (HashMap<String, String>)receive.get("department");
            HashMap<String, String> emitPerson = (HashMap<String, String>)receive.get("emitPerson");

            newReceive.put("department", department != null && department.get("name") != null ? department.get("name") : "");
            newReceive.put("reception", receive.get("reception") != null ? receive.get("reception") : "");
            newReceive.put("emitPerson", emitPerson != null && emitPerson.get("ename") != null ? emitPerson.get("ename") : "");
            receiveLists.add(newReceive);
        }
        resultMap.put("receiveLists", receiveLists);

        // 处理试验试制时间 pilotTime、endPilotTime(yyyy-MM-dd 至 yyyy-MM-dd) || trialDates, 其中前者优先级高
        if (!jsonMap.containsKey("pilotTime") && !jsonMap.containsKey("endPilotTime")) {
            // 发出时间(emitTime)在试验试制开始时间(trialDates)的前一天(跳过节假日或周末)
            DocFileDataModel model = projectDocFileDataDao.getData(this.docFile.getId());
            if (model.getMonth() != null) {
                // 存在试制周期
                QueryYieldConfigModel queryModel = new QueryYieldConfigModel();
                queryModel.setProjectId(model.getProjectId());
                queryModel.setMonth(model.getMonth());
                // 获取项目对应月份的试制日期
                List<Date> list = projectYieldConfigDao.getTrialDate(queryModel);
                if (!CollectionUtils.isEmpty(list)) {
                    // 获取试验试制时间
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    List<String> dates = new ArrayList<>();
                    list.forEach(item -> {
                        String format = dateFormat.format(item);
                        dates.add(format);
                    });
                    resultMap.put("trialDates", String.join(",", dates));

                    // 获取发出时间
                    Date emitTime = DateUtil.addDays(list.get(0), -1);
                    Date firstDay = DateUtil.getMonthFirstDay(emitTime);
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(emitTime);
                    int day = calendar.get(Calendar.DAY_OF_MONTH);
                    while (true) {
                        CompanyHolidayModel holiday = companyHolidayDao.getMonthHoliday(firstDay, dataFactory.getProjectInfo().getCompanyId());
                        Map<Integer, Boolean> holidayMap = ToolUtils.getHolidayMap(holiday, false, firstDay);
                        Boolean hasWorkDay = getEmitDay(holidayMap, day, calendar);
                        if (hasWorkDay) {
                            break;
                        }
                        firstDay = DateUtil.addMonths(firstDay, -1);
                        calendar.setTime(DateUtil.getMonthLastDay(firstDay));
                        day = calendar.get(Calendar.DAY_OF_MONTH);
                    }
                    resultMap.put("emitTime", dateFormat.format(calendar.getTime()));
                    return resultMap;
                }
            }

            // 不存在试制周期，直接处理试制时间
            Object trialDates = jsonMap.get("trialDates");
            if (trialDates instanceof List) {
                trialDates = String.join(",", (List)trialDates);
            }
            resultMap.put("trialDates", trialDates);
        }

        return resultMap;
    }
}
