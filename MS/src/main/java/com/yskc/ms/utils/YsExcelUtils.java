package com.yskc.ms.utils;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.TemplateExportParams;
import cn.afterturn.easypoi.excel.export.template.ExcelExportOfTemplateUtil;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.exceptions.POIException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yskc.common.annotation.Excel;
import com.yskc.common.enums.ErrorEnum;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.utils.CommonUtils;
import com.yskc.ms.config.Constant;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.aspectj.util.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * 报表工具类
 *
 * @author huronghua
 */
public class YsExcelUtils {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private static Logger logger = LoggerFactory.getLogger(YsExcelUtils.class);


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
            returnList.add(BeanUtil.toBeanIgnoreCase(map, beanType, true));
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
     * 导出报告 单sheet
     *
     * @param data         写入数据
     * @param templatePath 模板路径
     * @param action       携带workbook供其他处理
     * @param <T>
     */
    public static <T> void generalReport(Map<String, Object> data, String templatePath, Consumer<Workbook> action) {
        TemplateExportParams params = new TemplateExportParams(templatePath);
        Workbook workbook = ExcelExportUtil.exportExcel(params, data);
        workbook.setForceFormulaRecalculation(true);
        action.accept(workbook);
    }

    /**
     * 导出报告 多sheet 固定sheet页 data--> key：Integer 值为sheet页的index，index从0 开始 data-->
     * value：List为数据集，固定数据类型为Map<String,Object>，对应sheet页的数据 data--> value--> list:
     * map为真实数据，模板参数对应map数据
     *
     * @param data         data.put(sheetIndex,sheetData()) -->setData.add(map)
     *                     -->map.put(name,any)
     * @param templatePath 模板路径
     * @param action       携带workbook供其他处理
     * @param <T>
     */
    public static <T> void generalMoreSheetsReport(Map<Integer, List<Map<String, Object>>> data, String templatePath,
                                                   Consumer<Workbook> action) {
        TemplateExportParams params = new TemplateExportParams(templatePath);
        params.setColForEach(true);
        Workbook workbook = new ExcelExportOfTemplateUtil().createExcelCloneByTemplate(params, data);
        workbook.setForceFormulaRecalculation(true);
        action.accept(workbook);
    }

    /**
     * 合并单元格
     *
     * @param workbook   工作表
     * @param sheetIndex 需要操作的sheet
     * @param firstRow   开始行
     * @param lastRow    结束行
     * @param firstCol   开始列
     * @param lastCol    结束列
     * @param cellValue  单元格内容
     */
    public static <T> void mergeCell(Workbook workbook, int sheetIndex, int firstRow, int lastRow, int firstCol,
                                     int lastCol, String cellValue) {
        Sheet sheet = workbook.getSheetAt(sheetIndex);
        if (lastRow > firstRow) {
            sheet.addMergedRegion(new CellRangeAddress(firstRow, lastRow, firstCol, lastCol));
            if (cellValue == null) {
                cellValue = "";
            }
        }
        setCellValue(sheet, firstRow, firstCol, cellValue);
    }

    /**
     * @param sheet
     * @param row
     * @param col
     * @param cellValue
     * @param <T>
     */
    public static <T> void setCellValue(Sheet sheet, int row, int col, String cellValue) {
        Cell cell = sheet.getRow(row).getCell(col);
        cell.setCellValue(cellValue);
    }

    /**
     * 设置sheet名称 默认为数字
     *
     * @param workbook   工作表
     * @param sheetNames sheet名称
     * @param <T>
     */
    public static <T> void setSheetName(Workbook workbook, List<String> sheetNames) {

        int total = workbook.getNumberOfSheets();
        if (sheetNames.size() <= 0) {
            sheetNames = new ArrayList<>(total);
            for (int i = 0; i < total; i++) {
                sheetNames.add((i + 1) + "");
            }
        }
        for (int i = 0; i < sheetNames.size() && i < total; i++) {
            workbook.setSheetName(i, sheetNames.get(i));
        }
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
     * excel转换html
     *
     * @param fullPath 文件全路径
     * @param fileName 文件名
     * @return html文本
     * @throws OwnerException
     */
    public static String excelToHtml(String fullPath,String fileName) throws OwnerException {
        try (BufferedInputStream input = cn.hutool.core.io.FileUtil.getInputStream(fullPath)) {
            // return POIReadExcelToHtml.pdf2html(path + fileName);
            HSSFWorkbook excelBook = new HSSFWorkbook();
            // 判断Excel文件将07+版本转换为03版本
            if (fileName.toLowerCase().endsWith(Constant.EXCEL_XLSX)) {
                XlsxTransform xls = new XlsxTransform();
                XSSFWorkbook workbookOld = new XSSFWorkbook(input);
                xls.transformXSSF(workbookOld, excelBook);
            } else {
                excelBook = new HSSFWorkbook(input);
            }
            YsExcelToHtmlConverter excelToHtmlConverter = new YsExcelToHtmlConverter(
                    DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument());
            // 去掉Excel头行
            excelToHtmlConverter.setOutputColumnHeaders(false);
            // 去掉Excel行号
            excelToHtmlConverter.setOutputRowNumbers(false);

            excelToHtmlConverter.processWorkbook(excelBook);

            Document htmlDocument = excelToHtmlConverter.getDocument();
            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
            DOMSource domSource = new DOMSource(htmlDocument);
            StreamResult streamResult = new StreamResult(outStream);
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer serializer = tf.newTransformer();

            serializer.setOutputProperty(OutputKeys.ENCODING, "utf-8");
            serializer.setOutputProperty(OutputKeys.INDENT, "yes");
            serializer.setOutputProperty(OutputKeys.METHOD, "html");

            serializer.transform(domSource, streamResult);
            outStream.close();
            String content = new String(outStream.toByteArray());
            File f = new File(fullPath.replace(".xlsx", ".html").replace(".xls", ".html"));
            FileUtil.writeAsString(f, content);
            // Excel转换成Html
            return new String(outStream.toByteArray());
        } catch (Exception e) {
            logger.error("msg", e);
            throw new OwnerException(ErrorEnum.FAIL);
        }
    }

}
