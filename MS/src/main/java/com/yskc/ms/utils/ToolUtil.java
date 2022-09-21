package com.yskc.ms.utils;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import com.yskc.ms.config.Constant;
import com.yskc.ms.entity.ms.models.MsBaseEntity;
import com.yskc.ms.models.company.CompanyHolidayModel;
import com.yskc.ms.models.rs.summary.TimeRangeModel;
import org.springframework.util.StringUtils;

import java.io.FileInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.*;

/**
 * @BelongsProject: ms
 * @BelongsPackage: com.yskc.ms.utils
 * @Author: zhangdingfu
 * @CreateTime: 2020-12-19 11:23
 * @Description:
 */
public class ToolUtil {

    public static String runPolicy(Integer sourceId) throws Exception {
        String basePath = "/home/policy";
        String logPath = basePath + "/.log/" + sourceId + ".log";
        if(FileUtil.exist(logPath)){
            FileUtil.del(logPath);
        }
        Process pr = Runtime.getRuntime().exec(basePath + "/runPolicy.sh " + sourceId);
        pr.waitFor();
        InputStream inputStream = new FileInputStream(logPath);
        byte[] b = new byte[1024];
        StringBuilder result = new StringBuilder();
        while (inputStream.read(b) != -1) {
            result.append(new String(b));
        }
        return result.toString();
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
        for (int i = 0; i < 31; i++) {
            temp = BigDecimal.valueOf(Double.parseDouble(subtractionArr[i])).subtract(BigDecimal.valueOf(Double.parseDouble(subtractedArr[i]))).setScale(2, BigDecimal.ROUND_HALF_UP);
            if (temp.compareTo(BigDecimal.ZERO) < 0) {
                temp = BigDecimal.ZERO;
            }
            result.add(temp.toString());
        }
        return String.join(",", result);
    }

    /**
     * 分割集合
     * @param sourceList 需要分割的集合
     * @param batchCount 分割条数
     * @param <T> 分割后的集合
     * @return
     */
    public static <T> List<List<T>> subList(List<T> sourceList, Integer batchCount) {
        List<List<T>> returnList = new ArrayList<List<T>>();
        Integer totalNum = sourceList.size();
        Integer insertTimes = totalNum / batchCount;
        List<T> tempList = new ArrayList<T>();
        for (int i = 0; i <= insertTimes; i++) {
            if (i < insertTimes) {
                tempList = sourceList.subList(i*batchCount, (i+1)*batchCount);
            } else {
                tempList = sourceList.subList(i*batchCount, totalNum);
            }
            if (tempList.size() > 0) {
                returnList.add(tempList);
            }
        }
        return returnList;
    }

    /**
     * map list 数据,当不存在key的时候,会根据key put一个list,随后添加当前的 V数据;否则直接添加 V数据
     *
     * @param map
     * @param k
     * @param v
     * @param <K>
     * @param <V>
     */
    public static <K, V> void putAndAdd(Map<K, List<V>> map, K k, V v) {
        if (!map.containsKey(k)) {
            map.put(k, new ArrayList<>());
        }
        map.get(k).add(v);
    }

    /**
     * 获取节假日map，若不存在节假日，默认取周末
     *
     * @param holiday
     * @param skipHoliday
     * @param month
     * @return
     */
    public static Map<Integer, Boolean> getHolidayMap(CompanyHolidayModel holiday, Boolean skipHoliday, Date month) {
        Map<Integer, Boolean> holidayMap = new LinkedHashMap<>();
        if (skipHoliday) {
            return holidayMap;
        }
        if (null != holiday && !StringUtils.isEmpty(holiday.getHolidays())) {
            String[] holidays = holiday.getHolidays().split(",");
            for (String d : holidays) {
                holidayMap.put(Integer.valueOf(d), true);
            }
        } else {
            List<Integer> weekEnds = com.yskc.common.utils.DateUtil.weekEndList(month);
            weekEnds.forEach(d -> holidayMap.put(d, true));
        }
        return holidayMap;
    }

    /**
     * 实体创建
     * @param entity
     * @param userId
     * @param now
     */
    public static void entityCreate(MsBaseEntity entity,Integer userId,Date now){
        entity.setCreatorId(userId);
        entity.setCreateTime(now);
        entityUpdate(entity,userId,now);
    }

    /**
     * 实体更新
     * @param entity
     * @param userId
     * @param now
     */
    public static void entityUpdate(MsBaseEntity entity,Integer userId,Date now){
        entity.setLastUpdatorId(userId);
        entity.setLastUpdateTime(now);
    }

}
