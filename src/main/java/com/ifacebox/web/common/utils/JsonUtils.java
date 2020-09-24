package com.ifacebox.web.common.utils;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author znn
 */
public class JsonUtils {
    private static final Logger logger = LoggerFactory.getLogger(JsonUtils.class);
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    static {
        OBJECT_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public static <T> T readValue(String content, Class<T> valueType) {
        try {
            return OBJECT_MAPPER.readValue(content, valueType);
        } catch (Exception e) {
            if (logger.isErrorEnabled()) {
                logger.error("解析Json异常：" + e.toString(), e);
            }
        }
        return null;
    }

    public static String writeValueAsString(Object value) {
        try {
            return OBJECT_MAPPER.writeValueAsString(value);
        } catch (Exception e) {
            if (logger.isErrorEnabled()) {
                logger.error("解析Json异常：" + e.toString(), e);
            }
        }
        return null;
    }
}
