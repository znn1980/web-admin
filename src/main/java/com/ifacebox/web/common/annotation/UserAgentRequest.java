package com.ifacebox.web.common.annotation;

import java.lang.annotation.*;

/**
 * @author znn
 */
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface UserAgentRequest {
}
