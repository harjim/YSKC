package com.yskc.ms.models.Article;

import com.yskc.ms.models.params.PageParams;

public class QueryArticleModel extends PageParams {
    private String author;  //作者
    private String title;   //标题
    private String source;  //来源
    private Integer type;   //类型

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
