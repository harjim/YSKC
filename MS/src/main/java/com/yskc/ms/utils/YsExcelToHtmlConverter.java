package com.yskc.ms.utils;

import org.apache.poi.hpsf.SummaryInformation;
import org.apache.poi.hssf.converter.AbstractExcelConverter;
import org.apache.poi.hssf.converter.ExcelToHtmlUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.hwpf.converter.HtmlDocumentFacade;
import org.apache.poi.ss.formula.eval.ErrorEval;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.util.Beta;
import org.apache.poi.util.POILogFactory;
import org.apache.poi.util.POILogger;
import org.apache.poi.util.XMLHelper;
import org.springframework.util.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Converts xls files (97-2007) to HTML file.
 *
 * @author Sergey Vladimirov (vlsergey {at} gmail {dot} com)
 */
@Beta
public class YsExcelToHtmlConverter extends AbstractExcelConverter {
    private static final POILogger LOGGER = POILogFactory
            .getLogger( org.apache.poi.hssf.converter.ExcelToHtmlConverter.class );
    private String cssClassContainerCell;

    private String cssClassContainerDiv;

    private String cssClassPrefixCell = "c";

    private String cssClassPrefixDiv = "d";

    private String cssClassPrefixRow = "r";

    private String cssClassPrefixTable = "t";

    private Map<Short, String> excelStyleToClass = new LinkedHashMap<>();

    private final HtmlDocumentFacade htmlDocumentFacade;

    private boolean useDivsToSpan;

    /**
     *
     * @param doc
     */
    public YsExcelToHtmlConverter(Document doc )
    {
        htmlDocumentFacade = new HtmlDocumentFacade( doc );
    }

    /**
     *
     * @param htmlDocumentFacade
     */
    public YsExcelToHtmlConverter(HtmlDocumentFacade htmlDocumentFacade ) {
        this.htmlDocumentFacade = htmlDocumentFacade;
    }



    /**
     * Converts Excel file (97-2007) into HTML file.
     *
     * @param xlsFile
     *            workbook file to process
     * @return DOM representation of result HTML
     * @throws IOException If an error occurs reading or writing files
     * @throws ParserConfigurationException If configuration is incorrect
     */
    public static Document process( File xlsFile ) throws IOException, ParserConfigurationException {
        try (HSSFWorkbook workbook = ExcelToHtmlUtils.loadXls(xlsFile)) {
            return org.apache.poi.hssf.converter.ExcelToHtmlConverter.process(workbook);
        }
    }

    /**
     * Converts Excel file (97-2007) into HTML file.
     *
     * @param xlsStream workbook stream to process
     * @return DOM representation of result HTML
     * @throws IOException If an error occurs reading or writing files
     * @throws ParserConfigurationException If configuration is incorrect
     */
    public static Document process( InputStream xlsStream ) throws IOException, ParserConfigurationException {
        try (HSSFWorkbook workbook = new HSSFWorkbook(xlsStream)) {
            return org.apache.poi.hssf.converter.ExcelToHtmlConverter.process(workbook);
        }
    }

    /**
     * Converts Excel file (97-2007) into HTML file.
     *
     * @param workbook workbook instance to process
     * @return DOM representation of result HTML
     * @throws IOException If an error occurs reading or writing files
     * @throws ParserConfigurationException If configuration is incorrect
     */
    public static Document process( HSSFWorkbook workbook ) throws IOException, ParserConfigurationException {
        org.apache.poi.hssf.converter.ExcelToHtmlConverter excelToHtmlConverter = new org.apache.poi.hssf.converter.ExcelToHtmlConverter(
                XMLHelper.getDocumentBuilderFactory().newDocumentBuilder()
                        .newDocument() );
        excelToHtmlConverter.processWorkbook( workbook );
        return excelToHtmlConverter.getDocument();
    }

