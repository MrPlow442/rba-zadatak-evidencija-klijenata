package hr.mlovrekovic.evidencijaklijenata.service;

import hr.mlovrekovic.evidencijaklijenata.persistence.model.Client;
import hr.mlovrekovic.evidencijaklijenata.persistence.repository.ClientRepository;
import hr.mlovrekovic.evidencijaklijenata.rest.v1.client.model.ClientDto;
import hr.mlovrekovic.evidencijaklijenata.rest.v1.client.model.ClientSearchDto;
import hr.mlovrekovic.evidencijaklijenata.service.model.CardRequestStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ClientService {

    private static final String NATIONAL_IDENTIFIER_FIELD_NAME = "oib";
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

    public Optional<ClientDto> getClient(String nationalIdentifier) {
        return clientRepository.findByNationalIdentifier(nationalIdentifier)
                .map(ClientConverter::toDto);
    }

    public ClientDto createClient(ClientDto dto) {
        var existingClient = clientRepository.findByNationalIdentifier(dto.getOib());
        if (existingClient.isPresent()) {
            throw new IllegalArgumentException(String.format("Client with OIB: %s exists", dto.getOib()));
        }

        var entity = ClientConverter.toEntity(dto);
        return ClientConverter.toDto(clientRepository.save(entity));
    }

    public ClientDto updateClient(ClientDto dto) {
        if (dto.getOib() == null) {
            throw new IllegalArgumentException("OIB cannot be null");
        }

        var existingClient = clientRepository.findByNationalIdentifier(dto.getOib())
                .orElseThrow(() ->
                        new IllegalArgumentException(String.format("Client with OIB: %s not found", dto.getOib())));

        var entity = ClientConverter.toEntity(dto);
        return ClientConverter.toDto(clientRepository.save(entity));
    }

    public ClientDto partialUpdateClient(Map<String, Object> values) {
        if (!values.containsKey(NATIONAL_IDENTIFIER_FIELD_NAME) || values.get(NATIONAL_IDENTIFIER_FIELD_NAME) == null) {
            throw new IllegalArgumentException("OIB cannot be null");
        }

        var oib = values.get(NATIONAL_IDENTIFIER_FIELD_NAME).toString();
        var existingClient = clientRepository.findByNationalIdentifier(oib)
                .orElseThrow(() -> new IllegalArgumentException(String.format("Client with OIB: %s not found", oib)));

        values.forEach((field, value) -> {
            switch (field) {
                case "firstName" -> existingClient.setFirstName((String) value);
                case "lastName" -> existingClient.setLastName((String) value);
                case "status" -> existingClient.setStatus(CardRequestStatus.valueOf((String) value));
                // OIB should not be updated
            }
        });

        return ClientConverter.toDto(clientRepository.save(existingClient));
    }

    public void deleteClient(String oib) {
        clientRepository.deleteByNationalIdentifier(oib);
    }
}
