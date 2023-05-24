package com.notes;

import cn.hutool.extra.spring.SpringUtil;
import com.notes.util.SpringContextUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.mail.MailSenderValidatorAutoConfiguration;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

@ServletComponentScan
@SpringBootApplication
@EnableCaching
@Slf4j
@EnableAutoConfiguration(exclude={MailSenderValidatorAutoConfiguration.class})
public class NotesApplication {

    public static void main(String[] args) {
        ApplicationContext app =  SpringApplication.run(NotesApplication.class, args);
        SpringContextUtil.setApplicationContext(app);
    }

    @Bean
    CommandLineRunner commandLineRunner() {
        return args -> {
            //TODO init
        };
    }
}
