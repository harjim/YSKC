package com.yskc.rs.entity.project;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.yskc.rs.entity.tech.BaseEntity;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.controller
 * @Author: zhangdingfu
 * @CreateTime: 2021-11-18 10:32
 * @Description: 成果文件实体
 */
@TableName("p_achievement_file")
public class AchievementFileEntity extends BaseEntity {

    @TableId
    private Integer id;

    private Integer companyId;
    /**
     * 成果id
     */
    private Integer achievementId;
    private Integer listId;

    /**
     * 文件路径
     */
    private String filepath;
    /**
     * 文件名称
     */
    private String fileName;

    /**
     * 阶段key
     */
    private String stageKey;

    private Integer seq;
    private Integer converResult;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Integer getAchievementId() {
        return achievementId;
    }

    public void setAchievementId(Integer achievementId) {
        this.achievementId = achievementId;
    }

    public Integer getListId() {
        return listId;
    }

    public void setListId(Integer listId) {
        this.listId = listId;
    }

    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getStageKey() {
        return stageKey;
    }

    public void setStageKey(String stageKey) {
        this.stageKey = stageKey;
    }

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    public Integer getConverResult() {
        return converResult;
    }

    public void setConverResult(Integer converResult) {
        this.converResult = converResult;
    }
}
