package com.ifacebox.web.admin.logger.controller;

import com.ifacebox.web.admin.controller.BaseBodyController;
import com.ifacebox.web.admin.logger.model.TraceLogger;
import com.ifacebox.web.admin.logger.model.TraceLoggerQuery;
import com.ifacebox.web.common.annotation.UserAgentRequest;
import com.ifacebox.web.common.model.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author znn
 */
@Controller
public class TraceLoggerController extends BaseBodyController {

    @GetMapping("/admin/logger/list.json")
    @ResponseBody
    public ModelResponse<List<TraceLogger>> list(@UserAgentRequest UserAgent userAgent, TraceLoggerQuery traceLoggerQuery) {
        if (!permissionManager.hasPermission(userAgent)) {
            return new ModelResponse<>(StatusResponse.LOGOUT);
        }
        return new ModelResponse<>(StatusResponse.SUCCESS, traceLoggerService.total(traceLoggerQuery), traceLoggerService.query(traceLoggerQuery));
    }

    @DeleteMapping("/admin/logger/delete.json")
    @ResponseBody
    public BaseResponse delete(@UserAgentRequest UserAgent userAgent, @RequestBody Integer[] id) {
        if (!permissionManager.hasPermission(userAgent)) {
            return new BaseResponse(StatusResponse.LOGOUT);
        }
        if (!traceLoggerService.delete(id)) {
            return new BaseResponse(StatusResponse.DELETE_FAIL);
        }
        return new BaseResponse(StatusResponse.SUCCESS);
    }
}