    /**
     *
     * @param workbook
     * @param cellStyle
     * @return
     */
    protected String buildStyle( HSSFWorkbook workbook, HSSFCellStyle cellStyle ) {
        StringBuilder style = new StringBuilder();

        style.append( "white-space:pre-wrap;" );
        ExcelToHtmlUtils.appendAlign( style, cellStyle.getAlignment() );

        switch (cellStyle.getFillPattern()) {
            // no fill
            case NO_FILL: break;
            case SOLID_FOREGROUND:
                final HSSFColor foregroundColor = cellStyle.getFillForegroundColorColor();
                if ( foregroundColor == null ) {break;}
                String fgCol = ExcelToHtmlUtils.getColor( foregroundColor );
                style.append("background-color:").append(fgCol).append(";");
                break;
            default:
                final HSSFColor backgroundColor = cellStyle.getFillBackgroundColorColor();
                if ( backgroundColor == null ) {break;}
                String bgCol = ExcelToHtmlUtils.getColor( backgroundColor );
                style.append("background-color:").append(bgCol).append(";");
                break;
        }

        buildStyleBorder( workbook, style, "top", cellStyle.getBorderTop(),
                cellStyle.getTopBorderColor() );
        buildStyleBorder( workbook, style, "right",
                cellStyle.getBorderRight(), cellStyle.getRightBorderColor() );
        buildStyleBorder( workbook, style, "bottom",
                cellStyle.getBorderBottom(), cellStyle.getBottomBorderColor() );
        buildStyleBorder( workbook, style, "left", cellStyle.getBorderLeft(),
                cellStyle.getLeftBorderColor() );

        HSSFFont font = cellStyle.getFont( workbook );
        buildStyleFont( workbook, style, font );

        return style.toString();
    }

    /**
     *
     * @param workbook
     * @param style
     * @param type
     * @param xlsBorder
     * @param borderColor
     */
    private void buildStyleBorder(HSSFWorkbook workbook, StringBuilder style,
                                  String type, BorderStyle xlsBorder, short borderColor ) {
        if ( xlsBorder == BorderStyle.NONE ) {
            return;
        }

        StringBuilder borderStyle = new StringBuilder();
        borderStyle.append( ExcelToHtmlUtils.getBorderWidth( xlsBorder ) );
        borderStyle.append( ' ' );
        borderStyle.append( ExcelToHtmlUtils.getBorderStyle( xlsBorder ) );

        final HSSFColor color = workbook.getCustomPalette().getColor(
                borderColor );
        if ( color != null )
        {
            borderStyle.append( ' ' );
            borderStyle.append( ExcelToHtmlUtils.getColor( color ) );
        }

        style.append("border-").append(type).append(":").append(borderStyle).append(";");
    }

    /**
     *
     * @param workbook
     * @param style
     * @param font
     */
    void buildStyleFont(HSSFWorkbook workbook, StringBuilder style,
                        HSSFFont font ) {
        if ( font.getBold() )
        {
            style.append( "font-weight:bold;" );
        }

        final HSSFColor fontColor = workbook.getCustomPalette().getColor(
                font.getColor() );
        if ( fontColor != null )
        {   style.append("color: ").append(ExcelToHtmlUtils.getColor(fontColor)).append("; ");}

        if ( font.getFontHeightInPoints() != 0 )
        {    style.append("font-size:").append(font.getFontHeightInPoints()).append("pt;");}

        if ( font.getItalic() )
        {
            style.append( "font-style:italic;" );
        }
    }

    /**
     *
     * @return
     */
    public String getCssClassPrefixCell()
    {
        return cssClassPrefixCell;
    }

    /**
     *
     * @return
     */
    public String getCssClassPrefixDiv()
    {
        return cssClassPrefixDiv;
    }

    /**
     *
     * @return
     */
    public String getCssClassPrefixRow()
    {
        return cssClassPrefixRow;
    }

    /**
     *
     * @return
     */
    public String getCssClassPrefixTable()
    {
        return cssClassPrefixTable;
    }

    /**
     *
     * @return
     */
    @Override
    public Document getDocument() {
        return htmlDocumentFacade.getDocument();
    }

    /**
     *
     * @param workbook
     * @param cellStyle
     * @return
     */
    protected String getStyleClassName( HSSFWorkbook workbook,
                                        HSSFCellStyle cellStyle ) {
        final Short cellStyleKey = Short.valueOf( cellStyle.getIndex() );

        String knownClass = excelStyleToClass.get( cellStyleKey );
        if ( knownClass != null )
        {   return knownClass;}

        String cssStyle = buildStyle( workbook, cellStyle );
        String cssClass = htmlDocumentFacade.getOrCreateCssClass(
                cssClassPrefixCell, cssStyle );
        excelStyleToClass.put( cellStyleKey, cssClass );
        return cssClass;
    }

