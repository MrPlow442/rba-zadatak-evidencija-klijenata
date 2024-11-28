package hr.mlovrekovic.evidencijaklijenata.rest.v1.auth.model;

public record LoginDto(
    String token,
    long expiresIn
) {
}
