package com.ifacebox.web.admin.controller;

import com.ifacebox.web.admin.logger.service.TraceLoggerService;
import com.ifacebox.web.admin.manager.PermissionManager;
import com.ifacebox.web.admin.master.service.MasterUserService;
import com.ifacebox.web.common.captcha.CalcMathCaptcha;
import com.ifacebox.web.common.manager.PlatformManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;

/**
 * @author znn
 */
public class BaseController {
    protected static final Logger logger = LoggerFactory.getLogger(BaseController.class);
    protected static final String VIEWS_ADMIN_ERROR = "views/error/500";
    protected static final String VIEWS_ADMIN_LOGOUT = "views/error/403";
    @Resource
    protected MasterUserService masterUserService;
    @Resource
    protected TraceLoggerService traceLoggerService;
    @Resource
    protected PlatformManager platformManager;
    @Resource
    protected PermissionManager permissionManager;
    @Resource
    protected CalcMathCaptcha calcMathCaptcha;
}
