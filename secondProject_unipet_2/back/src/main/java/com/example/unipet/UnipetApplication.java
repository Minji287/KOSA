package com.example.unipet;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@ComponentScan(basePackages = {"com.example.unipet"})
@MapperScan(value = {"com.example.unipet.mypage.dao", "com.example.unipet.signup.dao",
        "com.example.unipet.main.dao",
        "com.example.unipet.boards.dao", "com.example.unipet.list.converter"})
@EnableScheduling
public class UnipetApplication {
    public static void main(String[] args) {
        SpringApplication.run(UnipetApplication.class, args);
    }

}