package ru.mai.zaytsevvagen.deepfake.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.mai.zaytsevvagen.deepfake.aspect.LogExecutionTime;
import ru.mai.zaytsevvagen.deepfake.dto.AuthenticationRequest;
import ru.mai.zaytsevvagen.deepfake.dto.AuthenticationResponse;
import ru.mai.zaytsevvagen.deepfake.dto.RegisterRequest;
import ru.mai.zaytsevvagen.deepfake.entity.Role;
import ru.mai.zaytsevvagen.deepfake.entity.User;
import ru.mai.zaytsevvagen.deepfake.repository.UserRepository;


@Service
@Slf4j
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    @LogExecutionTime
    public AuthenticationResponse register(RegisterRequest request) {
        log.info("AuthenticationService.register -- input. Parameter: RegisterRequest: password: {}, firstname: {}, lastname: {}, email: {}", request.getPassword(), request.getFirstname(), request.getLastname(), request.getEmail());

        var user = User.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.MEMBER)
                .build();

        log.info("AuthenticationService.register. Create user object");

        User savedUser = repository.save(user);
        log.info("AuthenticationService.register. User saved to db");
        return AuthenticationResponse.builder()
                .username(request.getFirstname() + " " + request.getLastname())
                .id(savedUser.getId())
                .build();
    }

    @LogExecutionTime
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        log.info("AuthenticationService.authenticate -- input. AuthenticationRequest: email: {}, password: {}", request.getEmail(), request.getPassword());

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        log.info("AuthenticationService.authenticate auth user");
        var user = repository.findByEmail(request.getEmail())
                .orElseThrow();
        log.info("AuthenticationService.authenticate. Find user by email. User with id: {}", user.getId());

        return AuthenticationResponse.builder()
                .username(user.getFirstname() + " " + user.getLastname())
                .id(user.getId())
                .build();
    }

}
