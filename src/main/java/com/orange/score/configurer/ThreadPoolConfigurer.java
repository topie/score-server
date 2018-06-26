package com.orange.score.configurer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
public class ThreadPoolConfigurer {

    @Bean
    public ExecutorService getThreadPool() {
        return Executors.newFixedThreadPool(20);
    }
}
