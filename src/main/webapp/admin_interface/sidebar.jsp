<%--
  Created by IntelliJ IDEA.
  User: agata
  Date: 15.12.2019
  Time: 13:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<ul class="sidebar navbar-nav">
    <li class="nav-item active">
        <a class="nav-link" href="indexAdmin.jsp">
            <i class="fas fa-fw fa-tachometer-alt"></i>
            <span>Main page</span>
        </a>
    </li>
    <li class="nav-item dropdown">
        <a class="nav-link dropdown-toggle" href="#" id="usersDropdown" role="button" data-toggle="dropdown"
           aria-haspopup="true" aria-expanded="false">
            <i class="fas fa-fw fa-folder"></i>
            <span>Students</span>
        </a>
        <div class="dropdown-menu" aria-labelledby="pagesDropdown">
            <a class="dropdown-item" href="/showUsers">Show</a>
            <a class="dropdown-item" href="/userAdd">Add</a>
            <a class="dropdown-item" href="/userEdit">Edit</a>
            <a class="dropdown-item" href="/userDelete">Delete</a>
        </div>
    </li>
    <li class="nav-item dropdown">
        <a class="nav-link dropdown-toggle" href="#" id="groupsDropdown" role="button" data-toggle="dropdown"
           aria-haspopup="true" aria-expanded="false">
            <i class="fas fa-fw fa-folder"></i>
            <span>Groups</span>
        </a>
        <div class="dropdown-menu" aria-labelledby="pagesDropdown">
            <h6 class="dropdown-header">Groups:</h6>
            <a class="dropdown-item" href="/groupsPrint">Show</a>
            <a class="dropdown-item" href="/groupAdd">Add</a>
            <a class="dropdown-item" href="/groupAddUser">Assign student</a>
            <a class="dropdown-item" href="/groupDelete">Delete</a>
        </div>
    </li>
    <li class="nav-item dropdown">
        <a class="nav-link dropdown-toggle" href="#" id="exercisesDropdown" role="button" data-toggle="dropdown"
           aria-haspopup="true" aria-expanded="false">
            <i class="fas fa-fw fa-folder"></i>
            <span>Exercises</span>
        </a>
        <div class="dropdown-menu" aria-labelledby="pagesDropdown">
            <h6 class="dropdown-header">Exercises:</h6>
            <a class="dropdown-item" href="/exercisePrint">Show</a>
            <a class="dropdown-item" href="/exerciseAssignToUser">Assign to student</a>
            <a class="dropdown-item" href="/exerciseAssignToGroup">Assign to group</a>
            <a class="dropdown-item" href="/exerciseAdd">Add</a>
            <a class="dropdown-item" href="/exerciseEdit">Edit</a>
            <a class="dropdown-item" href="/exerciseDelete">Delete</a>
        </div>
    </li>
    <li class="nav-item">
        <a class="nav-link" href="/solutionsPrint">
            <i class="fas fa-fw fa-table"></i>
            <span>Solutions</span></a>
    </li>
</ul>
