<%--
  Created by IntelliJ IDEA.
  User: gnamu
  Date: 26/04/2023
  Time: 22:33
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
  <title>TodoApp - Todo List</title>

  <link rel="stylesheet"
        href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
        integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"
        crossorigin="anonymous">
</head>

</head>
<body>
<header>
  <nav class="navbar navbar-expand-md navbar-dark"
       style="background-color: tomato">
    <div>
      <a href="https://www.javaguides.net" class="navbar-brand"> Todo
        App</a>
    </div>

    <ul class="navbar-nav">
      <li><a href="<%=request.getContextPath()%>/list"
             class="nav-link">Todos</a></li>
    </ul>

    <ul class="navbar-nav navbar-collapse justify-content-end">
      <li><a href="logout"
             class="nav-link">Logout</a></li>
    </ul>
  </nav>
</header>

<div class="row">
  <div class="container">
    <h1 class="text-center">${user.username}</h1>
    <p>${user.firstName}</p>
  </div>
</div>
<div class="row">

  <div class="container">
    <h3 class="text-center">List of Todos</h3>
    <hr>
    <div class="container text-left">

      <a href="<%=request.getContextPath()%>/new"
         class="btn btn-success">Add New</a>
    </div>
    <br>
    <table class="table table-bordered">
      <thead>
      <tr>
        <th>Subject</th>
        <th>Todo Date</th>
        <th>Todo Status</th>
        <th>Actions</th>
      </tr>
      </thead>
      <tbody>
      <!--   for (Todo todo: todos) {  -->
      <c:forEach var="todo" items="${listUserTodo}">

        <tr>
          <td><c:out value="${todo.subject}" /></td>
          <td><c:out value="${todo.todoDate}" /></td>
          <td><c:out value="${todo.status}" /></td>

          <td><a href="edit?id=<c:out value='${todo.id}' />">Edit</a>
            &nbsp;&nbsp;&nbsp;&nbsp; <a
                    href="delete?id=<c:out value='${todo.id}' />">Delete</a></td>
        </tr>
      </c:forEach>
      <!-- } -->
      </tbody>

    </table>
  </div>
</div>

<jsp:include page="../assets/footer.jsp"></jsp:include>
</body>
</html>
