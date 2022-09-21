package com.yskc.docservice.utils;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.utils.JsonUtils;
import com.yskc.docservice.config.Constant;
import com.yskc.docservice.models.rs.excel.ExcelResult;
import com.yskc.docservice.models.rs.excel.TableField;
import org.apache.poi.ss.usermodel.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.text.MessageFormat;
import java.util.*;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.utils
 * @Author: zhangdingfu
 * @CreateTime: 2019-12-26 15:14
 * @Description: excel行handle
 */
public class YsRowHandler<T> {
    /**
     * 最大不能超过当前年5年
     */
    public final int maxYear = cn.hutool.core.date.DateUtil.year(new Date()) + 5;
    /*
     * 最小不能低于1898年
     */
    public static final int minYear = 1898;

    public static final String SLASH = Constant.PATH_SEPARATOR;
    public final static String SAMPLE_SHEET = "样例数据";
    public final static String HEADER_MONTH = "月份(必填)";
    public final static String TABLE_MONTH = "月份";

    public final static String TABLE_PAY = "应发工资";
    public final static String TABLE_INSURANCE = "五险一金";

    public final static String COUNT_REQUIRED = "countRequired";

    public final static String PAY = "pay";

    public final static String PAY_DETAIL = "payDetail";
    public final static String INSURANCE_FUND = "insuranceFund";

    public final static String INSURANCE_DETAIL = "insuranceDetail";

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private String sheetName;

    private Boolean readAll = false;
    private Boolean hasPay = false;

    private Class<T> beanType;

    private TableField tableField;
    private ExcelResult<T> excelResult = new ExcelResult<>();
    private boolean filterMonth = false;
    private Date currentMonth = null;

    private List<String> headerList;

    private List<T> data = new ArrayList<>();

    private Integer maxRow;

    /**
     * 关闭
     */
    private Boolean close = false;

    Map<String, String> headerAlias = new HashMap<>();
    Map<String, String> requiredMap = new HashMap<>();
    Map<String, Map<String, Object>> fieldMap = null;
    Map<String, Boolean> beanFieldMap = null;

    public ExcelResult<T> getExcelResult() {
        excelResult.setData(data);
        return excelResult;
    }

    public String getSheetName() {
        return sheetName;
    }

    public List<T> getData() {
        return data;
    }

    public Boolean getClose() {
        return close;
    }

    /**
     * 构造
     *
     * @param beanType
     * @param tableField
     */
    public YsRowHandler(Class<T> beanType, TableField tableField, Integer maxRow) {
        this.beanType = beanType;
        this.tableField = tableField;
        this.maxRow = maxRow;
        // 初始化bean的字段map对象
        setBeanFieldMap();
    }

    /**
     * 设置bean的字段map
     */
    private void setBeanFieldMap() {
        Field[] fields = beanType.getDeclaredFields();
        beanFieldMap = new HashMap<>(fields.length);
        for (Field field : fields) {
            beanFieldMap.put(field.getName(), true);
        }
    }

    /**
     * handle处理数据
     *
     * @param sheetName
     * @param sheetIndex
     * @param rowIndex
     * @param objList
     * @throws Exception
     */
    public void handle(String sheetName, int sheetIndex, int rowIndex, List<Object> objList) throws Exception {
        // 跳过样例数据(首页不能为样例数据)
        if (sheetName.equals(SAMPLE_SHEET)) {
            return;
        }
        // 不再读取
        if (this.close) {
            return;
        }
        // 超过1页且sheetName名称不是【yyyyMM】格式,close置为true，停止读取数据并进入保存数据环节
        if (sheetIndex > 0 && !readAll) {
            this.close = true;
            return;
        }
        this.sheetName = sheetName;
        // 第一行获取header
        if (rowIndex == 0) {
            setDataHeader(objList);
            this.hasPay = headerAlias.containsKey(TABLE_PAY);
            // 第一页确定readAll
            if (sheetIndex == 0) {
                try {
                    getSheetNameMonth();
                    readAll = true;
                } catch (Exception e) {
                    readAll = false;
                }
            }
        } else {
            // 添加数据操作
            T t = getResult(sheetName, rowIndex, objList);
            if (t != null) {
                data.add(t);
            }
            if (data.size() > maxRow) {
                throw new OwnerException(MessageFormat.format("导入失败，最多只能导入【{0,number,#}】条数据，请拆分。", maxRow));
            }
        }
    }

