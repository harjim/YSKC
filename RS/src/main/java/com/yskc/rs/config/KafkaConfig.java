package com.yskc.rs.config;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.config
 * @Author: zhangdingfu
 * @CreateTime: 2021-02-25 16:58
 * @Description: kafka配置
 */
@Configuration
public class KafkaConfig {

    @Value("${kafka.bootstrapServers}")
    private String bootstrapServers;
    @Value("${kafka.producer.acks}")
    private String producerAcks;
    @Value("${kafka.producer.retries}")
    private String producerRetries;
    @Value("${kafka.producer.batchSize}")
    private String producerBatchSize;
    @Value("${kafka.producer.lingerMs}")
    private String producerLingerMs;

    public Map<String, Object> producerConfig() {
        Map<String, Object> producerConfig = new HashMap<>();
        producerConfig.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        producerConfig.put(ProducerConfig.ACKS_CONFIG, producerAcks);
        producerConfig.put(ProducerConfig.RETRIES_CONFIG, producerRetries);
        producerConfig.put(ProducerConfig.BATCH_SIZE_CONFIG, producerBatchSize);
        producerConfig.put(ProducerConfig.LINGER_MS_CONFIG, producerLingerMs);
        producerConfig.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        producerConfig.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        return producerConfig;
    }

    public ProducerFactory<String, Object> producerFactory() {
        return new DefaultKafkaProducerFactory<String, Object>(producerConfig());
    }

    @Bean
    KafkaTemplate<String, Object> kafkaTemplate() {
        return new KafkaTemplate<String, Object>(producerFactory());
    }

}
