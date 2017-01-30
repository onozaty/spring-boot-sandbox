package com.example.web;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import lombok.Data;

@Data
public class CustomerForm {

    private Integer id;

    @NotEmpty
    private String firstName;

    @NotEmpty
    private String lastName;
}
