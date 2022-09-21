/*
 * @Description: xlsx转换 
 * @Author: huronghua
 * @LastEditors: huronghua
 * @LastEditTime: 2019-09-29 09:58:50
 */
package com.yskc.ms.utils;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;

/**
 * @author huronghua
 */
public class XlsxTransform {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private int lastColumn = 0; 
    private HashMap<Integer, HSSFCellStyle> styleMap = new HashMap();

    /**
     *
     * @param workbookOld
     * @param workbookNew
     */
    public void transformXSSF(XSSFWorkbook workbookOld, HSSFWorkbook workbookNew) {  
        HSSFSheet sheetNew; 
        XSSFSheet sheetOld;
        // 激活公式
        workbookNew.setForceFormulaRecalculation(true);
        workbookOld.setForceFormulaRecalculation(true);
        workbookNew.setMissingCellPolicy(workbookOld.getMissingCellPolicy()); 
   
        for (int i = 0; i < workbookOld.getNumberOfSheets(); i++) { 
            sheetOld = workbookOld.getSheetAt(i);
            sheetNew = workbookNew.createSheet(sheetOld.getSheetName()); 
            this.transform(workbookOld, workbookNew, sheetOld, sheetNew); 
        }   
    }

    /**
     *
     * @param workbookOld
     * @param workbookNew
     * @param sheetOld
     * @param sheetNew
     */
    private void transform(XSSFWorkbook workbookOld, HSSFWorkbook workbookNew, 
            XSSFSheet sheetOld, HSSFSheet sheetNew) { 
   
        sheetNew.setDisplayFormulas(sheetOld.isDisplayFormulas()); 
        sheetNew.setDisplayGridlines(sheetOld.isDisplayGridlines()); 
        sheetNew.setDisplayGuts(sheetOld.getDisplayGuts()); 
        sheetNew.setDisplayRowColHeadings(sheetOld.isDisplayRowColHeadings()); 
        sheetNew.setDisplayZeros(sheetOld.isDisplayZeros()); 
        sheetNew.setFitToPage(sheetOld.getFitToPage()); 
         
        sheetNew.setHorizontallyCenter(sheetOld.getHorizontallyCenter()); 
        sheetNew.setMargin(Sheet.BottomMargin, 
                sheetOld.getMargin(Sheet.BottomMargin)); 
        sheetNew.setMargin(Sheet.FooterMargin, 
                sheetOld.getMargin(Sheet.FooterMargin)); 
        sheetNew.setMargin(Sheet.HeaderMargin, 
                sheetOld.getMargin(Sheet.HeaderMargin)); 
        sheetNew.setMargin(Sheet.LeftMargin, 
                sheetOld.getMargin(Sheet.LeftMargin)); 
        sheetNew.setMargin(Sheet.RightMargin, 
                sheetOld.getMargin(Sheet.RightMargin)); 
        sheetNew.setMargin(Sheet.TopMargin, sheetOld.getMargin(Sheet.TopMargin)); 
        sheetNew.setPrintGridlines(sheetNew.isPrintGridlines()); 
        sheetNew.setRightToLeft(sheetNew.isRightToLeft()); 
        sheetNew.setRowSumsBelow(sheetNew.getRowSumsBelow()); 
        sheetNew.setRowSumsRight(sheetNew.getRowSumsRight()); 
        sheetNew.setVerticallyCenter(sheetOld.getVerticallyCenter()); 
   
        HSSFRow rowNew; 
        for (Row row : sheetOld) {
            int rowNumber = row.getRowNum();
            if (rowNumber>65535)
                break;
            rowNew = sheetNew.createRow(rowNumber);
            if (rowNew != null) {
                this.transform(workbookOld, workbookNew, (XSSFRow) row, rowNew);
            }
        } 
   
        for (int i = 0; i < this.lastColumn; i++) { 
            sheetNew.setColumnWidth(i, sheetOld.getColumnWidth(i)); 
            sheetNew.setColumnHidden(i, sheetOld.isColumnHidden(i)); 
        } 
   
        for (int i = 0; i < sheetOld.getNumMergedRegions(); i++) { 
            CellRangeAddress merged = sheetOld.getMergedRegion(i); 
            sheetNew.addMergedRegion(merged); 
        } 
    }

    /**
     *
     * @param workbookOld
     * @param workbookNew
     * @param rowOld
     * @param rowNew
     */
    private void transform(XSSFWorkbook workbookOld, HSSFWorkbook workbookNew, 
            XSSFRow rowOld, HSSFRow rowNew) {
        HSSFCell cellNew;
        rowNew.setHeight(rowOld.getHeight());
        int rowCount = rowOld.getLastCellNum();
        for (int i = 0; i < rowCount; i++) {
            if (i >= 256) {
                break;
            }
            Cell cell = rowOld.getCell(i);
            if (rowNew != null && cell != null) {
                cellNew = rowNew.createCell(cell.getColumnIndex(),
                        cell.getCellType());
                if (cellNew != null) {
                    this.transform(workbookOld, workbookNew, (XSSFCell) cell,
                            cellNew);
                }
            }

        }
        this.lastColumn = Math.max(this.lastColumn, rowOld.getLastCellNum());
    }

