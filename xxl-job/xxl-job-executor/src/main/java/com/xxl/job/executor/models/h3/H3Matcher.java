package com.xxl.job.executor.models.h3;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class H3Matcher {
    /**
     * 类型
     */
    @JsonProperty("Type")
    private String type;
    /**
     * 条件
     */
    @JsonProperty("Matchers")
    private List<H3Matcher> matchers;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<H3Matcher> getMatchers() {
        return matchers;
    }

    @JsonProperty("Name")
    private String Name;
    @JsonProperty("Operator")
    private Integer Operator;
    @JsonProperty("Value")
    private String Value;

    public void setMatchers(List<H3Matcher> matchers) {
        this.matchers = matchers;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public Integer getOperator() {
        return Operator;
    }

    public void setOperator(Integer operator) {
        Operator = operator;
    }

    public String getValue() {
        return Value;
    }

    public void setValue(String value) {
        Value = value;
    }
}
