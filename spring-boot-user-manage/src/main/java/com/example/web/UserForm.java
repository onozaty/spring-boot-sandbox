package com.example.web;

import lombok.Data;

@Data
public class UserForm {

    private Integer id;

    private String loginId;

    private String password;

    private String confirmPassword;

    private String lastName;

    private String firstName;

    private String mailAddress;
}
