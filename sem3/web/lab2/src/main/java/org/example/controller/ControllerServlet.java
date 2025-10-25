package org.example.controller;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

public class ControllerServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String x = req.getParameter("x");
        String[] yValues = req.getParameterValues("y");
        String[] rValues = req.getParameterValues("r");

        boolean hasParams = x != null && !x.isEmpty() &&
                yValues != null && yValues.length > 0 &&
                rValues != null && rValues.length > 0;

        RequestDispatcher dispatcher;
        if (hasParams) {
            dispatcher = req.getRequestDispatcher("/areaCheck");
        } else {
            dispatcher = req.getRequestDispatcher("/index.jsp");
        }
        dispatcher.forward(req, resp);
    }
}
