package com.yskc.rs.utils;

import cn.afterturn.easypoi.cache.WordCache;
import cn.afterturn.easypoi.entity.ImageEntity;
import cn.afterturn.easypoi.util.PoiElUtil;
import cn.afterturn.easypoi.util.PoiPublicUtil;
import cn.afterturn.easypoi.word.entity.MyXWPFDocument;
import cn.afterturn.easypoi.word.entity.params.ExcelListEntity;
import cn.afterturn.easypoi.word.parse.excel.ExcelEntityParse;
import cn.afterturn.easypoi.word.parse.excel.ExcelMapParse;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xwpf.usermodel.*;
import org.apache.xmlbeans.XmlException;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTSym;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static cn.afterturn.easypoi.util.PoiElUtil.*;

public class YsParseWord {
    private static final Logger LOGGER = LoggerFactory.getLogger(YsParseWord.class);
    /**
     * 判断是不是迭代输出
     *
     * @return
     * @throws Exception
     * @author JueYue
     * 2013-11-18
     */
    private Object checkThisTableIsNeedIterator(XWPFTableCell cell,
                                                Map<String, Object> map) throws Exception {
        String text = cell.getText().trim();
        // 判断是不是迭代输出
        if (text != null && text.contains(FOREACH) && text.startsWith(START_STR)) {
            text = text.replace(FOREACH_NOT_CREATE, EMPTY).replace(FOREACH_AND_SHIFT, EMPTY)
                    .replace(FOREACH, EMPTY).replace(START_STR, EMPTY);
            String[] keys = text.replaceAll("\\s{1,}", " ").trim().split(" ");
            return PoiPublicUtil.getParamsValue(keys[0], map);
        }
        return null;
    }

    /**
     * 解析所有的文本
     *
     * @param paragraphs
     * @param map
     * @author JueYue
     * 2013-11-17
     */
    private void parseAllParagraphic(List<XWPFParagraph> paragraphs,
                                     Map<String, Object> map) throws Exception {
        XWPFParagraph paragraph;
        for (int i = 0; i < paragraphs.size(); i++) {
            paragraph = paragraphs.get(i);
            if (paragraph.getText().indexOf(START_STR) != -1) {
                parseThisParagraph(paragraph, map);
            }

        }

    }

