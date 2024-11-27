package hr.mlovrekovic.evidencijaklijenata.service;

import hr.mlovrekovic.evidencijaklijenata.service.model.CardStatusMessage;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class CardStatusConsumer {

    private final ClientService clientService;

    public CardStatusConsumer(ClientService clientService) {
        this.clientService = clientService;
    }

    @KafkaListener(topics = "card-status", groupId = "card-status")
    public void handleMessage(CardStatusMessage message) {
        clientService.partialUpdateClient(message.oib(), Map.of("status", message.status()));
    }
}
