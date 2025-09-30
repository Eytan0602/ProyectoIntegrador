package com.pnp.portal.controller;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServletConfig {

    @Bean
    public ServletRegistrationBean<CaptchaGenerator> captchaServlet() {
        ServletRegistrationBean<CaptchaGenerator> srb = new ServletRegistrationBean<>(new CaptchaGenerator(), "/CaptchaGenerator");
        return srb;
    }
}
