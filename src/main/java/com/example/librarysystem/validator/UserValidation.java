package com.example.librarysystem.validator;

import com.example.librarysystem.entity.User;
import com.example.librarysystem.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.regex.Pattern;

@Component
@RequiredArgsConstructor
public class UserValidation {
    private final UserRepository userRepository;


    public boolean isValidUserName(String userName) {
        return userRepository.findByUsername(userName).isEmpty();
    }

    public boolean isValidPassword(String password) {
        return Pattern.matches("^(?=.*[0-9])"
                + "(?=.*[a-z])(?=.*[A-Z])"
                + "(?=.*[@#$%^&+=])"
                + "(?=\\S+$).{8,20}$", password);

    }

    public boolean isValidEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isPresent()) {
            return false;
        }
        return Pattern.matches("^[a-zA-Z0-9_! #$%&'*+/=?`{|}~^. -]+@[a-zA-Z0-9. -]+$", email);
    }
}