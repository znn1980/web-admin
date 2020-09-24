package com.ifacebox.web.admin.controller;

import com.ifacebox.web.common.annotation.UserAgentRequest;
import com.ifacebox.web.common.model.UserAgent;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author znn
 */
public class BaseViewController extends BaseController {

    @ExceptionHandler(Exception.class)
    public ModelAndView error(@UserAgentRequest UserAgent userAgent, Exception ex) {
        ModelAndView modelAndView = new ModelAndView();
        traceLoggerService.addTraceLogger(userAgent, ex.toString());
        modelAndView.addObject("error", ex);
        modelAndView.setViewName(VIEWS_ADMIN_ERROR);
        return modelAndView;
    }
}
