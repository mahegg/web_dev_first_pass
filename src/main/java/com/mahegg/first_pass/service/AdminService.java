package com.mahegg.first_pass.service;

import com.mahegg.first_pass.dto.UserDto;
import com.mahegg.first_pass.model.Role;
import com.mahegg.first_pass.model.User;
import com.mahegg.first_pass.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AdminService {

    private UserRepository userRepository;

    @Autowired
    public AdminService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserDto> getUsers() {
        return convertToUserDtoList(userRepository.findAll());
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    private List<UserDto> convertToUserDtoList(List<User> users) {
        List<UserDto> userDtos = new ArrayList<>();
        for (User user : users) {
            UserDto userDto = convertTouserDto(user);
            userDtos.add(userDto);
        }
        return userDtos;
    }

    private UserDto convertTouserDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setEmail(user.getEmail());
        userDto.setUsername(user.getUsername());
        Set<String> roles = user.getRoles().stream().collect(Collectors.mapping(Role::toString, Collectors.toSet()));
        userDto.setRoles(roles);
        return userDto;
    }
}
