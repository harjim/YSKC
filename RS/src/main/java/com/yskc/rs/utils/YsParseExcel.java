package com.yskc.rs.utils;

import cn.afterturn.easypoi.cache.ExcelCache;
import cn.afterturn.easypoi.entity.ImageEntity;
import cn.afterturn.easypoi.excel.entity.TemplateExportParams;
import cn.afterturn.easypoi.excel.entity.TemplateSumEntity;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import cn.afterturn.easypoi.excel.entity.params.ExcelForEachParams;
import cn.afterturn.easypoi.excel.export.base.BaseExportService;
import cn.afterturn.easypoi.excel.export.styler.IExcelExportStyler;
import cn.afterturn.easypoi.excel.export.template.ExcelExportOfTemplateUtil;
import cn.afterturn.easypoi.excel.export.template.TemplateSumHandler;
import cn.afterturn.easypoi.excel.html.helper.MergedRegionHelper;
import cn.afterturn.easypoi.exception.excel.ExcelExportException;
import cn.afterturn.easypoi.exception.excel.enums.ExcelExportEnum;
import cn.afterturn.easypoi.util.*;
import cn.hutool.core.util.NumberUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

import static cn.afterturn.easypoi.excel.ExcelExportUtil.SHEET_NAME;
import static cn.afterturn.easypoi.util.PoiElUtil.*;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.utils
 * @Author: zhangdingfu
 * @CreateTime: 2020-08-27 17:19
 * @Description: 解析excel
 */
public class YsParseExcel extends BaseExportService {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(ExcelExportOfTemplateUtil.class);

    /**
     * 缓存TEMP 的for each创建的cell ,跳过这个cell的模板语法查找,提高效率
     */
    private Set<String> tempCreateCellSet = new HashSet<String>();
    /**
     * 模板参数,全局都用到
     */
    private TemplateExportParams teplateParams;
    /**
     * 单元格合并信息
     */
    private MergedRegionHelper mergedRegionHelper;

    private TemplateSumHandler templateSumHandler;

    private int cellCount = 0;

    /**
     * 利用foreach循环输出数据
     *
     * @param cell
     * @param map
     * @param name
     * @throws Exception
     */
    private void addListDataToExcel(Cell cell, Map<String, Object> map,
                                    String name) throws Exception {
        boolean isCreate = !name.contains(FOREACH_NOT_CREATE);
        boolean isShift = name.contains(FOREACH_AND_SHIFT);
        name = name.replace(FOREACH_NOT_CREATE, EMPTY).replace(FOREACH_AND_SHIFT, EMPTY)
                .replace(FOREACH, EMPTY).replace(START_STR, EMPTY);
        String[] keys = name.replaceAll("\\s{1,}", " ").trim().split(" ");
        Collection<?> datas = (Collection<?>) PoiPublicUtil.getParamsValue(keys[0], map);
        Object[] columnsInfo = getAllDataColumns(cell, name.replace(keys[0], EMPTY),
                mergedRegionHelper);
        if (datas == null) {
            return;
        }
        Iterator<?> its = datas.iterator();
        int rowspan = (Integer) columnsInfo[0], colspan = (Integer) columnsInfo[1];
        @SuppressWarnings("unchecked")
        List<ExcelForEachParams> columns = (List<ExcelForEachParams>) columnsInfo[2];
        Row row = null;
        int rowIndex = cell.getRow().getRowNum() + 1;
        //处理当前行
        if (its.hasNext()) {
            Object t = its.next();
            setForEeachRowCellValue(isCreate, cell.getRow(), cell.getColumnIndex(), t, columns, map,
                    rowspan, colspan, mergedRegionHelper);
            rowIndex += rowspan - 1;
        }
        //修复不论后面有没有数据,都应该执行的是插入操作
        if (isShift && datas.size() * rowspan > 1 && cell.getRowIndex() + rowspan < cell.getRow().getSheet().getLastRowNum()) {
            int lastRowNum = cell.getRow().getSheet().getLastRowNum();
            cell.getRow().getSheet().shiftRows(cell.getRowIndex() + rowspan, lastRowNum, (datas.size() - 1) * rowspan, true, true);
            mergedRegionHelper.shiftRows(cell.getSheet(), cell.getRowIndex() + rowspan, (datas.size() - 1) * rowspan);
            templateSumHandler.shiftRows(cell.getRowIndex() + rowspan, (datas.size() - 1) * rowspan);
            PoiExcelTempUtil.reset(cell.getSheet(), cell.getRowIndex() + rowspan + (datas.size() - 1) * rowspan, cell.getRow().getSheet().getLastRowNum());
        }
        while (its.hasNext()) {
            Object t = its.next();
            row = createRow(rowIndex, cell.getSheet(), isCreate, rowspan);
            setForEeachRowCellValue(isCreate, row, cell.getColumnIndex(), t, columns, map, rowspan,
                    colspan, mergedRegionHelper);
            rowIndex += rowspan;
        }
    }


