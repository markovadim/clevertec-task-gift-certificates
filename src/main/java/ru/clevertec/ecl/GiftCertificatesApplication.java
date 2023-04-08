package ru.clevertec.ecl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
//@EnableJpaRepositories(basePackages = {"ru.clevertec.ecl.dao"})
public class GiftCertificatesApplication {
    public static void main(String[] args) {
        SpringApplication.run(GiftCertificatesApplication.class, args);
    }
}
