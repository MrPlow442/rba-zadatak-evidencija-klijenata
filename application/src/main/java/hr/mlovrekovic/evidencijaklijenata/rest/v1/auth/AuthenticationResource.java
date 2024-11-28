package hr.mlovrekovic.evidencijaklijenata.rest.v1.auth;

import hr.mlovrekovic.evidencijaklijenata.persistence.model.User;
import hr.mlovrekovic.evidencijaklijenata.rest.v1.auth.model.LoginDto;
import hr.mlovrekovic.evidencijaklijenata.service.model.auth.LoginUserDto;
import hr.mlovrekovic.evidencijaklijenata.service.security.AuthenticationService;
import hr.mlovrekovic.evidencijaklijenata.service.security.JwtService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthenticationResource {
    private final JwtService jwtService;

    private final AuthenticationService authenticationService;

    public AuthenticationResource(JwtService jwtService, AuthenticationService authenticationService) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginDto> authenticate(@RequestBody LoginUserDto loginUserDto) {
        User authenticatedUser = authenticationService.authenticate(loginUserDto);
        String jwtToken = jwtService.generateToken(authenticatedUser);;
        return ResponseEntity.ok(new LoginDto(jwtToken, jwtService.getExpirationTime()));
    }
}
