//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.zgm.server;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.zgm.server.mapper")
public class EmpAdminApplication {
    public static void main(String[] args) {
        SpringApplication.run(EmpAdminApplication.class, args);
    }
}
