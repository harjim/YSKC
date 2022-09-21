package com.xxl.job.executor.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * 集合帮助类
 * @author huronghua
 */
public class ListUtils {
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

}
