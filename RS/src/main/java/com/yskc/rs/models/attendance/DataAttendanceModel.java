package com.yskc.rs.models.attendance;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 考勤记录
 * @author huronghua
 */
public class DataAttendanceModel implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer id;
    private String enumber;
    private String ename;
    private Date month;
    private String attData;
    private Integer companyId;
    private Integer creatorId;
    private Date createTime;
    private String remark;
    private String remainAttData;
    private List<String> attDataList;
    private List<String> remainAttDataList;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Date getMonth() {
        return month;
    }

    public void setMonth(Date month) {
        this.month = month;
    }

    public String getAttData() {
        return attData;
    }

    public void setAttData(String attData) {
        this.attData = attData;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Integer getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Integer creatorId) {
        this.creatorId = creatorId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getRemainAttData() {
        return remainAttData;
    }

    public void setRemainAttData(String remainAttData) {
        this.remainAttData = remainAttData;
    }

    public List<String> getAttDataList() {
        return Arrays.asList(attData .split(",")).stream().map(s -> (s.trim())).collect(Collectors.toList());
    }

    public void setAttDataList(List<String> attDataList) {
        this.attDataList = attDataList;
    }

    public List<String> getRemainAttDataList() {
        return Arrays.asList(remainAttData .split(",")).stream().map(s -> (s.trim())).collect(Collectors.toList());
    }

    public void setRemainAttDataList(List<String> remainAttDataList) {
        this.remainAttDataList = remainAttDataList;
    }
}
