package com.yskc.ms.entity.rs;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.yskc.ms.entity.rs.models.BaseEntity;

/**
 * @program: ms
 * @description:
 * @author: wjy
 * @create: 2022-09-15 14:14
 **/
@TableName("p_achievement_file")
public class RsAchievementFileEntity extends BaseEntity {
    @TableId
    private Integer id;
    private Integer companyId;
    private Integer achievementId;
    private String filepath;
    private String fileName;
    private String stageKey;
    private Integer listId;
    private Integer seq;
    private Integer converResult;

    public RsAchievementFileEntity() {
    }

    public RsAchievementFileEntity(Integer companyId,Integer achievementId,
                                   String filepath, String fileName,String stageKey,
                                   Integer listId, Integer seq, Integer converResult) {
        this.companyId = companyId;
        this.achievementId = achievementId;
        this.filepath = filepath;
        this.fileName = fileName;
        this.stageKey = stageKey;
        this.listId = listId;
        this.seq = seq;
        this.converResult = converResult;
    }

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

    public Integer getListId() {
        return listId;
    }

    public void setListId(Integer listId) {
        this.listId = listId;
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
