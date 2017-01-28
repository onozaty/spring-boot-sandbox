package com.example.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private Integer id;

    private String loginId;

    private String encodedPassword;

    private String lastName;

    private String firstName;

    private String mailAddress;

    public User(String loginId, String encodedPassword, String lastName, String firstName, String mailAddress) {
        super();
        this.loginId = loginId;
        this.encodedPassword = encodedPassword;
        this.firstName = firstName;
        this.lastName = lastName;
        this.mailAddress = mailAddress;
    }
}
