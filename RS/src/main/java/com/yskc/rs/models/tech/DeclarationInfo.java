package com.yskc.rs.models.tech;

import com.yskc.common.utils.JsonUtils;
import io.swagger.models.auth.In;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * 技改申请报告
 * @author huronghua
 */
public class DeclarationInfo {
    private Integer id;
    private Integer projectId;
    private Integer companyId;
    private Integer seq;
    private String key;
    private String parentKey;
    private String title;
    private String content;
    private Integer creatorId;
    private Integer lastUpdatorId;
    private Date createTime;
    private Date lastUpdateTime;
    private DeclarationItem textItem;
    private DeclarationItem imageItem;
    private DeclarationItem tableItem;

    private List<DeclarationItem> declarationItems;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    private List<DeclarationItem> getDeclarationItems() {
        if (StringUtils.isEmpty(content)) {
            return new ArrayList<>();
        }
        return JsonUtils.jsonToList(content,DeclarationItem.class);
    }

    private void setDeclarationItems(List<DeclarationItem> declarationItems) {
        this.declarationItems = declarationItems;
    }
    public DeclarationItem getTextItem() {
        if(this.textItem!=null){
            return this.textItem;
        }
        declarationItems=getDeclarationItems().stream().filter(item->Integer.valueOf(1).equals(item.getType())).collect(Collectors.toList());
        if(declarationItems.size()==0){
            return null;
        }
        return declarationItems.get(0);
    }

    public void setTextItem(DeclarationItem textItem) {
        this.textItem = textItem;
    }

    public DeclarationItem getImageItem() {
        if(this.imageItem!=null){
            return this.imageItem;
        }
        declarationItems=getDeclarationItems().stream().filter(item->Integer.valueOf(3).equals(item.getType())).collect(Collectors.toList());
        if(declarationItems.size()==0){
            return null;
        }
        return declarationItems.get(0);
    }

    public void setImageItem(DeclarationItem imageItem) {
        this.imageItem = imageItem;
    }

    public DeclarationItem getTableItem() {
        if(this.tableItem!=null){
            return this.tableItem;
        }
        declarationItems=getDeclarationItems().stream().filter(item->Integer.valueOf(2).equals(item.getType())).collect(Collectors.toList());
        if(declarationItems.size()==0){
            return null;
        }
        return declarationItems.get(0);
    }

    public void setTableItem(DeclarationItem tableItem) {
        this.tableItem = tableItem;
    }
    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getParentKey() {
        return parentKey;
    }

    public void setParentKey(String parentKey) {
        this.parentKey = parentKey;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Integer creatorId) {
        this.creatorId = creatorId;
    }

    public Integer getLastUpdatorId() {
        return lastUpdatorId;
    }

    public void setLastUpdatorId(Integer lastUpdatorId) {
        this.lastUpdatorId = lastUpdatorId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }
}