    /**
     * 设置数据header
     *
     * @param objList
     * @throws OwnerException
     */
    public void setDataHeader(List<Object> objList) throws OwnerException {
        headerList = new ArrayList<>();
        int emptyCount = 0;
        String temp = "temp";
        for (Object o : objList) {
            if (!StringUtils.isEmpty(o)) {
                headerList.add(o.toString().trim());
            } else {
                emptyCount++;
                headerList.add(temp + emptyCount);
            }
        }
        filterMonth = tableField.getHasMonth() && !(headerList.contains(HEADER_MONTH) || headerList.contains(TABLE_MONTH));
        if (filterMonth) {
//            if (sheetName.length() != 6) {
//                throw new OwnerException(MessageFormat.format("sheet【{0}】名称格式错误，格式：年月，例如：201901", sheetName));
//            }
            try {
                currentMonth = getSheetNameMonth();
                requiredMap.remove(HEADER_MONTH);
                //calendar.set
            } catch (NumberFormatException n) {
                logger.error("sheet名称数字格式化失败", n);
                throw new OwnerException(MessageFormat.format("sheet【{0}】名称格式错误，格式：年月，例如：201901", sheetName));
            }
        }
        fieldMap = ImportExcelUtils.checkHeaderAlias(tableField, headerAlias, requiredMap, headerList, filterMonth, tableField.getHasCol(), tableField.getHasICol(), sheetName);
    }

    /**
     * 获取数据
     *
     * @param sheetName
     * @param rowIndex
     * @param objList
     * @return
     * @throws Exception
     */
    public T getResult(String sheetName, int rowIndex, List<Object> objList) throws Exception {
        //空行数据忽略
        if (CollectionUtils.isEmpty(objList)) {
            return null;
        }
        T t = null;
        Map<String, Object> objectMap = getMapData(objList);
        String start = MessageFormat.format("sheet【{0}】，第【{1}】行，必填项： ", sheetName, rowIndex + 1);
        StringBuilder rowBuilder = new StringBuilder(start);
        Map<String, Object> recordMap = new HashMap<>();
        if (filterMonth) {
            objectMap.put(TABLE_MONTH, currentMonth);
        }
        int countRequired = 0;
        Map<String, Object> colMap = new HashMap<>();
        Map<String, Object> iColMap = new HashMap<>();
        Object colObj;
        String currentCol;
        for (String key : objectMap.keySet()) {
            if (requiredMap.containsKey(key) && StringUtils.isEmpty(objectMap.get(key))) {
                rowBuilder.append(MessageFormat.format("【{0}】", key));
                countRequired++;
            }
            currentCol = headerAlias.get(key);
            if (currentCol != null) {
                if (!beanFieldMap.containsKey(currentCol)) {
                    if ((null == objectMap.get(key) || StringUtils.isEmpty(objectMap.get(key)))) {
                        colObj = hasPay ? 0 : "";
                    } else {
                        if (hasPay && !NumberUtil.isNumber(objectMap.get(key).toString())) {
                            throw new OwnerException(MessageFormat.format("sheet【{0}】，第【{1}】行，【{2}】格式不对，请输入有效数字!", sheetName, rowIndex + 1, key));
                        }
                        colObj = objectMap.get(key);
                    }
                    if (currentCol.startsWith("col")) {
                        colMap.put(currentCol, colObj);
                    } else {
                        iColMap.put(currentCol, colObj);
                    }
                } else if (beanType.getDeclaredField(currentCol).getType() == Date.class && !StringUtils.isEmpty(objectMap.get(key))) {
                    Date temp = getDateTime(objectMap.get(key), rowBuilder, rowIndex, currentCol);
                    if (null == temp) {
                        throw new OwnerException(rowBuilder.append(
                                MessageFormat.format("{0}{1}{2}", key,
                                        MapUtil.isEmpty(fieldMap.get(currentCol)) ?
                                                "" : StringUtils.isEmpty(fieldMap.get(currentCol).get("sampleValue")) ?
                                                "" : fieldMap.get(currentCol).get("sampleValue") + "，"
                                        , "时间格式错误，请检查!")).toString());
                    }
                    recordMap.put(currentCol, temp);
                } else {
                    if (objectMap.get(key) != null) {
                        recordMap.put(currentCol, objectMap.get(key).toString().trim());
                    }

                }
            }
        }
        //// // TODO: 2020-03-16 关于导入数据拼接json，写死操作方法及字段
        if (tableField.getHasCol()) {
            loadDetail(colMap, recordMap, hasPay ? PAY_DETAIL : "data", hasPay ? PAY : null);
        }
        if (tableField.getHasICol()) {
            loadDetail(iColMap, recordMap, INSURANCE_DETAIL, INSURANCE_FUND);
        }
        if (countRequired == (int) fieldMap.get(COUNT_REQUIRED).get(COUNT_REQUIRED)) {
            return null;
        }
        String errMessage = null;
        if (rowBuilder.length() > start.length()) {
            errMessage = rowBuilder.append("为空，请检查!").toString();
        } else {
            try {
                t = ImportExcelUtils.mapToPojo(recordMap, beanType);
            } catch (Exception e) {
                InvalidFormatException invalidFormatException = (InvalidFormatException) e.getCause();
                if (invalidFormatException != null && invalidFormatException.getPath().size() > 0) {
                    String fieldName = invalidFormatException.getPath().get(0).getFieldName();
                    if (fieldMap != null && fieldMap.containsKey(fieldName)) {
                        Object title = fieldMap.get(fieldName).get("title");
                        Object sampleValue = fieldMap.get(fieldName).get("sampleValue");
                        if (StringUtils.isEmpty(sampleValue)) {
                            errMessage = MessageFormat.format("sheet【{0}】，第【{1}】行，【{2}】格式不对，请检查!", sheetName, rowIndex + 1, title);
                        } else {
                            errMessage = MessageFormat.format("sheet【{0}】，第【{1}】行，【{2}】格式不对，{3}，请检查!", sheetName, rowIndex + 1, title, sampleValue);
                        }
                    }
                } else {
                    logger.error("导入数据格式不对", e);
                    errMessage = MessageFormat.format("sheet【{0}】，第【{1}】行，数据有问题，请检查!", sheetName, rowIndex + 1);
                }
            }
        }
        if (errMessage != null) {
            throw new OwnerException(errMessage);
        }
        return t;
    }

