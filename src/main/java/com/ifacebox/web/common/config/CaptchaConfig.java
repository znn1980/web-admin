package com.ifacebox.web.common.config;

import com.ifacebox.web.common.captcha.CalcMathCaptcha;
import com.ifacebox.web.common.captcha.impl.CalcMathCaptchaImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * @author znn
 */
@Configuration
public class CaptchaConfig {
    @Bean
    public CalcMathCaptcha calcMathCaptcha() {
        return new CalcMathCaptchaImpl();
    }
}
