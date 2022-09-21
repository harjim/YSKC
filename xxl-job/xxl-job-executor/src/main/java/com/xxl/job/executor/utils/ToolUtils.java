package com.xxl.job.executor.utils;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.NumberUtil;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * @BelongsProject: xxl-job
 * @BelongsPackage: com.xxl.job.executor.utils
 * @Author: zhangdingfu
 * @CreateTime: 2020-10-14 15:15
 * @Description: 工具类
 */
public class ToolUtils {

    public static Date getBeforeTime(Date now, String param, int offset) {
        Date before;
        if (StringUtils.isEmpty(param)) {
            before = DateUtil.offsetMinute(now, offset);
        } else {
            before = DateUtil.parse(param);
        }
        return before;
    }

    public static <K, V> void putAndAddSet(Map<K, Set<V>> map, K k, V v) {
        if (!map.containsKey(k)) {
            map.put(k, new HashSet<>());
        }
        map.get(k).add(v);
    }

    public static <K, V> void putAndAddList(Map<K, List<V>> map, K k, V v) {
        if (!map.containsKey(k)) {
            map.put(k, new ArrayList<>());
        }
        map.get(k).add(v);
    }

    public static Map<String, String> kvMap(String k, String v) {
        Map<String, String> kvMap = new HashMap<>(2);
        kvMap.put("key", k);
        kvMap.put("value", v);
        return kvMap;
    }

    /**
     * 获取一个key为value 的map对象
     *
     * @param obj
     * @return
     */
    public static Map<String, Object> getValueMap(Object obj) {
        Map<String, Object> m = new HashMap<>(1);
        m.put("value", obj);
        return m;
    }

    /**
     * 偏移时间，如果param参数是一个时间，则直接转为日期
     * @param param
     * @param now
     * @return
     */
    public static Date offsetDay(String param, Date now) {
        int rangeDay = -1;
        Date begin;
        if (!StringUtils.isEmpty(param)) {
            if (NumberUtil.isNumber(param)) {
                rangeDay = Integer.parseInt(param);
                // 如果间距日期是一个正 数，则转换为负数,向过去偏移
                if (rangeDay > 0) {
                    rangeDay = -rangeDay;
                }
                begin = DateUtil.beginOfDay(DateUtil.offsetDay(now, rangeDay));
            } else {
                begin = DateUtil.parse(param);
            }
        } else {
            begin = DateUtil.beginOfDay(DateUtil.offsetDay(now, rangeDay));
        }
        return begin;
    }

}
