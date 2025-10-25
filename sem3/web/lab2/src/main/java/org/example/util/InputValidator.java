package org.example.util;

import java.math.BigDecimal;
import java.util.Set;

public class InputValidator {

    private static final Set<Float> VALID_R_VALUES = Set.of(1f, 1.5f, 2f, 2.5f, 3f);

    public static void validate(String xStr, String yStr, String rStr) throws IllegalArgumentException {
        if (xStr == null || yStr == null || rStr == null)
            throw new IllegalArgumentException("Не переданы все параметры.");

        // Заменяем запятую на точку для удобства
        xStr = xStr.replace(',', '.');
        yStr = yStr.replace(',', '.');
        rStr = rStr.replace(',', '.');

        BigDecimal x;
        float y;
        float r;

        try {
            x = new BigDecimal(xStr);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Некорректный формат X. Введите число от -5 до 5.");
        }

        try {
            y = Float.parseFloat(yStr);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Некорректный формат Y. Введите число от -5 до 5.");
        }

        try {
            r = Float.parseFloat(rStr);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Некорректный формат R. Допустимые значения: 1, 1.5, 2, 2.5, 3.");
        }

        // Проверяем диапазоны
        if (x.compareTo(BigDecimal.valueOf(-5)) < 0 || x.compareTo(BigDecimal.valueOf(5)) > 0)
            throw new IllegalArgumentException("X должен быть в диапазоне от -5 до 5.");

        if (y < -5 || y > 5)
            throw new IllegalArgumentException("Y должен быть в диапазоне от -5 до 5.");

        if (!VALID_R_VALUES.contains(r))
            throw new IllegalArgumentException("R должен быть одним из значений: 1, 1.5, 2, 2.5, 3.");
    }
}
