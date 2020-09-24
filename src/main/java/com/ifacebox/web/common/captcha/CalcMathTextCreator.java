package com.ifacebox.web.common.captcha;

import com.google.code.kaptcha.text.impl.DefaultTextCreator;

import java.security.SecureRandom;
import java.util.Random;

/**
 * @author znn
 */
public class CalcMathTextCreator extends DefaultTextCreator {
    public static final String MATH_SPLIT = "@";
    private static final int CALC_ADDITION = 0;
    private static final int CALC_SUBTRACTION = 1;
    private static final int CALC_MULTIPLICATION = 2;
    private static final int CALC_DIVISION = 3;
    private static final String[] CALC_SYMBOL = {"+", "-", "×", "÷"};
    private static final String[] CALC_MATH_NUMBER = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};

    @Override
    public String getText() {
        Random random = new SecureRandom();
        int x = random.nextInt(10);
        int y = random.nextInt(10);
        int result = x + y;
        StringBuilder text = new StringBuilder();
        int calc = (int) Math.round(Math.random() * 3);
        if (calc == CALC_ADDITION) {
            //加法
            result = x + y;
            text.append(CALC_MATH_NUMBER[x]).append(CALC_SYMBOL[CALC_ADDITION]).append(CALC_MATH_NUMBER[y]);
        } else if (calc == CALC_SUBTRACTION) {
            //减法，交换位置不出现负数
            result = x >= y ? x - y : y - x;
            text.append(x >= y ? CALC_MATH_NUMBER[x] : CALC_MATH_NUMBER[y]).append(CALC_SYMBOL[CALC_SUBTRACTION]).append(x >= y ? CALC_MATH_NUMBER[x] : CALC_MATH_NUMBER[y]);
        } else if (calc == CALC_MULTIPLICATION) {
            //乘法
            result = x * y;
            text.append(CALC_MATH_NUMBER[x]).append(CALC_SYMBOL[CALC_MULTIPLICATION]).append(CALC_MATH_NUMBER[y]);
        } else if (calc == CALC_DIVISION) {
            //除法，除数==0或余数!=0使用加法
            result = (y != 0 && x % y == 0) ? x / y : x + y;
            text.append(CALC_MATH_NUMBER[x]).append((y != 0 && x % y == 0) ? CALC_SYMBOL[CALC_DIVISION] : CALC_SYMBOL[CALC_ADDITION]).append(CALC_MATH_NUMBER[y]);
        } else {
            text.append(CALC_MATH_NUMBER[x]).append(CALC_SYMBOL[CALC_ADDITION]).append(CALC_MATH_NUMBER[y]);
        }
        text.append("=?").append(MATH_SPLIT).append(result);
        return text.toString();
    }
}
