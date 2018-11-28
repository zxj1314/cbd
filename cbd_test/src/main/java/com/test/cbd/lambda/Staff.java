package com.test.cbd.lambda;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class Staff {

    private String name;
    private int age;
    private BigDecimal salary;
    private Boolean isMan;
    //...

    public Staff(String name, int age, BigDecimal salary) {
        this.name = name;
        this.age = age;
        this.salary = salary;
    }
}