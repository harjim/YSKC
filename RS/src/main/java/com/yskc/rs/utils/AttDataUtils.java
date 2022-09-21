package com.yskc.rs.utils;

import cn.hutool.core.date.DateUtil;
import com.yskc.rs.config.Constant;
import com.yskc.rs.entity.CustomerAttendanceEntity;
import com.yskc.rs.entity.project.ProjectAttendance;
import com.yskc.rs.entity.project.ProjectAttendanceUsed;
import com.yskc.rs.models.TimeRangeModel;
import com.yskc.rs.models.rdequipment.FullYearProjectModel;
import com.yskc.rs.models.rdhourconfig.RdHourConfigInfoModel;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.*;

/**
 * AttData字符串处理类
 *
 * @author huronghua
 */
public class AttDataUtils {
    private final static BigDecimal zero = BigDecimal.ZERO;
    private final static String zeroStr = zero.toString();
    public final static int MAX_DAY = 31;
    public final static Date BEGIN = DateUtil.parseTime("00:00:00");
    public final static Date DEFAULT_ON_TIME1 = DateUtil.parseTime("08:00:00");
    public final static Date DEFAULT_OFF_TIME1 = DateUtil.parseTime("12:00:00");
    public final static Date DEFAULT_ON_TIME2 = DateUtil.parseTime("14:00:00");
    public final static Date DEFAULT_OFF_TIME2 = DateUtil.parseTime("18:00:00");
    public final static Date DEFAULT_ON_TIME3 = DateUtil.parseTime("18:00:00");
    public final static Date DEFAULT_OFF_TIME3 = DateUtil.parseTime("23:59:59");
    public final static BigDecimal DEFAULT_MAX_HOUR = BigDecimal.valueOf(14);
    public final static BigDecimal TIME_SEPT = BigDecimal.valueOf(60 * 60 * 1000);
    public final static BigDecimal DAY_HOUR = BigDecimal.valueOf(24);

    /**
     * 默认的考勤记录
     *
     * @return
     */
    private static List<TimeRangeModel> getDefaultRangeList(boolean isImport) {
        List<TimeRangeModel> range = new ArrayList<>();
        if (isImport) {
            range.add(new TimeRangeModel(BEGIN.getTime(), DEFAULT_OFF_TIME3.getTime()));
        } else {
            range.add(new TimeRangeModel(DEFAULT_ON_TIME1.getTime(), DEFAULT_OFF_TIME1.getTime()));
            range.add(new TimeRangeModel(DEFAULT_ON_TIME2.getTime(), DEFAULT_OFF_TIME2.getTime()));
            range.add(new TimeRangeModel(DEFAULT_ON_TIME3.getTime(), DEFAULT_OFF_TIME3.getTime()));
        }
        return range;
    }

    /**
     * 考勤记录
     *
     * @param entity
     * @return
     */
    public static List<TimeRangeModel> getTimeRangeList(CustomerAttendanceEntity entity) {
        return getTimeRangeList(entity.getOnTime1(), entity.getOffTime1(), entity.getOnTime2(), entity.getOffTime2(),
                entity.getOnTime3(), entity.getOffTime3());
    }

    /**
     * 考勤记录
     *
     * @param entity
     * @return
     */
    public static List<TimeRangeModel> getTimeRangeList(ProjectAttendance entity) {
        return getTimeRangeList(entity.getOnTime1(), entity.getOffTime1(), entity.getOnTime2(), entity.getOffTime2(),
                entity.getOnTime3(), entity.getOffTime3());
    }

    public static List<TimeRangeModel> getTimeRangeList(Date onTime1, Date offTime1, Date onTime2, Date offTime2,
                                                        Date onTime3, Date offTime3) {
        // TODO: 20/10/29 zdf 如果没有下班 默认先给 23:59:59
        List<TimeRangeModel> range = new ArrayList<>();
        if (offTime1 == null) {
            offTime1 = DEFAULT_OFF_TIME3;
        }
        range.add(new TimeRangeModel(onTime1.getTime(), offTime1.getTime()));
        if (null != offTime2) {
            range.add(new TimeRangeModel(onTime2.getTime(), offTime2.getTime()));
        }
        if (null != offTime3) {
            range.add(new TimeRangeModel(onTime3.getTime(), offTime3.getTime()));
        }
        return range;
    }


