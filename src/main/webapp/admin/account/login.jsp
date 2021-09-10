<%--
  Created by IntelliJ IDEA.
  User: anhnbt
  Date: 01/12/2020
  Time: 15:48
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:set var="pageTitle" scope="request" value="Admin Login"/>
<jsp:include page="../inc/header.jsp"></jsp:include>
<div class="container" style="padding-top: 40px;padding-bottom: 40px;">
    <div class="row d-flex justify-content-center align-self-center">
        <div class="col-md-6">
            <div class="card shadow mb-4 login-container text-white">
                <form action="${pageContext.request.contextPath}/administrator/login" method="post">
                    <div class="card-body px-4">
<%--                        <img class="w-25 my-4 rounded mx-auto d-block" src="${pageContext.request.contextPath}/admin/assets/images/cont_1_04.jpg" alt="Logo">--%>
                        <a href="#"><img class="w-25 my-4 mx-auto d-block" href="${pageContext.request.contextPath}/admin/assets/images/cont_1_04.jpg" rel=""></img></a>
                        <h6 id="pageTitle" class="font-weight-bold text-center text-dark">${pageTitle}</h6>
                        <c:if test="${sessionScope['msg'] != null}">
                            <%=session.getAttribute("msg")%>
                            <%session.removeAttribute("msg");%>
                        </c:if>
                        <div class="form-group">
                            <label class="text-dark" for="username">Username <span
                                    class="text-danger">*</span>:</label>
                            <input type="text" class="form-control" name="username" id="username"
                                   placeholder="Username" required>
                        </div>
                        <div class="form-group">
                            <label class="text-dark" for="password">Password <span
                                    class="text-danger">*</span>:</label>
                            <input type="password" class="form-control" name="password" id="password"
                                   placeholder="Password" required>
                        </div>
                        <div class="form-group">
                            <button type="submit" class="btn btn-primary btn-lg btn-block" data-toggle="tooltip" data-placement="top"
                                    title="Save">Login
                            </button>
                        </div>
                    </div><!-- /.card-body-->
                </form>
            </div><!-- /.card -->
        </div><!--- /.col-md-6 -->
    </div><!-- /.row -->
</div><!-- /.container-fluid -->
<jsp:include page="../inc/footer.jsp"></jsp:include>
