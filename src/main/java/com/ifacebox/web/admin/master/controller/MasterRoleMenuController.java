package com.ifacebox.web.admin.master.controller;

import com.ifacebox.web.admin.controller.BaseBodyController;
import com.ifacebox.web.admin.master.model.MasterRole;
import com.ifacebox.web.admin.master.service.MasterRoleMenuService;
import com.ifacebox.web.admin.master.service.MasterRoleService;
import com.ifacebox.web.common.annotation.UserAgentRequest;
import com.ifacebox.web.common.model.BaseResponse;
import com.ifacebox.web.common.model.StatusResponse;
import com.ifacebox.web.common.model.UserAgent;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * @author znn
 */
@Controller
public class MasterRoleMenuController extends BaseBodyController {

    @Resource
    private MasterRoleMenuService masterRoleMenuService;
    @Resource
    private MasterRoleService masterRoleService;

    @PostMapping("/admin/role/menu/save.json")
    @ResponseBody
    public BaseResponse save(@UserAgentRequest UserAgent userAgent, @RequestBody MasterRole masterRole) {
        if (!permissionManager.hasPermission(userAgent)) {
            return new BaseResponse(StatusResponse.LOGOUT);
        }
        MasterRole oldMasterRole = masterRoleService.queryById(masterRole.getId());
        if (ObjectUtils.isEmpty(oldMasterRole)) {
            return new BaseResponse(StatusResponse.DELETE_FAIL, "角色不存在！");
        }
        if (masterRoleMenuService.save(masterRole)) {
            traceLoggerService.addTraceLogger(userAgent, "分配菜单权限成功！【{}】", oldMasterRole.toString());
            return new BaseResponse(StatusResponse.SUCCESS);
        } else {
            return new BaseResponse(StatusResponse.SAVE_FAIL);
        }
    }
}
