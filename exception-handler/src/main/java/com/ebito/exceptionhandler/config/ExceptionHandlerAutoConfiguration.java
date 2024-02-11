package com.ebito.exceptionhandler.config;

import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "com.ebito.exceptionhandler")
@AutoConfigureBefore(ErrorMvcAutoConfiguration.class)
public class ExceptionHandlerAutoConfiguration {
}