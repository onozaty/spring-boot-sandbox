package com.example.web;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import com.example.web.validation.Confirm;

import lombok.Data;

@Data
@Confirm(field = "password")
public class UserForm {

    private Integer id;

    @Size(min = 3, max = 20)
    @Pattern(regexp = "[0-9a-zA-Z_]*")
    private String loginId;

    private boolean changePassword;

    @Size(min = 8, max = 20)
    private String password;

    @Size(min = 8, max = 20)
    private String confirmPassword;

    @NotEmpty
    private String lastName;

    @NotEmpty
    private String firstName;

    @NotEmpty
    @Email
    private String mailAddress;
}
