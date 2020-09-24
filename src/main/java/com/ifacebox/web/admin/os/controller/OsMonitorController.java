package com.ifacebox.web.admin.os.controller;

import com.ifacebox.web.admin.controller.BaseBodyController;
import com.ifacebox.web.admin.os.manager.OsMonitorManager;
import com.ifacebox.web.admin.os.model.OsMonitor;
import com.ifacebox.web.common.annotation.UserAgentRequest;
import com.ifacebox.web.common.model.ModelResponse;
import com.ifacebox.web.common.model.StatusResponse;
import com.ifacebox.web.common.model.UserAgent;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author znn
 */
@Controller
public class OsMonitorController extends BaseBodyController {

    @GetMapping("/admin/os/monitor.json")
    @ResponseBody
    public ModelResponse<OsMonitor> monitor(@UserAgentRequest UserAgent userAgent) {
        if (!permissionManager.hasLogin(userAgent)) {
            return new ModelResponse<>(StatusResponse.LOGOUT);
        }
        OsMonitor osMonitor = new OsMonitor();
        osMonitor.setJvmMemory(OsMonitorManager.getJvmMemory());
        osMonitor.setOsCpu(OsMonitorManager.getOsCpu());
        osMonitor.setOsMemory(OsMonitorManager.getOsMemory());
        osMonitor.setOsDiskSpaceList(OsMonitorManager.getDiskSpace());
        osMonitor.setOsDiskSpace(OsMonitorManager.getDiskSpace(osMonitor.getOsDiskSpaceList()));
        return new ModelResponse<>(StatusResponse.SUCCESS, osMonitor);
    }
}