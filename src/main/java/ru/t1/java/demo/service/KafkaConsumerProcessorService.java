package ru.t1.java.demo.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.retrytopic.DestinationTopic;
import org.springframework.stereotype.Service;
import ru.t1.java.demo.buildBeansTopicsKafka.BuildBeansTopicsKafkaConfig;
import ru.t1.java.demo.configuration.kafkaConfigProducer.KafkaConfigProducer;
import ru.t1.java.demo.configuration.kafkaConsumer.KafkaCreateConsumer;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.util.Collections;
import java.util.Properties;
import java.util.stream.StreamSupport;

@Slf4j
@Service
public class KafkaConsumerProcessorService {

    private final KafkaCreateConsumer kafkaCreateConsumer;
    private final BuildBeansTopicsKafkaConfig kafkaTopics;

    @Autowired
    KafkaConsumerProcessorService(
        KafkaCreateConsumer kafkaCreateConsumer,
        BuildBeansTopicsKafkaConfig kafkaTopics
    )
    {
        this.kafkaCreateConsumer = kafkaCreateConsumer;
        this.kafkaTopics = kafkaTopics;
    }

    public String t1DemoAccountsConsumer(){
        KafkaConsumer<String, String> kafkaConsumer = kafkaCreateConsumer.getKafkaConsumer();
        kafkaConsumer.subscribe(Pattern.compile(kafkaTopics.t1_demo_accounts().name()));


        ObjectMapper mapper = new ObjectMapper();
        String jsonString = null;
        Map<String,String> map = new HashMap<>();
        for(ConsumerRecord<String, String> consumerRecord: consumerRecords(kafkaConsumer)){
            map.put(consumerRecord.key(),consumerRecord.value());
            try {
                jsonString = mapper.writeValueAsString(map);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return jsonString;
    }
    public String t1DemoATransactionConsumer(){
        KafkaConsumer<String, String> kafkaConsumer = kafkaCreateConsumer.getKafkaConsumer();
        kafkaConsumer.subscribe(Pattern.compile(kafkaTopics.t1_demo_transactions().name()));

        ObjectMapper mapper = new ObjectMapper();
        String jsonString = null;
        Map<String,String> map = new HashMap<>();
        for(ConsumerRecord<String, String> consumerRecord: consumerRecords(kafkaConsumer)){
            map.put(consumerRecord.key(),consumerRecord.value());
            try {
                jsonString = mapper.writeValueAsString(map);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return jsonString;
    }
    public ConsumerRecords<String, String> consumerRecords(KafkaConsumer<String, String> kafkaConsumer){
        return kafkaConsumer.poll(Duration.ofMillis(100));
    }

    //для проверки
    public static void main(String[] args) {
        // "t1_demo_accounts"
        KafkaConsumer<String, String> consumer = getKafkaConsumer();
        consumer.subscribe(Pattern.compile("t1_demo_transactions"));

        ConsumerRecords<String, String> records = consumer.poll(Duration.ofSeconds(100));

        StreamSupport.stream(records.spliterator(), false).forEach(record -> log.info("records: {}", record));
        records.forEach(record -> {
            System.out.printf("Consumed record with key %s and value %s%n", record.key(), record.value());
        });
        consumer.close();
    }

    private static KafkaConsumer<String, String> getKafkaConsumer() {
        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "unique-group-id");
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        return new KafkaConsumer<>(props);
    }
}
