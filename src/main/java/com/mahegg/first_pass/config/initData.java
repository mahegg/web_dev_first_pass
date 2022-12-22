package com.mahegg.first_pass.config;

import com.mahegg.first_pass.enums.RoleEnum;
import com.mahegg.first_pass.model.Role;
import com.mahegg.first_pass.model.User;
import com.mahegg.first_pass.repository.RoleRepository;
import com.mahegg.first_pass.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;


@Component
public class initData implements CommandLineRunner {

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public void run(String... args) throws Exception {

        if (roleRepository.findByName(RoleEnum.ROLE_ADMIN).isEmpty()) {
            Set<Role> roles = new HashSet();
            Role roleAdmin = new Role(RoleEnum.ROLE_ADMIN);
            Role roleUser = new Role(RoleEnum.ROLE_USER);
            roles.add(roleAdmin);
            roles.add(roleUser);
            roleRepository.saveAll(roles);
        }

        if (userRepository.findByUsername("admin").isEmpty()) {
            User user = new User();
            user.setUsername("admin");
            user.setPassword(encoder.encode("adminPass"));
            user.setEmail("admin@firstpassmail.com");
            Set<Role> userRoles = new HashSet<>();
            Role adminRole = roleRepository.findByName(RoleEnum.ROLE_ADMIN)
                    .orElseThrow(() -> new RuntimeException("Role not found"));
            userRoles.add(adminRole);
            user.setRoles(userRoles);
            userRepository.save(user);
        }
    }
}
