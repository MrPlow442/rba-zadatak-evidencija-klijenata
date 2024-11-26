package hr.mlovrekovic.evidencijaklijenata.persistence.model;

import hr.mlovrekovic.evidencijaklijenata.service.model.CardRequestStatus;
import jakarta.persistence.*;

import java.util.Objects;
import java.util.StringJoiner;

@Entity
@Table(name = "client")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "national_identifier", nullable = false, unique = true)
    private String nationalIdentifier;

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

    public String getNationalIdentifier() {
        return nationalIdentifier;
    }

    public void setNationalIdentifier(String nationalIdentifier) {
        this.nationalIdentifier = nationalIdentifier;
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
        Client client = (Client) o;
        return Objects.equals(id, client.id) && Objects.equals(firstName, client.firstName) && Objects.equals(lastName, client.lastName) && Objects.equals(nationalIdentifier, client.nationalIdentifier) && status == client.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, nationalIdentifier, status);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Client.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("firstName='" + firstName + "'")
                .add("lastName='" + lastName + "'")
                .add("nationalIdentifier='" + nationalIdentifier + "'")
                .add("status=" + status)
                .toString();
    }
}