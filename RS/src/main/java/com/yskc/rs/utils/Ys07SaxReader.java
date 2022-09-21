package com.yskc.rs.utils;

import cn.hutool.core.exceptions.DependencyException;
import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.poi.excel.sax.CellDataType;
import cn.hutool.poi.excel.sax.ExcelSaxReader;
import cn.hutool.poi.excel.sax.ExcelSaxUtil;
import cn.hutool.poi.excel.sax.SheetRidReader;
import cn.hutool.poi.exceptions.POIException;
import com.yskc.common.exception.OwnerException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.BuiltinFormats;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.model.SharedStringsTable;
import org.apache.poi.xssf.model.StylesTable;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.*;
import org.xml.sax.helpers.XMLReaderFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.utils
 * @Author: zhangdingfu
 * @CreateTime: 2019-12-26 15:29
 * @Description: 处理07版excel
 */
public class Ys07SaxReader implements ContentHandler, ExcelSaxReader<Ys07SaxReader> {

    // saxParser
    private static final String CLASS_SAXPARSER = "org.apache.xerces.parsers.SAXParser";
    /**
     * Cell单元格元素
     */
    private static final String C_ELEMENT = "c";
    /**
     * 行元素
     */
    private static final String ROW_ELEMENT = "row";
    /**
     * Cell中的行列号
     */
    private static final String R_ATTR = "r";
    /**
     * Cell类型
     */
    private static final String T_ELEMENT = "t";
    /**
     * SST（SharedStringsTable） 的索引
     */
    private static final String S_ATTR_VALUE = "s";
    // 列中属性值
    private static final String T_ATTR_VALUE = "t";
    // sheet r:Id前缀
    private static final String RID_PREFIX = "rId";

    // excel 2007 的共享字符串表,对应sharedString.xml
    private SharedStringsTable sharedStringsTable;
    // 当前行
    private int curRow;
    // 当前列
    private int curCell;
    // 上一次的内容
    private String lastContent;
    // 单元数据类型
    private CellDataType cellDataType;
    // 当前列坐标， 如A1，B5
    private String curCoordinate;
    // 前一个列的坐标
    private String preCoordinate;
    // 行的最大列坐标
    private String maxCellCoordinate;
    // 单元格的格式表，对应style.xml
    private StylesTable stylesTable;
    // 单元格存储格式的索引，对应style.xml中的numFmts元素的子元素索引
    private int numFmtIndex;
    // 单元格存储的格式化字符串，nmtFmt的formateCode属性的值
    private String numFmtString;
    // sheet的索引
    private int sheetIndex;

    private String sheetName;

    // 存储每行的列元素
    List<Object> rowCellList = new ArrayList<>();

    /**
     * 行处理器
     */
    private YsRowHandler ysRowHandler;

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 获取sheetName
     *
     * @return
     */
    public String getSheetName() {
        return sheetName;
    }

    /**
     * 构造
     *
     * @param ysRowHandler 行处理器
     */
    public Ys07SaxReader(YsRowHandler ysRowHandler) {
        this.ysRowHandler = ysRowHandler;
    }

    /**
     * 设置行处理器
     *
     * @param ysRowHandler 行处理器
     * @return this
     */
    public Ys07SaxReader setRowHandler(YsRowHandler ysRowHandler) {
        this.ysRowHandler = ysRowHandler;
        return this;
    }

    @Override
    public Ys07SaxReader read(File file, String idOrRidOrSheetName) throws POIException {
        try {
            return read(new FileInputStream(file), idOrRidOrSheetName);
        } catch (FileNotFoundException e) {
            throw new POIException(e);
        }
    }

