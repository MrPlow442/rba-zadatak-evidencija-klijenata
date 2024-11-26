package hr.mlovrekovic.evidencijaklijenata.rest.v1.client.model;

import hr.mlovrekovic.evidencijaklijenata.service.model.CardRequestStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ClientDto {

    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    @NotBlank
    // @Oib // custom oib validator
    private String oib;
    @NotNull
    private CardRequestStatus status;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getOib() {
        return oib;
    }

    public void setOib(String oib) {
        this.oib = oib;
    }

    public CardRequestStatus getStatus() {
        return status;
    }

    public void setStatus(CardRequestStatus status) {
        this.status = status;
    }
}
