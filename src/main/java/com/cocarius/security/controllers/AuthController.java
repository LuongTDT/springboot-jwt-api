package com.cocarius.security.controllers;

import com.cocarius.security.auth.AuthenticationRequest;
import com.cocarius.security.auth.AuthenticationResponse;
import com.cocarius.security.auth.AuthenticationService;
import com.cocarius.security.auth.RegisterRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(value = "/api/v1/auth")
public class AuthController {
    private final AuthenticationService authenticationService;
    private final PasswordEncoder passwordEncoder;
    @Autowired
    public AuthController(AuthenticationService authenticationService, PasswordEncoder passwordEncoder) {
        this.authenticationService = authenticationService;
        this.passwordEncoder = passwordEncoder;
    }
    //This api is used to get jwt token,
    // after server does several verifying and then return the token back to header
    @PostMapping("/sign-in")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody AuthenticationRequest request) {
        try {
            //Do authenticate the account and return a authentication object
            AuthenticationResponse authenticationResponse = authenticationService.authenticate(request);
            return ResponseEntity.ok()
                    .header(HttpHeaders.AUTHORIZATION, authenticationResponse.getToken())
                    .body(authenticationResponse);
        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> registerUser(@RequestBody RegisterRequest registerRequest){
        AuthenticationResponse authenticationResponse= authenticationService.register(registerRequest);
        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, authenticationResponse.getToken())
                .body(authenticationResponse);
    }
    @GetMapping
    public String greeting (){
        final String pass = "password";
        final String encodedPass1 = "$2a$10$s/Hv25aRhut85bCE/roqf.bnCPvBbc2nUVWyEEmf4sfN7tZ524ozC";
        final String encodedPass2 = "$2a$10$AijjnJv8eDt2bPmiryyANey7h194ngBtS3ouA3DX6el9NRhRkSIQ.";

        return String.valueOf(passwordEncoder.matches(pass,encodedPass1))+"\n"+String.valueOf(passwordEncoder.matches(pass,encodedPass2));
    }
}