    /**
     * 加载薪资/五险一金/data明细
     * 当前headerAlias若不存在 TABLE_PAY列，则认定为存入data，否则存入payDetail
     * 当前detailKey = "data" 时，不做统计
     *
     * @param colMap    col数据
     * @param recordMap 行
     * @param detailKey payDetail insuranceDetail (默认data)
     * @param key       pay insuranceFund null
     */
    private void loadDetail(Map<String, Object> colMap, Map<String, Object> recordMap, String detailKey, String key) {
        if (CollectionUtils.isEmpty(colMap)) {
            if (StringUtils.hasLength(key)) {
                recordMap.put(key, 0);
            }
            return;
        }
        recordMap.put(detailKey, JsonUtils.objectToJson(colMap));
        if (StringUtils.hasLength(key)) {
            double tempPay = 0;
            for (Object colValue : colMap.values()) {
                tempPay += Double.parseDouble(colValue.toString());
            }
            recordMap.put(key, tempPay);
        }
    }

    /**
     * 获取时间 ，若时间是一个数字，调用getJavaDate(double)
     *
     * @param v
     * @param rowBuilder
     * @param rowIndex
     * @param currentCol
     * @return
     * @throws Exception
     */
    private Date getDateTime(Object v, StringBuilder rowBuilder, int rowIndex, String currentCol) throws Exception {
        Date d = null;
        if (v.getClass() != DateTime.class
                && v.getClass() != Date.class) {
            // 非日期对象，若为一个number类型，转换为时间
            if (NumberUtil.isNumber(v.toString())) {
                String vStr = v.toString();
                // 长度为8，尝试以yyyyMMdd格式转换
                if (8 == vStr.length()) {
                    try {
                        d = com.yskc.common.utils.DateUtil.parse(vStr, DatePattern.PURE_DATE_PATTERN);
                    } catch (Exception e) {
                        // 转换失败不处理
                    }
                }
                if (null == d) {
                    d = DateUtil.getJavaDate(Double.parseDouble(vStr));
                }
            }
            if (v instanceof String) {
                String temp = v.toString();
                if (temp.contains(SLASH)) {
                    temp = fillZero(temp, SLASH, Constant.HYPHEN);
                }
                if (temp.contains(Constant.HYPHEN)) {
                    temp = fillZero(temp, Constant.HYPHEN, Constant.HYPHEN);
                }
                try {
                    d = com.yskc.common.utils.DateUtil.parse(temp, com.yskc.common.utils.DateUtil.DEFAULT_DATE_FORMAT);
                } catch (Exception e) {
                    throw new OwnerException(rowBuilder.append(
                            MessageFormat.format("{0}{1}",
                                    MapUtil.isEmpty(fieldMap.get(currentCol)) ?
                                            "" : StringUtils.isEmpty(fieldMap.get(currentCol).get("sampleValue")) ?
                                            "" : fieldMap.get(currentCol).get("sampleValue") + "，"
                                    , "时间格式错误，请检查!")).toString());
                }
            }
        } else {
            d = (Date) v;
        }
        if (null != d) {
            int year = cn.hutool.core.date.DateUtil.year(d);
            if (year > maxYear || year < minYear) {
                throw new OwnerException(rowBuilder.append(
                        MessageFormat.format("第{2,number,#}行，{0}{1}",
                                MapUtil.isEmpty(fieldMap.get(currentCol)) ?
                                        "" : StringUtils.isEmpty(fieldMap.get(currentCol).get("sampleValue")) ?
                                        "" : fieldMap.get(currentCol).get("sampleValue") + "，"
                                , "时间格式错误，请检查!", rowIndex)).toString());
            }
        }
        return d;
    }