    /**
     * 移除废弃的区间
     *
     * @param timeRange
     * @param rangeList
     * @return
     */
    public static String removeRange(String timeRange, List<TimeRangeModel> rangeList) {
        if (StringUtils.isEmpty(timeRange)) {
            return "";
        }
        String[] rangeArr = timeRange.split(",");
        Date timeBegin;
        Date timeEnd;
        Map<Integer, String> timeMap = new HashMap<>();
        for (int i = 0; i < rangeArr.length; i++) {
            timeMap.put(i, rangeArr[i]);
        }
        // 遍历所有区间，存在相同区间，则删除该区间
        for (TimeRangeModel range : rangeList) {
            for (Integer key : timeMap.keySet()) {
                String[] timeArr = timeMap.get(key).split(Constant.HYPHEN);
                timeBegin = DateUtil.parseTime(timeArr[0]);
                timeEnd = DateUtil.parseTime(timeArr[1]);
                if (range.getOnTime() >= timeBegin.getTime() && range.getOffTime() <= timeEnd.getTime()) {
                    timeMap.remove(key);
                    break;
                }
            }
        }
        return String.join(",", timeMap.values());
    }


    /**
     * 分配工时操作
     *
     * @param workDate
     * @param key
     * @param avg
     * @param remainHours
     * @param usedMap
     * @param rangeList
     * @param attendanceList
     * @param maxUsedHour
     * @return
     */
    private static BigDecimal doDistribution(Date workDate, Integer key, BigDecimal avg, BigDecimal remainHours,
                                             Map<Integer, ProjectAttendanceUsed> usedMap,
                                             List<TimeRangeModel> rangeList,
                                             List<ProjectAttendance> attendanceList, BigDecimal maxUsedHour) {
        ProjectAttendanceUsed used = usedMap.getOrDefault(key, ProjectAttendanceUsed.build(null, maxUsedHour, maxUsedHour, null));
        ProjectAttendance attendance = new ProjectAttendance();
        attendance.setWorkDate(workDate);
        used.setWorkDate(workDate);
        List<TimeRangeModel> usedRangeList = timeRangeStrToList(used.getTimeRange());
        List<String> timeRangeStr;
        // 无使用记录
        if (CollectionUtils.isEmpty(usedRangeList)) {
            timeRangeStr = new ArrayList<>();
            for (TimeRangeModel item : rangeList) {
                BigDecimal subHour = subTime(item.getOnTime(), item.getOffTime());
                remainHours = buildTime(avg, remainHours, used, attendance, timeRangeStr, subHour, item.getOnTime());
                if (remainHours == null) {
                    break;
                }
            }
        } else {
            // 继承原来的使用区间
            timeRangeStr = new ArrayList<>(Arrays.asList(used.getTimeRange().split(",")));
            handleInfoUsed(avg, rangeList, usedRangeList, remainHours, used, attendance, timeRangeStr);
        }
        if (attendance.getWorkHour() == null || attendance.getWorkHour().compareTo(BigDecimal.ZERO) <= 0) {
            return BigDecimal.ZERO;
        }
        used.setTimeRange(String.join(",", timeRangeStr));
        attendanceList.add(attendance);
        usedMap.put(key, used);
        return attendance.getWorkHour();
    }

