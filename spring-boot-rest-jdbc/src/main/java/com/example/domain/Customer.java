package com.example.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Customer {

    @ApiModelProperty(position = 1, example = "1")
    private Integer id;

    @ApiModelProperty(position = 2, example = "First Name", required = true)
    private String firstName;

    @ApiModelProperty(position = 3, example = "Last Name", required = true)
    private String lastName;

    @ApiModelProperty(position = 4, example = "Address")
    private String address;
}
