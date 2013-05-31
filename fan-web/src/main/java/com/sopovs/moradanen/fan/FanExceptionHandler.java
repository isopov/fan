package com.sopovs.moradanen.fan;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

public class FanExceptionHandler implements HandlerExceptionResolver {

    private final Logger logger = LoggerFactory.getLogger(FanExceptionHandler.class);

    @Override
    public ModelAndView resolveException(HttpServletRequest request,
            HttpServletResponse response, Object handler, Exception ex) {
        logger.error(ex.getMessage(), ex);
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        return new ModelAndView("errors/500");
    }
}
