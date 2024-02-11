package com.ebito.exceptionhandler.common;

import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

public class WebUtil {

    public static String getEndpoint(WebRequest request) {
        return ((ServletWebRequest) request)
                .getRequest()
                .getRequestURI();
    }
}