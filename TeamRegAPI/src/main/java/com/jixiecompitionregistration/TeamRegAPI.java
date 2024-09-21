package com.jixiecompitionregistration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching // 设置缓存，不然无法储存session到内存中
@SpringBootApplication
public class TeamRegAPI {

    public static void main(String[] args) {
        SpringApplication.run(TeamRegAPI.class, args);
    }

}
