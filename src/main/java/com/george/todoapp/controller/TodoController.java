package com.george.todoapp.controller;

import com.george.todoapp.dao.TodoDao;
import com.george.todoapp.dao.TodoDaoImpl;
import com.george.todoapp.model.Todo;
import com.george.todoapp.model.User;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@WebServlet("/")
public class TodoController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private TodoDao todoDAO;

    public void init() {
        todoDAO = new TodoDaoImpl();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getServletPath();

        try {
            switch (action) {
                case "/new":
                    showNewForm(request, response);
                    break;
                case "/insert":
                    insertTodo(request, response);
                    break;
                case "/delete":
                    deleteTodo(request, response);
                    break;
                case "/edit":
                    showEditForm(request, response);
                    break;
                case "/update":
                    updateTodo(request, response);
                    break;
                case "/list":
                    listUserTodo(request, response);
                    break;
                default:
                    RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
                    dispatcher.forward(request, response);
                    break;
            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }

    private void listTodo(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        List<Todo> listTodo = todoDAO.selectAllTodos();
        request.setAttribute("listTodo", listTodo);
        RequestDispatcher dispatcher = request.getRequestDispatcher("pages/todo_list.jsp");
        dispatcher.forward(request, response);
    }

    private void listUserTodo(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        User user = (User) request.getSession().getAttribute("user");
        String username = user.getUsername();
        System.out.println("Username: " + username);
        List<Todo> listUserTodo = todoDAO.selectUserTodos(username);
        request.setAttribute("listUserTodo", listUserTodo);
        RequestDispatcher dispatcher = request.getRequestDispatcher("pages/todo_list.jsp");
        dispatcher.forward(request, response);
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("pages/todo_form.jsp");
        dispatcher.forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Todo existingTodo = todoDAO.selectTodo(id);
        RequestDispatcher dispatcher = request.getRequestDispatcher("pages/todo_form.jsp");
        request.setAttribute("todo", existingTodo);
        dispatcher.forward(request, response);

    }

    private void insertTodo(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {

        String subject = request.getParameter("subject");
        String username = request.getParameter("username");
        String description = request.getParameter("description");

        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate todoDate = LocalDate.parse(request.getParameter("todoDate"),df);

        boolean isDone = Boolean.valueOf(request.getParameter("isDone"));
        Todo newTodo = new Todo(subject, username, description, todoDate, isDone);
        todoDAO.insertTodo(newTodo);
        response.sendRedirect("list");
    }

    private void updateTodo(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));

        String subject = request.getParameter("subject");
        String username = request.getParameter("username");
        String description = request.getParameter("description");
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate todoDate = LocalDate.parse(request.getParameter("todoDate"));

        boolean isDone = Boolean.valueOf(request.getParameter("isDone"));
        Todo updateTodo = new Todo(id, subject, username, description, todoDate, isDone);

        todoDAO.updateTodo(updateTodo);

        response.sendRedirect("list");
    }

    private void deleteTodo(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        todoDAO.deleteTodo(id);
        response.sendRedirect("list");
    }
}
