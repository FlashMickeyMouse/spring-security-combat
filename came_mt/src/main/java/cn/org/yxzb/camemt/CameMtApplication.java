package cn.org.yxzb.camemt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan("cn.org.yxzb.camemt.mapper")
public class CameMtApplication {

    public static void main(String[] args) {
        SpringApplication.run(CameMtApplication.class, args);
    }

}