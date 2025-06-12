package com.example;

import java.io.IOException;
import java.sql.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;

public class HelloServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException {
        
        resp.setContentType("text/plain");

        String secret = System.getenv("POSTGRES_CONN"); // Vault’tan gelen değer

        // Secret'ı parçala
        String[] parts = secret.split(";");
        String host = "", dbname = "", username = "", password = "";
        for (String part : parts) {
            String[] keyValue = part.split("=");
            switch (keyValue[0].toLowerCase()) {
                case "host": host = keyValue[1]; break;
                case "database": dbname = keyValue[1]; break;
                case "username": username = keyValue[1]; break;
                case "password": password = keyValue[1]; break;
            }
        }

        String jdbcUrl = "jdbc:postgresql://" + host + ":5432/" + dbname;

        try (Connection conn = DriverManager.getConnection(jdbcUrl, username, password)) {
            resp.getWriter().println("✅ Veritabanına başarıyla bağlanıldı!");
        } catch (SQLException e) {
            resp.getWriter().println("❌ Bağlantı hatası: " + e.getMessage());
        }
    }
}
