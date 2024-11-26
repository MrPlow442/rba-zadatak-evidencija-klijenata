package hr.mlovrekovic.evidencijaklijenata.persistence.repository;

import hr.mlovrekovic.evidencijaklijenata.persistence.model.Client;
import hr.mlovrekovic.evidencijaklijenata.service.model.CardRequestStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Long> {

    Optional<Client> findByNationalIdentifier(String nationalIdentifier);

    void deleteByNationalIdentifier(String nationalIdentifier);

    @Query("SELECT c FROM Client c WHERE " +
            "(:nationalIdentifier IS NULL OR c.nationalIdentifier = :nationalIdentifier) " +
            "AND (:status IS NULL OR c.status = :status)")
    List<Client> findByOptionalFilters(String nationalIdentifier, CardRequestStatus status);
}
