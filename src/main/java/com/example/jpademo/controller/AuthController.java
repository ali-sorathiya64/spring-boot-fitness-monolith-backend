package com.example.jpademo.controller;
import com.example.jpademo.Security.JwtUtils;
import com.example.jpademo.dto.LoginRequest;
import com.example.jpademo.dto.LoginResponse;
import com.example.jpademo.dto.RegisterRequest;
import com.example.jpademo.dto.UserResponse;
import com.example.jpademo.model.User;
import com.example.jpademo.repository.UserRepository;
import com.example.jpademo.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation  .PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor

public class AuthController {


    @Autowired
    JwtUtils jwtUtils;

    private final UserService userService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    @PostMapping("/register")
    public ResponseEntity<UserResponse> registerUser(@Valid @RequestBody RegisterRequest request) {
        return ResponseEntity.ok(userService.register(request));
    }


    @PostMapping("/login")

    public ResponseEntity<LoginResponse> login( @RequestBody LoginRequest loginRequest) {
        try {


//            User user = userRepository.findByEmail(loginRequest.getEmail());
//            if (user == null) return ResponseEntity.status(401).build();
//
//            if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword()))
//                return ResponseEntity.status(401).build();

            User user = userService.authenticate(loginRequest);
            String token = jwtUtils.generateToken(user.getId(),
                    user.getRole().name());


            return ResponseEntity.ok(new LoginResponse(
                    token ,userService.mappedToResponse(user)
            ));


        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(401).build();

        }


    }




}
