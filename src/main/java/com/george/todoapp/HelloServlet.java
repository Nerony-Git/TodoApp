package com.george.todoapp;

import java.io.*;
import java.sql.SQLException;
import java.util.Enumeration;

import com.george.todoapp.dao.LoginDao;
import com.george.todoapp.model.User;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet("/hello-servlet")
public class HelloServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private LoginDao loginDao;

    public void init() {
        loginDao = new LoginDao();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect("index.jsp");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        authenticate(request, response);
    }

    private void authenticate(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        try {
            User user = loginDao.validate(username, password);
            String destPage = "index.jsp";

            if (user != null) {
                HttpSession session = request.getSession();
                session.setAttribute("user", user);
                Enumeration<String> attributeNames = session.getAttributeNames();
                while (attributeNames.hasMoreElements()){
                    String attributeName = attributeNames.nextElement();
                    Object attributeValue = session.getAttribute(attributeName);
                    System.out.println(attributeName + ": " + attributeValue);
                }
                destPage = "/list";
            }else {
                String message = "Invalid username or password";
                request.setAttribute("message", message);
            }

            RequestDispatcher dispatcher = request.getRequestDispatcher(destPage);
            dispatcher.forward(request, response);
        } catch (SQLException | ClassNotFoundException ex) {
            throw new ServletException(ex);
        }

    }

    public void destroy() {
    }
}
