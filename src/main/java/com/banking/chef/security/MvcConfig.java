package com.banking.chef.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("chf");
        registry.addViewController("/chf").setViewName("chf");
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/sent").setViewName("sent");
    }
}
