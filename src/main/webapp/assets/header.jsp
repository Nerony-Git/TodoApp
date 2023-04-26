<%--
  Created by IntelliJ IDEA.
  User: gnamu
  Date: 26/04/2023
  Time: 22:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<header>
  <nav class="navbar navbar-expand-md navbar-dark"
       style="background-color: tomato">
    <div>
      <a href="<%= request.getContextPath() %>/" class="navbar-brand"> Todo App</a>
    </div>

    <ul class="navbar-nav navbar-collapse justify-content-end">
      <li><a href="<%= request.getContextPath() %>/" class="nav-link">Login</a></li>
      <li><a href="<%= request.getContextPath() %>/register" class="nav-link">Signup</a></li>
    </ul>
  </nav>
</header>
