package com.mahegg.first_pass.controller;

import com.mahegg.first_pass.config.JwtUtils;
import com.mahegg.first_pass.dto.LoginRequest;
import com.mahegg.first_pass.dto.RegisterDto;
import com.mahegg.first_pass.dto.UserInfoResponse;
import com.mahegg.first_pass.enums.RoleEnum;
import com.mahegg.first_pass.model.Role;
import com.mahegg.first_pass.model.User;
import com.mahegg.first_pass.model.UserDetailsImpl;
import com.mahegg.first_pass.repository.RoleRepository;
import com.mahegg.first_pass.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/*@CrossOrigin(origins = "*", maxAge = 3600)*/
@RestController
public class AuthenticationController {
    private static final Logger logger = LoggerFactory.getLogger("AuthenticationController.class");
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @PostMapping(value = "/api/auth/login", produces = "application/json")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(userDetails);

        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                .body(new UserInfoResponse(userDetails.getId(),
                        userDetails.getUsername(),
                        userDetails.getEmail(),
                        roles));
    }

    @PostMapping("/api/auth/register")
    public ResponseEntity registerUser(@Valid @RequestBody RegisterDto registerDto) {
        logger.info(registerDto.getEmail());

        if (userRepository.existsByUsername(registerDto.getUsername())) {
            return ResponseEntity.badRequest().body("Username is already taken!");
        }

        if (userRepository.existsByEmail(registerDto.getEmail())) {
            return ResponseEntity.badRequest().body("Email is already in use!");
        }

        User user = new User();
        user.setEmail(registerDto.getEmail());
        user.setUsername(registerDto.getUsername());
        user.setPassword(encoder.encode(registerDto.getPassword()));
        Optional<Role> role = roleRepository.findByName(RoleEnum.ROLE_USER);
        Set<Role> roles = new HashSet<>();
        roles.add(role.get());
        user.setRoles(roles);
        User returnedUser = userRepository.save(user);
        return ResponseEntity.ok().body(returnedUser);
    }


    @PostMapping("/signout")
    public ResponseEntity<?> logoutUser() {
        ResponseCookie cookie = jwtUtils.getCleanJwtCookie();
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body("You've been signed out!");
    }

}