    /**
     * 创建考勤考勤时间
     *
     * @param avg          平均工时(根据时间段递减-->8->3->1)
     * @param remainHours  可分配工时
     * @param used         used已使用工时对象
     * @param attendance   研发考勤对象
     * @param timeRangeStr 分配时间str列表
     * @param subHour      当前分配工时
     * @param onTime       上班时间 +subHour = offTime
     * @return
     */
    private static BigDecimal buildTime(BigDecimal avg, BigDecimal remainHours, ProjectAttendanceUsed used, ProjectAttendance attendance, List<String> timeRangeStr, BigDecimal subHour, Long onTime) {
        // 平均工时 = 原平均工时 - 当前分配工时
        if (null != attendance.getWorkHour()) {
            if (attendance.getWorkHour().compareTo(avg) < 0) {
                avg = avg.subtract(attendance.getWorkHour());
            } else {
                return remainHours;
            }
        }
        //分配工时不能大于平均工时
        if (subHour.compareTo(avg) > 0) {
            subHour = avg;
        }
        // 分配工时不能大于剩余可分配工时
        if (subHour.compareTo(remainHours) > 0) {
            subHour = remainHours;
            remainHours = null;
        } else {
            remainHours = remainHours.subtract(subHour);
        }
        // 分配工时不能大于used的剩余可分配工时
        if (subHour.compareTo(used.getRemainHours()) > 0) {
            subHour = used.getRemainHours();
        }
        setWorkHour(subHour, used, attendance, new Date(onTime), timeRangeStr);
        // 若工时全部分配，返回null
        if (used.getRemainHours().compareTo(BigDecimal.ZERO) <= 0) {
            return null;
        }
        return remainHours;
    }

    /**
     * 设置研发工时
     *
     * @param subHour
     * @param used
     * @param attendance
     * @param onTime
     * @param timeRangeStr
     */
    private static void setWorkHour(BigDecimal subHour, ProjectAttendanceUsed used, ProjectAttendance attendance, Date onTime, List<String> timeRangeStr) {
        attendance.addOrSetWorkHour(subHour);
        used.subOrSetRemainHours(subHour);
        Date currentOffTime = addTimeHour(onTime, subHour);
        timeRangeStr.add(DateUtil.formatTime(onTime) + Constant.HYPHEN + DateUtil.formatTime(currentOffTime));
        setAttendanceTime(attendance, onTime, currentOffTime);
    }

    /**
     * 分配无考勤研发工时
     *
     * @param rdHour     可分配工时（研发工时）
     * @param max        可遍历最大天数
     * @param month      月份开始日期（当月第一天）
     * @param usedMap    已使用数据
     * @param holidayMap 周末
     * @param avg        每日分配工时
     * @return
     */
    private static List<ProjectAttendance> noInfoDistribution(BigDecimal rdHour, Integer max, Date month,
                                                              Map<Integer, ProjectAttendanceUsed> usedMap,
                                                              Map<Integer, Boolean> holidayMap, BigDecimal avg, Integer begin, BigDecimal dayHour) {
        BigDecimal remainHours = rdHour;
        List<ProjectAttendance> result = new ArrayList<>();
        for (Integer i = begin; i <= max; i++) {
            if (remainHours.compareTo(BigDecimal.ZERO) <= 0) {
                break;
            }
            if (holidayMap.containsKey(i)) {
                continue;
            }
            remainHours = remainHours.subtract(doDistribution(DateUtil.offsetDay(month, i - 1), i, avg, remainHours, usedMap, getDefaultRangeList(false), result, dayHour));
        }
        // 可分配工时大于0,按周末分配
        if (remainHours.compareTo(BigDecimal.ZERO) > 0) {
            for (Integer key : holidayMap.keySet()) {
                if (key < begin || key > max) {
                    continue;
                }
                if (remainHours.compareTo(BigDecimal.ZERO) <= 0) {
                    break;
                }
                remainHours = remainHours.subtract(doDistribution(DateUtil.offsetDay(month, key - 1), key, avg, remainHours, usedMap, getDefaultRangeList(false), result, dayHour));
            }
        }
        return result;
    }

    /**
     * 有考勤记录
     *
     * @param rdHour  可分配工时
     * @param month   月份（当月第一天）
     * @param infoMap 考勤map
     * @param usedMap 已使用map
     * @param avg     每日分配工时
     * @return
     */
    private static List<ProjectAttendance> infoDistribution(BigDecimal rdHour, Date month,
                                                            Map<Integer, CustomerAttendanceEntity> infoMap,
                                                            Map<Integer, ProjectAttendanceUsed> usedMap, BigDecimal avg) {
        BigDecimal remainHours = rdHour;
        List<ProjectAttendance> result = new ArrayList<>();
        // 按考勤数据遍历分配
        for (Integer key : infoMap.keySet()) {
            if (remainHours.compareTo(BigDecimal.ZERO) <= 0) {
                break;
            }
            CustomerAttendanceEntity entity = infoMap.get(key);
            remainHours = remainHours.subtract(doDistribution(DateUtil.offsetDay(month, key - 1), key, avg, remainHours, usedMap, getTimeRangeList(entity), result, entity.getWorkHour()));
        }
        return result;
    }

