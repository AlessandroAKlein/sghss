package com.vidaplus.sghss.service;

import com.vidaplus.sghss.model.User;
import com.vidaplus.sghss.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder encoder){
        this.userRepository = userRepository;
        this.encoder = encoder;
    }

    public User register(User user){
        user.setSenha(encoder.encode(user.getSenha()));
        if (user.getRole() == null) user.setRole("ROLE_USER");
        return userRepository.save(user);
    }
}
