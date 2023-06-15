package org.hnxxxy.rg1b;

import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.annotation.MapperScans;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class },scanBasePackages = "org.hnxxxy.rg1b")
@MapperScans({
        @MapperScan("org.hnxxxy.rg1b.mapper"),
        @MapperScan("org.hnxxxy.rg1b.system.mapper"),
        @MapperScan("mapper")
})
public class TrainApplication
{

    public static void main(String[] args)
    {
        // System.setProperty("spring.devtools.restart.enabled", "false");
        SpringApplication.run(TrainApplication.class, args);
        System.out.println("启动成功............");
    }
}
