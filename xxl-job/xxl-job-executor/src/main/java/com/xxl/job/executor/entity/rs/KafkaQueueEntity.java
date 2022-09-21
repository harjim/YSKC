package com.xxl.job.executor.entity.rs;

import com.baomidou.mybatisplus.annotations.TableName;

import java.util.Date;

/**
 * @Author: zhangdingfu
 * @CreateTime: 2021-03-16 08:07
 * @Description: kafka队列实例
 */
@TableName("kafka_queue")
public class KafkaQueueEntity {

    private Integer id;
    private String topic;

    private String key;

    private String data;

    private Date createTime;

    private Date lastUpdateTime;

    private Integer status;

    public KafkaQueueEntity(String topic, String key, String data, Date now){
        this.topic = topic;
        this.key = key;
        this.data = data;
        this.createTime = now;
        this.status = 0;
    }

    public KafkaQueueEntity(){}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    @Override
    public String toString() {
        return "KafkaQueueEntity{" +
                "topic='" + topic + '\'' +
                ", key='" + key + '\'' +
                ", data='" + data + '\'' +
                '}';
    }
}