    @Override
    public Ys07SaxReader read(InputStream in, String idOrRidOrSheetName) throws POIException {
        OPCPackage opcPackage = null;
        try {
            opcPackage = OPCPackage.open(in);
            final XSSFReader xssfReader = new XSSFReader(opcPackage);
            int sheetIndex = getSheetIndex(xssfReader, idOrRidOrSheetName);
            // 获取共享样式表
            stylesTable = xssfReader.getStylesTable();
            // 获取共享字符串表
            this.sharedStringsTable = xssfReader.getSharedStringsTable();

            if (sheetIndex > -1) {
                this.sheetIndex = sheetIndex;
                // 根据 rId# 或 rSheet# 查找sheet
                in = xssfReader.getSheet(RID_PREFIX + (sheetIndex + 1));
                parse(in);
            } else {
                this.sheetIndex = -1;
                // 遍历所有sheet
                final Iterator<InputStream> sheetInputStreams = xssfReader.getSheetsData();
                while (sheetInputStreams.hasNext()) {
                    // 重新读取一个sheet时行归零
                    curRow = 0;
                    this.sheetIndex++;
                    in = sheetInputStreams.next();
                    this.sheetName = ((XSSFReader.SheetIterator) sheetInputStreams).getSheetName();
                    parse(in);
                }
            }
        } catch (Exception e) {
            throw new POIException(e);
        } finally {
            IoUtil.close(in);
            IoUtil.close(opcPackage);
        }
        return this;
    }

    // ------------------------------------------------------------------------------ Read start
    @Override
    public Ys07SaxReader read(File file, int sheetIndex) throws POIException {
        try {
            return read(OPCPackage.open(file), sheetIndex);
        } catch (Exception e) {
            throw new POIException(e);
        }
    }

    @Override
    public Ys07SaxReader read(InputStream in, int sheetIndex) throws POIException {
        try {
            return read(OPCPackage.open(in), sheetIndex);
        } catch (DependencyException e) {
            throw new POIException(e);
        } catch (Exception e) {
            throw ExceptionUtil.wrap(e, POIException.class);
        }
    }

    /**
     * 开始读取Excel，Sheet编号从0开始计数
     *
     * @param opcPackage {@link OPCPackage}，Excel包
     * @param sheetIndex Excel中的sheet编号，如果为-1处理所有编号的sheet
     * @return this
     * @throws POIException POI异常
     */
    public Ys07SaxReader read(OPCPackage opcPackage, int sheetIndex) throws POIException {
        InputStream sheetInputStream = null;
        try {
            final XSSFReader xssfReader = new XSSFReader(opcPackage);

            // 获取共享样式表
            stylesTable = xssfReader.getStylesTable();
            // 获取共享字符串表
            this.sharedStringsTable = xssfReader.getSharedStringsTable();

            if (sheetIndex > -1) {
                this.sheetIndex = sheetIndex;
                // 根据 rId# 或 rSheet# 查找sheet
                sheetInputStream = xssfReader.getSheet(RID_PREFIX + (sheetIndex + 1));
                parse(sheetInputStream);
            } else {
                this.sheetIndex = -1;
                // 遍历所有sheet
                final Iterator<InputStream> sheetInputStreams = xssfReader.getSheetsData();
                while (sheetInputStreams.hasNext()) {
                    // 重新读取一个sheet时行归零
                    curRow = 0;
                    this.sheetIndex++;
                    sheetInputStream = sheetInputStreams.next();
                    this.sheetName = ((XSSFReader.SheetIterator) sheetInputStreams).getSheetName();
                    parse(sheetInputStream);
                }
            }
        } catch (DependencyException e) {
            throw new POIException(e);
        } catch (Exception e) {
            throw ExceptionUtil.wrap(e, POIException.class);
        } finally {
            IoUtil.close(sheetInputStream);
            IoUtil.close(opcPackage);
        }
        return this;
    }
    // ------------------------------------------------------------------------------ Read end

    /**
     * 读到一个xml开始标签时的回调处理方法
     */
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        // 单元格元素
        if (C_ELEMENT.equals(qName)) {

            // 获取当前列坐标
            String tempCurCoordinate = attributes.getValue(R_ATTR);
            // 前一列为null，则将其设置为"@",A为第一列，ascii码为65，前一列即为@，ascii码64
            if (preCoordinate == null) {
                preCoordinate = String.valueOf(ExcelSaxUtil.CELL_FILL_CHAR);
            } else {
                // 存在，则前一列要设置为上一列的坐标
                preCoordinate = curCoordinate;
            }
            // 重置当前列
            curCoordinate = tempCurCoordinate;
            // 设置单元格类型
            setCellType(attributes);
        }

