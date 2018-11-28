package com.test.cbd.lambda;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StaffPublic {

    private String name;
    private int age;
    private String extra;
    //...
}
