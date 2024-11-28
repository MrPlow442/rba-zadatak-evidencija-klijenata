package hr.mlovrekovic.evidencijaklijenata.service;

import hr.mlovrekovic.evidencijaklijenata.persistence.repository.CardRequestRepository;
import hr.mlovrekovic.evidencijaklijenata.rest.v1.cardrequest.model.CardRequestDto;
import hr.mlovrekovic.evidencijaklijenata.service.converter.CardRequestConverter;
import hr.mlovrekovic.evidencijaklijenata.service.exception.AlreadyExistsException;
import hr.mlovrekovic.evidencijaklijenata.service.exception.NotFoundException;
import hr.mlovrekovic.evidencijaklijenata.service.model.CardRequestStatus;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional
public class CardRequestService {
    private final CardRequestRepository cardRequestRepository;
    public CardRequestService(CardRequestRepository cardRequestRepository) {
        this.cardRequestRepository = cardRequestRepository;
    }

    public List<CardRequestDto> getAll() {
        return cardRequestRepository.findAll()
                .stream()
                .map(CardRequestConverter::toDto)
                .toList();
    }

    public Optional<CardRequestDto> getOneSafe(String oib) {
        return cardRequestRepository.findByOib(oib)
                .map(CardRequestConverter::toDto);
    }

    public CardRequestDto getOne(String oib) {
        return cardRequestRepository.findByOib(oib)
                .map(CardRequestConverter::toDto)
                .orElseThrow(() ->
                        new NotFoundException(String.format("Card request with OIB: %s not found", oib)));
    }

    public CardRequestDto create(CardRequestDto dto) {
        var existingCardRequest = cardRequestRepository.findByOib(dto.getOib());
        if (existingCardRequest.isPresent()) {
            throw new AlreadyExistsException(String.format("Card request with OIB: %s exists", dto.getOib()));
        }

        var entity = CardRequestConverter.toEntity(dto);
        return CardRequestConverter.toDto(cardRequestRepository.save(entity));
    }

    public CardRequestDto update(String oib, CardRequestDto dto) {
        if (oib == null) {
            throw new IllegalArgumentException("OIB cannot be null");
        }

        dto.setOib(oib);
        var existing = cardRequestRepository.findByOib(dto.getOib())
                .orElseThrow(() ->
                        new NotFoundException(String.format("Card request with OIB: %s not found", dto.getOib())));

        existing.setFirstName(dto.getFirstName());
        existing.setLastName(dto.getLastName());
        existing.setStatus(dto.getStatus());
        return CardRequestConverter.toDto(cardRequestRepository.save(existing));
    }

    public CardRequestDto partialUpdate(String oib, Map<String, Object> values) {
        if (oib == null) {
            throw new IllegalArgumentException("OIB cannot be null");
        }

        var existingCardRequest = cardRequestRepository.findByOib(oib)
                .orElseThrow(() -> new NotFoundException(String.format("Card request with OIB: %s not found", oib)));

        values.forEach((field, value) -> {
            switch (field) {
                case "firstName" -> existingCardRequest.setFirstName((String) value);
                case "lastName" -> existingCardRequest.setLastName((String) value);
                case "status" -> existingCardRequest.setStatus(CardRequestStatus.valueOf((String) value));
            }
        });

        return CardRequestConverter.toDto(cardRequestRepository.save(existingCardRequest));
    }

    public void delete(String oib) {
        cardRequestRepository.deleteByOib(oib);
    }
}
