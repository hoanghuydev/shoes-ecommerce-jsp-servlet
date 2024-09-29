<%--
  Created by IntelliJ IDEA.
  User: PC
  Date: 3/29/2024
  Time: 1:15 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Đổi mật khẩu</title>
</head>
<body>
<div class="col-md-6 offset-md-3">
    <span class="anchor" id="formChangePassword"></span>
    <hr class="mb-5">

    <!-- form card change password -->
    <div class="card card-outline-secondary">
        <div class="card-header">
            <h3 class="mb-0">Đổi mật khẩu</h3>
            <%
                String error = (String)(request.getAttribute("error"));
                if (error != null) {
                    request.getAttribute("error");
                } else {
                    error = "";
                }
            %>
            <p style="margin: 5px 0px; color: #fc1616"> <%= error%> </p>
        </div>
        <div class="card-body">
            <form class="form" role="form" autocomplete="off" method="post" action="/change-password">
                <div class="form-group">
                    <label for="inputPasswordOld">Mật khẩu hiện tại</label>
                    <input type="password" class="form-control" id="inputPasswordOld" name="currentpassword" required="">
                </div>
                <div class="form-group">
                    <label for="inputPasswordNew">Mật khẩu mới</label>
                    <input type="password" class="form-control" id="inputPasswordNew" name="newpassword" required="">
<%--                    <span class="form-text small text-muted">--%>
<%--                                            The password must be 8-20 characters, and must <em>not</em> contain spaces.--%>
<%--                                        </span>--%>
                </div>
                <div class="form-group">
                    <label for="inputPasswordNewVerify">Xác thực mật khẩu</label>
                    <input type="password" class="form-control" id="inputPasswordNewVerify" name="repassword" required="">
<%--                    <span class="form-text small text-muted">--%>
<%--                                            To confirm, type the new password again.--%>
<%--                                        </span>--%>
                </div>
                <div class="form-group mt-3">
                    <button type="submit" class="btn btn-success btn-lg float-right">Lưu</button>
                </div>
            </form>
        </div>
    </div>
</div>
    <!-- /form card change password -->
</body>
</html>