    /**
     *
     * @param workbookOld
     * @param workbookNew
     * @param cellOld
     * @param cellNew
     */
    private void transform(XSSFWorkbook workbookOld, HSSFWorkbook workbookNew, 
            XSSFCell cellOld, HSSFCell cellNew) {

        try {
            cellNew.setCellComment(cellOld.getCellComment());

            Integer hash = cellOld.getCellStyle().hashCode();
            if (this.styleMap != null && !this.styleMap.containsKey(hash)) {
                this.transform(workbookOld, workbookNew, hash,
                        cellOld.getCellStyle(),
                        (HSSFCellStyle) workbookNew.createCellStyle());
            }
            cellNew.setCellStyle(this.styleMap.get(hash));

            switch (cellOld.getCellType()) {
                case BLANK:
                    break;
                case BOOLEAN:
                    cellNew.setCellValue(cellOld.getBooleanCellValue());
                    break;
                case ERROR:
                    cellNew.setCellValue(cellOld.getErrorCellValue());
                    break;
                case FORMULA:
                    cellNew.setCellValue(cellOld.getCellFormula());
                    break;
                case NUMERIC:
                    cellNew.setCellValue(cellOld.getNumericCellValue());
                    break;
                case STRING:
                    cellNew.setCellValue(cellOld.getStringCellValue());
                    break;
                default:
                    logger.info("transform: Unbekannter Zellentyp "
                            + cellOld.getCellType());
            }
        } catch (Exception ex) {
            logger.error("transform",ex);
        }
    }

    /**
     *
     * @param workbookOld
     * @param workbookNew
     * @param hash
     * @param styleOld
     * @param styleNew
     */
    private void transform(XSSFWorkbook workbookOld, HSSFWorkbook workbookNew, 
            Integer hash, XSSFCellStyle styleOld, HSSFCellStyle styleNew) {
        styleNew.setAlignment(styleOld.getAlignment());
        styleNew.setBorderBottom(styleOld.getBorderBottom()); 
        styleNew.setBorderLeft(styleOld.getBorderLeft()); 
        styleNew.setBorderRight(styleOld.getBorderRight()); 
        styleNew.setBorderTop(styleOld.getBorderTop()); 
        styleNew.setDataFormat(this.transform(workbookOld, workbookNew, 
                styleOld.getDataFormat())); 
        styleNew.setFillBackgroundColor(styleOld.getFillBackgroundColor()); 
        styleNew.setFillForegroundColor(styleOld.getFillForegroundColor()); 
        styleNew.setFillPattern(styleOld.getFillPattern()); 
        styleNew.setFont(this.transform(workbookNew, 
                (XSSFFont) styleOld.getFont())); 
        styleNew.setHidden(styleOld.getHidden()); 
        styleNew.setIndention(styleOld.getIndention()); 
        styleNew.setLocked(styleOld.getLocked()); 
        styleNew.setVerticalAlignment(styleOld.getVerticalAlignment()); 
        styleNew.setWrapText(styleOld.getWrapText()); 
        this.styleMap.put(hash, styleNew); 
    }

    /**
     *
     * @param workbookOld
     * @param workbookNew
     * @param index
     * @return
     */
    private short transform(XSSFWorkbook workbookOld, HSSFWorkbook workbookNew, 
            short index) {
        DataFormat formatOld = workbookOld.createDataFormat(); 
        DataFormat formatNew = workbookNew.createDataFormat();
        String format = formatOld.getFormat(index);
        if(null == format){
            return 0;
        }
        return formatNew.getFormat(format);
    }

    /**
     *
     * @param workbookNew
     * @param fontOld
     * @return
     */
    private HSSFFont transform(HSSFWorkbook workbookNew, XSSFFont fontOld) { 
        HSSFFont fontNew = workbookNew.createFont();
        fontNew.setBold(fontOld.getBold());
        fontNew.setCharSet(fontOld.getCharSet()); 
        fontNew.setColor(fontOld.getColor()); 
        fontNew.setFontName(fontOld.getFontName()); 
        fontNew.setFontHeight(fontOld.getFontHeight()); 
        fontNew.setItalic(fontOld.getItalic()); 
        fontNew.setStrikeout(fontOld.getStrikeout()); 
        fontNew.setTypeOffset(fontOld.getTypeOffset()); 
        fontNew.setUnderline(fontOld.getUnderline()); 
        return fontNew; 
    }  
}