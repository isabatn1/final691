package com.example;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;

public class HelloServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        resp.setContentType("text/plain");

        String secret = System.getenv("POSTGRES_CONN");

        resp.getWriter().println("Merhaba Week12 Jakarta!");
        resp.getWriter().println("Vault Secret: " + secret);
    }
}