    /**
     * 补零操作
     *
     * @param v     原值
     * @param split 分割字符串
     * @param join  替换分割字符串
     * @return
     */
    private String fillZero(String v, String split, String join) {
        String[] arr = v.split(split);
        for (int i = 0; i < arr.length; i++) {
            if (arr[i].trim().length() == 1) {
                arr[i] = 0 + arr[i];
            }
        }
        return String.join(join, arr);
    }

    /**
     * 数据转换为map对象处理
     *
     * @param objList
     * @return
     */
    private Map<String, Object> getMapData(List<Object> objList) {
        Map<String, Object> result = new HashMap<>();
        if (!CollectionUtils.isEmpty(this.headerList) && !CollectionUtils.isEmpty(objList)) {
            for (int i = 0; i < this.headerList.size(); i++) {
                if (objList.size() <= i) {
                    result.put(this.headerList.get(i), null);
                } else {
                    result.put(this.headerList.get(i), objList.get(i));
                }
            }
        }
        return result;
    }


    /**
     * 获取sheet月份名称
     *
     * @return date
     */
    private Date getSheetNameMonth() throws OwnerException {
        String tempNumber = getSheetNumber();
        if (tempNumber == null) {
            throw new OwnerException("sheet名称转化月份失败");
        }
        int yearMonth = Integer.parseInt(tempNumber);
        int month = (yearMonth % 100);
        if (yearMonth < 100000 || month > 12 || month < 1) {
            throw new NumberFormatException("sheet名称转化月份失败");
        }
        Calendar calendar = Calendar.getInstance();
        calendar.set(yearMonth / 100, month - 1, 1, 0, 0, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * sheet名称转化为月份
     *
     * @return
     */
    private String getSheetNumber() throws NumberFormatException {
        String tempNumber = sheetName;
        String split = null;
        if (tempNumber.contains(Constant.HYPHEN)) {
            split = Constant.HYPHEN;
        } else if (tempNumber.contains(Constant.PATH_SEPARATOR)) {
            split = Constant.PATH_SEPARATOR;
        }
        if (null != split) {
            String[] arr = tempNumber.split(split);
            if (arr.length > 1) {
                if (arr[0].length() == 4 && arr[1].length() <= 2) {
                    tempNumber = arr[0] + StrUtil.fillBefore(arr[1], '0', 2);
                }
            }
        }
        return NumberUtil.isNumber(tempNumber) ? tempNumber : null;
    }
}
