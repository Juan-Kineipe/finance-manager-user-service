package com.kineipe.financemanageruserservice.controller;

import com.kineipe.financemanageruserservice.domain.User;
import com.kineipe.financemanageruserservice.domain.dto.LoginRequestDTO;
import com.kineipe.financemanageruserservice.domain.dto.LoginResponseDTO;
import com.kineipe.financemanageruserservice.domain.dto.RegisterDTO;
import com.kineipe.financemanageruserservice.domain.dto.UserDTO;
import com.kineipe.financemanageruserservice.repository.UserRepository;
import com.kineipe.financemanageruserservice.security.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    TokenService tokenService;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @PostMapping(value = "/signin")
    public ResponseEntity signin(@RequestBody @Valid LoginRequestDTO loginRequestDTO) {
        var usernamePassowrd = new UsernamePasswordAuthenticationToken(loginRequestDTO.username(), loginRequestDTO.password());
        var auth = this.authenticationManager.authenticate(usernamePassowrd);

        var token = tokenService.generateToken((User) auth.getPrincipal());
        var user = (User) auth.getPrincipal();

        var userDTO = new UserDTO(
                user.getId(),
                user.getUsername(),
                user.getPermissions()
        );

        return ResponseEntity.ok(new LoginResponseDTO(token, userDTO));
    }

    @PostMapping(value = "/register")
    public ResponseEntity register(@RequestBody @Valid RegisterDTO registerDTO) {
        if (this.userRepository.findByUsername(registerDTO.username()) != null) return ResponseEntity.badRequest().build();

        String encryptedPassword = new BCryptPasswordEncoder().encode(registerDTO.password());
        User user = new User(registerDTO.username(), registerDTO.firstName(), registerDTO.lastName(), encryptedPassword, registerDTO.permissions());

        this.userRepository.save(user);

        return ResponseEntity.ok().build();
    }

}
