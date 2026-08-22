package com.gamevault.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.gamevault")
public class GameVaultApplication {
    protected GameVaultApplication() {
    }

    static void main(final String[] args) {
        SpringApplication.run(GameVaultApplication.class, args);
    }
}
