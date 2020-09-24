package com.ifacebox.web.common.captcha.impl;

import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import com.ifacebox.web.common.captcha.CalcMathCaptcha;
import com.ifacebox.web.common.captcha.CalcMathTextCreator;

import java.util.Properties;

/**
 * @author znn
 */
public class CalcMathCaptchaImpl implements CalcMathCaptcha {
    private DefaultKaptcha defaultKaptcha;

    public CalcMathCaptchaImpl() {
        defaultKaptcha = new DefaultKaptcha() {
            {
                this.setConfig(new Config(new Properties() {
                    {
                        this.put(Constants.KAPTCHA_BORDER, false);
                        this.put(Constants.KAPTCHA_TEXTPRODUCER_FONT_COLOR, "black");
                        this.put(Constants.KAPTCHA_TEXTPRODUCER_CHAR_SPACE, "5");
                        this.put(Constants.KAPTCHA_TEXTPRODUCER_IMPL, CalcMathTextCreator.class.getName());
                        this.put(Constants.KAPTCHA_TEXTPRODUCER_FONT_NAMES, "Arial,Courier,cmr10,宋体,楷体,微软雅黑");
                    }
                }));
            }
        };
    }

    @Override
    public Producer getProducer() {
        return defaultKaptcha;
    }

    @Override
    public String parseText(String text) {
        return text.substring(0, text.lastIndexOf(CalcMathTextCreator.MATH_SPLIT));
    }

    @Override
    public String parseResult(String text) {
        return text.substring(text.lastIndexOf(CalcMathTextCreator.MATH_SPLIT) + CalcMathTextCreator.MATH_SPLIT.length());
    }
}
