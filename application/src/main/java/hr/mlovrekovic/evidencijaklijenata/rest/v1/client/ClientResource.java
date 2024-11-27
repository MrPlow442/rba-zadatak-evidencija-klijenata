package hr.mlovrekovic.evidencijaklijenata.rest.v1.client;

import hr.mlovrekovic.evidencijaklijenata.rest.v1.client.model.ClientDto;
import hr.mlovrekovic.evidencijaklijenata.service.ClientService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/clients")
@CrossOrigin(origins = "http://localhost:3000")
public class ClientResource {

    private final ClientService clientService;

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

    @PostMapping
    public ResponseEntity<ClientDto> createClient(@RequestBody @Valid ClientDto client) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(clientService.createClient(client));
    }

    @GetMapping("/{oib}")
    public ResponseEntity<ClientDto> getClient(@PathVariable String oib) {
        return ResponseEntity.status(HttpStatus.OK).body(clientService.getClient(oib));
    }

    @PutMapping("/{oib}")
    public ResponseEntity<ClientDto> replaceClient(@PathVariable String oib,
                                                   @RequestBody @Valid ClientDto newClient) {
        return ResponseEntity.ok(clientService.updateClient(oib, newClient));
    }

    @DeleteMapping("/{oib}")
    public ResponseEntity<Void> deleteClient(@PathVariable String oib) {
        clientService.deleteClient(oib);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{oib}")
    public ResponseEntity<ClientDto> updateClient(@PathVariable String oib, @RequestBody Map<String, Object> values) {
        return ResponseEntity.ok(clientService.partialUpdateClient(oib, values));
    }

}
