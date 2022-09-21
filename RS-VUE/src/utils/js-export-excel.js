/* eslint-disable */
require("script-loader!file-saver");
require("script-loader!xlsx/dist/xlsx.core.min");
require("script-loader!blob.js/Blob");
import XLSX from 'xlsx-style'
/**
 * Created by kin on 2017/5/18.
 *
 * josn导出excel
 * mail：cuikangjie_90h@126.com
 */
const changeData = function (data, filter) {
    let sj = data,
        f = filter,
        re = [];
    Array.isArray(data)
        ? (function () {
            //对象
            f
                ? (function () {
                    //存在过滤
                    sj.forEach(function (obj) {
                        let one = [];
                        filter.forEach(function (no) {
                            one.push(obj[no]);
                        });
                        re.push(one);
                    });
                })()
                : (function () {
                    //不存在过滤
                    sj.forEach(function (obj) {
                        let col = Object.keys(obj);
                        let one = [];
                        col.forEach(function (no) {
                            one.push(obj[no]);
                        });
                        re.push(one);
                    });
                })();
        })()
        : (function () {
            re = sj;
        })();
    return re;
};

// 转换数据
const sheetChangeData = function (data) {
    let ws = {};
    let range = {
        s: {
            c: 10000000,
            r: 10000000
        },
        e: {
            c: 0,
            r: 0
        }
    };
    for (let R = 0; R != data.length; ++R) {
        for (let C = 0; C != data[R].length; ++C) {
            if (range.s.r > R) range.s.r = R;
            if (range.s.c > C) range.s.c = C;
            if (range.e.r < R) range.e.r = R;
            if (range.e.c < C) range.e.c = C;
            let cell = {
                v: data[R][C]
            };
            if (typeof cell.s === 'undefined') {
                cell.s = { border: borderAll }
            }
            if (cell.v == null) {
                cell.v = ''
            };
            let cell_ref = XLSX.utils.encode_cell({
                c: C,
                r: R
            });

            if (typeof cell.v === "number") cell.t = "n";
            else if (typeof cell.v === "boolean") cell.t = "b";
            else if (cell.v instanceof Date) {
                cell.t = "n";
                cell.z = XLSX.SSF._table[14];
                cell.v = datenum(cell.v);
            } else cell.t = "s";
            ws[cell_ref] = cell;
        }
    }
    if (range.s.c < 10000000) ws["!ref"] = XLSX.utils.encode_range(range);
    return ws;
};

