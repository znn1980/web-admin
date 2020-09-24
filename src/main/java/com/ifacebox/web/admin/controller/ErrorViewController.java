package com.ifacebox.web.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author znn
 */
@Controller
public class ErrorViewController extends BaseViewController {

    @GetMapping("/views/error/403.html")
    public ModelAndView e403() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("views/error/403");
        return modelAndView;
    }

    @GetMapping("/views/error/404.html")
    public ModelAndView e404() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("views/error/404");
        return modelAndView;
    }

    @GetMapping("/views/error/500.html")
    public ModelAndView e500() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("error", new NullPointerException());
        modelAndView.setViewName("views/error/500");
        return modelAndView;
    }
}
