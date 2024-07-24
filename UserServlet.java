package com.example.servlet;

import com.example.dao.UserDao;
import com.example.model.User;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/users")
public class UserServlet extends HttpServlet {
    private UserDao userDao;

    @Override
    public void init() throws ServletException {
        try {
            String url = "jdbc:mysql://mysql-service:3306/mydb";
            String username = "user";
            String password = "password";
            Connection connection = DriverManager.getConnection(url, username, password);
            userDao = new UserDao(connection);
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            List<User> users = userDao.getAllUsers();
            req.setAttribute("users", users);
            req.getRequestDispatcher("/WEB-INF/users.jsp").forward(req, resp);
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    // Métodos para manejar POST, PUT, DELETE
}
