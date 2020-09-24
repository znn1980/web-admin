package com.ifacebox.web.admin.controller;

import com.ifacebox.web.common.annotation.UserAgentRequest;
import com.ifacebox.web.common.model.BaseResponse;
import com.ifacebox.web.common.model.StatusResponse;
import com.ifacebox.web.common.model.UserAgent;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author znn
 */
public class BaseBodyController extends BaseController {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public BaseResponse valid(@UserAgentRequest UserAgent userAgent, MethodArgumentNotValidException ex) {
        BindingResult bindingResult = ex.getBindingResult();
        FieldError fieldError = bindingResult.getFieldError();
        if (!ObjectUtils.isEmpty(fieldError)) {
            traceLoggerService.addTraceLogger(userAgent, fieldError.getDefaultMessage());
            return new BaseResponse(StatusResponse.VALID_FAIL, fieldError.getDefaultMessage());
        }
        return new BaseResponse(StatusResponse.VALID_FAIL);
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public BaseResponse error(@UserAgentRequest UserAgent userAgent, Exception ex) {
        traceLoggerService.addTraceLogger(userAgent, ex.toString());
        if (logger.isErrorEnabled()) {
            logger.error(ex.getMessage(), ex);
        }
        return new BaseResponse(StatusResponse.EXCEPTION, ex.toString());
    }
}
