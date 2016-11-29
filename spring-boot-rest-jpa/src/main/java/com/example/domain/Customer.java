package com.example.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "customers")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Customer {

    @Id
    @GeneratedValue
    @ApiModelProperty(position = 1, example = "1", value = "新規追加時は使用しません")
    private Integer id;

    @ApiModelProperty(position = 2, example = "名前", required = true)
    private String name;

    @ApiModelProperty(position = 3, example = "住所")
    private String address;
}
