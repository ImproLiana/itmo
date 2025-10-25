package org.example.util;

import java.math.BigDecimal;

public class PointChecker {

    public static boolean checkCordsIn(BigDecimal x, Float y, Float r) {
        BigDecimal rBD = new BigDecimal(r.toString());
        BigDecimal halfR = rBD.divide(BigDecimal.valueOf(2));

        boolean rectangle =
                (x.compareTo(BigDecimal.ZERO) <= 0) &&               // x ≤ 0
                (x.compareTo(rBD.negate()) >= 0) &&                  // x ≥ −R
                (BigDecimal.valueOf(y).compareTo(BigDecimal.ZERO) <= 0) &&      // y ≤ 0
                (BigDecimal.valueOf(y).compareTo(rBD.negate()) >= 0);            // y ≥ −R


        boolean triangle = (x.compareTo(BigDecimal.ZERO) >= 0) &&
                (BigDecimal.valueOf(y).compareTo(BigDecimal.ZERO) <= 0) &&
                (BigDecimal.valueOf(y).compareTo(x.subtract(halfR)) >= 0);

        boolean circle = (x.compareTo(BigDecimal.ZERO) >= 0) &&
                (BigDecimal.valueOf(y).compareTo(BigDecimal.ZERO) >= 0) &&
                (x.pow(2).add(BigDecimal.valueOf(y).pow(2))
                        .compareTo(halfR.pow(2)) <= 0);

        return rectangle || triangle || circle;
    }
}
