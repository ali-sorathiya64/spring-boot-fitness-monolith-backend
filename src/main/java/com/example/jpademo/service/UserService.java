package com.example.jpademo.service;


import com.example.jpademo.dto.LoginRequest;
import com.example.jpademo.dto.RegisterRequest;
import com.example.jpademo.dto.UserResponse;
import com.example.jpademo.model.User;
import com.example.jpademo.model.UserRoles;
import com.example.jpademo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    public UserResponse register(RegisterRequest request) {

        UserRoles role = request.getRoles() != null ? request.getRoles()
                : UserRoles.USER;

        User user = User.builder()
                .email(request.getEmail())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(role)
                .build();

//        User user = new User(
//                null,
//                request.getEmail(),
//                request.getPassword(),
//                request.getFirstName(),
//                request.getLastName(),
//                Instant.parse("2026-05-12T14:28:53.253Z")
//                        .atZone(ZoneOffset.UTC)
//                        .toLocalDateTime(),
//                Instant.parse("2026-05-12T14:28:53.253Z")
//                        .atZone(ZoneOffset.UTC)
//                        .toLocalDateTime(),
//                List.of(),
//                List.of()
//        );

   User savedUser = userRepository.save(user);

        return mappedToResponse(savedUser);
    }

    public UserResponse mappedToResponse(User savedUser) {

        UserResponse response = new UserResponse();
        response.setId(savedUser.getId());
        response.setEmail(savedUser.getEmail());
        response.setPassword(savedUser.getPassword());
        response.setFirstName(savedUser.getFirstName());
        response.setLastName(savedUser.getLastName());
        response.setCreatedAt(savedUser.getCreatedAt());
        response.setUpdatedAt(savedUser.getUpdatedAt());

        return  response;
    }

    public User authenticate(LoginRequest loginRequest) {


        User user = userRepository.findByEmail(loginRequest.getEmail());
        if (user == null) throw new RuntimeException("Invalid Credentials");

        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword()))
            throw new RuntimeException("Invalid Credentials");


        return user;
    }
}
