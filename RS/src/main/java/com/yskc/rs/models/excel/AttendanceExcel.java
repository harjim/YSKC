package com.yskc.rs.models.excel;
import com.yskc.common.annotation.Excel;
import com.yskc.common.utils.DateUtil;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 员工考勤
 * @author huronghua
 */
public class AttendanceExcel implements Serializable {
    @Excel(name = "员工编号")
    private String enumber;
    @Excel(name = "姓名")
    private String ename;
    @Excel(name = "身份证号码")
    private String idNumber;
    @Excel(name = "所属职务")
    private String position;
    @Excel(name = "所属部门")
    private String deptName;
    @Excel(name = "月份",dateFormat = "yyyy-MM")
    private Date month;
    @Excel(name = "考勤记录",type = 1,max = 31,min = 1)
    private String attData;

    private Integer monthDay1;
    private Integer monthDay2;
    private Integer monthDay3;
    private Integer monthDay4;
    private Integer monthDay5;
    private Integer monthDay6;
    private Integer monthDay7;
    private Integer monthDay8;
    private Integer monthDay9;
    private Integer monthDay10;
    private Integer monthDay11;
    private Integer monthDay12;
    private Integer monthDay13;
    private Integer monthDay14;
    private Integer monthDay15;
    private Integer monthDay16;
    private Integer monthDay17;
    private Integer monthDay18;
    private Integer monthDay19;
    private Integer monthDay20;
    private Integer monthDay21;
    private Integer monthDay22;
    private Integer monthDay23;
    private Integer monthDay24;
    private Integer monthDay25;
    private Integer monthDay26;
    private Integer monthDay27;
    private Integer monthDay28;
    private Integer monthDay29;
    private Integer monthDay30;
    private Integer monthDay31;
    public Date getMonth() {
        return month;
    }

    public void setMonth(Date month) {
        this.month = month;
    }

    public String getAttData() {
        int maxDay = DateUtil.getMonthMaxDays(month);
        List<String> values = new ArrayList<>();
        if (StringUtils.isEmpty(attData)) {
            Field[] fields = this.getClass().getDeclaredFields();
            for (int day = 1; day <= 31; day++) {
                for (Field field : fields) {
                    try {
                        if (field.getName().equals("monthDay" + day)) {
                            if (day > maxDay) {
                                values.add("0");
                                continue;
                            }
                            Object value = field.get(this);
                            if (value != null) {
                                values.add(value.toString());
                            } else {
                                values.add("0");
                            }
                        }
                    } catch (IllegalAccessException e) {
                    }
                }
            }
            return   String.join(",", values);
        }
        return attData;
    }

    public void setAttData(String attData) {
        this.attData = attData;
    }

    public String getEnumber() {
        return enumber;
    }

    public void setEnumber(String enumber) {
        this.enumber = enumber;
    }

    public String getEname() {
        return ename;
    }

    public void setEname(String ename) {
        this.ename = ename;
    }



    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public Integer getMonthDay1() {
        return monthDay1;
    }

    public void setMonthDay1(Integer monthDay1) {
        this.monthDay1 = monthDay1;
    }

    public Integer getMonthDay2() {
        return monthDay2;
    }

    public void setMonthDay2(Integer monthDay2) {
        this.monthDay2 = monthDay2;
    }

    public Integer getMonthDay3() {
        return monthDay3;
    }

    public void setMonthDay3(Integer monthDay3) {
        this.monthDay3 = monthDay3;
    }

    public Integer getMonthDay4() {
        return monthDay4;
    }

    public void setMonthDay4(Integer monthDay4) {
        this.monthDay4 = monthDay4;
    }

    public Integer getMonthDay5() {
        return monthDay5;
    }

    public void setMonthDay5(Integer monthDay5) {
        this.monthDay5 = monthDay5;
    }

    public Integer getMonthDay6() {
        return monthDay6;
    }

    public void setMonthDay6(Integer monthDay6) {
        this.monthDay6 = monthDay6;
    }

    public Integer getMonthDay7() {
        return monthDay7;
    }

    public void setMonthDay7(Integer monthDay7) {
        this.monthDay7 = monthDay7;
    }