    public Workbook createExcelCloneByTemplate(TemplateExportParams params,
                                               Map<Integer, List<Map<String, Object>>> map) {
        // step 1. 判断模板的地址
        if (params == null || map == null || StringUtils.isEmpty(params.getTemplateUrl())) {
            throw new ExcelExportException(ExcelExportEnum.PARAMETER_ERROR);
        }
        Workbook wb = null;
        // step 2. 判断模板的Excel类型,解析模板
        try {
            this.teplateParams = params;
            wb = ExcelCache.getWorkbook(teplateParams.getTemplateUrl(), teplateParams.getSheetNum(),
                    true);
            int oldSheetNum = wb.getNumberOfSheets();
            List<String> oldSheetName = new ArrayList<>();
            for (int i = 0; i < oldSheetNum; i++) {
                oldSheetName.add(wb.getSheetName(i));
            }
            // 把所有的KEY排个顺序
            List<Map<String, Object>> mapList;
            List<Integer> sheetNumList = new ArrayList<>();
            sheetNumList.addAll(map.keySet());
            Collections.sort(sheetNumList);

            //把需要克隆的全部克隆一遍
            for (Integer sheetNum : sheetNumList) {
                mapList = map.get(sheetNum);
                for (int i = mapList.size(); i > 0; i--) {
                    wb.cloneSheet(sheetNum);
                }
            }
            for (int i = 0; i < oldSheetName.size(); i++) {
                wb.removeSheetAt(wb.getSheetIndex(oldSheetName.get(i)));
            }
            // 创建表格样式
            setExcelExportStyler((IExcelExportStyler) teplateParams.getStyle()
                    .getConstructor(Workbook.class).newInstance(wb));
            // step 3. 解析模板
            int sheetIndex = 0;
            for (Integer sheetNum : sheetNumList) {
                mapList = map.get(sheetNum);
                for (int i = mapList.size() - 1; i >= 0; i--) {
                    tempCreateCellSet.clear();
                    if (mapList.get(i).containsKey(SHEET_NAME)) {
                        wb.setSheetName(sheetIndex, mapList.get(i).get(SHEET_NAME).toString());
                    }
                    parseTemplate(wb.getSheetAt(sheetIndex), mapList.get(i), params.isColForEach());
                    sheetIndex++;
                }
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return null;
        }
        return wb;
    }


    private void parseTemplate(Sheet sheet, Map<String, Object> map,
                               boolean colForeach) throws Exception {
        if (sheet.getWorkbook() instanceof XSSFWorkbook) {
            super.type = ExcelType.XSSF;
        }
        deleteCell(sheet, map);
        mergedRegionHelper = new MergedRegionHelper(sheet);
        templateSumHandler = new TemplateSumHandler(sheet);
        if (colForeach) {
            colForeach(sheet, map);
        }
        Row row = null;
        int index = 0;
        while (index <= sheet.getLastRowNum()) {
            row = sheet.getRow(index++);
            if (row == null) {
                continue;
            }
            for (int i = row.getFirstCellNum(); i < row.getLastCellNum(); i++) {
                if (row.getCell(i) != null && !tempCreateCellSet
                        .contains(row.getRowNum() + "_" + row.getCell(i).getColumnIndex())) {
                    setValueForCellByMap(row.getCell(i), map);
                }
            }
        }

        //修改需要处理的统计值
        handlerSumCell(sheet);
    }

    private void handlerSumCell(Sheet sheet) {
        for (TemplateSumEntity sumEntity : templateSumHandler.getDataList()) {
            Cell cell = sheet.getRow(sumEntity.getRow()).getCell(sumEntity.getCol());
            if (cell.getStringCellValue().contains(sumEntity.getSumKey())) {
                cell.setCellValue(cell.getStringCellValue()
                        .replace("sum:(" + sumEntity.getSumKey() + ")", sumEntity.getValue() + ""));
            } else {
                cell.setCellValue(cell.getStringCellValue() + sumEntity.getValue());
            }
        }
    }

    /**
     * 先进行列的循环,因为涉及很多数据
     *
     * @param sheet
     * @param map
     */
    private void colForeach(Sheet sheet, Map<String, Object> map) throws Exception {
        Row row = null;
        Cell cell = null;
        int index = 0;
        while (index <= sheet.getLastRowNum()) {
            row = sheet.getRow(index++);
            if (row == null) {
                continue;
            }
            for (int i = row.getFirstCellNum(); i < row.getLastCellNum(); i++) {
                cell = row.getCell(i);
                if (row.getCell(i) != null && (cell.getCellType() == CellType.STRING
                        || cell.getCellType() == CellType.NUMERIC)) {
                    String text = PoiCellUtil.getCellValue(cell);
                    if (text.contains(FOREACH_COL) || text.contains(FOREACH_COL_VALUE)) {
                        foreachCol(cell, map, text);
                    }
                }
            }
        }
    }

    /**
     * 循环列表
     *
     * @param cell
     * @param map
     * @param name
     * @throws Exception
     */
    private void foreachCol(Cell cell, Map<String, Object> map, String name) throws Exception {
        name = name.replace(FOREACH_COL_VALUE, EMPTY).replace(FOREACH_COL, EMPTY).replace(START_STR,
                EMPTY);
        String[] keys = name.replaceAll("\\s{1,}", " ").trim().split(" ");
        Collection<?> datas = (Collection<?>) PoiPublicUtil.getParamsValue(keys[0], map);
        Object[] columnsInfo = getAllDataColumns(cell, name.replace(keys[0], EMPTY),
                mergedRegionHelper);
        if (datas == null) {
            return;
        }
        exchangeCell(cell.getRow(), cell.getColumnIndex(), datas.size(), cellCount);
        // 用完以后置零
        cellCount = 0;
        Iterator<?> its = datas.iterator();
        int rowspan = (Integer) columnsInfo[0], colspan = (Integer) columnsInfo[1];
        @SuppressWarnings("unchecked")
        List<ExcelForEachParams> columns = (List<ExcelForEachParams>) columnsInfo[2];
        while (its.hasNext()) {
            Object t = its.next();
            setForEeachRowCellValue(true, cell.getRow(), cell.getColumnIndex(), t, columns, map,
                    rowspan, colspan, mergedRegionHelper);
//            if (cell.getRow().getCell(cell.getColumnIndex() + colspan) == null) {
//                cell.getRow().createCell(cell.getColumnIndex() + colspan);
//            }
            Cell temp = cell.getRow().getCell(cell.getColumnIndex() + colspan);
            if (temp == null) {
                cell = cell.getRow().createCell(cell.getColumnIndex() + colspan);
            } else {
                cell = temp;
            }
        }
    }

    /**
     * 遍历列，交换格子，避免遍历生成格子替换原有格子
     *
     * @param row
     * @param colIndex
     * @param dataSize
     * @param cellCount
     */
    private void exchangeCell(Row row, int colIndex, int dataSize, int cellCount) {
        int oldCellLen = row.getLastCellNum();
        int begin = cellCount * dataSize + colIndex;
        int move = begin = begin < oldCellLen ? oldCellLen : begin;
        for (int j = colIndex + cellCount; j < oldCellLen; j++) {
            Cell c = row.createCell(move);
            if (row.getCell(j) != null) {
                c.setCellStyle(row.getCell(j).getCellStyle());
                c.setCellValue(row.getCell(j).getStringCellValue());
            }
            move++;
        }
        int max = cellCount * dataSize + colIndex;
        for (int j = colIndex + 1; j < max; j++) {
            Cell c = row.createCell(j);
            c.setCellStyle(row.getCell(colIndex).getCellStyle());
        }
        for (int j = begin; j < move; j++) {
            Cell copy = row.getCell(j);
            Cell paste = row.getCell(max);
            paste.setCellValue(copy.getStringCellValue());
            paste.setCellStyle(copy.getCellStyle());
            max++;
        }
        for (int j = max; j < move; j++) {
            Cell c = row.getCell(j);
            if (c != null) {
                row.removeCell(c);
            }
        }
    }

    /**
     * 先判断删除,省得影响效率
     *
     * @param sheet
     * @param map
     * @throws Exception
     */
    private void deleteCell(Sheet sheet, Map<String, Object> map) throws Exception {
        Row row = null;
        Cell cell = null;
        int index = 0;
        while (index <= sheet.getLastRowNum()) {
            row = sheet.getRow(index++);
            if (row == null) {
                continue;
            }
            for (int i = row.getFirstCellNum(); i < row.getLastCellNum(); i++) {
                cell = row.getCell(i);
                if (row.getCell(i) != null && (cell.getCellType() == CellType.STRING
                        || cell.getCellType() == CellType.NUMERIC)) {
                    cell.setCellType(CellType.STRING);
                    String text = cell.getStringCellValue();
                    if (text.contains(IF_DELETE)) {
                        if (Boolean.valueOf(
                                eval(text.substring(text.indexOf(START_STR) + 2, text.indexOf(END_STR))
                                        .trim(), map).toString())) {
                            PoiSheetUtil.deleteColumn(sheet, i);
                            i--;
                        }
                        cell.setCellValue("");
                    }
                }
            }
        }
    }

    /**
     * 给每个Cell通过解析方式set值
     *
     * @param cell
     * @param map
     */
    private void setValueForCellByMap(Cell cell, Map<String, Object> map) throws Exception {
        CellType cellType = cell.getCellType();
        if (cellType != CellType.STRING && cellType != CellType.NUMERIC) {
            return;
        }
        String oldString;
        if (cellType == CellType.NUMERIC) {
            oldString = cell.getNumericCellValue() + "";
        } else {
            oldString = cell.getStringCellValue();
        }
        if (oldString != null && oldString.indexOf(START_STR) != -1
                && !oldString.contains(FOREACH)) {
            // step 2. 判断是否含有解析函数
            String params = null;
            boolean isNumber = false;
            if (isNumber(oldString)) {
                isNumber = true;
                oldString = oldString.replaceFirst(NUMBER_SYMBOL, "");
            }
            boolean isStyleBySelf = false;
            if (isStyleBySelf(oldString)) {
                isStyleBySelf = true;
                oldString = oldString.replaceFirst(NUMBER_SYMBOL, "");
            }
            Object obj = PoiPublicUtil.getRealValue(oldString, map);
            //如何是数值 类型,就按照数值类型进行设置// 如果是图片就设置为图片
            if (obj instanceof ImageEntity) {
                ImageEntity img = (ImageEntity) obj;
                cell.setCellValue("");
                if (img.getRowspan() > 1 || img.getColspan() > 1) {
                    img.setHeight(0);
                    PoiMergeCellUtil.addMergedRegion(cell.getSheet(), cell.getRowIndex(),
                            cell.getRowIndex() + img.getRowspan() - 1, cell.getColumnIndex(), cell.getColumnIndex() + img.getColspan() - 1);
                }
                createImageCell(cell, img.getHeight(), img.getRowspan(), img.getColspan(), img.getUrl(), img.getData());
            } else if (isNumber && StringUtils.isNotBlank(obj.toString())) {
                cell.setCellValue(Double.parseDouble(obj.toString()));
            } else {
                cell.setCellValue(obj.toString());
            }
        }
        //判断foreach 这种方法
        if (oldString != null && oldString.contains(FOREACH)) {
            addListDataToExcel(cell, map, oldString.trim());
        }

    }

    private boolean isNumber(String text) {
        return text.startsWith(NUMBER_SYMBOL) || text.contains("{" + NUMBER_SYMBOL)
                || text.contains(" " + NUMBER_SYMBOL);
    }

    private boolean isStyleBySelf(String text) {
        return text.startsWith(STYLE_SELF) || text.contains("{" + STYLE_SELF)
                || text.contains(" " + STYLE_SELF);
    }

    /**
     * 创建并返回第一个Row
     *
     * @param sheet
     * @param rowIndex
     * @param isCreate
     * @param rows
     * @return
     */
    private Row createRow(int rowIndex, Sheet sheet, boolean isCreate, int rows) {
        for (int i = 0; i < rows; i++) {
            if (isCreate) {
                sheet.createRow(rowIndex++);
            } else if (sheet.getRow(rowIndex++) == null) {
                sheet.createRow(rowIndex - 1);
            }
        }
        return sheet.getRow(rowIndex - rows);
    }

    private void setForEeachRowCellValue(boolean isCreate, Row row, int columnIndex, Object t,
                                         List<ExcelForEachParams> columns, Map<String, Object> map,
                                         int rowspan, int colspan,
                                         MergedRegionHelper mergedRegionHelper) throws Exception {
        //所有的cell创建一遍
        for (int i = 0; i < rowspan; i++) {
            int size = columns.size();//判断是不是超出设置了
            for (int j = columnIndex, max = columnIndex + colspan; j < max; j++) {
                if (row.getCell(j) == null) {
                    row.createCell(j);
                    CellStyle style = row.getRowNum() % 2 == 0
                            ? getStyles(false,
                            size <= j - columnIndex ? null : columns.get(j - columnIndex))
                            : getStyles(true,
                            size <= j - columnIndex ? null : columns.get(j - columnIndex));
                    //返回的styler不为空时才使用,否则使用Excel设置的,更加推荐Excel设置的样式
                    if (style != null) {
                        row.getCell(j).setCellStyle(style);
                    }
                }
            }
            if (i < rowspan - 1) {
                row = row.getSheet().getRow(row.getRowNum() + 1);
            }
        }
        //填写数据
        ExcelForEachParams params;
        row = row.getSheet().getRow(row.getRowNum() - rowspan + 1);
        for (int k = 0; k < rowspan; k++) {
            int ci = columnIndex;
            int oldCi = columnIndex;
            short high = columns.get(0).getHeight();
            int n = k;
            while (n > 0) {
                if (columns.get(n * colspan).getHeight() == 0) {
                    n--;
                } else {
                    high = columns.get(n * colspan).getHeight();
                    break;
                }
            }
            row.setHeight(high);
            for (int i = 0; i < colspan && i < columns.size(); i++) {
                boolean isNumber = false;
                params = columns.get(colspan * k + i);
                tempCreateCellSet.add(row.getRowNum() + "_" + (ci));
                if (params == null) {
                    continue;
                }
                if (StringUtils.isEmpty(params.getName())
                        && StringUtils.isEmpty(params.getConstValue())) {
                    row.getCell(ci).setCellStyle(params.getCellStyle());
                    ci = ci + params.getColspan();
                    oldCi = oldCi + params.getColspan();
                    continue;
                }
                String val = null;
                Object obj = null;
                //是不是常量
                if (StringUtils.isEmpty(params.getName())) {
                    val = params.getConstValue();
                } else {
                    String tempStr = new String(params.getName());
                    if (isNumber(tempStr)) {
                        isNumber = true;
                        tempStr = tempStr.replaceFirst(NUMBER_SYMBOL, "");
                    }
                    map.put(teplateParams.getTempParams(), t);
                    obj = eval(tempStr, map);
                    val = obj.toString();
                }
                if (obj != null && obj instanceof ImageEntity) {
                    ImageEntity img = (ImageEntity) obj;
                    row.getCell(ci).setCellValue("");
                    if (img.getRowspan() > 1 || img.getColspan() > 1) {
                        img.setHeight(0);
                        row.getCell(ci).getSheet().addMergedRegion(new CellRangeAddress(row.getCell(ci).getRowIndex(),
                                row.getCell(ci).getRowIndex() + img.getRowspan() - 1, row.getCell(ci).getColumnIndex(), row.getCell(ci).getColumnIndex() + img.getColspan() - 1));
                    }
                    createImageCell(row.getCell(ci), img.getHeight(), img.getRowspan(), img.getColspan(), img.getUrl(), img.getData());
                } else if (isNumber && StringUtils.isNotEmpty(val)) {
                    row.getCell(ci).setCellValue(Double.parseDouble(val));
                } else if (obj != null && obj instanceof List) {
                    List<Object> list = (List) obj;
                    exchangeCell(row, ci, list.size(), 1);
                    for (int index = 0; index < list.size(); index++) {
                        if (list.get(index) != null) {
                            // 判断是否为数字（不能精准判断格子是否应该是数值 zdf）
                            if (NumberUtil.isNumber(list.get(index).toString())) {
                                row.getCell(ci + index).setCellValue(Double.parseDouble(list.get(index).toString()));
                            } else {
                                row.getCell(ci + index).setCellValue(list.get(index).toString());
                            }
                        } else {
                            row.getCell(ci + index).setCellValue("");
                        }
                        row.getCell(ci + index).setCellStyle(columns.get(oldCi).getCellStyle());
                    }
                    ci += list.size() - 1;
                } else {
                    try {
                        row.getCell(ci).setCellValue(val);
                    } catch (Exception e) {
                        LOGGER.error(e.getMessage(), e);
                    }
                }
                if (params.getCellStyle() != null) {
                    row.getCell(ci).setCellStyle(params.getCellStyle());
                }
                //判断这个属性是不是需要统计
                if (params.isNeedSum()) {
                    templateSumHandler.addValueOfKey(params.getName(), val);
                }
                //如果合并单元格,就把这个单元格的样式和之前的保持一致
                setMergedRegionStyle(row, ci, params);
                //合并对应单元格
                if ((params.getRowspan() != 1 || params.getColspan() != 1)
                        && !mergedRegionHelper.isMergedRegion(row.getRowNum() + 1, ci)
                        && PoiCellUtil.isMergedRegion(row.getSheet(), row.getRowNum(), ci)) {
                    PoiMergeCellUtil.addMergedRegion(row.getSheet(), row.getRowNum(),
                            row.getRowNum() + params.getRowspan() - 1, ci,
                            ci + params.getColspan() - 1);
                }
                ci = ci + params.getColspan();
                oldCi = oldCi + params.getColspan();
            }
            row = row.getSheet().getRow(row.getRowNum() + 1);
        }
    }

    private CellStyle getStyles(boolean isSingle, ExcelForEachParams excelForEachParams) {
        return excelExportStyler.getTemplateStyles(isSingle, excelForEachParams);
    }

    /**
     * 设置合并单元格的样式
     *
     * @param row
     * @param ci
     * @param params
     */
    private void setMergedRegionStyle(Row row, int ci, ExcelForEachParams params) {
        //第一行数据
        for (int i = 1; i < params.getColspan(); i++) {
            if (params.getCellStyle() != null) {
                row.getCell(ci + i).setCellStyle(params.getCellStyle());
            }
        }
        for (int i = 1; i < params.getRowspan(); i++) {
            for (int j = 0; j < params.getColspan(); j++) {
                if (params.getCellStyle() != null) {
                    row.getCell(ci + j).setCellStyle(params.getCellStyle());
                }
            }
        }
    }

    /**
     * 获取迭代的数据的值
     *
     * @param cell
     * @param name
     * @param mergedRegionHelper
     * @return
     */
    private Object[] getAllDataColumns(Cell cell, String name,
                                       MergedRegionHelper mergedRegionHelper) {
        List<ExcelForEachParams> columns = new ArrayList<ExcelForEachParams>();
        cell.setCellValue("");
        columns.add(getExcelTemplateParams(name.replace(END_STR, EMPTY), cell, mergedRegionHelper));
        int rowspan = 1, colspan = 1;
        if (!name.contains(END_STR)) {
            int index = cell.getColumnIndex();
            //保存col 的开始列
            int startIndex = cell.getColumnIndex();
            Row row = cell.getRow();
            while (index < row.getLastCellNum()) {
                int colSpan = columns.get(columns.size() - 1) != null
                        ? columns.get(columns.size() - 1).getColspan() : 1;
                index += colSpan;


                for (int i = 1; i < colSpan; i++) {
                    //添加合并的单元格,这些单元可能不是空,但是没有值,所以也需要跳过
                    columns.add(null);
                    continue;
                }
                cell = row.getCell(index);
                //可能是合并的单元格
                if (cell == null) {
                    //读取是判断,跳过
                    columns.add(null);
                    continue;
                }
                String cellStringString;
                try {//不允许为空 便利单元格必须有结尾和值
                    cellStringString = cell.getStringCellValue();
                    if (StringUtils.isBlank(cellStringString) && colspan + startIndex <= index) {
                        throw new ExcelExportException("for each 当中存在空字符串,请检查模板");
                    } else if (StringUtils.isBlank(cellStringString)
                            && colspan + startIndex > index) {
                        //读取是判断,跳过,数据为空,但是不是第一次读这一列,所以可以跳过
                        columns.add(new ExcelForEachParams(null, cell.getCellStyle(), (short) 0));
                        continue;
                    }
                } catch (Exception e) {
                    throw new ExcelExportException(ExcelExportEnum.TEMPLATE_ERROR, e);
                }
                //把读取过的cell 置为空
                cell.setCellValue("");
                if (cellStringString.contains(END_STR)) {
                    columns.add(getExcelTemplateParams(cellStringString.replace(END_STR, EMPTY),
                            cell, mergedRegionHelper));
                    //补全缺失的cell(合并单元格后面的)
                    int lastCellColspan = columns.get(columns.size() - 1).getColspan();
                    for (int i = 1; i < lastCellColspan; i++) {
                        //添加合并的单元格,这些单元可能不是空,但是没有值,所以也需要跳过
                        columns.add(null);
                    }
                    break;
                } else if (cellStringString.contains(WRAP)) {
                    columns.add(getExcelTemplateParams(cellStringString.replace(WRAP, EMPTY), cell,
                            mergedRegionHelper));
                    //发现换行符,执行换行操作
                    colspan = index - startIndex + 1;
                    index = startIndex - columns.get(columns.size() - 1).getColspan();
                    row = row.getSheet().getRow(row.getRowNum() + 1);
                    rowspan++;
                } else {
                    columns.add(getExcelTemplateParams(cellStringString.replace(WRAP, EMPTY), cell,
                            mergedRegionHelper));
                }
            }
        }
        colspan = 0;
        for (int i = 0; i < columns.size(); i++) {
            colspan += columns.get(i) != null ? columns.get(i).getColspan() : 0;
        }
        cellCount = colspan = colspan / rowspan;
        return new Object[]{rowspan, colspan, columns};
    }

    /**
     * 获取模板参数
     *
     * @param name
     * @param cell
     * @param mergedRegionHelper
     * @return
     */
    private ExcelForEachParams getExcelTemplateParams(String name, Cell cell,
                                                      MergedRegionHelper mergedRegionHelper) {
        name = name.trim();
        ExcelForEachParams params = new ExcelForEachParams(name, cell.getCellStyle(),
                cell.getRow().getHeight());
        //判断是不是常量
        if (name.startsWith(CONST) && name.endsWith(CONST)) {
            params.setName(null);
            params.setConstValue(name.substring(1, name.length() - 1));
        }
        //判断是不是空
        if (NULL.equals(name)) {
            params.setName(null);
            params.setConstValue(EMPTY);
        }
        //获取合并单元格的数据
        if (mergedRegionHelper.isMergedRegion(cell.getRowIndex() + 1, cell.getColumnIndex())) {
            Integer[] colAndrow = mergedRegionHelper.getRowAndColSpan(cell.getRowIndex() + 1,
                    cell.getColumnIndex());
            params.setRowspan(colAndrow[0]);
            params.setColspan(colAndrow[1]);
        }
        params.setNeedSum(templateSumHandler.isSumKey(params.getName()));
        return params;
    }

}
