package com.example;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class HelloServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException {

        resp.setContentType("text/plain");
        PrintWriter out = resp.getWriter();

        String user = System.getenv("PGUSERNAME");
        String pass = System.getenv("PGPASSWORD");
        String host = System.getenv("PGHOST");
        String db   = System.getenv("PGDBNAME");

        String url = "jdbc:postgresql://" + host + ":5432/" + db;

        try {
            Class.forName("org.postgresql.Driver");
            Connection conn = DriverManager.getConnection(url, user, pass);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM students");

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                int age = rs.getInt("age");
                String department = rs.getString("department");

                out.println("[" + id + "] " + name + ", Age: " + age + ", Dept: " + department);
            }

            rs.close();
            stmt.close();
            conn.close();

        } catch (Exception e) {
            out.println("Error: " + e.getMessage());
            e.printStackTrace(out);
        }
    }
}
