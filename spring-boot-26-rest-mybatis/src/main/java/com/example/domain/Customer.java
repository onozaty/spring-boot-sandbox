package com.example.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Customer {

    @Schema(example = "1")
    private Integer id;

    @Schema(example = "First Name", required = true)
    private String firstName;

    @Schema(example = "Last Name", required = true)
    private String lastName;

    @Schema(example = "Address")
    private String address;
}
