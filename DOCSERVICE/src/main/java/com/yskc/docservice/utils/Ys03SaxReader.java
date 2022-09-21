package com.yskc.docservice.utils;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import cn.hutool.poi.excel.sax.ExcelSaxReader;
import cn.hutool.poi.exceptions.POIException;
import com.yskc.common.exception.OwnerException;
import org.apache.poi.hssf.eventusermodel.*;
import org.apache.poi.hssf.eventusermodel.dummyrecord.LastCellOfRowDummyRecord;
import org.apache.poi.hssf.eventusermodel.dummyrecord.MissingCellDummyRecord;
import org.apache.poi.hssf.model.HSSFFormulaParser;
import org.apache.poi.hssf.record.*;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.utils
 * @Author: zhangdingfu
 * @CreateTime: 2019-12-26 15:04
 * @Description: 处理03版excel
 */
public class Ys03SaxReader implements HSSFListener, ExcelSaxReader<Ys03SaxReader> {
    /**
     * 如果为公式，true表示输出公式计算后的结果值，false表示输出公式本身
     */
    private boolean isOutputFormulaValues = true;

    /**
     * 用于解析公式
     */
    private EventWorkbookBuilder.SheetRecordCollectingListener workbookBuildingListener;
    /**
     * 子工作簿，用于公式计算
     */
    private HSSFWorkbook stubWorkbook;

    /**
     * 静态字符串表
     */
    private SSTRecord sstRecord;

    private FormatTrackingHSSFListener formatListener;

    /**
     * Sheet边界记录，此Record中可以获得Sheet名
     */
    private List<BoundSheetRecord> boundSheetRecords = new ArrayList<>();

    private boolean isOutputNextStringRecord;

    // 存储行记录的容器
    private List<Object> rowCellList = new ArrayList<>();

    /**
     * 自定义需要处理的sheet编号，如果-1表示处理所有sheet
     */
    private int sheetIndex = -1;
    // 当前表索引
    private int curSheetIndex = -1;

    private YsRowHandler ysRowHandler;

    private int currentRow = 0;

    private String sheetName;

    /**
     * 构造
     *
     * @param ysRowHandler 行处理器
     */
    public Ys03SaxReader(YsRowHandler ysRowHandler) {
        this.ysRowHandler = ysRowHandler;
    }

    @Override
    public Ys03SaxReader read(File file, String idOrRidOrSheetName) throws POIException {
        return read(file, getSheetIndex(idOrRidOrSheetName));
    }

    @Override
    public Ys03SaxReader read(InputStream in, String idOrRidOrSheetName) throws POIException {
        return read(in, getSheetIndex(idOrRidOrSheetName));
    }


    @Override
    public Ys03SaxReader read(File file, int sheetIndex) throws POIException {
        try {
            return read(new POIFSFileSystem(file), sheetIndex);
        } catch (IOException e) {
            throw new POIException("文件格式/内容不对，请检查!");
        }
    }

    @Override
    public Ys03SaxReader read(InputStream excelStream, int sheetIndex) throws POIException {
        try {
            return read(new POIFSFileSystem(excelStream), sheetIndex);
        } catch (IOException e) {
            throw new POIException("文件格式/内容不对，请检查!");
        }
    }

    /**
     * 读取
     *
     * @param fs         {@link POIFSFileSystem}
     * @param sheetIndex sheet序号
     * @return this
     * @throws POIException IO异常包装
     */
    public Ys03SaxReader read(POIFSFileSystem fs, int sheetIndex) throws POIException {
        this.sheetIndex = sheetIndex;

        formatListener = new FormatTrackingHSSFListener(new MissingRecordAwareHSSFListener(this));
        final HSSFRequest request = new HSSFRequest();
        if (isOutputFormulaValues) {
            request.addListenerForAllRecords(formatListener);
        } else {
            workbookBuildingListener = new EventWorkbookBuilder.SheetRecordCollectingListener(formatListener);
            request.addListenerForAllRecords(workbookBuildingListener);
        }
        final HSSFEventFactory factory = new HSSFEventFactory();
        try {
            factory.processWorkbookEvents(request, fs);
        } catch (IOException e) {
            throw new POIException("文件格式/内容不对，请检查!");
        } finally {
            IoUtil.close(fs);
        }
        return this;
    }
    // ------------------------------------------------------------------------------ Read end

