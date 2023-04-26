<%--
  Created by IntelliJ IDEA.
  User: gnamu
  Date: 26/04/2023
  Time: 22:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
  <c:if test="${todo != null}">
    <title>Todo App - Edit</title>
  </c:if>
  <c:if test="${todo == null}">
    <title>Todo App - Add New</title>
  </c:if>

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
<div class="container col-md-5">
  <div class="card">
    <div class="card-body">
      <c:if test="${todo != null}">
      <form action="update" method="post">
        </c:if>
        <c:if test="${todo == null}">
        <form action="insert" method="post">
          </c:if>

          <caption>
            <h2>
              <c:if test="${todo != null}">
                Edit Todo
              </c:if>
              <c:if test="${todo == null}">
                Add New Todo
              </c:if>
            </h2>
          </caption>

          <c:if test="${todo != null}">
            <input type="hidden" name="id" value="<c:out value='${todo.id}' />" />
            <input type="hidden" name="username" id="username" value="<c:out value='${todo.username}' />" />
          </c:if>

          <c:if test="${todo == null}">
            <input type="hidden" name="username" id="username"  value="<c:out value='${user.username}' />" />
          </c:if>
          <fieldset class="form-group">
            <label>Todo Subject</label> <input type="text"
                                               value="<c:out value='${todo.subject}' />" class="form-control"
                                               name="subject" id="subject" required="required" minlength="5">
          </fieldset>

          <fieldset class="form-group">
            <label>Todo Decription</label> <input type="text"
                                                  value="<c:out value='${todo.description}' />" class="form-control"
                                                  name="description" id="description" required="required" minlength="5">
          </fieldset>

          <fieldset class="form-group">
            <label>Todo Status</label> <select class="form-control"
                                               name="isDone" id="isDone">
            <option value="false">In Progress</option>
            <option value="true">Complete</option>
          </select>
          </fieldset>

          <fieldset class="form-group">
            <label>Todo Target Date</label> <input type="date"
                                                   value="<c:out value='${todo.todoDate}' />" class="form-control"
                                                   name="todoDate" id="todoDate" required="required">
          </fieldset>

          <button type="submit" class="btn btn-success">Save</button>
        </form>
    </div>
  </div>
</div>

<jsp:include page="../assets/footer.jsp"></jsp:include>
</body>
</html>
