package com.yskc.rs.models.docFile;

import com.yskc.rs.entity.project.DocFileMeetingEntity;

import java.io.Serializable;
import java.util.List;

/**
 * @DateTime: 2022/2/16 14:51
 * @Description:
 * @author: hsx
 */
public class MeetingFromModel implements Serializable {
    /**
     * 项目RD
     */
    private String rd;

    /**
     * 会议纪要文件id
     */
    private Integer pdocFileId;

    /**
     * 项目id
     */
    private Integer projectId;

    /**
     * 附件
     */
    private List<DocFileMeetingEntity> files;

    /**
     * 会议内容
     */
    private String mattersInvolved;

    /**
     * 会议主题
     */
    private String theme;

    /**
     * 会议数据
     */
    private String data;

    private Object checkedList;

    public Integer getPdocFileId() {
        return pdocFileId;
    }

    public void setPdocFileId(Integer pdocFileId) {
        this.pdocFileId = pdocFileId;
    }

    public String getRd() {
        return rd;
    }

    public void setRd(String rd) {
        this.rd = rd;
    }

    public List<DocFileMeetingEntity> getFiles() {
        return files;
    }

    public void setFiles(List<DocFileMeetingEntity> files) {
        this.files = files;
    }

    public String getMattersInvolved() {
        return mattersInvolved;
    }

    public void setMattersInvolved(String mattersInvolved) {
        this.mattersInvolved = mattersInvolved;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Object getCheckedList() {
        return checkedList;
    }

    public void setCheckedList(Object checkedList) {
        this.checkedList = checkedList;
    }
}