        lastContent = "";
    }

    /**
     * 设置单元格的类型
     *
     * @param attribute
     */
    private void setCellType(Attributes attribute) {
        // 重置numFmtIndex,numFmtString的值
        numFmtIndex = 0;
        numFmtString = "";
        this.cellDataType = CellDataType.of(attribute.getValue(T_ATTR_VALUE));

        // 获取单元格的xf索引，对应style.xml中cellXfs的子元素xf
        final String xfIndexStr = attribute.getValue(S_ATTR_VALUE);
        if (xfIndexStr != null) {
            int xfIndex = Integer.parseInt(xfIndexStr);
            XSSFCellStyle xssfCellStyle = stylesTable.getStyleAt(xfIndex);
            numFmtIndex = xssfCellStyle.getDataFormat();
            numFmtString = xssfCellStyle.getDataFormatString();

            if (numFmtString == null) {
                cellDataType = CellDataType.NULL;
                numFmtString = BuiltinFormats.getBuiltinFormat(numFmtIndex);
            }
//            } else if (org.apache.poi.ss.usermodel.DateUtil.isADateFormat(numFmtIndex, numFmtString)) {
//                cellDataType = CellDataType.DATE;
//            }
        }

    }

    /**
     * 标签结束的回调处理方法
     */
    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        final String contentStr = StrUtil.trim(lastContent);
        if (T_ELEMENT.equals(qName)) {
            //type标签
            // rowCellList.add(curCell++, contentStr);
            ;
        } else if (C_ELEMENT.equals(qName)) {
            // cell标签
            Object value;
            if (curRow == 0) {
                value = ExcelSaxUtil.getDataValue(this.cellDataType, contentStr, this.sharedStringsTable, this.numFmtString);
            } else {
                CellDataType current = this.cellDataType;
                if (current == CellDataType.FORMULA) {
                    current = null;
                }
                value = ExcelSaxUtil.getDataValue(current, contentStr, this.sharedStringsTable, this.numFmtString);
            }
            // 补全单元格之间的空格
            fillBlankCell(preCoordinate, curCoordinate, false);
            rowCellList.add(curCell++, value);
        } else if (ROW_ELEMENT.equals(qName)) {
            // 如果是row标签，说明已经到了一行的结尾
            // 最大列坐标以第一行的为准
            if (curRow == 0) {
                maxCellCoordinate = curCoordinate;
            }

            // 补全一行尾部可能缺失的单元格
            if (maxCellCoordinate != null) {
                fillBlankCell(curCoordinate, maxCellCoordinate, true);
            }
            try {
                ysRowHandler.handle(sheetName, sheetIndex, curRow, rowCellList);
            } catch (OwnerException e) {
                throw new SAXException(e.getMsg());
            } catch (Exception e) {
                throw new SAXException("读取数据失败");
            }


            // 一行结束
            // 清空rowCellList,
            rowCellList.clear();
            // 行数增加
            curRow++;
            // 当前列置0
            curCell = 0;
            // 置空当前列坐标和前一列坐标
            curCoordinate = null;
            preCoordinate = null;
        }
    }

    /**
     * s标签结束的回调处理方法
     */
    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        // 得到单元格内容的值
        lastContent = lastContent.concat(new String(ch, start, length));
    }

    // --------------------------------------------------------------------------------------- Pass method start
    @Override
    public void setDocumentLocator(Locator locator) {
        // pass
    }

    /**
     * ?xml标签的回调处理方法
     */
    @Override
    public void startDocument() throws SAXException {
        // pass
    }

    @Override
    public void endDocument() throws SAXException {
        // pass
    }

    @Override
    public void startPrefixMapping(String prefix, String uri) throws SAXException {
        // pass
    }

    @Override
    public void endPrefixMapping(String prefix) throws SAXException {
        // pass
    }

    @Override
    public void ignorableWhitespace(char[] ch, int start, int length) throws SAXException {
        // pass
    }

    @Override
    public void processingInstruction(String target, String data) throws SAXException {
        // pass
    }

    @Override
    public void skippedEntity(String name) throws SAXException {
        // pass
    }
    // --------------------------------------------------------------------------------------- Pass method end

    // --------------------------------------------------------------------------------------- Private method start

    /**
     * 处理流中的Excel数据
     *
     * @param sheetInputStream sheet流
     * @throws IOException  IO异常
     * @throws SAXException SAX异常
     */
    private void parse(InputStream sheetInputStream) throws IOException, SAXException {
        fetchSheetReader().parse(new InputSource(sheetInputStream));
    }

    /**
     * 填充空白单元格，如果前一个单元格大于后一个，不需要填充<br>
     *
     * @param preCoordinate 前一个单元格坐标
     * @param curCoordinate 当前单元格坐标
     * @param isEnd         是否为最后一个单元格
     */
    private void fillBlankCell(String preCoordinate, String curCoordinate, boolean isEnd) {
        if (false == curCoordinate.equals(preCoordinate)) {
            int len = ExcelSaxUtil.countNullCell(preCoordinate, curCoordinate);
            if (isEnd) {
                len++;
            }
            while (len-- > 0) {
                rowCellList.add(curCell++, "");
            }
        }
    }

    /**
     * 获取sheet的解析器
     *
     * @return {@link XMLReader}
     * @throws SAXException SAX异常
     */
    private XMLReader fetchSheetReader() throws SAXException {
        XMLReader xmlReader = null;
        try {
            xmlReader = XMLReaderFactory.createXMLReader(CLASS_SAXPARSER);
        } catch (SAXException e) {
            if (e.getMessage().contains("org.apache.xerces.parsers.SAXParser")) {
                throw new DependencyException(e, "You need to add 'xerces:xercesImpl' to your project and version >= 2.11.0");
            } else {
                throw e;
            }
        }
        xmlReader.setContentHandler(this);
        return xmlReader;
    }

    // --------------------------------------------------------------------------------------- Private method end
    private int getSheetIndex(XSSFReader xssfReader, String idOrRidOrSheetName) {
        // rid直接处理
        if (StrUtil.startWithIgnoreCase(idOrRidOrSheetName, RID_PREFIX)) {
            return Integer.parseInt(StrUtil.removePrefixIgnoreCase(idOrRidOrSheetName, RID_PREFIX));
        }

        // sheetIndex需转换为rid
        final SheetRidReader ridReader = new SheetRidReader().read(xssfReader);

        if (StrUtil.startWithIgnoreCase(idOrRidOrSheetName, SHEET_NAME_PREFIX)) {
            // name:开头的被认为是sheet名称直接处理
            idOrRidOrSheetName = StrUtil.removePrefixIgnoreCase(idOrRidOrSheetName, SHEET_NAME_PREFIX);
            final Integer rid = ridReader.getRidByNameBase0(idOrRidOrSheetName);
            if (null != rid) {
                return rid;
            }
        } else {
            // 尝试查找名称
            Integer rid = ridReader.getRidByNameBase0(idOrRidOrSheetName);
            if (null != rid) {
                return rid;
            }

            try {
                final int sheetIndex = Integer.parseInt(idOrRidOrSheetName);
                rid = ridReader.getRidBySheetIdBase0(sheetIndex);
                // 如果查找不到对应index，则认为用户传入的直接是rid
                return ObjectUtil.defaultIfNull(rid, sheetIndex);
            } catch (NumberFormatException ignore) {
                // 非数字，说明非index，且没有对应名称，抛出异常
            }
        }

        throw new IllegalArgumentException("Invalid rId or id or sheetName: " + idOrRidOrSheetName);
    }
}
