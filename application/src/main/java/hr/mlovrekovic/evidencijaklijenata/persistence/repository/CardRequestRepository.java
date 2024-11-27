package hr.mlovrekovic.evidencijaklijenata.persistence.repository;

import hr.mlovrekovic.evidencijaklijenata.persistence.model.CardRequest;
import hr.mlovrekovic.evidencijaklijenata.service.model.CardRequestStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CardRequestRepository extends JpaRepository<CardRequest, Long> {

    Optional<CardRequest> findByOib(String nationalIdentifier);

    void deleteByOib(String nationalIdentifier);
}
