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

        // Environment değişkenlerini oku
        String host = System.getenv("PGHOST");
        String dbname = System.getenv("PGDBNAME");
        String username = System.getenv("PGUSERNAME");
        String password = System.getenv("PGPASSWORD");

        if (host == null || dbname == null || username == null || password == null) {
            resp.getWriter().println("❌ Bazı environment değişkenleri eksik veya çözülemedi.");
            return;
        }

        // JDBC bağlantı URL'si
        String jdbcUrl = "jdbc:postgresql://" + host + ":5432/" + dbname;

        resp.getWriter().println("🔗 JDBC URL: " + jdbcUrl);

        try (Connection conn = DriverManager.getConnection(jdbcUrl, username, password)) {
            resp.getWriter().println("✅ Veritabanına başarıyla bağlanıldı!");
        } catch (SQLException e) {
            resp.getWriter().println("❌ Bağlantı hatası: " + e.getMessage());
        }
    }
}
