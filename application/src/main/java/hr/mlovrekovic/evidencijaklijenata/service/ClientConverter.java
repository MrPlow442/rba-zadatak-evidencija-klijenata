package hr.mlovrekovic.evidencijaklijenata.service;

import hr.mlovrekovic.evidencijaklijenata.persistence.model.Client;
import hr.mlovrekovic.evidencijaklijenata.rest.v1.client.model.ClientDto;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

public class ClientConverter {

    public static Client toEntity(ClientDto dto) {
        Client client = new Client();
        client.setNationalIdentifier(dto.getOib());
        client.setFirstName(dto.getFirstName());
        client.setLastName(dto.getLastName());
        client.setStatus(dto.getStatus());
        return client;
    }

    public static ClientDto toDto(Client client) {
        ClientDto clientDto = new ClientDto();
        clientDto.setOib(client.getNationalIdentifier());
        clientDto.setFirstName(client.getFirstName());
        clientDto.setLastName(client.getLastName());
        clientDto.setStatus(client.getStatus());
        return clientDto;
    }
}
