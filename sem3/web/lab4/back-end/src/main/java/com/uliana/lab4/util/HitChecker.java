package com.uliana.lab4.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class HitChecker {

    public static boolean check(double x, BigDecimal y, int r) {
        BigDecimal R = BigDecimal.valueOf(Math.abs(r));
        BigDecimal xBD = BigDecimal.valueOf(x);

        return isInCircle(xBD, y, R)
                || isInRectangle(xBD, y, R)
                || isInTriangle(xBD, y, R);
    }

    // 🔵 четверть круга
    private static boolean isInCircle(BigDecimal x, BigDecimal y, BigDecimal R) {
        if (x.compareTo(BigDecimal.ZERO) > 0 || y.compareTo(BigDecimal.ZERO) > 0) {
            return false;
        }

        BigDecimal left = x.pow(2).add(y.pow(2));
        BigDecimal right = R.pow(2);

        return left.compareTo(right) <= 0;
    }

    // 🟦 прямоугольник
    private static boolean isInRectangle(BigDecimal x, BigDecimal y, BigDecimal R) {
        return x.compareTo(BigDecimal.ZERO) >= 0
                && x.compareTo(R) <= 0
                && y.compareTo(BigDecimal.ZERO) <= 0
                && y.compareTo(R.negate()) >= 0;
    }

    // 🔺 треугольник
    private static boolean isInTriangle(BigDecimal x, BigDecimal y, BigDecimal R) {
        if (x.compareTo(BigDecimal.ZERO) < 0 || y.compareTo(BigDecimal.ZERO) < 0) {
            return false;
        }

        BigDecimal twoX = x.multiply(BigDecimal.valueOf(2));
        BigDecimal border = R.subtract(twoX);

        return y.compareTo(border) <= 0;
    }
}
