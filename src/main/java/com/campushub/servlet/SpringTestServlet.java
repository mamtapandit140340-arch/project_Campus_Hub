package com.campushub.servlet;

import com.campushub.service.CollaborationService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import java.io.IOException;
@WebServlet("/spring-test")
public class SpringTestServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        WebApplicationContext context =
                WebApplicationContextUtils.getWebApplicationContext(
                        getServletContext());
        if (context == null) {
            throw new ServletException(
                    "Spring application context not initialized");
        }

        CollaborationService service =
                context.getBean(CollaborationService.class);

        response.setContentType("text/plain;charset=UTF-8");
        response.getWriter().write(
                service.getServiceStatus());
    }
}