    /**
     * 分配工时
     *
     * @param rdHour
     * @param max
     * @param month
     * @param infoMap
     * @param usedMap
     * @param holidayMap
     * @return
     */
    public static List<ProjectAttendance> distribution(BigDecimal rdHour, Integer max, Date month,
                                                       Map<Integer, CustomerAttendanceEntity> infoMap,
                                                       Map<Integer, ProjectAttendanceUsed> usedMap,
                                                       Map<Integer, Boolean> holidayMap, Integer begin,
                                                       BigDecimal dayHour) {
        BigDecimal avg;
        // 无考勤
        if (CollectionUtils.isEmpty(infoMap)) {
            avg = rdHour.divide(BigDecimal.valueOf(max), 2, BigDecimal.ROUND_HALF_UP);
            if (avg.compareTo(dayHour) < 0) {
                avg = dayHour;
            }
            return noInfoDistribution(rdHour, max, month, usedMap, holidayMap, avg, begin, dayHour);
        } else {
            avg = DAY_HOUR;
            return infoDistribution(rdHour, month, infoMap, usedMap, avg);
        }
    }

    public static List<ProjectAttendance> termDistribution(BigDecimal rdHour, Date month,
                                                           Map<Integer, CustomerAttendanceEntity> infoMap,
                                                           Map<Integer, ProjectAttendanceUsed> usedMap,
                                                           Map<Integer, Boolean> holidayMap,
                                                           List<RdHourConfigInfoModel> configs) {
        List<ProjectAttendance> result = new ArrayList<>();
        // 无考勤
        if (CollectionUtils.isEmpty(infoMap)) {
            for (RdHourConfigInfoModel config : configs) {
                if (null == config.getEndDay()) {
                    continue;
                }
                BigDecimal remainHour = rdHour;
                if (remainHour.compareTo(config.getTotalHour()) > 0) {
                    remainHour = config.getTotalHour();
                }
                List<ProjectAttendance> tempList = noInfoDistribution(remainHour, config.getEndDay(), month, usedMap, holidayMap, config.getDayHour(), config.getStartDay(), config.getDayHour());
                for (ProjectAttendance att : tempList) {
                    rdHour = rdHour.subtract(att.getWorkHour());
                }
                result.addAll(tempList);
            }

        } else {
            for (RdHourConfigInfoModel config : configs) {
                if (null == config.getEndDay()) {
                    continue;
                }
                BigDecimal remainHour = rdHour;
                if (remainHour.compareTo(config.getTotalHour()) > 0) {
                    remainHour = config.getTotalHour();
                }
                Map<Integer, CustomerAttendanceEntity> currentInfoMap = new LinkedHashMap<>();
                for (Integer i = config.getStartDay(); i <= config.getEndDay(); i++) {
                    if (infoMap.containsKey(i)) {
                        currentInfoMap.put(i, infoMap.get(i));
                    }
                }
                List<ProjectAttendance> tempList = infoDistribution(remainHour, month, currentInfoMap, usedMap, config.getDayHour());
                for (ProjectAttendance att : tempList) {
                    rdHour = rdHour.subtract(att.getWorkHour());
                }
                result.addAll(tempList);
            }
        }
        return result;
    }

    /**
     * 设置工时段
     *
     * @param attendance
     * @param onTime
     * @param offTime
     */
    private static void setAttendanceTime(ProjectAttendance attendance, Date onTime, Date offTime) {
        if (null == attendance.getOnTime1()) {
            attendance.setOnTime1(onTime);
            attendance.setOffTime1(offTime);
        } else if (null == attendance.getOnTime2()) {
            attendance.setOnTime2(onTime);
            attendance.setOffTime2(offTime);
        } else if (null == attendance.getOnTime3()) {
            attendance.setOnTime3(onTime);
            attendance.setOffTime3(offTime);
        }
    }

