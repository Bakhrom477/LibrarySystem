package com.example.librarysystem.service;

import com.example.librarysystem.dto.JwtResponseDto;
import com.example.librarysystem.dto.SingIdDto;
import com.example.librarysystem.dto.UserRequestDto;
import com.example.librarysystem.dto.UserResponseDto;
import com.example.librarysystem.entity.User;
import com.example.librarysystem.entity.enums.UserRole;
import com.example.librarysystem.exception.DataAlreadyExistsException;
import com.example.librarysystem.exception.DataNotFoundException;
import com.example.librarysystem.exception.WrongInputException;
import com.example.librarysystem.repository.UserRepository;
import com.example.librarysystem.service.jwt.JwtService;
import com.example.librarysystem.validator.UserValidation;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;
import java.util.UUID;
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository repository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final NotificationService notificationService;
    private final Random random = new Random();
    private final UserValidation validation;

    @Transactional
    public UserResponseDto singUp(UserRequestDto createReq) {
        if (!validation.isValidUserName(createReq.getUserName())) {
            throw new DataAlreadyExistsException("Username already exists");
        }
        if (!validation.isValidPassword(createReq.getPassword())) {
            throw new WrongInputException("Password must be at least 8 characters and contain one uppercase,one lowercase , one character");
        }
        if (!validation.isValidEmail(createReq.getEmail())) {
            throw new DataAlreadyExistsException("Email already exists");
        }
        User user = mapReqToEntity(createReq);
        user.setRoles(UserRole.USER);
        user.setCode(random.nextInt(1000, 10000));
        repository.save(user);
        notificationService.sendVerifyCode(user.getEmail(), user.getCode());
        return mapEntityToRES(user);
    }

    public JwtResponseDto singIn(SingIdDto singIdDto) {
        User userEntity = repository.findByUsername(singIdDto.getUserName()).orElseThrow(() -> new DataNotFoundException("USER NOT FOUND"));
        if (!userEntity.isVerify()) {
            throw new WrongInputException("User not verified");
        } else if (!passwordEncoder.matches(singIdDto.getPassword(), userEntity.getPassword()))
            throw new WrongInputException("PASSWORD INCORRECT");
        else {
            return new JwtResponseDto(jwtService.generateToken(userEntity));
        }
    }

    public boolean verify(String email, int code) {
        User user = repository.findByEmail(email).orElseThrow(() -> new DataNotFoundException("USER NOT FOUND"));
        if (user.getCode() == code) {
            user.setVerify(true);
            repository.save(user);
            return true;
        }
        throw new WrongInputException("incorrect code");
    }

    public List<UserResponseDto> getAll(int pageNumber, int size) {
        Pageable pageable = PageRequest.of(pageNumber, size);
        Page<User> page = repository.findAll(pageable);
        return page.get().map(this::mapEntityToRES).toList();
    }
    public void getVerifyCode(String email) {
        User userEntity = repository.findByEmail(email).orElseThrow(() -> new WrongInputException("USER NOT FOUND"));
        userEntity.setCode(random.nextInt(1000, 10000));
        repository.save(userEntity);
        notificationService.sendVerifyCode(userEntity.getEmail(), userEntity.getCode());
    }
    public UserResponseDto update(UserRequestDto userRequestDto, UUID id) {
        User userEntity = repository.findById(id).orElseThrow(() -> new DataNotFoundException("USER NOT FOUND"));
        modelMapper.map(userRequestDto,userEntity);
        repository.save(userEntity);
        return mapEntityToRES(userEntity);
    }

    protected UserResponseDto mapEntityToRES(User entity) {
        return new UserResponseDto(
                entity.getFullName(),
                entity.getUsername(),
                entity.getRoles(),
                entity.getPermissions());
    }


    protected User mapReqToEntity(UserRequestDto createReq) {
        User map = modelMapper.map(createReq, User.class);
        map.setPassword(passwordEncoder.encode(map.getPassword()));
        return map;
    }

}
