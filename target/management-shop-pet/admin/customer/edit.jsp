<%--
  Created by IntelliJ IDEA.
  User: anhnbt
  Date: 01/12/2020
  Time: 08:32
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:set var="pageTitle" scope="request" value="Edit User: ${requestScope['customer'].getName()}"/>
<jsp:include page="../inc/header.jsp"></jsp:include>
<jsp:include page="../inc/nav.jsp"></jsp:include>
<div class="container-fluid">
    <div class="card shadow mb-4">
        <div class="card-header py-3">
            <h6 class="m-0 font-weight-bold">${pageTitle} <a
                    href="${pageContext.request.contextPath}/administrator/customers"
                    class="btn btn-primary btn-sm" data-toggle="tooltip" data-placement="top" title="All Users"><i
                    class="fas fa-reply"></i> All Users</a></h6>
        </div>
        <form action="${pageContext.request.contextPath}/administrator/customers" method="post">

            <div class="card-body">
                <% if (session.getAttribute("msg") != null) { %>
                <%=session.getAttribute("msg") %>
                <% session.removeAttribute("msg"); %>
                <% } %>
                <input type="hidden" name="act" value="update">
                <input type="hidden" name="id" value="${requestScope['customer'].getId()}">

                <fieldset>
                    <legend>Personal Details</legend>
                    <div class="form-group row">
                        <label for="name" class="col-md-2 col-form-label text-md-right">Name <span
                                class="text-danger">*</span>:</label>
                        <div class="col-md-10">
                            <input type="text" class="form-control" name="name" id="name"
                                   placeholder="Enter name" value="${requestScope['customer'].getName()}" required>
                        </div>
                    </div>
                    <div class="form-group row">
                        <label for="email" class="col-md-2 col-form-label text-md-right">Email <span
                                class="text-danger">*</span>:</label>
                        <div class="col-md-10">
                            <input type="text" class="form-control" name="email" id="email"
                                   placeholder="Enter email" value="${requestScope['customer'].getEmail()}" required>
                        </div>
                    </div>
                    <div class="form-group row">
                        <label for="phone" class="col-md-2 col-form-label text-md-right">Phone <span
                                class="text-danger">*</span>:</label>
                        <div class="col-md-10">
                            <input type="text" class="form-control" name="phone" id="phone"
                                   placeholder="Enter phone number" value="${requestScope['customer'].getPhone()}" required>
                        </div>
                    </div>
                    <div class="form-group row">
                        <label for="address" class="col-md-2 col-form-label text-md-right">Address <span
                                class="text-danger">*</span>:</label>
                        <div class="col-md-10">
                            <input type="text" class="form-control" name="address" id="address"
                                   placeholder="Enter address" value="${requestScope['customer'].getAddress()}" required>
                        </div>
                    </div>
                    <div class="form-group row">
                        <label class="col-md-2 col-form-label text-md-right">Gender</label>
                        <div class="col-md-10">
                            <div class="form-check form-check-inline">
                                <input class="form-check-input" type="radio" name="gender" id="male" value="1" <c:if test="${requestScope['customer'].getGender() == 1}">checked</c:if>>
                                <label class="form-check-label" for="male">Male</label>
                            </div>
                            <div class="form-check form-check-inline">
                                <input class="form-check-input" type="radio" name="gender" id="female" value="2" <c:if test="${requestScope['customer'].getGender() == 2}">checked</c:if>>
                                <label class="form-check-label" for="female">Female</label>
                            </div>
                        </div>
                    </div>
                </fieldset>
            </div><!-- /.card-body-->
            <div class="card-footer">
                <div class="col-md-6 offset-md-4">
                    <button type="submit" class="btn btn-primary" data-toggle="tooltip" data-placement="top"
                            title="Save"><i class="fas fa-save"></i> Save
                    </button>
                    <a href="${pageContext.request.contextPath}/administrator/customers"
                       class="btn btn-secondary"
                       data-toggle="tooltip" data-placement="top" title="Cancel"><i class="fas fa-reply"></i>
                        Cancel</a>
                </div>
            </div>
        </form>
    </div><!-- /.card -->
</div><!-- /.container-fluid -->
<jsp:include page="../inc/scripts.jsp"></jsp:include>
<jsp:include page="../inc/footer.jsp"></jsp:include>