    /**
     * 时间相减，返回相差的小时数（保留两位小数）
     *
     * @param begin
     * @param end
     * @return
     */
    public static BigDecimal subTime(Long begin, Long end) {
        return BigDecimal.valueOf(end - begin).abs().divide(TIME_SEPT, 2, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * 时间添加小时
     *
     * @param time
     * @param hour
     * @return
     */
    public static Date addTimeHour(Date time, BigDecimal hour) {
        return new Date(hour.multiply(TIME_SEPT).add(BigDecimal.valueOf(time.getTime())).longValue());
    }

    /**
     * 分配一天的工时
     *
     * @param attendance
     * @param used
     * @param entity
     * @param base
     */
    public static void distributionOne(ProjectAttendance attendance, BigDecimal remainHour, ProjectAttendanceUsed used,
                                       CustomerAttendanceEntity entity, ProjectAttendance base, Boolean isImport) {
        BigDecimal avg = remainHour;
        List<TimeRangeModel> rangeList = entity != null ? getTimeRangeList(entity) : getDefaultRangeList(isImport);
        List<String> timeRangeStr = new ArrayList<>();
        List<TimeRangeModel> usedRangeList = timeRangeStrToList(used.getTimeRange());
        if (CollectionUtils.isEmpty(usedRangeList)) {
            used.setRemainHours(isImport ? DAY_HOUR : DEFAULT_MAX_HOUR);
            used.setWorkHour(isImport ? DAY_HOUR : DEFAULT_MAX_HOUR);
            for (TimeRangeModel range : rangeList) {
                BigDecimal subHour = subTime(range.getOnTime(), range.getOffTime());
                remainHour = buildTime(avg, remainHour, used, attendance, timeRangeStr, subHour, range.getOnTime());
                if (remainHour == null) {
                    break;
                }
            }
        } else {
            List<TimeRangeModel> baseRangeList = new ArrayList<>();
            BigDecimal baseHour = BigDecimal.ZERO;
            if (null != base && null != base.getOnTime1()) {
                baseRangeList.addAll(getTimeRangeList(base));
                baseHour = base.getWorkHour();
            }
            used.setRemainHours(used.getRemainHours().add(baseHour));
            Map<Long, TimeRangeModel> rangeMap = new HashMap<>();
            usedRangeList.forEach(item -> rangeMap.put(item.getOnTime(), item));
            for (TimeRangeModel baseRange : baseRangeList) {
                rangeMap.remove(baseRange.getOnTime());
            }
            usedRangeList = new ArrayList<>(rangeMap.values());
            timeRangeStr.addAll(getBaseUsedRange(usedRangeList));
            handleInfoUsed(avg, rangeList, usedRangeList, remainHour, used, attendance, timeRangeStr);
        }
        used.setTimeRange(String.join(",", timeRangeStr));
    }

    private static List<String> getBaseUsedRange(List<TimeRangeModel> usedRangeList) {
        List<String> list = new ArrayList<>();
        usedRangeList.forEach(item -> {
            list.add(DateUtil.formatTime(new Date(item.getOnTime())) + Constant.HYPHEN + DateUtil.formatTime(new Date(item.getOffTime())));
        });
        return list;
    }

    /**
     * 处理被其他项目使用过的数据
     *
     * @param rangeList     考勤区间
     * @param usedRangeList 已使用区间
     * @param remainHour    剩余可分配工时
     * @param used          已使用记录
     * @param attendance    研发考勤对象
     * @param timeRangeStr  新的时间区间字符串列表
     */
    public static void handleInfoUsed(BigDecimal avg, List<TimeRangeModel> rangeList, List<TimeRangeModel> usedRangeList,
                                      BigDecimal remainHour, ProjectAttendanceUsed used, ProjectAttendance attendance, List<String> timeRangeStr) {
        // 循环考勤
        for (TimeRangeModel range : rangeList) {
            long offTime = range.getOffTime();
            long onTime = range.getOnTime();
            boolean isUse = false;
            // 循环已使用区间
            for (TimeRangeModel usedRange : usedRangeList) {
                // 考勤下班时间小于等于使用区间的下班时间，则该段考勤已被使用，跳过整段考勤
                if (offTime <= usedRange.getOffTime() && onTime >= usedRange.getOnTime()) {
                    isUse = true;
                    break;
                }
                // 考勤下班时间大于使用区间的下班时间，且考勤上班时间小于使用区间的下班时间，则考勤上班时间== 使用区间的下班时间
                if (offTime > usedRange.getOffTime() && onTime < usedRange.getOffTime()) {
                    onTime = usedRange.getOffTime();
                }
            }
            if (isUse) {
                continue;
            }
            // 遍历出的数据若为一个正确的区间，则计算工时
            if (onTime < offTime) {
                Date time = new Date(onTime);
                BigDecimal subHour = subTime(onTime, range.getOffTime());
                remainHour = buildTime(avg, remainHour, used, attendance, timeRangeStr, subHour, onTime);
                if (remainHour == null) {
                    break;
                }
            }
        }
    }

    /**
     * 时间区间字符串转为Time list
     *
     * @param timeRangeStr
     * @return
     */
    public static List<TimeRangeModel> timeRangeStrToList(String timeRangeStr) {
        List<TimeRangeModel> usedRangeList = new ArrayList<>();
        if (!StringUtils.isEmpty(timeRangeStr)) {
            String[] rangeArr = timeRangeStr.split(",");
            for (String timeRange : rangeArr) {
                String[] timeArr = timeRange.split(Constant.HYPHEN);
                usedRangeList.add(new TimeRangeModel(DateUtil.parseTime(timeArr[0]).getTime(), DateUtil.parseTime(timeArr[1]).getTime()));
            }
        }
        return usedRangeList;
    }

    /**
     * 工时相减
     *
     * @param subtraction
     * @param subtracted
     * @return
     */
    public static String subAttData(String subtraction, String subtracted) {
        String[] subtractionArr = subtraction.split(",");
        String[] subtractedArr = subtracted.split(",");
        List<String> result = new ArrayList<>();
        BigDecimal temp;
        for (int i = 0; i < MAX_DAY; i++) {
            temp = BigDecimal.valueOf(Double.parseDouble(subtractionArr[i])).subtract(BigDecimal.valueOf(Double.parseDouble(subtractedArr[i]))).setScale(2, BigDecimal.ROUND_HALF_UP);
            if (temp.compareTo(BigDecimal.ZERO) < 0) {
                temp = BigDecimal.ZERO;
            }
            result.add(temp.toString());
        }
        return String.join(",", result);
    }

    /**
     * 分配工时
     *
     * @param used
     * @param zero
     * @param zeroStr
     * @param data
     * @param index
     * @param rdHour
     * @param avgHour
     * @return
     */
    private static BigDecimal doDistribution(String used, BigDecimal zero, String zeroStr, String[] data, Integer index,
                                             BigDecimal rdHour, BigDecimal avgHour,boolean noRepeat) {
        BigDecimal canUsed = DAY_HOUR.subtract(BigDecimal.valueOf(Double.parseDouble(used))).setScale(2, BigDecimal.ROUND_HALF_UP);
        if((noRepeat && canUsed.compareTo(DAY_HOUR)!=0) || canUsed.compareTo(zero) <= 0){
            data[index] = zeroStr;
            return rdHour;
        }
        if (canUsed.compareTo(avgHour) >= 0) {
            canUsed = avgHour;
        }
        if (canUsed.compareTo(rdHour) >= 0) {
            canUsed = rdHour;
        }
        rdHour = rdHour.subtract(canUsed);
        data[index] = canUsed.toString();
        return rdHour;
    }

    /**
     * 相加
     *
     * @param add
     * @param addPlus
     * @return
     */
    public static String addAttData(String add, String addPlus) {
        String[] subtractionArr = add.split(",");
        String[] subtractedArr = addPlus.split(",");
        List<String> result = new ArrayList<>();
        for (int i = 0; i < MAX_DAY; i++) {
            BigDecimal current = BigDecimal.valueOf(Double.parseDouble(subtractionArr[i])).add(BigDecimal.valueOf(Double.parseDouble(subtractedArr[i])));
            result.add((current.compareTo(DAY_HOUR) > 0 ? DAY_HOUR : current).setScale(2, BigDecimal.ROUND_HALF_UP).toString());
        }
        return String.join(",", result);
    }

    /**
     * 获取总工时
     *
     * @param attData
     * @return
     */
    public static BigDecimal getTotal(String attData) {
        String[] d = attData.split(",");
        BigDecimal total = BigDecimal.ZERO;
        for (int i = 0; i < MAX_DAY; i++) {
            total = total.add(BigDecimal.valueOf(Double.parseDouble(d[i])).setScale(2, BigDecimal.ROUND_HALF_UP));
        }
        return total;
    }
    /**
     * 获取总工时
     *
     * @param attData
     * @return
     */
    public static BigDecimal getTotal(String[] attData) {
        BigDecimal total = BigDecimal.ZERO;
        for (int i = 0; i < MAX_DAY; i++) {
            total = total.add(BigDecimal.valueOf(Double.parseDouble(attData[i])).setScale(2, BigDecimal.ROUND_HALF_UP));
        }
        return total;
    }

    /**
     * 分配设备研发工时
     *
     * @param rdHour
     * @param holidayMap
     * @param usedEquData
     * @param maxDay
     * @param avgHour
     * @param dayBegin
     * @return
     */
    public static String distribution(BigDecimal rdHour, Map<Integer, Boolean> holidayMap, String usedEquData,
                                      Integer maxDay, BigDecimal avgHour, Integer dayBegin,
                                      Map<Integer, Boolean> attDayMap,boolean noRepeat) {
        String arr[] = usedEquData.split(",");
        BigDecimal zero = BigDecimal.ZERO;
        String zeroStr = zero.toString();
        String result[] = new String[31];
        // 先根据有人员考勤的日期分
        for (Integer index : attDayMap.keySet()) {
            if (index < dayBegin) {
                continue;
            }
            index = index - 1;
            if (rdHour.compareTo(zero) <= 0) {
                result[index] = zeroStr;
                continue;
            }
            rdHour = doDistribution(arr[index], zero, zeroStr, result, index, rdHour, avgHour,noRepeat);
        }

        for (Integer i = dayBegin; i <= maxDay; i++) {
            if (attDayMap.containsKey(i)) {
                continue;
            }
            if (rdHour.compareTo(zero) <= 0 || holidayMap.containsKey(i)) {
                result[i - 1] = zeroStr;
                continue;
            }
            rdHour = doDistribution(arr[i - 1], zero, zeroStr, result, i - 1, rdHour, avgHour,noRepeat);
        }
        if (rdHour.compareTo(zero) > 0) {
            for (Integer day : holidayMap.keySet()) {
                if (day < dayBegin || attDayMap.containsKey(day)) {
                    continue;
                }
                if (rdHour.compareTo(zero) <= 0) {
                    break;
                }
                rdHour = doDistribution(arr[day - 1], zero, zeroStr, result, day - 1, rdHour, avgHour,noRepeat);
            }
        }
        for (int i = 0; i < result.length; i++) {
            if (null == result[i]) {
                result[i] = zeroStr;
            }
        }
        return String.join(",", result);
    }

    /**
     * 按配置分配设备研发工时
     *
     * @param rdHour
     * @param termDayMap
     * @param holidayMap
     * @param usedEquData
     * @param attDayMap
     * @return
     */
    public static String termDistribution(BigDecimal rdHour, Map<Integer, Boolean> holidayMap, String usedEquData,
                                          Map<Integer, RdHourConfigInfoModel> termDayMap, Map<Integer, Boolean> attDayMap,Boolean noRepeat) {
        String arr[] = usedEquData.split(",");
        String result[] = new String[31];
        for (Integer day : attDayMap.keySet()) {
            if (!termDayMap.containsKey(day)) {
                continue;
            }
            rdHour = rdHourDistribution(termDayMap.get(day), rdHour, result, day, arr,noRepeat);
        }
        for (Integer day : termDayMap.keySet()) {
            if (attDayMap.containsKey(day)) {
                continue;
            }
            if (rdHour.compareTo(zero) <= 0 || holidayMap.containsKey(day)) {
                result[day - 1] = zeroStr;
                continue;
            }
            rdHour = rdHourDistribution(termDayMap.get(day), rdHour, result, day, arr,noRepeat);
        }
        for (int i = 0; i < result.length; i++) {
            if (null == result[i]) {
                result[i] = zeroStr;
            }
        }
        return String.join(",", result);
    }

    /**
     * 根据研发工时配置分配
     *
     * @param configInfo
     * @param rdHour
     * @param data
     * @param day
     * @param arr
     * @return
     */
    private static BigDecimal rdHourDistribution(RdHourConfigInfoModel configInfo, BigDecimal rdHour, String[] data,
                                                 Integer day, String[] arr,boolean noRepeat) {
        int index = day - 1;
        BigDecimal total = configInfo.getTotalHour();
        if (rdHour.compareTo(zero) <= 0 || total.compareTo(zero) <= 0) {
            data[index] = zeroStr;
            return rdHour;
        }
        if (rdHour.compareTo(total) < 0) {
            total = rdHour;
        }
        BigDecimal tempTotal = doDistribution(arr[index], zero, zeroStr, data, index, total, configInfo.getDayHour(),noRepeat);
        rdHour = rdHour.subtract(total.subtract(tempTotal));
        configInfo.setTotalHour(tempTotal);
        return rdHour;
    }


    /**
     * 获取key(ecode,enumber)对应的RD
     *
     * @param keyRdInfos
     * @param year
     * @return
     */
    public static Map<String, FullYearProjectModel> getKeyAndRdLackMonthMap(List<FullYearProjectModel> keyRdInfos, Integer year) {
        Map<String, FullYearProjectModel> keyAndRdLackMonthMap = new HashMap<>();
        if (!CollectionUtils.isEmpty(keyRdInfos)) {
            Date yearBegin = com.yskc.common.utils.DateUtil.getYearFirstDay(year);
            Date yearEnd = com.yskc.common.utils.DateUtil.getYearLastDay(year);
            keyRdInfos.forEach(item -> {
                keyAndRdLackMonthMap.put(item.getKey(), item);
                Date endDate = item.getLeaveDate();
                endDate = endDate == null ?
                        com.yskc.common.utils.DateUtil.getMonthLastDay(item.getEndDate()) :
                        com.yskc.common.utils.DateUtil.getMonthLastDay(endDate.compareTo(item.getEndDate()) < 0 ? endDate : item.getEndDate());
                int mBegin, mEnd;
                // 结束时间或离职年份小于当前年，则缺失整年月份
                if (DateUtil.year(endDate) < year) {
                    mBegin = 0;
                    mEnd = -1;
                } else {
                    Date begin = com.yskc.common.utils.DateUtil.getMonthFirstDay(item.getBeginDate());
                    mBegin = cn.hutool.core.date.DateUtil.month((begin.compareTo(yearBegin) >= 0 ? begin : yearBegin));
                    mEnd = cn.hutool.core.date.DateUtil.month(endDate.compareTo(yearEnd) >= 0 ? yearEnd : endDate);
                }
                if (mBegin == 0 && mEnd == Constant.MAX_MONTH) {
                    item.setLackMonth(Constant.NOTHING);
                } else {
                    List<String> lackMonths = new ArrayList<>();
                    for (int m = 0; m < mBegin; m++) {
                        lackMonths.add(String.valueOf(m + 1));
                    }
                    for (; mEnd < Constant.MAX_MONTH; ) {
                        mEnd++;
                        lackMonths.add(String.valueOf(mEnd + 1));
                    }
                    item.setLackMonth(String.join(",", lackMonths));
                }
            });
        }
        return keyAndRdLackMonthMap;
    }
}