    /**
     *
     * @return
     */
    public boolean isUseDivsToSpan()
    {
        return useDivsToSpan;
    }

    /**
     *
     * @param cell
     * @param tableCellElement
     * @param normalWidthPx
     * @param maxSpannedWidthPx
     * @param normalHeightPt
     * @return
     */
    protected boolean processCell(HSSFCell cell, Element tableCellElement,
                                  int normalWidthPx, int maxSpannedWidthPx, float normalHeightPt ) {
        final HSSFCellStyle cellStyle = cell.getCellStyle();
        String value;
        switch ( cell.getCellType() ) {
            case STRING:
                HSSFRichTextString str = cell.getRichStringCellValue();
                // XXX: enrich
                if ( str != null && str.length() > 0 )
                {
                    value =  str.toString() ;
                }
                else
                {
                    value = "";
                }
                break;
            case FORMULA:
                switch ( cell.getCachedFormulaResultType() ) {
                    case STRING:
                        HSSFRichTextString str1 = cell.getRichStringCellValue();
                        if ( str1 != null && str1.length() > 0 )
                        {
                            value = ( str1.toString() );
                        }
                        else
                        {
                            value = "";
                        }
                        break;
                    case NUMERIC:
                        double nValue = cell.getNumericCellValue();
                        short df = cellStyle.getDataFormat();
                        String dfs = cellStyle.getDataFormatString();
                        value = _formatter.formatRawCellContents(nValue, df, dfs);
                        break;
                    case BOOLEAN:
                        value = String.valueOf( cell.getBooleanCellValue() );
                        break;
                    case ERROR:
                        value = ErrorEval.getText( cell.getErrorCellValue() );
                        break;
                    default:
                        LOGGER.log(
                                POILogger.WARN,
                                "Unexpected cell cachedFormulaResultType ("
                                        + cell.getCachedFormulaResultType() + ")" );
                        value = "";
                        break;
                }
                break;
            case BLANK:
                value = "";
                break;
            case NUMERIC:
                value = _formatter.formatCellValue( cell );
                break;
            case BOOLEAN:
                value = String.valueOf( cell.getBooleanCellValue() );
                break;
            case ERROR:
                value = ErrorEval.getText( cell.getErrorCellValue() );
                break;
            default:
                LOGGER.log( POILogger.WARN,
                        "Unexpected cell type (" + cell.getCellType() + ")" );
                return true;
        }

        final boolean noText = StringUtils.isEmpty( value );
        final boolean wrapInDivs = !noText && isUseDivsToSpan() && !cellStyle.getWrapText();

        if ( cellStyle.getIndex() != 0 ) {
            @SuppressWarnings("resource")
            HSSFWorkbook workbook = cell.getRow().getSheet().getWorkbook();
            String mainCssClass = getStyleClassName( workbook, cellStyle );

            if ( wrapInDivs ) {
                tableCellElement.setAttribute( "class", mainCssClass + " "
                        + cssClassContainerCell );
            } else {
                tableCellElement.setAttribute( "class", mainCssClass );
            }

            if ( noText ) {
                /*
                 * if cell style is defined (like borders, etc.) but cell text
                 * is empty, add "&nbsp;" to output, so browser won't collapse
                 * and ignore cell
                 */
                value = "\u00A0";
            }
        }

        if ( isOutputLeadingSpacesAsNonBreaking() && value.startsWith( " " ) ) {
            StringBuilder builder = new StringBuilder();
            for ( int c = 0; c < value.length(); c++ )
            {
                if ( value.charAt( c ) != ' ' )
                {   break;}
                builder.append( '\u00a0' );
            }

            if ( value.length() != builder.length() )
            {   builder.append( value.substring( builder.length() ) );}

            value = builder.toString();
        }

        Text text = htmlDocumentFacade.createText( value );

        if ( wrapInDivs ) {
            Element outerDiv = htmlDocumentFacade.createBlock();
            outerDiv.setAttribute( "class", this.cssClassContainerDiv );

            Element innerDiv = htmlDocumentFacade.createBlock();
            StringBuilder innerDivStyle = new StringBuilder();
            innerDivStyle.append( "position:absolute;min-width:" );
            innerDivStyle.append( normalWidthPx );
            innerDivStyle.append( "px;" );
            if ( maxSpannedWidthPx != Integer.MAX_VALUE ) {
                innerDivStyle.append( "max-width:" );
                innerDivStyle.append( maxSpannedWidthPx );
                innerDivStyle.append( "px;" );
            }
            innerDivStyle.append( "overflow:hidden;max-height:" );
            innerDivStyle.append( normalHeightPt );
            innerDivStyle.append( "pt;white-space:nowrap;" );
            ExcelToHtmlUtils.appendAlign( innerDivStyle, cellStyle.getAlignment() );
            htmlDocumentFacade.addStyleClass( outerDiv, cssClassPrefixDiv,
                    innerDivStyle.toString() );

            innerDiv.appendChild( text );
            outerDiv.appendChild( innerDiv );
            tableCellElement.appendChild( outerDiv );
        } else {
            tableCellElement.appendChild( text );
        }

        return StringUtils.isEmpty( value ) && (cellStyle.getIndex() == 0);
    }

