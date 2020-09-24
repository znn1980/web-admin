package com.ifacebox.web.admin.master.controller;

import com.ifacebox.web.admin.controller.BaseViewController;
import com.ifacebox.web.admin.master.model.MasterUser;
import com.ifacebox.web.admin.os.manager.OsMonitorManager;
import com.ifacebox.web.admin.os.model.OsDiskSpace;
import com.ifacebox.web.common.annotation.UserAgentRequest;
import com.ifacebox.web.common.model.UserAgent;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

/**
 * @author znn
 */
@Controller
public class MasterViewController extends BaseViewController {

    @GetMapping({"/", "/views", "/views/admin", "/views/admin/login.html"})
    public ModelAndView login() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("platformProperties", platformManager.getPlatformProperties());
        modelAndView.setViewName("views/admin/login");
        return modelAndView;
    }

    @GetMapping("/views/admin/captcha.jpg")
    public void captcha(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setDateHeader("Expires", 0L);
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");
        response.setHeader("Pragma", "no-cache");
        response.setContentType("image/jpeg");
        String text = calcMathCaptcha.getProducer().createText();
        masterUserService.setCaptcha(request.getSession(true), calcMathCaptcha.parseResult(text));
        BufferedImage image = calcMathCaptcha.getProducer().createImage(calcMathCaptcha.parseText(text));
        ImageIO.write(image, "jpg", response.getOutputStream());
    }

    @GetMapping("/views/admin/logout.html")
    public ModelAndView logout(@UserAgentRequest UserAgent userAgent) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("platformProperties", platformManager.getPlatformProperties());
        modelAndView.setViewName("views/admin/login");
        MasterUser masterUser = userAgent.getMasterUser();
        if (!ObjectUtils.isEmpty(masterUser)) {
            traceLoggerService.addTraceLogger(userAgent, "用户登出成功！【{}】", masterUser.toString());
        }
        userAgent.setMasterUser(null);
        return modelAndView;
    }

    @GetMapping("/views/admin/index.html")
    public ModelAndView index(@UserAgentRequest UserAgent userAgent) {
        ModelAndView modelAndView = new ModelAndView();
        if (!permissionManager.hasLogin(userAgent)) {
            modelAndView.setViewName(VIEWS_ADMIN_LOGOUT);
            return modelAndView;
        }
        modelAndView.addObject("platformProperties", platformManager.getPlatformProperties());
        modelAndView.addObject("systemProperties", OsMonitorManager.getSystemProperties());
        modelAndView.setViewName("views/admin/index");
        return modelAndView;
    }

    @GetMapping("/views/admin/home.html")
    public ModelAndView home(@UserAgentRequest UserAgent userAgent) {
        ModelAndView modelAndView = new ModelAndView();
        if (!permissionManager.hasLogin(userAgent)) {
            modelAndView.setViewName(VIEWS_ADMIN_LOGOUT);
            return modelAndView;
        }
        List<OsDiskSpace> osDiskSpaceList = OsMonitorManager.getDiskSpace();
        modelAndView.addObject("osDiskSpaceList", osDiskSpaceList);
        modelAndView.addObject("osDiskSpace", OsMonitorManager.getDiskSpace(osDiskSpaceList));
        modelAndView.addObject("osCpu", OsMonitorManager.getOsCpu());
        modelAndView.addObject("osMemory", OsMonitorManager.getOsMemory());
        modelAndView.addObject("jvmMemory", OsMonitorManager.getJvmMemory());
        modelAndView.setViewName("views/admin/home");
        return modelAndView;
    }

    @GetMapping("/views/admin/user.html")
    public ModelAndView user(@UserAgentRequest UserAgent userAgent) {
        ModelAndView modelAndView = new ModelAndView();
        if (!permissionManager.hasPermission(userAgent)) {
            modelAndView.setViewName(VIEWS_ADMIN_LOGOUT);
            return modelAndView;
        }
        modelAndView.setViewName("views/admin/master/user");
        return modelAndView;
    }

    @GetMapping("/views/admin/group.html")
    public ModelAndView group(@UserAgentRequest UserAgent userAgent) {
        ModelAndView modelAndView = new ModelAndView();
        if (!permissionManager.hasPermission(userAgent)) {
            modelAndView.setViewName(VIEWS_ADMIN_LOGOUT);
            return modelAndView;
        }
        modelAndView.setViewName("views/admin/master/group");
        return modelAndView;
    }

    @GetMapping("/views/admin/role.html")
    public ModelAndView role(@UserAgentRequest UserAgent userAgent) {
        ModelAndView modelAndView = new ModelAndView();
        if (!permissionManager.hasPermission(userAgent)) {
            modelAndView.setViewName(VIEWS_ADMIN_LOGOUT);
            return modelAndView;
        }
        modelAndView.setViewName("views/admin/master/role");
        return modelAndView;
    }

    @GetMapping("/views/admin/menu.html")
    public ModelAndView menu(@UserAgentRequest UserAgent userAgent) {
        ModelAndView modelAndView = new ModelAndView();
        if (!permissionManager.hasPermission(userAgent)) {
            modelAndView.setViewName(VIEWS_ADMIN_LOGOUT);
            return modelAndView;
        }
        modelAndView.setViewName("views/admin/master/menu");
        return modelAndView;
    }

    @GetMapping("/views/admin/operate.html")
    public ModelAndView operate(@UserAgentRequest UserAgent userAgent) {
        ModelAndView modelAndView = new ModelAndView();
        if (!permissionManager.hasPermission(userAgent)) {
            modelAndView.setViewName(VIEWS_ADMIN_LOGOUT);
            return modelAndView;
        }
        modelAndView.setViewName("views/admin/master/operate");
        return modelAndView;
    }

    @GetMapping("/views/admin/role_menu.html")
    public ModelAndView roleMenu(@UserAgentRequest UserAgent userAgent) {
        ModelAndView modelAndView = new ModelAndView();
        if (!permissionManager.hasPermission(userAgent)) {
            modelAndView.setViewName(VIEWS_ADMIN_LOGOUT);
            return modelAndView;
        }
        modelAndView.setViewName("views/admin/master/role_menu");
        return modelAndView;
    }

    @GetMapping("/views/admin/role_operate.html")
    public ModelAndView roleOperate(@UserAgentRequest UserAgent userAgent) {
        ModelAndView modelAndView = new ModelAndView();
        if (!permissionManager.hasPermission(userAgent)) {
            modelAndView.setViewName(VIEWS_ADMIN_LOGOUT);
            return modelAndView;
        }
        modelAndView.setViewName("views/admin/master/role_operate");
        return modelAndView;
    }

}
