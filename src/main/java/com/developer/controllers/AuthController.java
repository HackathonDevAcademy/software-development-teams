package com.developer.controllers;

import com.developer.dto.AuthenticationDTO;
import com.developer.dto.DeveloperDTO;
import com.developer.mapper.DeveloperMapper;
import com.developer.security.JWTUtil;
import com.developer.services.DeveloperService;
import com.developer.util.DeveloperValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

//@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class AuthController {

    private final DeveloperService developerService;
    private final DeveloperMapper developerMapper;
    private final DeveloperValidator developerValidator;
    private final AuthenticationManager authenticationManager;
    private final JWTUtil jwtUtil;

    @Autowired
    public AuthController(DeveloperService developerService, DeveloperMapper developerMapper, DeveloperValidator developerValidator, AuthenticationManager authenticationManager, JWTUtil jwtUtil) {
        this.developerService = developerService;
        this.developerMapper = developerMapper;
        this.developerValidator = developerValidator;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @GetMapping("/activate")
    public List<String> activateAccount(@RequestParam("email") String email, @RequestParam("token") String token) {
        return developerService.activateAccount(email, token);
    }

    @PostMapping("/register")
    public Map<String, String> registerUser(@RequestBody @Valid DeveloperDTO developerDTO,
                                            BindingResult bindingResult) throws MessagingException {
        developerValidator.validate(developerMapper.convertToEntity(developerDTO), bindingResult);
        if(bindingResult.hasErrors())
            return Map.of("message", "Ошибка!");

        developerService.registerUser(developerMapper.convertToEntity(developerDTO));

        String token = jwtUtil.generateToken(developerDTO.getEmail());
        return Map.of("jwt-token", token);
    }

    @PostMapping("/login")
    public Map<String, String> performLogin(@RequestBody AuthenticationDTO authenticationDTO) {
        UsernamePasswordAuthenticationToken authInputToken =
                new UsernamePasswordAuthenticationToken(authenticationDTO.getUsername(),
                        authenticationDTO.getPassword());

        try {
            authenticationManager.authenticate(authInputToken);
        } catch (BadCredentialsException e) {
            return Map.of("message", "Incorrect credentials!");
        }

        String token = jwtUtil.generateToken(authenticationDTO.getUsername());
        return Map.of("jwt-token", token);
    }

}

