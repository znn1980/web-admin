package com.ifacebox.web.common.manager;

import com.ifacebox.web.common.model.PlatformProperties;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author znn
 */
@Component
public class PlatformManager {
    @Resource
    private PlatformProperties platformProperties;

    public final PlatformProperties getPlatformProperties() {
        return platformProperties;
    }
}
