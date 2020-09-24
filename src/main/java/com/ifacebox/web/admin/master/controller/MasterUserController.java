package com.ifacebox.web.admin.master.controller;

import com.ifacebox.web.admin.controller.BaseBodyController;
import com.ifacebox.web.admin.master.model.*;
import com.ifacebox.web.admin.master.service.MasterUserService;
import com.ifacebox.web.common.annotation.UserAgentRequest;
import com.ifacebox.web.common.model.*;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author znn
 */
@Controller
public class MasterUserController extends BaseBodyController {

    @PostMapping("/admin/login.json")
    @ResponseBody
    public ModelResponse<MasterUser> login(@UserAgentRequest UserAgent userAgent, @RequestBody @Validated MasterUserLogin masterUserLogin) {
        if (!masterUserService.isCaptcha(userAgent.getSession(), masterUserLogin.getCaptcha())) {
            return new ModelResponse<>(StatusResponse.LOGOUT, "验证码输入不正确！");
        }
        masterUserLogin.setPassword(permissionManager.hasPassword(masterUserLogin.getPassword()));
        MasterUser masterUser = masterUserService.login(masterUserLogin, userAgent);
        if (ObjectUtils.isEmpty(masterUser)) {
            return new ModelResponse<>(StatusResponse.LOGOUT, "登陆失败，请检查用户名密码是否正确！");
        }
        if (!MasterUserStatus.ENABLE.getStatusId().equals(masterUser.getStatusId())) {
            return new ModelResponse<>(StatusResponse.LOGOUT, "账号未启用，请联系管理员！");
        }
        traceLoggerService.addTraceLogger(userAgent, "用户登陆成功！【{}】", masterUser.toString());
        return new ModelResponse<>(StatusResponse.SUCCESS, masterUser);
    }

    @GetMapping("/admin/user/attr.json")
    @ResponseBody
    public ModelResponse<List<Map<String, Object>>> attr() {
        return new ModelResponse<>(StatusResponse.SUCCESS, MasterUserAttr.toArray());
    }

    @GetMapping("/admin/user/status.json")
    @ResponseBody
    public ModelResponse<List<Map<String, Object>>> status() {
        return new ModelResponse<>(StatusResponse.SUCCESS, MasterUserStatus.toArray());
    }

    @GetMapping("/admin/user/list.json")
    @ResponseBody
    public ModelResponse<List<MasterUser>> list(@UserAgentRequest UserAgent userAgent, MasterUserQuery userQuery) {
        if (!permissionManager.hasPermission(userAgent)) {
            return new ModelResponse<>(StatusResponse.LOGOUT);
        }
        return new ModelResponse<>(StatusResponse.SUCCESS, masterUserService.total(userQuery), masterUserService.query(userQuery));
    }

    @PostMapping("/admin/user/save.json")
    @ResponseBody
    public BaseResponse save(@UserAgentRequest UserAgent userAgent, @RequestBody @Validated MasterUser masterUser) {
        if (!permissionManager.hasPermission(userAgent)) {
            return new BaseResponse(StatusResponse.LOGOUT);
        }
        if (!ObjectUtils.isEmpty(masterUserService.queryByUsername(masterUser.getUsername()))) {
            return new BaseResponse(StatusResponse.SAVE_FAIL, "用户名称已存在！");
        }
        if (!ObjectUtils.isEmpty(masterUserService.queryByMobile(masterUser.getMobile()))) {
            return new BaseResponse(StatusResponse.SAVE_FAIL, "用户手机号码已存在！");
        }
        if (!ObjectUtils.isEmpty(masterUserService.queryByEmail(masterUser.getEmail()))) {
            return new BaseResponse(StatusResponse.SAVE_FAIL, "用户邮箱地址已存在！");
        }
        //默认密码为手机号码的后6位
        masterUser.setPassword(permissionManager.hasPassword(masterUser.getMobile().substring(masterUser.getMobile().length() - 6)));
        if (masterUserService.save(masterUser, userAgent)) {
            traceLoggerService.addTraceLogger(userAgent, "添加用户成功！【{}】", masterUser.toString());
            return new BaseResponse(StatusResponse.SUCCESS);
        } else {
            return new BaseResponse(StatusResponse.SAVE_FAIL);
        }
    }

    @PutMapping("/admin/user/edit.json")
    @ResponseBody
    public BaseResponse edit(@UserAgentRequest UserAgent userAgent, @RequestBody @Validated MasterUser masterUser) {
        if (!permissionManager.hasPermission(userAgent)) {
            return new BaseResponse(StatusResponse.LOGOUT);
        }
        MasterUser oldMasterUser = masterUserService.queryById(masterUser.getId());
        if (ObjectUtils.isEmpty(oldMasterUser)) {
            return new BaseResponse(StatusResponse.DELETE_FAIL, "修改的用户不存在！");
        }
        if (MasterUserService.ADMIN_NAME.equals(oldMasterUser.getUsername())
                && !MasterUserService.ADMIN_NAME.equals(userAgent.getMasterUser().getUsername())) {
            return new BaseResponse(StatusResponse.SAVE_FAIL, "你不能修改超级管理员的信息！");
        }
        if (!oldMasterUser.getUsername().equals(masterUser.getUsername())) {
            return new BaseResponse(StatusResponse.SAVE_FAIL, "用户名称不能修改！");
        }
        if (!oldMasterUser.getMobile().equals(masterUser.getMobile())
                && !ObjectUtils.isEmpty(masterUserService.queryByMobile(masterUser.getMobile()))) {
            return new BaseResponse(StatusResponse.SAVE_FAIL, "用户手机号码已存在！");
        }
        if (!oldMasterUser.getEmail().equals(masterUser.getEmail())
                && !ObjectUtils.isEmpty(masterUserService.queryByEmail(masterUser.getEmail()))) {
            return new BaseResponse(StatusResponse.SAVE_FAIL, "用户邮箱地址已存在！");
        }
        if (masterUserService.edit(masterUser, userAgent)) {
            traceLoggerService.addTraceLogger(userAgent, "修改用户成功！【{}】", masterUser.toString());
            return new BaseResponse(StatusResponse.SUCCESS);
        } else {
            return new BaseResponse(StatusResponse.UPDATE_FAIL);
        }
    }

