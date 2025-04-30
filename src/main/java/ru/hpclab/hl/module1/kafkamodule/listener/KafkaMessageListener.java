package ru.hpclab.hl.module1.kafkamodule.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import ru.hpclab.hl.module1.kafkamodule.queue.KafkaOperationMessage;
import ru.hpclab.hl.module1.kafkamodule.dispatch.KafkaMessageDispatcher;

import java.util.List;

@Component
@RequiredArgsConstructor
public class KafkaMessageListener {

    private final ObjectMapper objectMapper;
    private final KafkaMessageDispatcher kafkaMessageDispatcher;
    private static final Logger log = LoggerFactory.getLogger(KafkaMessageListener.class);

    @KafkaListener(
            topics = "${kafka.topic:articles-users-downloads}",
            groupId = "${kafka.groupId:seryak0ff-consumer-group}",
            concurrency = "${kafka.concurrency:2}", // Можно настроить по количеству партиций
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void handleMessage(List<String> messageJsonList) {
        for (String messageJson : messageJsonList) {
            try {
                KafkaOperationMessage message = objectMapper.readValue(messageJson, KafkaOperationMessage.class);
//                log.info("Received Kafka message: {}", message);
                kafkaMessageDispatcher.dispatch(message);
            } catch (Exception e) {
                log.error("Error while parsing or dispatching Kafka message: {}", messageJson, e);
            }
        }
    }
}