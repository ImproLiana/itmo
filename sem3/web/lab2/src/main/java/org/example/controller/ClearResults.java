package org.example.controller;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.*;

@WebServlet("/results")
public class ClearResults extends HttpServlet {

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String id = req.getParameter("id");
        ServletContext context = getServletContext();

        synchronized (context) { // чтобы не было гонок при одновременных запросах
            @SuppressWarnings("unchecked")
            List<Map<String, Object>> results =
                    (List<Map<String, Object>>) context.getAttribute("results");

            if (results == null) {
                resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
                return;
            }

            if (id == null) {
                // Удаляем все результаты
                results.clear();
                context.setAttribute("results", results);
                resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
            } else {
                // Удаляем конкретный результат по ID
                boolean removed = removeResultById(results, id);
                if (removed) {
                    context.setAttribute("results", results);
                    resp.setStatus(HttpServletResponse.SC_OK);
                } else {
                    resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                }
            }
        }
    }

    private boolean removeResultById(List<Map<String, Object>> results, String id) {
        Iterator<Map<String, Object>> iterator = results.iterator();
        while (iterator.hasNext()) {
            Map<String, Object> result = iterator.next();
            if (String.valueOf(result.get("id")).equals(id)) {
                iterator.remove();
                return true;
            }
        }
        return false;
    }
}
