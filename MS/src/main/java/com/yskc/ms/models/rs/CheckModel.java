package com.yskc.ms.models.rs;

public class CheckModel <T> {
    private T data;
    private Double similarity;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Double getSimilarity() {
        return similarity;
    }

    public void setSimilarity(Double similarity) {
        this.similarity = similarity;
    }
}
