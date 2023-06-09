package com.george.todoapp.dao;

import com.george.todoapp.model.Todo;

import java.sql.SQLException;
import java.util.List;

public interface TodoDao {
    void insertTodo(Todo todo) throws SQLException;

    Todo selectTodo(long todoId);

    List<Todo> selectAllTodos();

    List<Todo> selectUserTodos(String user);

    boolean deleteTodo(int id) throws SQLException;

    boolean updateTodo(Todo todo) throws SQLException;
}
