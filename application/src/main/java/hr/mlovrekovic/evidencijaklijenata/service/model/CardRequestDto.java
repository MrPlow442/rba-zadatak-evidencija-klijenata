package hr.mlovrekovic.evidencijaklijenata.service.model;

public class CardRequestDto {
    private String firstName;
    private String lastName;
    private CardRequestStatus status;
    private String oib;

    public static Builder builder() {
        return new Builder();
    }

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

    public CardRequestStatus getStatus() {
        return status;
    }

    public void setStatus(CardRequestStatus status) {
        this.status = status;
    }

    public String getOib() {
        return oib;
    }

    public void setOib(String oib) {
        this.oib = oib;
    }


    public static final class Builder {
        private String firstName;
        private String lastName;
        private CardRequestStatus status;
        private String oib;

        private Builder() {
        }

        public Builder withFirstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public Builder withLastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public Builder withStatus(CardRequestStatus status) {
            this.status = status;
            return this;
        }

        public Builder withOib(String oib) {
            this.oib = oib;
            return this;
        }

        public CardRequestDto build() {
            CardRequestDto cardRequestDto = new CardRequestDto();
            cardRequestDto.setFirstName(firstName);
            cardRequestDto.setLastName(lastName);
            cardRequestDto.setStatus(status);
            cardRequestDto.setOib(oib);
            return cardRequestDto;
        }
    }
}
