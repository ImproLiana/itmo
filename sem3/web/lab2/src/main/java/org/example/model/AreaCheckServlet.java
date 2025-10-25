package org.example.model;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import org.example.util.PointChecker;
import org.example.util.InputValidator;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@WebServlet("/areaCheck")
public class AreaCheckServlet extends HttpServlet {

    private long resultIdCounter = 0;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        try {
            String xParam = req.getParameter("x");
            String[] yParams = req.getParameterValues("y");
            String[] rParams = req.getParameterValues("r");

            if (xParam == null || yParams == null || rParams == null) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Отсутствуют параметры запроса");
                return;
            }

            List<Map<String, Object>> newResults = new ArrayList<>();
            long start = System.nanoTime();

            for (String yStr : yParams) {
                for (String rStr : rParams) {

                    try {
                        InputValidator.validate(xParam, yStr, rStr);
                    } catch (IllegalArgumentException ex) {
                        resp.sendError(HttpServletResponse.SC_BAD_REQUEST, ex.getMessage());
                        return;
                    }

                    BigDecimal x = new BigDecimal(xParam.replace(',', '.'));
                    float y = Float.parseFloat(yStr);
                    float r = Float.parseFloat(rStr);

                    boolean hit = PointChecker.checkCordsIn(x, y, r);
                    String currentTime = LocalDateTime.now()
                            .format(DateTimeFormatter.ofPattern("HH:mm:ss"));
                    long executionTime = System.nanoTime() - start;

                    Map<String, Object> result = new LinkedHashMap<>();
                    result.put("id", ++resultIdCounter);
                    result.put("x", x);
                    result.put("y", y);
                    result.put("r", r);
                    result.put("hit", hit);
                    result.put("currentTime", currentTime);
                    result.put("executionTime", executionTime + " нс");

                    newResults.add(result);
                }
            }

            // Добавляем в общий контекст приложения
            ServletContext context = getServletContext();
            synchronized (context) {
                @SuppressWarnings("unchecked")
                List<Map<String, Object>> results =
                        (List<Map<String, Object>>) context.getAttribute("results");

                if (results == null) results = new ArrayList<>();
                results.addAll(newResults);
                context.setAttribute("results", results);
            }

            // Отправляем результаты в JSP
            req.setAttribute("results", newResults);
            req.getRequestDispatcher("/result.jsp").forward(req, resp);

        } catch (Exception e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Некорректные данные");
        }
    }
}
