package hr.mlovrekovic.evidencijaklijenata.rest.v1.cardrequest;

import hr.mlovrekovic.evidencijaklijenata.rest.v1.cardrequest.model.CardRequestDto;
import hr.mlovrekovic.evidencijaklijenata.service.CardRequestService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/card-request")
@CrossOrigin(origins = "http://localhost:3000")
public class CardRequestResource {

    private final CardRequestService cardRequestService;

    public CardRequestResource(CardRequestService cardRequestService) {
        this.cardRequestService = cardRequestService;
    }

    @GetMapping
    public ResponseEntity<List<CardRequestDto>> getCardRequests() {
        return new ResponseEntity<>(cardRequestService.getAll(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CardRequestDto> createCardRequest(@RequestBody @Valid CardRequestDto client) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(cardRequestService.create(client));
    }

    @GetMapping("/{oib}")
    public ResponseEntity<CardRequestDto> getCardRequest(@PathVariable String oib) {
        return ResponseEntity.status(HttpStatus.OK).body(cardRequestService.getOne(oib));
    }

    @PutMapping("/{oib}")
    public ResponseEntity<CardRequestDto> replaceCardRequest(@PathVariable String oib,
                                                             @RequestBody @Valid CardRequestDto newClient) {
        return ResponseEntity.ok(cardRequestService.update(oib, newClient));
    }

    @DeleteMapping("/{oib}")
    public ResponseEntity<Void> deleteCardRequest(@PathVariable String oib) {
        cardRequestService.delete(oib);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{oib}")
    public ResponseEntity<CardRequestDto> updateCardRequest(@PathVariable String oib, @RequestBody Map<String, Object> values) {
        return ResponseEntity.ok(cardRequestService.partialUpdate(oib, values));
    }

}
