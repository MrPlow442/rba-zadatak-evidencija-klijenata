package hr.mlovrekovic.evidencijaklijenata.service;

import hr.mlovrekovic.evidencijaklijenata.persistence.repository.ClientRepository;
import hr.mlovrekovic.evidencijaklijenata.rest.v1.client.model.ClientDto;
import hr.mlovrekovic.evidencijaklijenata.service.exception.AlreadyExistsException;
import hr.mlovrekovic.evidencijaklijenata.service.exception.NotFoundException;
import hr.mlovrekovic.evidencijaklijenata.service.model.CardRequestStatus;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional
public class ClientService {
    private final ClientRepository clientRepository;
    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public List<ClientDto> getClients() {
        return clientRepository.findAll()
                .stream()
                .map(ClientConverter::toDto)
                .toList();
    }

    public Optional<ClientDto> getClientSafe(String nationalIdentifier) {
        return clientRepository.findByNationalIdentifier(nationalIdentifier)
                .map(ClientConverter::toDto);
    }

    public ClientDto getClient(String nationalIdentifier) {
        return clientRepository.findByNationalIdentifier(nationalIdentifier)
                .map(ClientConverter::toDto)
                .orElseThrow(() ->
                        new NotFoundException(String.format("Client with OIB: %s not found", nationalIdentifier)));
    }

    public ClientDto createClient(ClientDto dto) {
        var existingClient = clientRepository.findByNationalIdentifier(dto.getOib());
        if (existingClient.isPresent()) {
            throw new AlreadyExistsException(String.format("Client with OIB: %s exists", dto.getOib()));
        }

        var entity = ClientConverter.toEntity(dto);
        return ClientConverter.toDto(clientRepository.save(entity));
    }

    public ClientDto updateClient(String oib, ClientDto dto) {
        if (oib == null) {
            throw new IllegalArgumentException("OIB cannot be null");
        }

        dto.setOib(oib);
        clientRepository.findByNationalIdentifier(dto.getOib())
                .orElseThrow(() ->
                        new NotFoundException(String.format("Client with OIB: %s not found", dto.getOib())));

        var entity = ClientConverter.toEntity(dto);
        return ClientConverter.toDto(clientRepository.save(entity));
    }

    public ClientDto partialUpdateClient(String oib, Map<String, Object> values) {
        if (oib == null) {
            throw new IllegalArgumentException("OIB cannot be null");
        }

        var existingClient = clientRepository.findByNationalIdentifier(oib)
                .orElseThrow(() -> new NotFoundException(String.format("Client with OIB: %s not found", oib)));

        values.forEach((field, value) -> {
            switch (field) {
                case "firstName" -> existingClient.setFirstName((String) value);
                case "lastName" -> existingClient.setLastName((String) value);
                case "status" -> existingClient.setStatus(CardRequestStatus.valueOf((String) value));
            }
        });

        return ClientConverter.toDto(clientRepository.save(existingClient));
    }

    public void deleteClient(String oib) {
        clientRepository.deleteByNationalIdentifier(oib);
    }
}
