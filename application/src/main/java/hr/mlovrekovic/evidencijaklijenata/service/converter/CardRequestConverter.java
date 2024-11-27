package hr.mlovrekovic.evidencijaklijenata.service.converter;

import hr.mlovrekovic.evidencijaklijenata.persistence.model.CardRequest;
import hr.mlovrekovic.evidencijaklijenata.rest.v1.cardrequest.model.CardRequestDto;

public class CardRequestConverter {

    public static CardRequest toEntity(CardRequestDto dto) {
        CardRequest cardRequest = new CardRequest();
        cardRequest.setOib(dto.getOib());
        cardRequest.setFirstName(dto.getFirstName());
        cardRequest.setLastName(dto.getLastName());
        cardRequest.setStatus(dto.getStatus());
        return cardRequest;
    }

    public static CardRequestDto toDto(CardRequest cardRequest) {
        CardRequestDto cardRequestDto = new CardRequestDto();
        cardRequestDto.setOib(cardRequest.getOib());
        cardRequestDto.setFirstName(cardRequest.getFirstName());
        cardRequestDto.setLastName(cardRequest.getLastName());
        cardRequestDto.setStatus(cardRequest.getStatus());
        return cardRequestDto;
    }
}
