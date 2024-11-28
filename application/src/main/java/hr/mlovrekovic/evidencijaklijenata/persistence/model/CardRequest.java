package hr.mlovrekovic.evidencijaklijenata.persistence.model;

import hr.mlovrekovic.evidencijaklijenata.service.model.CardRequestStatus;
import jakarta.persistence.*;

import java.util.Objects;
import java.util.StringJoiner;

@Entity
@Table(name = "card_request")
public class CardRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "oib", nullable = false, unique = true)
    private String oib;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private CardRequestStatus status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getOib() {
        return oib;
    }

    public void setOib(String nationalIdentifier) {
        this.oib = nationalIdentifier;
    }

    public CardRequestStatus getStatus() {
        return status;
    }

    public void setStatus(CardRequestStatus status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CardRequest cardRequest = (CardRequest) o;
        return Objects.equals(id, cardRequest.id) && Objects.equals(firstName, cardRequest.firstName) && Objects.equals(lastName, cardRequest.lastName) && Objects.equals(oib, cardRequest.oib) && status == cardRequest.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, oib, status);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", CardRequest.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("firstName='" + firstName + "'")
                .add("lastName='" + lastName + "'")
                .add("nationalIdentifier='" + oib + "'")
                .add("status=" + status)
                .toString();
    }
}