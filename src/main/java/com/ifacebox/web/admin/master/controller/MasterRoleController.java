package com.ifacebox.web.admin.master.controller;

import com.ifacebox.web.admin.controller.BaseBodyController;
import com.ifacebox.web.admin.master.model.*;
import com.ifacebox.web.admin.master.service.MasterGroupRoleService;
import com.ifacebox.web.admin.master.service.MasterRoleMenuService;
import com.ifacebox.web.admin.master.service.MasterRoleOperateService;
import com.ifacebox.web.admin.master.service.MasterRoleService;
import com.ifacebox.web.common.annotation.UserAgentRequest;
import com.ifacebox.web.common.model.BaseResponse;
import com.ifacebox.web.common.model.ModelResponse;
import com.ifacebox.web.common.model.StatusResponse;
import com.ifacebox.web.common.model.UserAgent;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author znn
 */
@Controller
public class MasterRoleController extends BaseBodyController {
    @Resource
    private MasterRoleService masterRoleService;
    @Resource
    private MasterGroupRoleService masterGroupRoleService;
    @Resource
    private MasterRoleMenuService masterRoleMenuService;
    @Resource
    private MasterRoleOperateService masterRoleOperateService;

    @GetMapping("/admin/role/status.json")
    @ResponseBody
    public ModelResponse<List<Map<String, Object>>> status() {
        return new ModelResponse<>(StatusResponse.SUCCESS, MasterRoleStatus.toArray());
    }

    @GetMapping("/admin/role/list.json")
    @ResponseBody
    public ModelResponse<List<MasterRole>> list(@UserAgentRequest UserAgent userAgent) {
        if (!permissionManager.hasPermission(userAgent)) {
            return new ModelResponse<>(StatusResponse.LOGOUT);
        }
        return new ModelResponse<>(StatusResponse.SUCCESS, masterRoleService.query());
    }

    @PostMapping("/admin/role/save.json")
    @ResponseBody
    public BaseResponse save(@UserAgentRequest UserAgent userAgent, @RequestBody @Validated MasterRole masterRole) {
        if (!permissionManager.hasPermission(userAgent)) {
            return new BaseResponse(StatusResponse.LOGOUT);
        }
        if (!ObjectUtils.isEmpty(masterRoleService.queryByRoleName(masterRole.getRoleName()))) {
            return new BaseResponse(StatusResponse.SAVE_FAIL, "角色名称已存在！");
        }
        if (masterRoleService.save(masterRole, userAgent)) {
            traceLoggerService.addTraceLogger(userAgent, "添加角色成功！【{}】", masterRole.toString());
            return new BaseResponse(StatusResponse.SUCCESS);
        } else {
            return new BaseResponse(StatusResponse.SAVE_FAIL);
        }
    }

    @PutMapping("/admin/role/edit.json")
    @ResponseBody
    public BaseResponse edit(@UserAgentRequest UserAgent userAgent, @RequestBody @Validated MasterRole masterRole) {
        if (!permissionManager.hasPermission(userAgent)) {
            return new BaseResponse(StatusResponse.LOGOUT);
        }
        MasterRole oldMasterRole = masterRoleService.queryById(masterRole.getId());
        if (ObjectUtils.isEmpty(oldMasterRole)) {
            return new BaseResponse(StatusResponse.DELETE_FAIL, "修改的角色不存在！");
        }
        if (!oldMasterRole.getRoleName().equals(masterRole.getRoleName())
                && !ObjectUtils.isEmpty(masterRoleService.queryByRoleName(masterRole.getRoleName()))) {
            return new BaseResponse(StatusResponse.SAVE_FAIL, "角色名称已存在！");
        }
        if (masterRoleService.edit(masterRole, userAgent)) {
            traceLoggerService.addTraceLogger(userAgent, "修改角色成功！【{}】", masterRole.toString());
            return new BaseResponse(StatusResponse.SUCCESS);
        } else {
            return new BaseResponse(StatusResponse.UPDATE_FAIL);
        }
    }

    @DeleteMapping("/admin/role/delete.json")
    @ResponseBody
    public BaseResponse delete(@UserAgentRequest UserAgent userAgent, @RequestParam Integer id) {
        if (!permissionManager.hasPermission(userAgent)) {
            return new BaseResponse(StatusResponse.LOGOUT);
        }
        MasterRole masterRole = masterRoleService.queryById(id);
        if (ObjectUtils.isEmpty(masterRole)) {
            return new BaseResponse(StatusResponse.DELETE_FAIL, "删除的角色不存在！");
        }
        if (!ObjectUtils.isEmpty(masterGroupRoleService.queryByRoleId(masterRole.getId()))) {
            return new BaseResponse(StatusResponse.DELETE_FAIL, "此角色已添加到分组，请先删除分组下的角色！");
        }
        if (!ObjectUtils.isEmpty(masterRoleMenuService.queryByRoleId(masterRole.getId()))) {
            return new BaseResponse(StatusResponse.DELETE_FAIL, "此角色已分配菜单，请先删除角色下的菜单！");
        }
        if (!ObjectUtils.isEmpty(masterRoleOperateService.queryByRoleId(masterRole.getId()))) {
            return new BaseResponse(StatusResponse.DELETE_FAIL, "此角色已分配操作，请先删除角色下的操作！");
        }
        if (masterRoleService.delete(id)) {
            traceLoggerService.addTraceLogger(userAgent, "删除角色成功！【{}】", masterRole.toString());
            return new BaseResponse(StatusResponse.SUCCESS);
        } else {
            return new BaseResponse(StatusResponse.DELETE_FAIL);
        }
    }
}
