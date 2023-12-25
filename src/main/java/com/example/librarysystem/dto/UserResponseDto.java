package com.example.librarysystem.dto;

import com.example.librarysystem.entity.enums.Permissions;
import com.example.librarysystem.entity.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserResponseDto{
    private String fullName;
    private String userName;
    private UserRole userRoles;
    private List<Permissions> permissions;

}
