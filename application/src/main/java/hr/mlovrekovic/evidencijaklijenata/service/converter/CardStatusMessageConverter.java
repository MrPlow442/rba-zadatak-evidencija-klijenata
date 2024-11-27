package hr.mlovrekovic.evidencijaklijenata.service.converter;

import hr.mlovrekovic.evidencijaklijenata.rest.v1.ops.model.CardStatusMessageDto;
import hr.mlovrekovic.evidencijaklijenata.service.model.CardStatusMessage;

public class CardStatusMessageConverter {
    public static CardStatusMessage toEntity(CardStatusMessageDto dto) {
        return new CardStatusMessage(
                dto.oib(),
                dto.status()
        );
    }
}
