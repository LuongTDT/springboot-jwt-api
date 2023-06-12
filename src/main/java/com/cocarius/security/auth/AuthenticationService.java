package com.cocarius.security.auth;

import com.cocarius.security.jwt.JwtUtils;
import com.cocarius.security.role.Role;
import com.cocarius.security.user.User;
import com.cocarius.security.user.UserRepository;
import com.cocarius.security.user.UserService;
import com.cocarius.security.utils.EmailUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * @author LuongTDT
 */
@Service
public class AuthenticationService {
    private final UserRepository userRepository;
    private final JwtUtils jwtUtils;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    @Autowired
    public AuthenticationService(UserRepository userRepository,
                                 JwtUtils jwtUtils,
                                 PasswordEncoder passwordEncoder,
                                 UserService userService,
                                 AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.jwtUtils = jwtUtils;
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
        this.authenticationManager = authenticationManager;
    }

    public AuthenticationResponse register(RegisterRequest request){
        if(request == null)
            throw new IllegalArgumentException("The response body must not be null");
        final String username = request.getUsername();
        final String email =  request.getEmail();
        if(!EmailUtils.isValid(email))
            throw new RuntimeException("The email: "+email+" is invalid!");
        if(userService.isEmailTaken(email))
            throw new RuntimeException("User with email: "+email+" is already exist");
        if(userService.isEmailTaken(username))
            throw new RuntimeException("User with "+username+" is already exist");
        User user = User.Builder.getInstance()
                .setUsername(username)
                .setEmail(email)
                .setPassword(passwordEncoder.encode(request.getPassword()))
                .setRole(Role.forName(request.getRole()))
                .build();
        userRepository.save(user);
        return new AuthenticationResponse(jwtUtils.generateJwtToken(user));
    }
    public AuthenticationResponse authenticate(AuthenticationRequest request){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );
        User user = userRepository.findByUsername(request.getUsername()).orElseThrow();
        return new AuthenticationResponse(jwtUtils.generateJwtToken(user));
    }
}