    /**
     *
     * @param sheet
     * @param maxSheetColumns
     * @param table
     */
    protected void processColumnHeaders( HSSFSheet sheet, int maxSheetColumns,
                                         Element table ) {
        Element tableHeader = htmlDocumentFacade.createTableHeader();
        table.appendChild( tableHeader );

        Element tr = htmlDocumentFacade.createTableRow();

        if ( isOutputRowNumbers() ) {
            // empty row at left-top corner
            tr.appendChild( htmlDocumentFacade.createTableHeaderCell() );
        }

        for ( int c = 0; c < maxSheetColumns; c++ ) {
            if ( !isOutputHiddenColumns() && sheet.isColumnHidden( c ) )
            {   continue;}

            Element th = htmlDocumentFacade.createTableHeaderCell();
            String text = getColumnName( c );
            th.appendChild( htmlDocumentFacade.createText( text ) );
            tr.appendChild( th );
        }
        tableHeader.appendChild( tr );
    }

    /**
     * Creates COLGROUP element with width specified for all columns. (Except
     * first if <tt>{@link #isOutputRowNumbers()}==true</tt>)
     */
    protected void processColumnWidths( HSSFSheet sheet, int maxSheetColumns,
                                        Element table ) {
        // draw COLS after we know max column number
        Element columnGroup = htmlDocumentFacade.createTableColumnGroup();
        if ( isOutputRowNumbers() )
        {
            columnGroup.appendChild( htmlDocumentFacade.createTableColumn() );
        }
        for ( int c = 0; c < maxSheetColumns; c++ )
        {
            if ( !isOutputHiddenColumns() && sheet.isColumnHidden( c ) )
            {    continue;}

            Element col = htmlDocumentFacade.createTableColumn();
            col.setAttribute( "width",
                    String.valueOf( getColumnWidth( sheet, c ) ) );
            columnGroup.appendChild( col );
        }
        table.appendChild( columnGroup );
    }

    /**
     *
     * @param summaryInformation
     */
    protected void processDocumentInformation(SummaryInformation summaryInformation ) {
        if (!StringUtils.isEmpty(summaryInformation.getTitle())) {
            htmlDocumentFacade.setTitle(summaryInformation.getTitle());
        }

        if (StringUtils.isEmpty(summaryInformation.getAuthor())) {
            htmlDocumentFacade.addAuthor(summaryInformation.getAuthor());
        }

        if (StringUtils.isEmpty(summaryInformation.getKeywords())) {
            htmlDocumentFacade.addKeywords(summaryInformation.getKeywords());
        }

        if (StringUtils.isEmpty(summaryInformation.getComments())) {
            htmlDocumentFacade
                    .addDescription(summaryInformation.getComments());
        }
    }

