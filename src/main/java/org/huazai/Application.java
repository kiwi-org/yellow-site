package org.huazai;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author YanAnHuaZai
 * created at：2020-09-01 22:10
 */
@SpringBootApplication
@MapperScan("org.huazai.mapper")
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
