package com.mahegg.first_pass.service;

import com.mahegg.first_pass.dto.RecordDto;
import com.mahegg.first_pass.dto.RecordPostDto;
import com.mahegg.first_pass.model.Record;
import com.mahegg.first_pass.model.User;
import com.mahegg.first_pass.repository.RecordRepository;
import com.mahegg.first_pass.repository.UserRepository;
import com.mahegg.first_pass.util.PasswordGen;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.mahegg.first_pass.util.SecurityDataUtil.getAuthenticatedCurrentUser;
import static com.mahegg.first_pass.util.SecurityDataUtil.getCurrentUserLogin;

@Service
public class RecordService {
    private RecordRepository recordRepository;
    private UserDetailsServiceImpl userDetailsService;
    private PasswordGen passwordGen;
    private final UserRepository userRepository;

    @Autowired
    public RecordService(RecordRepository recordRepository, PasswordGen passwordGen,
                         UserDetailsServiceImpl userDetailsService,
                         UserRepository userRepository) {
        this.recordRepository = recordRepository;
        this.passwordGen = passwordGen;
        this.userDetailsService = userDetailsService;
        this.userRepository = userRepository;
    }

    public RecordDto saveRecord(RecordPostDto recordPostDto) {
        Optional<String> username = getCurrentUserLogin();
        Optional<User> user = userRepository.findByUsername(username.get());
        Record record  = new Record();
        record.setUser(user.get());
        record.setUrl(recordPostDto.getUrl());
        record.setUsername(recordPostDto.getUsername());
        String password = passwordGen.genpassword();
        record.setPassword(password);
        Record recordAdded = recordRepository.save(record);
        return convertToRecordDto(recordAdded);
    }

    public void deleteRecord(Long id) {
        recordRepository.deleteById(id);
    }

    public List<RecordDto> getRecords(Long userId) {
        return convertToRecordDtoList(recordRepository.findAllByUserId(userId));
    }

    private List<RecordDto> convertToRecordDtoList(List<Record> records) {
        List<RecordDto> recordDtos = new ArrayList<>();
        for (Record record : records) {
            RecordDto recordDto = convertToRecordDto(record);
            recordDtos.add(recordDto);
        }
        return recordDtos;
    }

    private RecordDto convertToRecordDto(Record record) {
        RecordDto recordDto = new RecordDto();
        recordDto.setId(record.getId());
        recordDto.setUrl(record.getUrl());
        recordDto.setUsername(record.getUsername());
        recordDto.setPassword(record.getPassword());
        return recordDto;
    }

}