    public static final String HalfSTART_STR = "{";
    public static final String HalsEND_STR = "}";
    /**
     * 解析这个段落
     *
     * @param paragraph
     * @param map
     * @author JueYue
     * 2013-11-16
     */
    private void parseThisParagraph(XWPFParagraph paragraph,
                                    Map<String, Object> map) throws Exception {
        XWPFRun run;
        String text;
        List<XWPFRun> runs = paragraph.getRuns();
        List<TemplateItem> items = new ArrayList<>();
        TemplateItem newItem = null;
        int scanIndex, textLength;
        for (int i = 0; i < runs.size(); i++) {
            run = runs.get(i);
            text = run.getText(0);
            if (StringUtils.isEmpty(text)) {
                continue;
            } // 如果为空或者""这种这继续循环跳过
            scanIndex = 0; //初始化当前run的扫描的位置,从0开始
            textLength = text.length();
            while (scanIndex < textLength) {
                if (newItem == null) { // 新的一轮模板查找
                    int startIndex = text.indexOf(START_STR, scanIndex); //从当前扫描的index位置后开始查找模板开始标记
                    if (startIndex > -1) { // 找到模板开始位置
                        newItem = createItem(run, startIndex, 2);  // 标识模板开始位置,状态为已经开始,下一阶段为找闭标记
                        scanIndex = startIndex + 2; // 扫描位置向前移
                    } else if (text.endsWith(HalfSTART_STR)) { // 找到模板开始的一半标记,如果当前run没包含全标记,如果有半标记,那肯定是最后一个字符为半标记.
                        newItem = createItem(run, textLength - 1, 1); // 新建模板数据项,但标记状态为开始一半,下一阶段找另一半
                        // scanIndex = textLength;
                        break; // 当前run已扫描完毕,跳出当前run.
                    } else {
                        break; // 当前run 中未找到模板的开始标记,跳出当前run
                    }
                } else { // 已经确定可能有模板存在
                    if (newItem.status == 1) { // 已经找到开始的半标记
                        if (text.startsWith(HalfSTART_STR)) { // 承接上一个run,这个run的开始应当为半标记
                            newItem.status = 2; // 标识已经找到整个开始标记
                            newItem.runs.add(run); // 将当前run添加进模板项的列表
                            scanIndex += 1; // 扫描位置向前移
                        } else {
                            newItem = null; // 只存在半个开始标记,不足以成一个模板项,释放临时的模板项
                        }
                    } else if (newItem.status == 3) { // 已经找到半结束标记
                        if (text.startsWith(HalsEND_STR)) { // 承接上一个run,这个run的开始应当为结束半标记
                            scanIndex += 1; // 扫描位置向前移
                            newItem.endPos = 1; // 确定结束位置
                            newItem.status = 4; // 确定状态为结束
                            newItem.runs.add(run); // 将当前run添加进模板项的列表
                            items.add(newItem); // 将当前的模板项加入模板项列表, 完成一次模板项的查找
                        }
                        newItem = null; // 结束一次查找,释放模板项变量.
                    } else { // 已经找到开始的全标记
                        if (newItem.runs.get(newItem.runs.size() - 1) != run) { // 将当前run加入模板项列表,重复run不加入
                            newItem.runs.add(run);
                        }
                        int endIndex = text.indexOf(END_STR, scanIndex);
                        if (endIndex > -1) { // 找到结束标记
                            scanIndex = endIndex + 2; // 扫描位置向前移
                            newItem.endPos = scanIndex; // 确定结束位置
                            newItem.status = 4; // 将当前run添加进模板项的列表
                            items.add(newItem); // 将当前的模板项加入模板项列表, 完成一次模板项的查找
                            newItem = null; // 结束一次查找,释放模板项变量.
                        } else if (text.endsWith(HalsEND_STR)) { // 以半结束标记结束当前run
                            newItem.status = 3; // 标记状态只找到半结束标记
                            break; // 当前run已扫描完毕,跳出当前run
                        } else {
                            break; // 没找到结束标记, 跳出当前run
                        }
                    }
                }
            }
        }
        int itemLengh = items.size();
        // 轮询模板项,替换各模板项数据
        for (int i = itemLengh - 1; i >= 0; i--) {
            TemplateItem tItem = items.get(i);
            int rSize = tItem.runs.size();
            String keyText; // 存储当前key的文本,包含开始结束标记,{{xxx}}
            XWPFRun firstRun = tItem.runs.get(0);
            XWPFRun lastRun = tItem.runs.get(rSize - 1);
            String preText = firstRun.getText(0).substring(0, tItem.startPos);
            String postText = lastRun.getText(0).substring(tItem.endPos);
            if (rSize == 1) {
                String runText = firstRun.getText(0);
                keyText = runText.substring(tItem.startPos, tItem.endPos);
                String resultText = handleFirstRun(firstRun,preText,keyText,map);
                setWordText(firstRun, resultText + postText);
            } else {
                keyText = lastRun.getText(0).substring(0, tItem.endPos);
                for (int j = rSize - 2; j > 0; j--) {
                    keyText = tItem.runs.get(j).getText(0) + keyText;
                    tItem.runs.get(j).setText("", 0);
                }
                keyText = firstRun.getText(0).substring(tItem.startPos) + keyText;
                String resultText = handleFirstRun(firstRun,preText,keyText,map);
                setWordText(firstRun, resultText);
                setWordText(lastRun, postText);
            }
        }
    }

    private String handleFirstRun(XWPFRun firstRun,String preText, String keyText,Map dMap) throws Exception {
        String key = keyText.substring(2, keyText.length() - 2);
        Object v = PoiElUtil.eval(key.trim(), dMap);
        String result = preText;
        if (key.endsWith("_chk")) {
            addAnCheckBox(firstRun, v instanceof Boolean && (Boolean) v);
        } else if (v != null) {
            if (v instanceof ImageEntity) {// 如果是图片就设置为图片
                addAnImage((ImageEntity) v, firstRun);
            } else if (v instanceof List && ((List) v).size()>0 && ((List) v).get(0) instanceof ImageEntity) {
                List<ImageEntity> list = (List) v;
                for (ImageEntity img : list) {
                    addAnImage(img, firstRun);
                }
            } else {
                result = preText + v.toString();
            }
        }
        return result;
    }