    @DeleteMapping("/admin/user/delete.json")
    @ResponseBody
    public BaseResponse delete(@UserAgentRequest UserAgent userAgent, @RequestParam Integer id) {
        if (!permissionManager.hasPermission(userAgent)) {
            return new BaseResponse(StatusResponse.LOGOUT);
        }
        MasterUser masterUser = masterUserService.queryById(id);
        if (ObjectUtils.isEmpty(masterUser)) {
            return new BaseResponse(StatusResponse.DELETE_FAIL, "删除的用户不存在！");
        }
        if (MasterUserService.ADMIN_NAME.equals(masterUser.getUsername())) {
            return new BaseResponse(StatusResponse.DELETE_FAIL, "超级管理员不能删除！");
        }
        if (masterUser.getId().equals(userAgent.getMasterUser().getId())) {
            return new BaseResponse(StatusResponse.DELETE_FAIL, "自己不能删除自己！");
        }
        if (masterUserService.delete(id)) {
            traceLoggerService.addTraceLogger(userAgent, "删除用户成功！【{}】", masterUser.toString());
            return new BaseResponse(StatusResponse.SUCCESS);
        } else {
            return new BaseResponse(StatusResponse.DELETE_FAIL);
        }
    }

    @GetMapping("/admin/user/reset.json")
    @ResponseBody
    public BaseResponse reset(@UserAgentRequest UserAgent userAgent, @RequestParam Integer id) {
        if (!permissionManager.hasPermission(userAgent)) {
            return new BaseResponse(StatusResponse.LOGOUT);
        }
        MasterUser masterUser = masterUserService.queryById(id);
        if (ObjectUtils.isEmpty(masterUser)) {
            return new BaseResponse(StatusResponse.DELETE_FAIL, "重置密码的用户不存在！");
        }
        if (MasterUserService.ADMIN_NAME.equals(masterUser.getUsername())) {
            return new BaseResponse(StatusResponse.DELETE_FAIL, "超级管理员的密码不能重置！");
        }
        if (masterUser.getId().equals(userAgent.getMasterUser().getId())) {
            return new BaseResponse(StatusResponse.DELETE_FAIL, "自己不能重置自己的密码！");
        }
        //默认密码为手机号码的后6位
        masterUser.setPassword(permissionManager.hasPassword(masterUser.getMobile().substring(masterUser.getMobile().length() - 6)));
        if (masterUserService.editPassword(masterUser)) {
            traceLoggerService.addTraceLogger(userAgent, "重置用户密码成功！【{}】", masterUser.toString());
            return new BaseResponse(StatusResponse.SUCCESS);
        } else {
            return new BaseResponse(StatusResponse.DELETE_FAIL);
        }
    }

    @GetMapping("/admin/user/unlock.json")
    @ResponseBody
    public BaseResponse unlock(@UserAgentRequest UserAgent userAgent, String lockPassword) {
        if (!permissionManager.hasLogin(userAgent)) {
            return new BaseResponse(StatusResponse.LOGOUT);
        }
        if (!userAgent.getMasterUser().getPassword().equals(permissionManager.hasPassword(lockPassword))) {
            return new BaseResponse(StatusResponse.VALID_FAIL, "密码输入错误，解锁失败！");
        }
        return new BaseResponse(StatusResponse.SUCCESS);
    }

    @PostMapping("/admin/user/password.json")
    @ResponseBody
    public BaseResponse password(@UserAgentRequest UserAgent userAgent, @RequestBody @Validated MasterUserPassword userPassword) {
        if (!permissionManager.hasPermission(userAgent)) {
            return new BaseResponse(StatusResponse.LOGOUT);
        }
        MasterUser masterUser = masterUserService.queryById(userAgent.getMasterUser().getId());
        if (ObjectUtils.isEmpty(masterUser)) {
            return new BaseResponse(StatusResponse.DELETE_FAIL, "用户不存在！");
        }
        if (!masterUser.getPassword().equals(permissionManager.hasPassword(userPassword.getOldPassword()))) {
            return new BaseResponse(StatusResponse.DELETE_FAIL, "原密码输入不正确！");
        }
        if (!userPassword.getPassword().equals(userPassword.getConfirmPassword())) {
            return new BaseResponse(StatusResponse.DELETE_FAIL, "新密码输入不一致！");
        }
        masterUser.setPassword(permissionManager.hasPassword(userPassword.getPassword()));
        if (masterUserService.editPassword(masterUser)) {
            traceLoggerService.addTraceLogger(userAgent, "用户修改密码成功！【{}】", masterUser.toString());
            return new BaseResponse(StatusResponse.SUCCESS);
        } else {
            return new BaseResponse(StatusResponse.DELETE_FAIL);
        }
    }
}