package com.ifacebox.web.admin.master.controller;

import com.ifacebox.web.admin.controller.BaseBodyController;
import com.ifacebox.web.admin.master.model.*;
import com.ifacebox.web.admin.master.service.MasterMenuService;
import com.ifacebox.web.admin.master.service.MasterRoleMenuService;
import com.ifacebox.web.admin.master.service.MasterUserService;
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
public class MasterMenuController extends BaseBodyController {
    @Resource
    private MasterMenuService masterMenuService;
    @Resource
    private MasterRoleMenuService masterRoleMenuService;

    @GetMapping("/admin/menu/side.json")
    @ResponseBody
    public ModelResponse<List<MasterMenuTree>> side(@UserAgentRequest UserAgent userAgent) {
        if (!permissionManager.hasLogin(userAgent)) {
            return new ModelResponse<>(StatusResponse.LOGOUT);
        }
        MasterUser masterUser = userAgent.getMasterUser();
        List<MasterMenuTree> menuTreeList;
        if (MasterUserService.ADMIN_NAME.equals(masterUser.getUsername())) {
            menuTreeList = masterMenuService.queryTree();
        } else {
            menuTreeList = masterMenuService.queryTreeByUserId(masterUser.getId());
        }
        return new ModelResponse<>(StatusResponse.SUCCESS, menuTreeList);
    }

    @GetMapping("/admin/menu/attr.json")
    @ResponseBody
    public ModelResponse<List<Map<String, Object>>> attr() {
        return new ModelResponse<>(StatusResponse.SUCCESS, MasterMenuAttr.toArray());
    }

    @GetMapping("/admin/menu/status.json")
    @ResponseBody
    public ModelResponse<List<Map<String, Object>>> status() {
        return new ModelResponse<>(StatusResponse.SUCCESS, MasterMenuStatus.toArray());
    }

    @GetMapping("/admin/menu/root.json")
    @ResponseBody
    public ModelResponse<List<MasterMenu>> root(@UserAgentRequest UserAgent userAgent) {
        if (!permissionManager.hasLogin(userAgent)) {
            return new ModelResponse<>(StatusResponse.LOGOUT);
        }
        return new ModelResponse<>(StatusResponse.SUCCESS, masterMenuService.queryByParentId(MasterMenuService.TREE_ROOT));
    }

    @GetMapping("/admin/menu/list.json")
    @ResponseBody
    public ModelResponse<List<MasterMenuTree>> list(@UserAgentRequest UserAgent userAgent) {
        if (!permissionManager.hasPermission(userAgent)) {
            return new ModelResponse<>(StatusResponse.LOGOUT);
        }
        return new ModelResponse<>(StatusResponse.SUCCESS, masterMenuService.queryTree());
    }

    @PostMapping("/admin/menu/save.json")
    @ResponseBody
    public BaseResponse save(@UserAgentRequest UserAgent userAgent, @RequestBody @Validated MasterMenu masterMenu) {
        if (!permissionManager.hasPermission(userAgent)) {
            return new BaseResponse(StatusResponse.LOGOUT);
        }
        if (!ObjectUtils.isEmpty(masterMenuService.queryByTitle(masterMenu.getTitle()))) {
            return new BaseResponse(StatusResponse.UPDATE_FAIL, "菜单标题已存在！");
        }
        if (MasterMenuService.TREE_ROOT != masterMenu.getParentId()
                && ObjectUtils.isEmpty(masterMenuService.queryById(masterMenu.getParentId()))) {
            return new BaseResponse(StatusResponse.UPDATE_FAIL, "上级菜单不存在！");
        }
        if (masterMenuService.save(masterMenu, userAgent)) {
            traceLoggerService.addTraceLogger(userAgent, "添加菜单成功！【{}】", masterMenu.toString());
            return new BaseResponse(StatusResponse.SUCCESS);
        } else {
            return new BaseResponse(StatusResponse.SAVE_FAIL);
        }
    }

    @PutMapping("/admin/menu/edit.json")
    @ResponseBody
    public BaseResponse edit(@UserAgentRequest UserAgent userAgent, @RequestBody @Validated MasterMenu masterMenu) {
        if (!permissionManager.hasPermission(userAgent)) {
            return new BaseResponse(StatusResponse.LOGOUT);
        }
        MasterMenu oldMasterMenu = masterMenuService.queryById(masterMenu.getId());
        if (ObjectUtils.isEmpty(oldMasterMenu)) {
            return new BaseResponse(StatusResponse.DELETE_FAIL, "修改的菜单不存在！");
        }
        if (!oldMasterMenu.getTitle().equals(masterMenu.getTitle())
                && !ObjectUtils.isEmpty(masterMenuService.queryByTitle(masterMenu.getTitle()))) {
            return new BaseResponse(StatusResponse.UPDATE_FAIL, "菜单标题已存在！");
        }
        if (masterMenu.getId().equals(masterMenu.getParentId())) {
            return new BaseResponse(StatusResponse.UPDATE_FAIL, "上级菜单不能选择自己！");
        }
        if (MasterMenuService.TREE_ROOT != masterMenu.getParentId()
                && ObjectUtils.isEmpty(masterMenuService.queryById(masterMenu.getParentId()))) {
            return new BaseResponse(StatusResponse.UPDATE_FAIL, "上级菜单不存在！");
        }
        if (masterMenuService.edit(masterMenu, userAgent)) {
            traceLoggerService.addTraceLogger(userAgent, "修改菜单成功！【{}】", masterMenu.toString());
            return new BaseResponse(StatusResponse.SUCCESS);
        } else {
            return new BaseResponse(StatusResponse.UPDATE_FAIL);
        }
    }

    @DeleteMapping("/admin/menu/delete.json")
    @ResponseBody
    public BaseResponse delete(@UserAgentRequest UserAgent userAgent, @RequestParam Integer id) {
        if (!permissionManager.hasPermission(userAgent)) {
            return new BaseResponse(StatusResponse.LOGOUT);
        }
        MasterMenu masterMenu = masterMenuService.queryById(id);
        if (ObjectUtils.isEmpty(masterMenu)) {
            return new BaseResponse(StatusResponse.DELETE_FAIL, "删除的菜单不存在！");
        }
        if (!ObjectUtils.isEmpty(masterMenuService.queryByParentId(id))) {
            return new BaseResponse(StatusResponse.DELETE_FAIL, "请先删除下级菜单！");
        }
        if (!ObjectUtils.isEmpty(masterRoleMenuService.queryByMenuId(id))) {
            return new BaseResponse(StatusResponse.DELETE_FAIL, "此菜单已分配角色，请先删除角色下的菜单！");
        }
        if (masterMenuService.delete(id)) {
            traceLoggerService.addTraceLogger(userAgent, "删除菜单成功！【{}】", masterMenu.toString());
            return new BaseResponse(StatusResponse.SUCCESS);
        } else {
            return new BaseResponse(StatusResponse.DELETE_FAIL);
        }
    }
}
