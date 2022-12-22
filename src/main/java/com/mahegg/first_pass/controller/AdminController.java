package com.mahegg.first_pass.controller;

import com.mahegg.first_pass.dto.UserDto;
import com.mahegg.first_pass.service.AdminService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AdminController {
    private static final Logger logger = LoggerFactory.getLogger("RecordController.class");
    private AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping(value = "/api/admin/users", produces = "application/json")
    public ResponseEntity getUserDtos() {
        List<UserDto> userDtos = adminService.getUsers();
        return ResponseEntity.ok().body(userDtos);
    }

    @DeleteMapping(value = "/api/admin/delete/{id}", produces = "application/json", consumes = "application/json")
    public ResponseEntity deleteUser(@PathVariable(name = "id") Long id) {
        adminService.deleteUser(id);
        return  ResponseEntity.noContent().build();
    }
}
