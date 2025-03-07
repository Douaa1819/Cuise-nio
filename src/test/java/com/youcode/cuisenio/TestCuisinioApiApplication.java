package com.youcode.cuisenio;

import org.springframework.boot.SpringApplication;

public class TestCuisinioApiApplication {
    public static void main(String[] args) {
        SpringApplication.from(CuisenioApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}


