package ru.t1.java.demo.buildBeansTopicsKafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.*;
import org.springframework.kafka.config.TopicBuilder;

// Автоматическое создание Тем и разделов через внедрения бин в программу
// можно создать новый класс для автоматического удаления Тем и разделов через внедрения бин в программу
// код программы расположен в папке adminKafka
// установлены комментарии для автоматического ограничения создания бин
@Configuration
public class BuildBeansTopicsKafkaConfig {
    @Bean
    public NewTopic t1_demo_metrics() {
        return TopicBuilder.name("t1_demo_metrics") // Имя первого топика
            .partitions(3) // Количество партиций
            .replicas(1) // Количество реплик
            .build();
    }

    @Bean
    public NewTopic t1_demo_accounts () {
        return TopicBuilder.name("t1_demo_accounts") // Имя второго топика
            .partitions(3) // Количество партиций
            .replicas(1) // Количество реплик
            .build();
    }
    @Bean
    public NewTopic t1_demo_transactions () {
        return TopicBuilder.name("t1_demo_transactions") // Имя второго топика
            .partitions(3) // Количество партиций
            .replicas(1) // Количество реплик
            .build();
    }
}
