package hr.mlovrekovic.evidencijaklijenata.rest.v1.ops;

import hr.mlovrekovic.evidencijaklijenata.rest.v1.ops.model.CardStatusMessageDto;
import hr.mlovrekovic.evidencijaklijenata.service.CardStatusMessagingService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/operations")
public class OperationsResource {

    private final CardStatusMessagingService cardStatusMessagingService;

    public OperationsResource(CardStatusMessagingService cardStatusMessagingService) {
        this.cardStatusMessagingService = cardStatusMessagingService;
    }

    @PostMapping("/card-request:send-message")
    public ResponseEntity<Void> sendStatusMessage(@RequestBody @Valid CardStatusMessageDto message) {
        cardStatusMessagingService.sendMessage(message);
        return ResponseEntity.noContent().build();
    }
}
