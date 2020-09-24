package com.ifacebox.web.common.captcha;

import com.google.code.kaptcha.Producer;

/**
 * @author znn
 */
public interface CalcMathCaptcha {
    String parseText(String text);

    String parseResult(String text);

    Producer getProducer();
}
