package hr.mlovrekovic.evidencijaklijenata.service;

import hr.mlovrekovic.evidencijaklijenata.rest.v1.ops.model.CardStatusMessageDto;
import hr.mlovrekovic.evidencijaklijenata.service.converter.CardStatusMessageConverter;
import hr.mlovrekovic.evidencijaklijenata.service.model.CardStatusMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class CardStatusMessagingService {
    private static final Logger LOGGER = LoggerFactory.getLogger(CardStatusMessagingService.class);
    private static final String TOPIC = "card-status";
    private final KafkaTemplate<String, CardStatusMessage> kafkaTemplate;
    private final CardRequestService cardRequestService;

    public CardStatusMessagingService(KafkaTemplate<String, CardStatusMessage> kafkaTemplate,
                                      CardRequestService cardRequestService) {
        this.kafkaTemplate = kafkaTemplate;
        this.cardRequestService = cardRequestService;
    }

    public void sendMessage(CardStatusMessageDto message) {
        kafkaTemplate.send(TOPIC, CardStatusMessageConverter.toEntity(message));
        LOGGER.info("Message sent: topic={}, value={}", TOPIC, message);
    }

    @KafkaListener(topics = TOPIC, groupId = "card-status")
    public void handleMessage(CardStatusMessage message) {
        LOGGER.info("Received message: topic={}, value={}", TOPIC, message);
        try {
            cardRequestService.partialUpdate(message.oib(), Map.of("status", message.status()));
        } catch (Exception e) {
            LOGGER.error("Error while processing message: {}, error: {}", message, e.getMessage(), e);
        }
    }
}
