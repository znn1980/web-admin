package com.ifacebox.web.admin.master.controller;

import com.ifacebox.web.admin.controller.BaseBodyController;
import com.ifacebox.web.admin.master.model.*;
import com.ifacebox.web.admin.master.service.MasterOperateService;
import com.ifacebox.web.admin.master.service.MasterRoleOperateService;
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
public class MasterOperateController extends BaseBodyController {
    @Resource
    private MasterOperateService masterOperateService;
    @Resource
    private MasterRoleOperateService masterRoleOperateService;

    @GetMapping("/admin/operate/method.json")
    @ResponseBody
    public ModelResponse<MasterOperateMethod[]> method() {
        return new ModelResponse<>(StatusResponse.SUCCESS, MasterOperateMethod.values());
    }

    @GetMapping("/admin/operate/attr.json")
    @ResponseBody
    public ModelResponse<List<Map<String, Object>>> attr() {
        return new ModelResponse<>(StatusResponse.SUCCESS, MasterOperateAttr.toArray());
    }

    @GetMapping("/admin/operate/status.json")
    @ResponseBody
    public ModelResponse<List<Map<String, Object>>> status() {
        return new ModelResponse<>(StatusResponse.SUCCESS, MasterOperateStatus.toArray());
    }

    @GetMapping("/admin/operate/root.json")
    @ResponseBody
    public ModelResponse<List<MasterOperate>> root() {
        return new ModelResponse<>(StatusResponse.SUCCESS, masterOperateService.queryByParentId(MasterOperateService.TREE_ROOT));
    }

    @GetMapping("/admin/operate/list.json")
    @ResponseBody
    public ModelResponse<List<MasterOperateTree>> list(@UserAgentRequest UserAgent userAgent) {
        if (!permissionManager.hasPermission(userAgent)) {
            return new ModelResponse<>(StatusResponse.LOGOUT);
        }
        return new ModelResponse<>(StatusResponse.SUCCESS, masterOperateService.queryTree());
    }

    @PostMapping("/admin/operate/save.json")
    @ResponseBody
    public BaseResponse save(@UserAgentRequest UserAgent userAgent, @RequestBody @Validated MasterOperate masterOperate) {
        if (!permissionManager.hasPermission(userAgent)) {
            return new BaseResponse(StatusResponse.LOGOUT);
        }
        if (!ObjectUtils.isEmpty(masterOperateService.queryByTitle(masterOperate.getTitle()))) {
            return new BaseResponse(StatusResponse.UPDATE_FAIL, "操作标题已存在！");
        }
        if (MasterOperateService.TREE_ROOT != masterOperate.getParentId()
                && ObjectUtils.isEmpty(masterOperateService.queryById(masterOperate.getParentId()))) {
            return new BaseResponse(StatusResponse.UPDATE_FAIL, "上级操作不存在！");
        }
        if (masterOperateService.save(masterOperate, userAgent)) {
            traceLoggerService.addTraceLogger(userAgent, "添加操作成功！【{}】", masterOperate.toString());
            return new BaseResponse(StatusResponse.SUCCESS);
        } else {
            return new BaseResponse(StatusResponse.SAVE_FAIL);
        }
    }

    @PutMapping("/admin/operate/edit.json")
    @ResponseBody
    public BaseResponse edit(@UserAgentRequest UserAgent userAgent, @RequestBody @Validated MasterOperate masterOperate) {
        if (!permissionManager.hasPermission(userAgent)) {
            return new BaseResponse(StatusResponse.LOGOUT);
        }
        MasterOperate oldMasterOperate = masterOperateService.queryById(masterOperate.getId());
        if (ObjectUtils.isEmpty(oldMasterOperate)) {
            return new BaseResponse(StatusResponse.DELETE_FAIL, "修改的操作不存在！");
        }
        if (!oldMasterOperate.getTitle().equals(masterOperate.getTitle())
                && !ObjectUtils.isEmpty(masterOperateService.queryByTitle(masterOperate.getTitle()))) {
            return new BaseResponse(StatusResponse.UPDATE_FAIL, "操作标题已存在！");
        }
        if (masterOperate.getId().equals(masterOperate.getParentId())) {
            return new BaseResponse(StatusResponse.UPDATE_FAIL, "上级操作不能选择自己！");
        }
        if (MasterOperateService.TREE_ROOT != masterOperate.getParentId()
                && ObjectUtils.isEmpty(masterOperateService.queryById(masterOperate.getParentId()))) {
            return new BaseResponse(StatusResponse.UPDATE_FAIL, "上级操作不存在！");
        }
        if (masterOperateService.edit(masterOperate, userAgent)) {
            traceLoggerService.addTraceLogger(userAgent, "修改操作成功！【{}】", masterOperate.toString());
            return new BaseResponse(StatusResponse.SUCCESS);
        } else {
            return new BaseResponse(StatusResponse.UPDATE_FAIL);
        }
    }

    @DeleteMapping("/admin/operate/delete.json")
    @ResponseBody
    public BaseResponse delete(@UserAgentRequest UserAgent userAgent, @RequestParam Integer id) {
        if (!permissionManager.hasPermission(userAgent)) {
            return new BaseResponse(StatusResponse.LOGOUT);
        }
        MasterOperate masterOperate = masterOperateService.queryById(id);
        if (ObjectUtils.isEmpty(masterOperate)) {
            return new BaseResponse(StatusResponse.DELETE_FAIL, "删除的操作不存在！");
        }
        if (!ObjectUtils.isEmpty(masterOperateService.queryByParentId(id))) {
            return new BaseResponse(StatusResponse.DELETE_FAIL, "请先删除下级操作！");
        }
        if (!ObjectUtils.isEmpty(masterRoleOperateService.queryByOperateId(id))) {
            return new BaseResponse(StatusResponse.DELETE_FAIL, "此操作已分配角色，请先删除角色下的操作！");
        }
        if (masterOperateService.delete(id)) {
            traceLoggerService.addTraceLogger(userAgent, "删除操作成功！【{}】", masterOperate.toString());
            return new BaseResponse(StatusResponse.SUCCESS);
        } else {
            return new BaseResponse(StatusResponse.DELETE_FAIL);
        }
    }
}
