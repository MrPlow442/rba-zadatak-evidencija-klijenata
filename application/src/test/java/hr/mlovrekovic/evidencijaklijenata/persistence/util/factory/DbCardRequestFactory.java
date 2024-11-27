package hr.mlovrekovic.evidencijaklijenata.persistence.util.factory;

import hr.mlovrekovic.evidencijaklijenata.persistence.model.CardRequest;
import hr.mlovrekovic.evidencijaklijenata.persistence.repository.CardRequestRepository;
import hr.mlovrekovic.evidencijaklijenata.service.model.CardRequestStatus;
import org.springframework.stereotype.Service;

import java.util.function.Consumer;

@Service
public class DbCardRequestFactory {

    private final CardRequestRepository repository;

    public DbCardRequestFactory(CardRequestRepository repository) {
        this.repository = repository;
    }

    public CardRequest createCardRequest(Consumer<CardRequest> modifier) {
        var cardRequest = new CardRequest();
        cardRequest.setFirstName("Gospodin");
        cardRequest.setLastName("Covjek");
        cardRequest.setOib("12345678901");
        cardRequest.setStatus(CardRequestStatus.PENDING);
        modifier.accept(cardRequest);
        return repository.save(cardRequest);
    }

    public CardRequest createCardRequest() {
        return createCardRequest(c -> {});
    }
}
