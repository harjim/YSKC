package com.yskc.ms.entity.rs;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * @author huronghua
 * @email 267267134@qq.com
 * @date 2019-08-05 11:34:28
 */
@TableName("docProcessTemplate")
public class DocProcessTemplateEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @TableId
    private Integer id;
    /**
     *
     */
    private Integer processId;
    /**
     *
     */
    private Integer seq;
    /**
     *
     */
    private Integer templateId;
    /**
     *
     */
    private Boolean optional;
    /**
     *
     */
    private Boolean enabled;

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setProcessId(Integer processId) {
        this.processId = processId;
    }

    public Integer getProcessId() {
        return processId;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    public Integer getSeq() {
        return seq;
    }

    public void setTemplateId(Integer templateId) {
        this.templateId = templateId;
    }

    public Integer getTemplateId() {
        return templateId;
    }

    public void setOptional(Boolean optional) {
        this.optional = optional;
    }

    public Boolean getOptional() {
        return optional;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Boolean getEnabled() {
        return enabled;
    }

}
