package com.cocarius.security.user;

import com.cocarius.security.payloads.RegisterUserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author LuongTDT
 */
@Service
public class UserService {
    private final UserRepository userRepository;
    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    public boolean isEmailTaken(String email){
        return userRepository.existsByEmail(email);
    }
    public boolean isUsernameTaken(String username){
        return userRepository.existsByUsername(username);
    }
    public boolean isEmailOrUsernameTaken(RegisterUserDTO registerUserDTO) {
        return isEmailTaken(registerUserDTO.getEmail())
                || isUsernameTaken(registerUserDTO.getUsername());
    }
}
