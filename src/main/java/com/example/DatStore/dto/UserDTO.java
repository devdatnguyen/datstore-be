package com.example.DatStore.dto;

import com.example.DatStore.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private String name;
    private String email;
    private String password;
    private String  username;
    private Role role;
    private String phoneNumber;
    private String address;
    private long id;
}
