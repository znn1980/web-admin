package com.ifacebox.web.admin.logger.controller;

import com.ifacebox.web.admin.controller.BaseViewController;
import com.ifacebox.web.common.annotation.UserAgentRequest;
import com.ifacebox.web.common.model.UserAgent;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author znn
 */
@Controller
public class TraceViewController extends BaseViewController {

    @GetMapping("/views/admin/logger.html")
    public ModelAndView logger(@UserAgentRequest UserAgent userAgent) {
        ModelAndView modelAndView = new ModelAndView();
        if (!permissionManager.hasPermission(userAgent)) {
            modelAndView.setViewName(VIEWS_ADMIN_LOGOUT);
            return modelAndView;
        }
        modelAndView.setViewName("views/admin/logger");
        return modelAndView;
    }
}
