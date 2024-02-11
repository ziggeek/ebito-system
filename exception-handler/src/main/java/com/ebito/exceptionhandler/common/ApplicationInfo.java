package com.ebito.exceptionhandler.common;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ApplicationInfo {

    private static String name;

    @Value("${spring.application.name:-}")
    public void setName(String applicationName){
        ApplicationInfo.name = applicationName;
    }

    public static String getName() {
        return name;
    }
}