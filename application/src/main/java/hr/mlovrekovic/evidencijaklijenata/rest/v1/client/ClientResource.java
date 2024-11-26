package hr.mlovrekovic.evidencijaklijenata.rest.v1.client;

import hr.mlovrekovic.evidencijaklijenata.service.ClientService;
import hr.mlovrekovic.evidencijaklijenata.service.model.CardRequestStatus;
import hr.mlovrekovic.evidencijaklijenata.rest.v1.client.model.ClientDto;
import hr.mlovrekovic.evidencijaklijenata.rest.v1.client.model.ClientSearchDto;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/clients")
public class ClientResource {

    private final ClientService clientService;
    private final Map<String, ClientDto> clientStore = new HashMap<>(); //TODO: Replace with DB

    public ClientResource(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping
    public ResponseEntity<List<ClientDto>> getClients() {
        var clients = clientService.getClients();
        if (clients.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(clients, HttpStatus.OK);
    }

    @GetMapping("/{oib}")
    public ResponseEntity<ClientDto> getClient(@PathVariable String oib) {
        var client = clientService.getClient(oib);
        return client.map(clientDto -> new ResponseEntity<>(clientDto, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<ClientDto> createClient(@RequestBody @Valid ClientDto client) {
        
        if (clientStore.containsKey(client.getOib())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        clientStore.put(client.getOib(), client);
        return ResponseEntity.status(HttpStatus.CREATED).body(client);
    }

    @DeleteMapping("/{oib}")
    public ResponseEntity<Void> deleteClient(@PathVariable String oib) {
        if (!clientStore.containsKey(oib)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        clientStore.remove(oib);
        return ResponseEntity.noContent().build();
    }

    @PutMapping
    public ResponseEntity<ClientDto> replaceClient(@RequestBody @Valid ClientDto newClient) {
        if (!clientStore.containsKey(newClient.getOib())) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        clientStore.put(newClient.getOib(), newClient);
        return ResponseEntity.ok(newClient);
    }

    @PatchMapping
    public ResponseEntity<ClientDto> updateClient(@RequestBody Map<String, Object> updates) {
        if (!updates.containsKey("oib") || updates.get("oib") == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        String oib = updates.get("oib").toString();
        ClientDto existingClient = clientStore.get(oib);
        if (existingClient == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        updates.forEach((field, value) -> {
            switch (field) {
                case "firstName" -> existingClient.setFirstName((String) value);
                case "lastName" -> existingClient.setLastName((String) value);
                case "status" -> existingClient.setStatus(CardRequestStatus.valueOf((String) value));
                // OIB should not be updated
            }
        });

        return ResponseEntity.ok(existingClient);
    }

}
