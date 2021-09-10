<%--
  Created by IntelliJ IDEA.
  User: anhnbt
  Date: 30/11/2020
  Time: 16:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:set var="pageTitle" scope="request" value="404 Page Not Found"/>
<jsp:include page="./inc/header.jsp"></jsp:include>
<!-- Begin Page Content -->
<div class="container-fluid">
    <!-- DataTales Example -->
    <div class="card shadow mb-4">
        <div class="card-header py-3">
            <h6 class="m-0 font-weight-bold">404 Page Not Found</h6>
        </div>
        <div class="card-body">
            <button onclick="history.back()">Back to Previous Page</button>
        </div><!-- .card-body -->
    </div><!-- .card -->
</div><!-- .container-fluid -->
<jsp:include page="./inc/footer.jsp"></jsp:include>