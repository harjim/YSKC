package com.yskc.rs.utils;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.yskc.common.enums.ErrorEnum;
import com.yskc.common.exception.OwnerException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.*;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.utils
 * @Author: zhangdingfu
 * @CreateTime: 2019-07-10 08:52
 * @Description: 公共xlsx类
 */
public class CommonXSLXUtil<T> {

    /**
    *@Description: 导入数据 titleMap请实例为LinkedHashMap 保证插入顺序
    *@Param: [in {文件流}, titleMap {表头 eg：titleMap.put(titleStr, bean-property)},bean {实体}]
    *@return: java.util.List<T>
    *@Author: zhangdingfu
    *@date: 2019-07-10
    */
    public static<T> List<T> readXlsx(InputStream in, Map<String, String> titleMap, Class<T> bean) throws OwnerException {
        //数据集合
        List<T> dataList = new ArrayList<T>();
        try {
            //通过流读取
            ExcelReader reader = ExcelUtil.getReader(in);
            //设置title别名为字段名
            reader.setHeaderAlias(titleMap);
            //获取所有列数据
            List<Map<String,Object>> readAll = reader.readAll();

            //便利获取的数据
            for(Map<String, Object> beanMap : readAll) {
                dataList.add(BeanUtil.mapToBean(beanMap, bean, true));
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new OwnerException(ErrorEnum.IMPORT_XLSX_FAIL);
        }
        return dataList;
    }

    /**
    *@Description: 导出数据 titleMap请实例为LinkedHashMap 保证插入顺序
    *@Param: [os文件输出流, data 数据集合, titleMap 表头 {eg：titleMap.put(bean-property， titleStr) 与导入数据title相反},
    *@Param: dateFormatMap 时间处理 {eg: dateForMatMap.put(bean-property,"yyyy-MM-dd")}]
    *@return: void
    *@Author: zhangdingfu
    *@date: 2019-07-10
    */
    public static<T> void writeXlsx(OutputStream out, List<T> data, Map<String, String> titleMap, Map<String, String> dateFormatMap){
        ExcelWriter writer = ExcelUtil.getWriter();
        //设置表头别名
        writer.setHeaderAlias(titleMap);
        writer.writeRow(titleMap,false);
        Map<String, Object> dataMap = null;
        if(dateFormatMap == null){
            dateFormatMap = new HashMap<>();
        }
        Map<String, Object> dataWriteMap = new LinkedHashMap<>();
        for(String titleKey : titleMap.keySet()) {
            dataWriteMap.put(titleKey, null);
        }
        // 遍历数据
        for(T t : data){
            //泛型转为map
            dataMap = BeanUtil.beanToMap(t);
            // 失败： 强制转为map
            if(dataMap.isEmpty()){
                dataMap = (Map) t;
            }
            // 格式化对应数据
            for(String dateKey: dateFormatMap.keySet()){
                dataMap.put(dateKey, DateUtil.format((Date)dataMap.get(dateKey),dateFormatMap.get(dateKey)));
            }
            // 数据对应
            for(String key : dataWriteMap.keySet()){
                dataWriteMap.put(key, dataMap.get(key));
            }
            //写入
            writer.writeRow(dataWriteMap,false);
        }
        writer.flush(out);
    }
}
