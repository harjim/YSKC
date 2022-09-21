package com.yskc.rs.models.excel;

import com.yskc.common.annotation.Excel;

import java.io.Serializable;
import java.util.Date;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.models.excel
 * @Author: zhangdingfu
 * @CreateTime: 2019-12-03 10:42
 * @Description: 项目人员excel
 */
public class InitMemberExcel implements Serializable {
    @Excel(name = "工号", order = 0, fieldName = "enumber")
    private String enumber;
    @Excel(name = "姓名", order = 1, fieldName = "ename")
    private String ename;
    @Excel(name = "人员类型", order = 3, fieldName = "typeName")
    private String typeName;
    @Excel(name = "项目角色", order = 4, fieldName = "role")
    private String role;
    @Excel(name = "出生日期", order = 5, fieldName = "birthday")
    private Date birthday;
    @Excel(name = "职位", order = 6, fieldName = "position")
    private String position;
    @Excel(name = "职称", order = 7, fieldName = "title")
    private String title;
    @Excel(name = "专业", order = 8, fieldName = "specialities")
    private String specialities;
    @Excel(name = "入职日期", order = 9, fieldName = "edate", dateFormat = "yyyy-MM-dd")
    private Date edate;
    @Excel(name = "进入时间")
    private Date entryDate;
    @Excel(name = "负责人")
    private String master;

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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSpecialities() {
        return specialities;
    }

    public void setSpecialities(String specialities) {
        this.specialities = specialities;
    }

    public Date getEdate() {
        return edate;
    }

    public void setEdate(Date edate) {
        this.edate = edate;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public Date getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(Date entryDate) {
        this.entryDate = entryDate;
    }

    public String getMaster() {
        return master;
    }

    public void setMaster(String master) {
        this.master = master;
    }
}