    public Integer getMonthDay8() {
        return monthDay8;
    }

    public void setMonthDay8(Integer monthDay8) {
        this.monthDay8 = monthDay8;
    }

    public Integer getMonthDay9() {
        return monthDay9;
    }

    public void setMonthDay9(Integer monthDay9) {
        this.monthDay9 = monthDay9;
    }

    public Integer getMonthDay10() {
        return monthDay10;
    }

    public void setMonthDay10(Integer monthDay10) {
        this.monthDay10 = monthDay10;
    }

    public Integer getMonthDay11() {
        return monthDay11;
    }

    public void setMonthDay11(Integer monthDay11) {
        this.monthDay11 = monthDay11;
    }

    public Integer getMonthDay12() {
        return monthDay12;
    }

    public void setMonthDay12(Integer monthDay12) {
        this.monthDay12 = monthDay12;
    }

    public Integer getMonthDay13() {
        return monthDay13;
    }

    public void setMonthDay13(Integer monthDay13) {
        this.monthDay13 = monthDay13;
    }

    public Integer getMonthDay14() {
        return monthDay14;
    }

    public void setMonthDay14(Integer monthDay14) {
        this.monthDay14 = monthDay14;
    }

    public Integer getMonthDay15() {
        return monthDay15;
    }

    public void setMonthDay15(Integer monthDay15) {
        this.monthDay15 = monthDay15;
    }

    public Integer getMonthDay16() {
        return monthDay16;
    }

    public void setMonthDay16(Integer monthDay16) {
        this.monthDay16 = monthDay16;
    }

    public Integer getMonthDay17() {
        return monthDay17;
    }

    public void setMonthDay17(Integer monthDay17) {
        this.monthDay17 = monthDay17;
    }

    public Integer getMonthDay18() {
        return monthDay18;
    }

    public void setMonthDay18(Integer monthDay18) {
        this.monthDay18 = monthDay18;
    }

    public Integer getMonthDay19() {
        return monthDay19;
    }

    public void setMonthDay19(Integer monthDay19) {
        this.monthDay19 = monthDay19;
    }

    public Integer getMonthDay20() {
        return monthDay20;
    }

    public void setMonthDay20(Integer monthDay20) {
        this.monthDay20 = monthDay20;
    }

    public Integer getMonthDay21() {
        return monthDay21;
    }

    public void setMonthDay21(Integer monthDay21) {
        this.monthDay21 = monthDay21;
    }

    public Integer getMonthDay22() {
        return monthDay22;
    }

    public void setMonthDay22(Integer monthDay22) {
        this.monthDay22 = monthDay22;
    }

    public Integer getMonthDay23() {
        return monthDay23;
    }

    public void setMonthDay23(Integer monthDay23) {
        this.monthDay23 = monthDay23;
    }

    public Integer getMonthDay24() {
        return monthDay24;
    }

    public void setMonthDay24(Integer monthDay24) {
        this.monthDay24 = monthDay24;
    }

    public Integer getMonthDay25() {
        return monthDay25;
    }

    public void setMonthDay25(Integer monthDay25) {
        this.monthDay25 = monthDay25;
    }

    public Integer getMonthDay26() {
        return monthDay26;
    }

    public void setMonthDay26(Integer monthDay26) {
        this.monthDay26 = monthDay26;
    }

    public Integer getMonthDay27() {
        return monthDay27;
    }

    public void setMonthDay27(Integer monthDay27) {
        this.monthDay27 = monthDay27;
    }

    public Integer getMonthDay28() {
        return monthDay28;
    }

    public void setMonthDay28(Integer monthDay28) {
        this.monthDay28 = monthDay28;
    }

    public Integer getMonthDay29() {
        return monthDay29;
    }

    public void setMonthDay29(Integer monthDay29) {
        this.monthDay29 = monthDay29;
    }

    public Integer getMonthDay30() {
        return monthDay30;
    }

    public void setMonthDay30(Integer monthDay30) {
        this.monthDay30 = monthDay30;
    }

    public Integer getMonthDay31() {
        return monthDay31;
    }

    public void setMonthDay31(Integer monthDay31) {
        this.monthDay31 = monthDay31;
    }
}