    /**
     * @return maximum 1-base index of column that were rendered, zero if none
     */
    protected int processRow(CellRangeAddress[][] mergedRanges, HSSFRow row,
                             Element tableRowElement ) {
        final HSSFSheet sheet = row.getSheet();
        final short maxColIx = row.getLastCellNum();
        if ( maxColIx <= 0 )
        {  return 0;}

        final List<Element> emptyCells = new ArrayList<>(maxColIx);

        if ( isOutputRowNumbers() )
        {
            Element tableRowNumberCellElement = htmlDocumentFacade
                    .createTableHeaderCell();
            processRowNumber( row, tableRowNumberCellElement );
            emptyCells.add( tableRowNumberCellElement );
        }

        int maxRenderedColumn = 0;
        for ( int colIx = 0; colIx < maxColIx; colIx++ )
        {
            if ( !isOutputHiddenColumns() && sheet.isColumnHidden( colIx ) )
            {    continue;}

            CellRangeAddress range = ExcelToHtmlUtils.getMergedRange(
                    mergedRanges, row.getRowNum(), colIx );

            if ( range != null
                    && ( range.getFirstColumn() != colIx || range.getFirstRow() != row
                    .getRowNum() ) )
            {    continue;}

            HSSFCell cell = row.getCell( colIx );

            int divWidthPx = 0;
            if ( isUseDivsToSpan() )
            {
                divWidthPx = getColumnWidth( sheet, colIx );

                boolean hasBreaks = false;
                for ( int nextColumnIndex = colIx + 1; nextColumnIndex < maxColIx; nextColumnIndex++ )
                {
                    if ( !isOutputHiddenColumns()
                            && sheet.isColumnHidden( nextColumnIndex ) )
                    {     continue;}

                    if ( row.getCell( nextColumnIndex ) != null
                            && !isTextEmpty( row.getCell( nextColumnIndex ) ) )
                    {
                        hasBreaks = true;
                        break;
                    }

                    divWidthPx += getColumnWidth( sheet, nextColumnIndex );
                }

                if ( !hasBreaks )
                {    divWidthPx = Integer.MAX_VALUE;}
            }

            Element tableCellElement = htmlDocumentFacade.createTableCell();

            if ( range != null )
            {
                if ( range.getFirstColumn() != range.getLastColumn() )
                {   tableCellElement.setAttribute(
                            "colspan",
                            String.valueOf( range.getLastColumn()
                                    - range.getFirstColumn() + 1 ) );}
                if ( range.getFirstRow() != range.getLastRow() )
                {    tableCellElement.setAttribute(
                            "rowspan",
                            String.valueOf( range.getLastRow()
                                    - range.getFirstRow() + 1 ) );}
            }

            boolean emptyCell;
            if ( cell != null )
            {
                emptyCell = processCell( cell, tableCellElement,
                        getColumnWidth( sheet, colIx ), divWidthPx,
                        row.getHeight() / 20f );
            } else {
                emptyCell = true;
            }

            if ( emptyCell ) {
                emptyCells.add( tableCellElement );
            } else {
                for ( Element emptyCellElement : emptyCells )
                {
                    tableRowElement.appendChild( emptyCellElement );
                }
                emptyCells.clear();

                tableRowElement.appendChild( tableCellElement );
                maxRenderedColumn = colIx;
            }
        }

        return maxRenderedColumn + 1;
    }

    /**
     *
     * @param row
     * @param tableRowNumberCellElement
     */
    protected void processRowNumber( HSSFRow row,
                                     Element tableRowNumberCellElement ) {
        tableRowNumberCellElement.setAttribute( "class", "rownumber" );
        Text text = htmlDocumentFacade.createText( getRowName( row ) );
        tableRowNumberCellElement.appendChild( text );
    }

