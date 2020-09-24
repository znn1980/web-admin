package com.ifacebox.web.admin.master.controller;

import com.ifacebox.web.admin.controller.BaseBodyController;
import com.ifacebox.web.admin.master.model.MasterGroup;
import com.ifacebox.web.admin.master.model.MasterGroupStatus;
import com.ifacebox.web.admin.master.service.MasterGroupService;
import com.ifacebox.web.admin.master.service.MasterUserGroupService;
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
public class MasterGroupController extends BaseBodyController {
    @Resource
    private MasterGroupService masterGroupService;
    @Resource
    private MasterUserGroupService masterUserGroupService;

    @GetMapping("/admin/group/status.json")
    @ResponseBody
    public ModelResponse<List<Map<String, Object>>> status() {
        return new ModelResponse<>(StatusResponse.SUCCESS, MasterGroupStatus.toArray());
    }

    @GetMapping("/admin/group/list.json")
    @ResponseBody
    public ModelResponse<List<MasterGroup>> list(@UserAgentRequest UserAgent userAgent) {
        if (!permissionManager.hasPermission(userAgent)) {
            return new ModelResponse<>(StatusResponse.LOGOUT);
        }
        return new ModelResponse<>(StatusResponse.SUCCESS, masterGroupService.query());
    }

    @PostMapping("/admin/group/save.json")
    @ResponseBody
    public BaseResponse save(@UserAgentRequest UserAgent userAgent, @RequestBody @Validated MasterGroup masterGroup) {
        if (!permissionManager.hasPermission(userAgent)) {
            return new BaseResponse(StatusResponse.LOGOUT);
        }
        if (!ObjectUtils.isEmpty(masterGroupService.queryByGroupName(masterGroup.getGroupName()))) {
            return new BaseResponse(StatusResponse.SAVE_FAIL, "分组名称已存在！");
        }
        if (masterGroupService.save(masterGroup, userAgent)) {
            traceLoggerService.addTraceLogger(userAgent, "添加分组成功！【{}】", masterGroup.toString());
            return new BaseResponse(StatusResponse.SUCCESS);
        } else {
            return new BaseResponse(StatusResponse.SAVE_FAIL);
        }
    }

    @PutMapping("/admin/group/edit.json")
    @ResponseBody
    public BaseResponse edit(@UserAgentRequest UserAgent userAgent, @RequestBody @Validated MasterGroup masterGroup) {
        if (!permissionManager.hasPermission(userAgent)) {
            return new BaseResponse(StatusResponse.LOGOUT);
        }
        MasterGroup oldMasterGroup = masterGroupService.queryById(masterGroup.getId());
        if (ObjectUtils.isEmpty(oldMasterGroup)) {
            return new BaseResponse(StatusResponse.SAVE_FAIL, "修改的分组不存在！");
        }
        if (!oldMasterGroup.getGroupName().equals(masterGroup.getGroupName())
                && !ObjectUtils.isEmpty(masterGroupService.queryByGroupName(masterGroup.getGroupName()))) {
            return new BaseResponse(StatusResponse.SAVE_FAIL, "分组名称已存在！");
        }
        if (masterGroupService.edit(masterGroup, userAgent)) {
            traceLoggerService.addTraceLogger(userAgent, "修改分组成功！【{}】", masterGroup.toString());
            return new BaseResponse(StatusResponse.SUCCESS);
        } else {
            return new BaseResponse(StatusResponse.UPDATE_FAIL);
        }
    }

    @DeleteMapping("/admin/group/delete.json")
    @ResponseBody
    public BaseResponse delete(@UserAgentRequest UserAgent userAgent, @RequestParam Integer id) {
        if (!permissionManager.hasPermission(userAgent)) {
            return new BaseResponse(StatusResponse.LOGOUT);
        }
        MasterGroup masterGroup = masterGroupService.queryById(id);
        if (ObjectUtils.isEmpty(masterGroup)) {
            return new BaseResponse(StatusResponse.DELETE_FAIL, "删除的分组不存在！");
        }
        if (!ObjectUtils.isEmpty(masterUserGroupService.queryByGroupId(id))) {
            return new BaseResponse(StatusResponse.DELETE_FAIL, "此分组已分配用户，请先删除用户下的分组！");
        }
        if (masterGroupService.delete(id)) {
            traceLoggerService.addTraceLogger(userAgent, "删除分组成功！【{}】", masterGroup.toString());
            return new BaseResponse(StatusResponse.SUCCESS);
        } else {
            return new BaseResponse(StatusResponse.DELETE_FAIL);
        }
    }
}
