package com.vidaplus.sghss.controller;

import com.vidaplus.sghss.model.User;
import com.vidaplus.sghss.repository.UserRepository;
import com.vidaplus.sghss.security.JwtUtil;
import com.vidaplus.sghss.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final UserService userService;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final BCryptPasswordEncoder encoder;

    public AuthController(UserService userService, UserRepository userRepository,
                          JwtUtil jwtUtil, BCryptPasswordEncoder encoder) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
        this.encoder = encoder;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        if (userRepository.existsByEmail(user.getEmail()))
            return ResponseEntity.badRequest().body(Map.of("error", "Email já cadastrado"));
        User saved = userService.register(user);
        return ResponseEntity.ok(Map.of("id", saved.getId(), "email", saved.getEmail()));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String,String> body){
        String email = body.get("email");
        String senha = body.get("senha");
        return userRepository.findByEmail(email)
                .filter(u -> encoder.matches(senha, u.getSenha()))
                .map(u -> ResponseEntity.ok(Map.of("token", jwtUtil.generateToken(u.getEmail()))))
                .orElse(ResponseEntity.status(401).body(Map.of("error","Credenciais inválidas")));
    }
}
