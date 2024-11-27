package hr.mlovrekovic.evidencijaklijenata.service.model;

public record CardStatusMessage(
        String oib,
        CardRequestStatus status
) {
}
