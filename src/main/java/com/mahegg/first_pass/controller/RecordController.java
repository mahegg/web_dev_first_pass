package com.mahegg.first_pass.controller;

import com.mahegg.first_pass.dto.RecordDto;
import com.mahegg.first_pass.dto.RecordPostDto;
import com.mahegg.first_pass.model.User;
import com.mahegg.first_pass.model.UserDetailsImpl;
import com.mahegg.first_pass.repository.UserRepository;
import com.mahegg.first_pass.service.RecordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RecordController {
    private static final Logger logger = LoggerFactory.getLogger("RecordController.class");
    private RecordService recordService;

    @Autowired
    public RecordController(RecordService recordService) {
        this.recordService = recordService;
    }

    @GetMapping(value = "/api/user/records", produces = "application/json")
    public ResponseEntity getUserRecordDtos() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<RecordDto> recordDtos = recordService.getRecords(userDetails.getId());
        return ResponseEntity.ok().body(recordDtos);
    }

    @PostMapping(value = "/api/user/create", produces = "application/json", consumes = "application/json")
    public ResponseEntity saveRecord(@RequestBody RecordPostDto recordPostDto) {
        RecordDto recordDto = recordService.saveRecord(recordPostDto);
        return  ResponseEntity.ok().body(recordDto);
    }

    @DeleteMapping(value = "/api/user/delete/{id}", produces = "application/json", consumes = "application/json")
    public ResponseEntity deleteRecord(@PathVariable(name = "id") Long id) {
        recordService.deleteRecord(id);
        return  ResponseEntity.noContent().build();
    }

}