    /**
     *
     * @param sheet
     */
    protected void processSheet( HSSFSheet sheet ) {
        processSheetHeader(htmlDocumentFacade.getBody(), sheet);

        final int physicalNumberOfRows = sheet.getPhysicalNumberOfRows();
        if (physicalNumberOfRows <= 0) {
            return;
        }

        Element table = htmlDocumentFacade.createTable();
        htmlDocumentFacade.addStyleClass(table, cssClassPrefixTable,
                "border-collapse:collapse;border-spacing:0;");

        Element tableBody = htmlDocumentFacade.createTableBody();

        final CellRangeAddress[][] mergedRanges = ExcelToHtmlUtils
                .buildMergedRangesMap(sheet);

        final List<Element> emptyRowElements = new ArrayList<>(
                physicalNumberOfRows);
        int maxSheetColumns = 1;
        for (int r = sheet.getFirstRowNum(); r <= sheet.getLastRowNum(); r++) {
            HSSFRow row = sheet.getRow(r);

            if (row == null) {
                continue;
            }

            if (!isOutputHiddenRows() && row.getZeroHeight()) {
                continue;
            }

            Element tableRowElement = htmlDocumentFacade.createTableRow();
            htmlDocumentFacade.addStyleClass(tableRowElement,
                    cssClassPrefixRow, "height:" + (row.getHeight() / 20f)
                            + "pt;");

            int maxRowColumnNumber = processRow(mergedRanges, row,
                    tableRowElement);

            if (maxRowColumnNumber == 0) {
                emptyRowElements.add(tableRowElement);
            } else {
                if (!emptyRowElements.isEmpty()) {
                    for (Element emptyRowElement : emptyRowElements) {
                        tableBody.appendChild(emptyRowElement);
                    }
                    emptyRowElements.clear();
                }

                tableBody.appendChild(tableRowElement);
            }
            maxSheetColumns = Math.max(maxSheetColumns, maxRowColumnNumber);
        }

        processColumnWidths(sheet, maxSheetColumns, table);

        if (isOutputColumnHeaders()) {
            processColumnHeaders(sheet, maxSheetColumns, table);
        }

        table.appendChild(tableBody);

        htmlDocumentFacade.getBody().appendChild(table);
    }

    /**
     *
     * @param htmlBody
     * @param sheet
     */
    protected void processSheetHeader( Element htmlBody, HSSFSheet sheet ) {
        Element h2 = htmlDocumentFacade.createHeader2();
        h2.appendChild( htmlDocumentFacade.createText( sheet.getSheetName() ) );
        htmlBody.appendChild( h2 );
    }

    /**
     *
     * @param workbook
     */
    public void processWorkbook( HSSFWorkbook workbook ) {
        final SummaryInformation summaryInformation = workbook
                .getSummaryInformation();
        if ( summaryInformation != null ) {
            processDocumentInformation( summaryInformation );
        }

        if ( isUseDivsToSpan() ) {
            // prepare CSS classes for later usage
            this.cssClassContainerCell = htmlDocumentFacade
                    .getOrCreateCssClass( cssClassPrefixCell,
                            "padding:0;margin:0;align:left;vertical-align:top;" );
            this.cssClassContainerDiv = htmlDocumentFacade.getOrCreateCssClass(
                    cssClassPrefixDiv, "position:relative;" );
        }

        for ( int s = 0; s < workbook.getNumberOfSheets(); s++ ) {
            HSSFSheet sheet = workbook.getSheetAt( s );
            processSheet( sheet );
        }

        htmlDocumentFacade.updateStylesheet();
    }

    /**
     *
     * @param cssClassPrefixCell
     */
    public void setCssClassPrefixCell( String cssClassPrefixCell )
    {
        this.cssClassPrefixCell = cssClassPrefixCell;
    }

    /**
     *
     * @param cssClassPrefixDiv
     */
    public void setCssClassPrefixDiv( String cssClassPrefixDiv )
    {
        this.cssClassPrefixDiv = cssClassPrefixDiv;
    }

    /**
     *
     * @param cssClassPrefixRow
     */
    public void setCssClassPrefixRow( String cssClassPrefixRow )
    {
        this.cssClassPrefixRow = cssClassPrefixRow;
    }

    /**
     *
     * @param cssClassPrefixTable
     */
    public void setCssClassPrefixTable( String cssClassPrefixTable )
    {
        this.cssClassPrefixTable = cssClassPrefixTable;
    }

    /**
     * Allows converter to wrap content into two additional DIVs with tricky
     * styles, so it will wrap across empty cells (like in Excel).
     * <p>
     * <b>Warning:</b> after enabling this mode do not serialize result HTML
     * with INDENT=YES option, because line breaks will make additional
     * (unwanted) changes
     */
    public void setUseDivsToSpan( boolean useDivsToSpan )
    {
        this.useDivsToSpan = useDivsToSpan;
    }
}