    /**
     * 获得Sheet序号，如果处理所有sheet，获得最大的Sheet序号，从0开始
     *
     * @return sheet序号
     */
    public int getSheetIndex() {
        return this.sheetIndex;
    }

    /**
     * 获得Sheet名，如果处理所有sheet，获得后一个Sheet名，从0开始
     *
     * @return Sheet名
     */
    public String getSheetName() {
        if (this.boundSheetRecords.size() > this.sheetIndex) {
            return this.boundSheetRecords.get(this.sheetIndex > -1 ? this.sheetIndex : this.curSheetIndex).getSheetname();
        }
        return null;
    }

    /**
     * HSSFListener 监听方法，处理 Record
     *
     * @param record 记录
     */
    @Override
    public void processRecord(Record record) {
        if (this.sheetIndex > -1 && this.curSheetIndex > this.sheetIndex) {
            // 指定Sheet之后的数据不再处理
            return;
        }

        if (record instanceof BoundSheetRecord) {
            // Sheet边界记录，此Record中可以获得Sheet名
            boundSheetRecords.add((BoundSheetRecord) record);
        } else if (record instanceof SSTRecord) {
            // 静态字符串表
            sstRecord = (SSTRecord) record;
        } else if (record instanceof BOFRecord) {
            BOFRecord bofRecord = (BOFRecord) record;
            if (bofRecord.getType() == BOFRecord.TYPE_WORKSHEET) {
                // 如果有需要，则建立子工作薄
                if (workbookBuildingListener != null && stubWorkbook == null) {
                    stubWorkbook = workbookBuildingListener.getStubHSSFWorkbook();
                }
                curSheetIndex++;
                currentRow = 0;

            }
        } else if (isProcessCurrentSheet()) {
            if (record instanceof MissingCellDummyRecord) {
                // 空值的操作
                MissingCellDummyRecord mc = (MissingCellDummyRecord) record;
                rowCellList.add(mc.getColumn(), StrUtil.EMPTY);
            } else if (record instanceof LastCellOfRowDummyRecord) {
                // 行结束
                processLastCell((LastCellOfRowDummyRecord) record);

            } else {
                // 处理单元格值
                processCellValue(record);
            }
        }
    }

    // ---------------------------------------------------------------------------------------------- Private method start

