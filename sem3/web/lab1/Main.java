import java.math.BigDecimal;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;

import com.fastcgi.FCGIInterface;

public class Main {
    public static void main(String[] args) {
        FCGIInterface fcgiInterface = new FCGIInterface();
        while (fcgiInterface.FCGIaccept() >= 0) {
            long startTime = System.nanoTime();
            try {
                System.out.print("HTTP/1.1 200 OK\r\n");
                System.out.print("Content-type: application/json\r\n");
                String request = FCGIInterface.request.params.getProperty("REQUEST_METHOD");
                if (!"GET".equals(request)) {
                    System.out.println("{\"error\":\"Only GET is supported\"}");
                    continue;
                }

                String queryString = FCGIInterface.request.params.getProperty("QUERY_STRING");
                if (queryString == null || queryString.trim().isEmpty()) {
                    System.out.println("{\"error\":\"Query string is empty\"}");
                    continue;
                }

                LinkedHashMap<String, String> map = getValues(queryString);

                if (!map.containsKey("x") || !map.containsKey("y") || !map.containsKey("r")) {
                    System.out.println("{\"error\":\"Missing parameters.\"}");
                    continue;
                }

                try {
                    float x = Float.parseFloat(map.get("x"));
                    BigDecimal y = new BigDecimal(map.get("y"));
                    int r = Integer.parseInt(map.get("r"));

                    if (!checkCords(x, y, r)) {
                        System.out.println("{\"error\":\"Bad parameters.\"}");
                        continue;
                    }

                    boolean res = checkCordsIn(x, y, r);
                    long endTime = System.nanoTime();
                    double execTimeMs = (endTime - startTime) / 1_000_000.0;
                    String body = buildJsonResponse(String.valueOf(x), String.valueOf(y), String.valueOf(r), res, execTimeMs);
                    byte[] bytes = body.getBytes(StandardCharsets.UTF_8);
                    System.out.print("Content-Length: " + bytes.length + "\r\n\r\n");
                    System.out.print(body);
                } catch (NumberFormatException e) {
                    System.out.println("{\"error\":\"Number format exception\"}");
                }
            } catch (Exception e) {
                System.out.println("{\"error\":\"Error on server: " + e.getMessage() + "\"}");
            }
        }
    }

    private static LinkedHashMap<String, String> getValues(String queryString) {
        LinkedHashMap<String, String> map = new LinkedHashMap<>();
        try {
            String[] args = queryString.split("&");
            for (String string : args) {
                String[] arg = string.split("=", 2);
                if (arg.length == 2) {
                    String key = URLDecoder.decode(arg[0], StandardCharsets.UTF_8);
                    String value = URLDecoder.decode(arg[1], StandardCharsets.UTF_8);
                    map.put(key, value);
                }
            }
        } catch (Exception e) {
            System.err.println("Error parsing query string: " + e.getMessage());
        }
        return map;
    }

    public static boolean checkCords(float x, BigDecimal y, int r) {
        List<Float> correctX = Arrays.asList(-2.0f, -1.5f, -1.0f, -0.5f, 0.0f, 0.5f, 1.0f, 1.5f, 2.0f);
        List<Integer> correctR = Arrays.asList(1, 2, 3, 4, 5);

        return correctX.contains(x) && correctR.contains(r) &&(y.compareTo(new BigDecimal("-3")) > 0) &&
                (y.compareTo(new BigDecimal("5")) < 0);
    }

    public static boolean checkCordsIn(float x, BigDecimal y, int r) {
        BigDecimal rHalf = new BigDecimal(r).divide(new BigDecimal("2.0"));

        boolean circle = (x <= 0) &&
                (y.compareTo(BigDecimal.ZERO) >= 0) &&
                (new BigDecimal(x * x).add(y.pow(2)).compareTo(new BigDecimal(r * r)) <= 0);

        boolean rect = (x <= 0) &&
                (y.compareTo(BigDecimal.ZERO) <= 0) &&
                (x >= -r) &&
                (y.compareTo(rHalf.negate()) >= 0);

        boolean triangle = (x >= 0) &&
                (y.compareTo(BigDecimal.ZERO) >= 0) &&
                (x <= rHalf.floatValue()) &&
                (y.compareTo(rHalf.subtract(new BigDecimal(x))) <= 0);

        return circle || rect || triangle;
    }

    public static String buildJsonResponse(String x, String y, String r, boolean Hit, double execTimeMs) {
        String execTimeStr = String.format(Locale.US, "%.2f", execTimeMs);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        return String.format(Locale.US,
                "{\"x\": \"%s\", \"y\": \"%s\", \"r\": \"%s\", \"time\": \"%s\" ,\"execTime\": \"%s\", \"hit\": %s}",
                x, y, r, dtf.format(java.time.LocalDateTime.now()), execTimeStr, Hit);
    }

    private static void respondError(String message) {
        System.out.print("Content-type: application/json\r\n\r\n");
        System.out.println("{\"error\": \"" + message + "\"}");
    }

}
