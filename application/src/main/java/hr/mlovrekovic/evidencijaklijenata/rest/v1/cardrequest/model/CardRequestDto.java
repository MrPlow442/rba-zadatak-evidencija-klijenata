package hr.mlovrekovic.evidencijaklijenata.rest.v1.cardrequest.model;

import hr.mlovrekovic.evidencijaklijenata.service.model.CardRequestStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class CardRequestDto {

    @NotBlank
    @Size(max = 100)
    private String firstName;
    @NotBlank
    @Size(max = 100)
    private String lastName;
    @NotBlank
    @Pattern(regexp = "^[0-9]*$")
    @Size(min = 11, max = 11)
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
