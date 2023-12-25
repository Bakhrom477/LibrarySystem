package com.example.librarysystem.controller;

import com.example.librarysystem.dto.JwtResponseDto;
import com.example.librarysystem.dto.SingIdDto;
import com.example.librarysystem.dto.UserRequestDto;
import com.example.librarysystem.dto.UserResponseDto;
import com.example.librarysystem.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @PostMapping("/sign-up")
    public UserResponseDto singUp(@RequestBody UserRequestDto userRequestDto) {
        return userService.singUp(userRequestDto);
    }

    @PostMapping("/sign-in")
    public JwtResponseDto singIn(@RequestBody SingIdDto singIdDto) {
        return userService.singIn(singIdDto);
    }

    @PostMapping("/verify")
    public boolean verify(@RequestParam String email, @RequestParam int code) {
        return userService.verify(email, code);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/get-all")
    public List<UserResponseDto> getAll(
            @RequestParam(defaultValue = "1") int size,
            @RequestParam(defaultValue = "1") int page) {
        return userService.getAll(page, size);
    }

    @PutMapping("/update")
    public UserResponseDto update(@RequestBody UserRequestDto userRequestDto, Principal principal) {
        return userService.update(userRequestDto, UUID.fromString(principal.getName()));
    }

    @GetMapping("/get-verify-code")
    public void getVerifyCode(@RequestParam String email) {
        userService.getVerifyCode(email);
    }
}