    /**
     * @param cells
     * @param map
     * @throws Exception
     */
    private void parseThisRow(List<XWPFTableCell> cells, Map<String, Object> map) throws Exception {
        for (XWPFTableCell cell : cells) {
            parseAllParagraphic(cell.getParagraphs(), map);
            for (XWPFTable table :
                    cell.getTables()) {
                if (table.getText().indexOf(START_STR) != -1) {
                    parseThisTable(table, map);
                }
            }
        }
    }

    /**
     * 解析这个表格
     *
     * @param table
     * @param map
     * @author JueYue
     * 2013-11-17
     */
    private void parseThisTable(XWPFTable table, Map<String, Object> map) throws Exception {
        XWPFTableRow row;
        List<XWPFTableCell> cells;
        Object listobj;
        for (int i = 0; i < table.getNumberOfRows(); i++) {
            row = table.getRow(i);
            cells = row.getTableCells();
            listobj = checkThisTableIsNeedIterator(cells.get(0), map);
            if (listobj == null) {
                parseThisRow(cells, map);
            } else if (listobj instanceof ExcelListEntity) {
                new ExcelEntityParse().parseNextRowAndAddRow(table, i, (ExcelListEntity) listobj);
                i = i + ((ExcelListEntity) listobj).getList().size() - 1;//删除之后要往上挪一行,然后加上跳过新建的行数
            } else {
                ExcelMapParse.parseNextRowAndAddRow(table, i, (List) listobj);
                i = i + ((List) listobj).size() - 1;//删除之后要往上挪一行,然后加上跳过新建的行数
            }
        }
    }

    /**
     * 解析07版的Word并且进行赋值
     *
     * @return
     * @throws Exception
     * @author JueYue
     * 2013-11-16
     */
    public XWPFDocument parseWord(String url, Map<String, Object> map) throws Exception {
        MyXWPFDocument doc = WordCache.getXWPFDocumen(url);
        parseWordSetValue(doc, map);
        return doc;
    }

    /**
     * 解析07版的Work并且进行赋值但是进行多页拼接
     *
     * @param url
     * @param list
     * @return
     */
    public XWPFDocument parseWord(String url, List<Map<String, Object>> list) throws Exception {
        if (list.size() == 1) {
            return parseWord(url, list.get(0));
        } else if (list.size() == 0) {
            return null;
        } else {
            MyXWPFDocument doc = WordCache.getXWPFDocumen(url);
            parseWordSetValue(doc, list.get(0));
            //插入分页
            doc.createParagraph().setPageBreak(true);
            for (int i = 1; i < list.size(); i++) {
                MyXWPFDocument tempDoc = WordCache.getXWPFDocumen(url);
                parseWordSetValue(tempDoc, list.get(i));
                tempDoc.createParagraph().setPageBreak(true);
                doc.getDocument().addNewBody().set(tempDoc.getDocument().getBody());

            }
            return doc;
        }

    }

    /**
     * 解析07版的Word并且进行赋值
     *
     * @throws Exception
     */
    public void parseWord(XWPFDocument document, Map<String, Object> map) throws Exception {
        parseWordSetValue((MyXWPFDocument) document, map);
    }

    /**
     * @param doc
     * @param map
     * @throws Exception
     */
    private void parseWordSetValue(MyXWPFDocument doc, Map<String, Object> map) throws Exception {
        // 第一步解析文档
        parseAllParagraphic(doc.getParagraphs(), map);
        // 第二步解析页眉,页脚
        parseHeaderAndFoot(doc, map);
        // 第三步解析所有表格
        XWPFTable table;
        Iterator<XWPFTable> itTable = doc.getTablesIterator();
        while (itTable.hasNext()) {
            table = itTable.next();
            if (table.getText().indexOf(START_STR) != -1) {
                parseThisTable(table, map);
            }
        }

    }