    /**
     * 处理单元格值
     *
     * @param record 单元格
     */
    private void processCellValue(Record record) {
//        if(currentRow == 0){
//            LabelSSTRecord str = (LabelSSTRecord)record;
//            rowCellList.add(str.getColumn(), str.toString());
//        }
        Object value = null;
        switch (record.getSid()) {
            case BlankRecord.sid:
                // 空白记录
                BlankRecord brec = (BlankRecord) record;
                rowCellList.add(brec.getColumn(), StrUtil.EMPTY);
                break;
            // 布尔类型
            case BoolErrRecord.sid:
                BoolErrRecord berec = (BoolErrRecord) record;
                rowCellList.add(berec.getColumn(), berec.getBooleanValue());
                break;
            // 公式类型
            case FormulaRecord.sid:
                FormulaRecord frec = (FormulaRecord) record;
                if (isOutputFormulaValues) {
                    if (Double.isNaN(frec.getValue())) {
                        // Formula result is a string
                        // This is stored in the next record
                        isOutputNextStringRecord = true;
                    } else {
                        value = formatListener.formatNumberDateCell(frec);
                    }
                } else {
                    value = '"' + HSSFFormulaParser.toFormulaString(stubWorkbook, frec.getParsedExpression()) + '"';
                }
                rowCellList.add(frec.getColumn(), value);
                break;
            // 单元格中公式的字符串
            case StringRecord.sid:
                if (isOutputNextStringRecord) {
                    // String for formula
                    StringRecord srec = (StringRecord) record;
                    value = srec.getString();
                    isOutputNextStringRecord = false;
                }
                break;
            case LabelRecord.sid:
                LabelRecord lrec = (LabelRecord) record;
                this.rowCellList.add(lrec.getColumn(), value);
                break;
            // 字符串类型
            case LabelSSTRecord.sid:
                LabelSSTRecord lsrec = (LabelSSTRecord) record;
                if (sstRecord == null) {
                    rowCellList.add(lsrec.getColumn(), StrUtil.EMPTY);
                } else {
                    value = sstRecord.getString(lsrec.getSSTIndex()).toString();
                    rowCellList.add(lsrec.getColumn(), value);
                }
                break;
            // 数字类型
            case NumberRecord.sid:
                NumberRecord numrec = (NumberRecord) record;

                final String formatString = formatListener.getFormatString(numrec);
                if (formatString.contains(StrUtil.DOT)) {
                    //浮点数
                    value = numrec.getValue();

//                } else if (formatString.contains(StrUtil.SLASH) || formatString.contains(StrUtil.COLON)) {
//                    //日期
//                    value = DateUtil.getJavaDate(numrec.getValue());
//
//                }
                } else {
                    double numValue = numrec.getValue();
                    final long longPart = (long) numValue;
                    // 对于无小数部分的数字类型，转为Long，否则保留原数字
                    if (longPart == numValue) {
                        value = longPart;
                    } else {
                        value = numValue;
                    }
                }

                // 向容器加入列值
                rowCellList.add(numrec.getColumn(), value);
                break;
            default:
                break;
        }
    }

    /**
     * 处理行结束后的操作，{@link LastCellOfRowDummyRecord}是行结束的标识Record
     *
     * @param lastCell 行结束的标识Record
     */
    private void processLastCell(LastCellOfRowDummyRecord lastCell) throws POIException {
        // 每行结束时， 调用handle() 方法
        try {
            this.ysRowHandler.handle(getSheetName(), curSheetIndex, currentRow, this.rowCellList);
            currentRow++;
            if(this.ysRowHandler.getClose()){
                throw new OwnerException(ImportExcelUtils.EXIT);
            }
        } catch (OwnerException e) {
            throw new POIException(e.getMsg());
        } catch (Exception e) {
            throw new POIException("读取数据出错");
        } finally {
            // 清空行Cache
            this.rowCellList.clear();
        }
    }

    /**
     * 是否处理当前sheet
     *
     * @return 是否处理当前sheet
     */
    private boolean isProcessCurrentSheet() {
        return this.sheetIndex < 0 || this.curSheetIndex == this.sheetIndex;
    }

    private int getSheetIndex(String idOrRidOrSheetName) {
        Assert.notBlank(idOrRidOrSheetName, "id or rid or sheetName must be not blank!");

        // rid直接处理
        if (StrUtil.startWithIgnoreCase(idOrRidOrSheetName, RID_PREFIX)) {
            return Integer.parseInt(StrUtil.removePrefixIgnoreCase(idOrRidOrSheetName, RID_PREFIX));
        } else if(StrUtil.startWithIgnoreCase(idOrRidOrSheetName, SHEET_NAME_PREFIX)){
            // since 5.7.10，支持任意名称
            this.sheetName = StrUtil.removePrefixIgnoreCase(idOrRidOrSheetName, SHEET_NAME_PREFIX);
        } else {
            try {
                return Integer.parseInt(idOrRidOrSheetName);
            } catch (NumberFormatException ignore) {
                // 如果用于传入非数字，按照sheet名称对待
                this.sheetName = idOrRidOrSheetName;
            }
        }
        return -1;
    }
}
