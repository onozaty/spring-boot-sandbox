package com.example;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Information {

    private Integer id;

    private String text;

    private List<Integer> intArray;

    private List<String> textArray;

    private JsonData json;
}