    /**
     * 解析页眉和页脚
     *
     * @param doc
     * @param map
     * @throws Exception
     */
    private void parseHeaderAndFoot(MyXWPFDocument doc, Map<String, Object> map) throws Exception {
        List<XWPFHeader> headerList = doc.getHeaderList();
        for (XWPFHeader xwpfHeader : headerList) {
            for (int i = 0; i < xwpfHeader.getListParagraph().size(); i++) {
                parseThisParagraph(xwpfHeader.getListParagraph().get(i), map);
            }
        }
        List<XWPFFooter> footerList = doc.getFooterList();
        for (XWPFFooter xwpfFooter : footerList) {
            for (int i = 0; i < xwpfFooter.getListParagraph().size(); i++) {
                parseThisParagraph(xwpfFooter.getListParagraph().get(i), map);
            }
        }
    }

    /**
     * 支持换行操作
     *
     * @param currentRun
     * @param currentText
     */
    public static void setWordText(XWPFRun currentRun, String currentText) {
        if (StringUtils.isNotEmpty(currentText)) {
            String[] tempArr = currentText.split("\n");
            for (int i = 0, le = tempArr.length - 1; i < le; i++) {
                currentRun.setText(tempArr[i], i);
                currentRun.addBreak();
            }
            currentRun.setText(tempArr[tempArr.length - 1], tempArr.length - 1);
        } else {
            currentRun.setText("", 0);
        }
    }

    /**
     * @param obj
     * @param currentRun
     */
    public static void addAnImage(ImageEntity obj, XWPFRun currentRun) {
        try {
            Object[] isAndType = PoiPublicUtil.getIsAndType(obj);
            String picId;
            if (currentRun.getParent().getPart() instanceof XWPFHeaderFooter) {
                picId = ((XWPFHeaderFooter) currentRun.getParent().getPart()).addPictureData((byte[]) isAndType[0],
                        (Integer) isAndType[1]);
            } else {
                picId = currentRun.getDocument().addPictureData((byte[]) isAndType[0],
                        (Integer) isAndType[1]);
            }
            ((MyXWPFDocument) currentRun.getDocument()).createPicture(currentRun,
                    picId, currentRun.getDocument()
                            .getNextPicNameNumber((Integer) isAndType[1]),
                    obj.getWidth(), obj.getHeight());

        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }

    }

    /**
     * 添加checkbox
     *
     * @param xwpfRun
     * @param chk
     */
    public static void addAnCheckBox(XWPFRun xwpfRun, Boolean chk) {
        try {
            String chkChar = chk ? "F052" : "F0A3";
            CTSym ctSym = CTSym.Factory.parse("<xml-fragment w:font=\"Wingdings 2\" w:char=\"" + chkChar + "\" xmlns:w=\"http://schemas.openxmlformats.org/wordprocessingml/2006/main\" xmlns:wne=\"http://schemas.microsoft.com/office/word/2006/wordml\"> </xml-fragment>");
            List<CTSym> symList = xwpfRun.getCTR().getSymList();
            symList.add(ctSym);
        } catch (XmlException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    /**
     * 新建一个Item
     *
     * @param run
     * @param startPos
     * @param status
     * @return
     */
    TemplateItem createItem(XWPFRun run, int startPos, int status) {
        TemplateItem newItem = new TemplateItem();
        newItem.runs = new ArrayList<>();
        newItem.runs.add(run);
        newItem.startPos = startPos;
        newItem.status = status;
        return newItem;
    }

    private class TemplateItem {
        /**
         * 当前Item在第一个run中的文本开始位置
         */
        private int startPos;
        /**
         * 当前Item在最后一个run中的文本结束位置
         */
        private int endPos;
        /**
         * 当前Item所占的run列表
         */
        private List<XWPFRun> runs;
        /**
         * 当前item数据key
         */
        private String key;
        /**
         * 当前item数据文本
         */
        private String itemText;
        /**
         * 0:初始值,1:开始半标记,即"{",2:开始全标记,即"{{",3:结束半标记,即"}",4:结束全标记,即"}}"
         */
        private int status;
    }
}