const s2ab = function (s) {
    let buf = new ArrayBuffer(s.length);
    let view = new Uint8Array(buf);
    for (let i = 0; i != s.length; ++i) view[i] = s.charCodeAt(i) & 0xff;
    return buf;
};
const datenum = function (v, date1904) {
    if (date1904) v += 1462;
    let epoch = Date.parse(v);
    return (epoch - new Date(Date.UTC(1899, 11, 30))) / (24 * 60 * 60 * 1000);
};
const columnwidths = function (columnWidths) {
    let out = [];
    out = columnWidths.map(function (item) {
        return { wch: item };
    });
    return out;
};
const borderAll = {  //单元格外侧框线
    top: {
        style: 'thin'
    },
    bottom: {
        style: 'thin'
    },
    left: {
        style: 'thin'
    },
    right: {
        style: 'thin'
    }
};
const getExcelHeadTitle = function (i) {
    if (i < 26) {
        return String.fromCharCode((65 + i))
    } else {
        var a = parseInt(i / 26)
        var b = i % 26
        return getExcelHeadTitle(a - 1) + getExcelHeadTitle(b)
    }
}
const getHeadTitle = function (sheetHeader, count) {
    var headerArr = []
    for (let i = sheetHeader.length - 1; i > -1; i--) {
        headerArr.push(getExcelHeadTitle(i) + count)
    }
    return headerArr
}
const exportExcel = function (options) {
    let _options = {
        fileName: options.fileName || "download",
        datas: options.datas,
        workbook: {
            SheetNames: [],
            Sheets: {}
        }
    };

    const instance = {
        saveExcel: function () {
            let wb = _options.workbook;
            _options.datas.forEach(function (data, index) {
                let sheetHeaderList = data.sheetHeaderList || [];
                let sheetHeader = data.sheetHeader || null;
                let sheetData = data.sheetData;
                let sheetName = data.sheetName || "sheet" + (index + 1);
                let sheetFilter = data.sheetFilter || null;
                let columnWidths = data.columnWidths || [];
                let merges = data.merges || []
                let multiHeader = data.multiHeader || []
                const headerArr = []

                //子表添加头信息
                sheetData = changeData(sheetData, sheetFilter);
                if (sheetHeaderList && sheetHeaderList.length > 0) {
                    for (let i = sheetHeaderList.length - 1; i > -1; i--) {
                        headerArr.unshift(getHeadTitle(sheetHeaderList[i], i + 1))
                        sheetData.unshift(sheetHeaderList[i])
                    }
                }
                if (sheetHeader && sheetHeader.length > 0) {
                    headerArr.push(getHeadTitle(sheetHeader, merges.length + 1))
                    sheetData.unshift(sheetHeader);
                }
                if (multiHeader && multiHeader.length > 0) {
                    for (let i = multiHeader.length - 1; i > -1; i--) {
                        sheetData.unshift(multiHeader[i])
                    }
                }
                let ws = sheetChangeData(sheetData);
                if (merges.length > 0) {
                    if (!ws['!merges']) ws['!merges'] = [];
                    merges.forEach(item => {
                        ws['!merges'].push(XLSX.utils.decode_range(item))
                    })
                }
                if (columnWidths.length === 0) {
                    /*设置worksheet每列的最大宽度*/
                    const colWidth = sheetData.map(row => row.map(val => {
                        /*先判断是否为null/undefined*/
                        if (val === null || typeof val === 'undefined') {
                            return {
                                'wch': 10
                            };
                        }
                        /*再判断是否为中文*/
                        else if (val.toString().charCodeAt(0) > 255) {
                            return {
                                'wch': val.toString().length * 2
                            };
                        } else {
                            return {
                                'wch': val.toString().length
                            };
                        }
                    }))
                    /*以第一行为初始值*/
                    let result = colWidth[0];
                    for (let i = 1; i < colWidth.length; i++) {
                        for (let j = 0; j < colWidth[i].length; j++) {
                            if (result[j] && colWidth[i] && colWidth[i][j] && result[j]['wch'] && colWidth[i][j]['wch'] && result[j]['wch'] < colWidth[i][j]['wch']) {
                                result[j]['wch'] = colWidth[i][j]['wch'];
                            }
                        }
                    }
                    ws['!cols'] = result;
                } else {
                    ws["!cols"] = columnwidths(columnWidths);
                }
                Object.keys(ws).map((key) => {
                    merges.map(merge => {
                        if (merge.includes(key)) {
                            ws[key].s = Object.assign(ws[key].s, {									//为某个单元格设置单独样式
                                font: {
                                    name: '宋体',
                                    bold: true
                                },
                                alignment: { horizontal: "center", vertical: "center", wrap_text: true }
                            }
                            )
                        }
                    })
                    for (let i = 1; i < headerArr.length; i++) {
                        var headerArrItem = headerArr[i]
                        if (headerArrItem.indexOf(key) !== -1) {
                            ws[key].s = Object.assign(ws[key].s, {									//为某个单元格设置单独样式
                                font: {
                                    name: '宋体',
                                    bold: true
                                },
                                alignment: { horizontal: "center", vertical: "center", wrap_text: true }
                            }
                            )
                        }
                    }

                })
                wb.SheetNames.push(sheetName);
                wb.Sheets[sheetName] = ws;
            });

            let wbout = XLSX.write(wb, {
                bookType: "xlsx",
                bookSST: false,
                type: "binary"
            });
            saveAs(
                new Blob([s2ab(wbout)], {
                    type: "application/octet-stream"
                }),
                _options.fileName + ".xlsx"
            );
        }
    };

    return instance;
};

export default exportExcel;
