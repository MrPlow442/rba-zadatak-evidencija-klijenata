package hr.mlovrekovic.evidencijaklijenata.rest.v1.ops.model;

import hr.mlovrekovic.evidencijaklijenata.service.model.CardRequestStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CardStatusMessageDto(
        @NotBlank String oib,
        @NotNull CardRequestStatus status
) { }
