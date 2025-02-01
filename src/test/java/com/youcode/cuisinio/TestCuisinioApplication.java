package com.youcode.cuisinio;

import org.springframework.boot.SpringApplication;

public class TestCuisinioApplication {

    public static void main(String[] args) {
        SpringApplication.from(CuisinioApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
