package com.yskc.docservice.utils;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.poi.excel.ExcelFileUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.exceptions.POIException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yskc.common.annotation.Excel;
import com.yskc.common.enums.ErrorEnum;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.utils.CommonUtils;
import com.yskc.common.utils.JsonUtils;
import com.yskc.docservice.models.rs.excel.ExcelResult;
import com.yskc.docservice.models.rs.excel.TableFieldItem;
import com.yskc.docservice.models.rs.excel.TableField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.BufferedInputStream;
import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 报表工具类
 *
 * @author zdf
 */
public class ImportExcelUtils {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private static Logger logger = LoggerFactory.getLogger(ImportExcelUtils.class);
    public final static String EXIT = "exit";


    /**
     * Map.class * 特殊转换
     *
     * @param map
     * @param clazz
     * @param <T>
     * @return
     * @throws Exception
     */
    private static <T> T mapToJavaBean(Map<String, Object> map, Class<T> clazz)
            throws OwnerException, IllegalAccessException, InstantiationException, IntrospectionException {
        // new 出一个对象
        T obj = clazz.newInstance();
        // 获取javaBean的BeanInfo对象
        BeanInfo beanInfo = Introspector.getBeanInfo(clazz, Object.class);
        Map<String, Excel> titleMap = getExcelMap(clazz);
        // 获取属性描述器
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
        for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
            // 获取属性名
            String key = propertyDescriptor.getName();
            if (!titleMap.containsKey(key)) {
                continue;
            }
            Excel excel = titleMap.get(key);
            String nameKey = excel.name();
            try {
                Method writeMethod = propertyDescriptor.getWriteMethod();
                Object value = null;
                if (excel.type() == 0) {
                    value = map.get(nameKey);
                    if (map.containsKey(nameKey)) {
                        // 解决 argument type mismatch 的问题，转换成对应的javaBean属性类型
                        String typeName = propertyDescriptor.getPropertyType().getTypeName();
                        if ("java.lang.Integer".equals(typeName)) {
                            value = StringUtils.isEmpty(value) ? 0 : Integer.parseInt(value.toString());
                        }
                        if ("java.lang.Long".equals(typeName)) {
                            value = StringUtils.isEmpty(value) ? 0 : Long.parseLong(value.toString());
                        }
                        if ("java.util.Date".equals(typeName)) {
                            value = StringUtils.isEmpty(value) ? new Date()
                                    : new SimpleDateFormat(excel.dateFormat()).parse(value.toString());
                        }
                        if ("java.math.BigDecimal".equals(typeName)) {
                            value = StringUtils.isEmpty(value) ? BigDecimal.ZERO
                                    : BigDecimal.valueOf(Double.parseDouble(value.toString()));
                        }
                        if ("java.lang.String".equals(typeName)) {
                            value = StringUtils.isEmpty(value) ? "" : value.toString();
                        }
                    }

                } else {
                    List<String> tempValueArray = new ArrayList<>();
                    for (int i = excel.min(); i <= excel.max(); i++) {
                        Object tempValue = map.get(CommonUtils.patchLeftPosition(String.valueOf(i), 2, "0"));
                        if (tempValue != null) {
                            tempValueArray.add(tempValue.toString());
                        }
                    }
                    value = String.join(",", tempValueArray);
                }
                // 通过反射来调用javaBean定义的setName()方法
                writeMethod.invoke(obj, value);
            } catch (Exception ex) {
                logger.error("mapToJavaBean", ex);
                throw new OwnerException(MessageFormat.format("导入字段：{0}格式有问题，请重新导入", excel.name()));
            }

        }
        return obj;
    }

    /**
     * 转换map 为了格式化
     *
     * @param beanType
     * @param datas
     * @param <T>
     * @return
     */
    public static <T> List<Map<String, Object>> listToMap(Class<T> beanType, List<T> datas) {
        List<Map<String, Object>> mapList = new ArrayList<>();
        for (T t : datas) {
            Field[] fields = t.getClass().getDeclaredFields();
            Map<String, Object> itemMap = BeanUtil.beanToMap(t);
            for (Field field : fields) {
                if (!field.isAccessible()) {
                    field.setAccessible(true);
                }
                Excel excel = field.getAnnotation(Excel.class);
                if (excel != null) {
                    if (!StringUtils.isEmpty(excel.dateFormat())) {
                        Object obj = itemMap.get(field.getName());
                        itemMap.remove(field.getName());
                        itemMap.put(field.getName(), DateUtil.format((Date) obj, excel.dateFormat()));
                    }
                } else {
                    itemMap.remove(field.getName());
                }
            }
            mapList.add(itemMap);
        }
        return mapList;
    }

    /**
     * 根据对象获取表格头部
     *
     * @param beanType
     * @param <T>
     * @return
     */
    public static <T> Map<String, String> getTitleMap(Class<T> beanType) {
        Field[] fields = beanType.getDeclaredFields();
        Map<String, String> titleMap = new HashMap<>();
        if (fields != null) {
            for (Field field : fields) {
                if (!field.isAccessible()) {
                    field.setAccessible(true);
                }
                Excel excel = field.getAnnotation(Excel.class);
                if (excel != null) {
                    if (StringUtils.isEmpty(excel.fieldName())) {
                        titleMap.put(excel.name(), field.getName());
                    } else {
                        titleMap.put(excel.name(), excel.fieldName());
                    }
                }
            }
        }
        return titleMap;
    }

    /**
     * 获取表头名字映射
     *
     * @param beanType
     * @param <T>
     * @return
     */
    public static <T> Map<String, String> getNameTitleMap(Class<T> beanType) {
        Field[] fields = beanType.getDeclaredFields();
        List<Excel> excelList = new ArrayList<>();
        Map<String, String> map = new LinkedHashMap<>();
        if (fields != null) {
            for (Field field : fields) {
                if (!field.isAccessible()) {
                    field.setAccessible(true);
                }
                Excel excel = field.getAnnotation(Excel.class);
                if (excel != null) {
                    excelList.add(excel);

                }
            }
        }
        excelList = excelList.stream().sorted(Comparator.comparing(Excel::order)).collect(Collectors.toList());
        for (Excel excel : excelList) {
            map.put(excel.fieldName(), excel.name());
        }
        return map;
    }

    /**
     * 获取普通报表对象内容
     *
     * @param excelFile
     * @param beanType
     * @param <T>
     * @return
     * @throws Exception
     */
    public static <T> List<T> getExcelList(File excelFile, Class<T> beanType) throws Exception {
        List<T> returnList = new ArrayList<>();
        ExcelReader reader = ExcelUtil.getReader(excelFile);
        reader.setHeaderAlias(getTitleMap(beanType));
        List<Map<String, Object>> maps = reader.readAll();
        for (Map<String, Object> map : maps) {
            returnList.add(BeanUtil.mapToBean(map, beanType, true));
        }
        return returnList;
    }

    /**
     * 获取字段注解
     *
     * @param beanType
     * @param <T>
     * @return
     */
    private static <T> Map<String, Excel> getExcelMap(Class<T> beanType) {
        Field[] fields = beanType.getDeclaredFields();
        Map<String, Excel> titleMap = new HashMap<>();
        if (fields != null) {
            for (Field field : fields) {
                if (!field.isAccessible()) {
                    field.setAccessible(true);
                }
                Excel excel = field.getAnnotation(Excel.class);
                if (excel != null) {
                    if (StringUtils.isEmpty(excel.fieldName())) {
                        titleMap.put(field.getName(), excel);
                    } else {
                        titleMap.put(excel.fieldName(), excel);
                    }
                }
            }
        }
        return titleMap;
    }

    /**
     * @param map
     * @param beanType
     * @param <T>
     * @return
     */
    public static <T> T mapToPojo(Map<String, Object> map, Class<T> beanType) {
        OBJECT_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        T t = OBJECT_MAPPER.convertValue(map, beanType);
        return t;
    }

    /**
     * 获取特殊报表内容，中间有字段合并
     *
     * @param excelFile
     * @param beanType
     * @param <T>
     * @return
     * @throws Exception
     */
    public static <T> List<T> getList(File excelFile, Class<T> beanType) throws OwnerException {
        try (ExcelReader reader = ExcelUtil.getReader(excelFile)) {
            List<T> returnList = new ArrayList<>();
            List<Map<String, Object>> maps = reader.readAll();
            for (Map<String, Object> map : maps) {
                returnList.add(mapToJavaBean(map, beanType));
            }
            return returnList;
        } catch (POIException ex) {
            logger.error("getList", ex);
            throw new OwnerException("文件格式/内容不对，请检查");
        } catch (IllegalAccessException e) {
            logger.error("getList", e);
            throw new OwnerException(ErrorEnum.FAIL);
        } catch (InstantiationException e) {
            logger.error("getList", e);
            throw new OwnerException(ErrorEnum.FAIL);
        } catch (IntrospectionException e) {
            logger.error("getList", e);
            throw new OwnerException(ErrorEnum.FAIL);
        } catch (OwnerException e) {
            throw e;
        }
    }

    /**
     * @param tableField
     * @param headerAlias
     * @param requiredMap
     * @param headerList
     * @param filterMonth
     * @return
     * @throws OwnerException
     */
    public static Map<String, Map<String, Object>> checkHeaderAlias(TableField tableField, Map<String, String> headerAlias,
                                                                    Map<String, String> requiredMap, List<String> headerList,
                                                                    Boolean filterMonth, Boolean filterPay, Boolean filerInsurance,
                                                                    String sheetName) throws OwnerException {
        Map<String, Map<String, Object>> tableFieldItemMap = JsonUtils.jsonToPojo(tableField.getFieldTitle(),
                Map.class);
        StringBuilder stringBuilder = new StringBuilder();
        Integer countRequired = 0;
        for (Map.Entry<String, Map<String, Object>> entry : tableFieldItemMap.entrySet()) {
            String key = entry.getKey();
            Map<String, Object> value = entry.getValue();
            TableFieldItem tableFieldItem = JsonUtils.jsonToPojo(JsonUtils.objectToJson(value), TableFieldItem.class);
            //去除title.trim()  考虑前后带空格的问题
            String title = tableFieldItem.getTitle();
            if (tableFieldItem.getImportField()) {
                headerAlias.put(title, key);
                if (tableFieldItem.getRequired()) {
                    if (filterMonth && title.equals(YsRowHandler.TABLE_MONTH)) {
                        continue;
                    }
                    if (filterPay && title.equals(YsRowHandler.TABLE_PAY)) {
                        continue;
                    }
                    if (filerInsurance && title.equals(YsRowHandler.TABLE_INSURANCE)) {
                        continue;
                    }
                    countRequired++;
                    requiredMap.put(title, key);
                    requiredMap.put(title + "(必填)", key);
                    if (headerList.contains(title)
                            || headerList.contains(title + "(必填)")) {
                        headerAlias.put(title + "(必填)", key);
                    } else {
                        stringBuilder.append("【" + title + "】为必填字段，");
                    }
                }
            }
        }
        if (stringBuilder.length() > 0) {
            stringBuilder.replace(stringBuilder.length() - 1, stringBuilder.length(), "");
            throw new OwnerException("sheet【" + sheetName + "】，" + stringBuilder.toString());
        }
        Map<String, Object> countMap = new HashMap<>(1);
        countMap.put(YsRowHandler.COUNT_REQUIRED, countRequired);
        tableFieldItemMap.put(YsRowHandler.COUNT_REQUIRED, countMap);
        return tableFieldItemMap;
    }

    /**
     * 获取导入报表内容，并检验必输项
     *
     * @param excelFile
     * @param beanType
     * @param tableField
     * @param <T>
     * @return
     * @throws OwnerException
     */
    public static <T> ExcelResult<T> getExcelResult(File excelFile, Class<T> beanType, TableField tableField, Integer maxRow)
            throws OwnerException {
        BufferedInputStream in = null;
        YsRowHandler<T> ysRowHandler = new YsRowHandler<T>(beanType, tableField, maxRow);
        try {
            in = cn.hutool.core.io.FileUtil.getInputStream(excelFile);
            if (ExcelFileUtil.isXlsx(IoUtil.toMarkSupportStream(in))) {
                Ys07SaxReader ys07SaxReader = new Ys07SaxReader(ysRowHandler);
                ys07SaxReader.read(excelFile, -1);
            } else {
                Ys03SaxReader ys03SaxReader = new Ys03SaxReader(ysRowHandler);
                ys03SaxReader.read(excelFile, -1);
            }
        } catch (POIException ex) {
            String msg = ex.getMessage();
            while (msg.contains(":")) {
                msg = msg.substring(msg.indexOf(":") + 1);
            }
            if (EXIT.equals(msg.trim())) {
                return ysRowHandler.getExcelResult();
            }
            logger.error(ex.getMessage(), ex);
            throw new OwnerException(msg);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new OwnerException(ErrorEnum.FAIL);
        } finally {
            IoUtil.close(in);
        }
        return ysRowHandler.getExcelResult();
    }

    /**
     * @param excelFile
     * @param beanType
     * @param tableField
     * @param <T>
     * @return
     * @throws OwnerException
     */
    public static <T> List<T> getList(File excelFile, Class<T> beanType, TableField tableField) throws OwnerException {
        try (ExcelReader reader = ExcelUtil.getReader(excelFile)) {
            List<T> returnList = new ArrayList<>();
            List<List<Object>> objects = reader.read(0, 0);
            if (objects.size() == 0) {
                throw new OwnerException("导入报表不包含数据，请检查！");
            }
            List<String> headerList = objects.get(0).stream().map(a -> a.toString()).collect(Collectors.toList());
            Map<String, Map<String, Object>> tableFieldItemMap = JsonUtils.jsonToPojo(tableField.getFieldTitle(),
                    Map.class);
            Map<String, String> headerAlias = new HashMap<>();
            StringBuilder stringBuilder = new StringBuilder();
            for (Map.Entry<String, Map<String, Object>> entry : tableFieldItemMap.entrySet()) {
                String key = entry.getKey();
                Map<String, Object> value = entry.getValue();
                TableFieldItem tableFieldItem = JsonUtils.jsonToPojo(JsonUtils.objectToJson(value),
                        TableFieldItem.class);
                String title = tableFieldItem.getTitle().trim();
                if (tableFieldItem != null && tableFieldItem.getImportField()) {
                    headerAlias.put(title, key);
                    if (tableFieldItem.getRequired()) {
                        if (headerList.contains(title)
                                || headerList.contains(title + "(必填)")) {
                            headerAlias.put(title + "(必填)", key);
                        } else {
                            stringBuilder.append("【" + title + "】为必填字段，");
                        }
                    }
                }
            }
            if (stringBuilder.length() > 0) {
                stringBuilder.replace(stringBuilder.length() - 1, stringBuilder.length(), "");
                throw new OwnerException(stringBuilder.toString());
            }
            reader.setHeaderAlias(headerAlias);
            returnList = reader.readAll(beanType);
            return returnList;
        } catch (OwnerException ex) {
            throw ex;
        } catch (POIException ex) {
            logger.error("getList", ex);
            throw new OwnerException("文件格式/内容不对，请检查");
        } catch (Exception e) {
            logger.error("getList", e);
            throw new OwnerException(ErrorEnum.FAIL);
        }
    }
}
