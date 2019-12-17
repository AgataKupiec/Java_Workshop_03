<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">

<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>SB Admin - Tables</title>

    <!-- Custom fonts for this template-->
    <link href="../vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">

    <!-- Page level plugin CSS-->
    <link href="../vendor/datatables/dataTables.bootstrap4.css" rel="stylesheet">

    <!-- Custom styles for this template-->
    <link href="../css/sb-admin.css" rel="stylesheet">

</head>

<body id="page-top">

<%@include file="../topNavbar.jsp" %>

<div id="wrapper">

    <!-- Sidebar -->
    <%@include file="sidebar.jsp" %>

    <div id="content-wrapper">

        <div class="container-fluid">

            <!-- Breadcrumbs-->
            <ol class="breadcrumb">
                <li class="breadcrumb-item">
                    <a href="#">Show data</a>
                </li>
                <li class="breadcrumb-item active">Exercises</li>
            </ol>

            <!-- DataTables Example -->

            <form method="post" action="/solutionsPrint">
                <select name="userId" onchange="this.form.submit()">
                    <option hidden disabled selected value> -- select student -- </option>
                    <c:forEach items="${users}" var="user">
                        <option value="${user.id}" name="userId">${user.user_name}
                            current group: ${user.group.name} </option>
                    </c:forEach>
                </select>
            </form>


            <div class="card mb-3">
                <div class="card-header">
                    <i class="fas fa-table"></i>
                    Solutions<c:if test="${not empty selectedUser}">: ${selectedUser.user_name}
                     (${selectedUser.group.name} group)</c:if>
                </div>
                <div class="card-body">
                    <div class="table-responsive">
                        <table class="table table-bordered" id="dataTable" width="100%" cellspacing="0">
                            <thead>
                            <tr>
                                <th>Exercise title</th>
                                <th>Solution</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${solutions}" var="solution">
                                <tr>
                                <td>${solution.key}</td>
                                <td>${solution.value.description}</td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>
                <%--          <div class="card-footer small text-muted">Updated yesterday at 11:59 PM</div>--%>
            </div>

        </div>
        <!-- /.container-fluid -->


    </div>
    <!-- /.content-wrapper -->

</div>
<!-- /#wrapper -->

<!-- Scroll to Top Button-->
<a class="scroll-to-top rounded" href="#page-top">
    <i class="fas fa-angle-up"></i>
</a>

<!-- Logout Modal-->
<%@include file="../logoutModal.jsp" %>

<!-- Bootstrap core JavaScript-->
<script src="../vendor/jquery/jquery.min.js"></script>
<script src="../vendor/bootstrap/js/bootstrap.bundle.min.js"></script>

<!-- Core plugin JavaScript-->
<script src="../vendor/jquery-easing/jquery.easing.min.js"></script>

<!-- Page level plugin JavaScript-->
<script src="../vendor/datatables/jquery.dataTables.js"></script>
<script src="../vendor/datatables/dataTables.bootstrap4.js"></script>

<!-- Custom scripts for all pages-->
<script src="../js/sb-admin.min.js"></script>

<!-- Demo scripts for this page-->
<script src="../js/demo/datatables-demo.js"></script>

</body>

</html>
