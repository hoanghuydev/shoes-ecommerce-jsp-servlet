<%@ page import="com.ltweb_servlet_ecommerce.model.UserModel" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/common/taglib.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Login</title>
    <!-- Meta Tag -->
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name='copyright' content=''>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <!-- Web Font -->
    <link href="https://fonts.googleapis.com/css?family=Poppins:200i,300,300i,400,400i,500,500i,600,600i,700,700i,800,800i,900,900i&display=swap" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">

</head>
<body>

<%UserModel userModel = (UserModel) request.getAttribute("userModel");%>
<div class="container col-md-4" id="account-user">
    <div class="main-account">
        <div class="main-account-header" style="margin-top: 100px;">
            <h3>Thông tin tài khoản</h3>
            <p style="font-size: 20px;">Xin chào <%= userModel.getFullName()%></p>
        </div>

        <form action="/user-info" method="post">
            <div class="main-account-body">
                <div class="main-account-body-col">
                    <div class="form-group">
                        <label for="infoname" class="form-label">Họ và tên</label>
                        <input class="form-control" type="text" name="fullname" id="infoname"
                               value="<%=userModel.getFullName()%>">
                    </div>
                    <div class="form-group">
                        <label for="infophone" class="form-label">Số điện thoại</label>
                        <input class="form-control" type="text" name="phone" id="infophone"
                               value="<%=userModel.getPhone()%>">
                    </div>
                    <div class="form-group">
                        <label for="infoemail" class="form-label">Email</label>
                        <input class="form-control" type="email" name="email" id="infoemail"
                               value="<%=userModel.getEmail()%>">
                        <span class="inforemail-error form-message"></span>
                    </div>

                    <div class="form-group">
                        <label for="infoBD" class="form-label">Ngày sinh</label>
                        <input class="form-control" type="date" name="dateOfBirth" id="infoBD"
                               value="<%=userModel.getBirthDay()%>">
                    </div>

<%--                    <div class="form-group">--%>
<%--                        <label for="infoaddress" class="form-label">Địa chỉ</label>--%>
<%--                        <input class="form-control" type="text" name="address" id="infoaddress"--%>
<%--                               value="">--%>
<%--                    </div>--%>
                </div>
                <div class="main-account-body-row mt-3">
                    <div>
                        <button id="save-info-user" class="btn btn-primary" type="submit"></i> Lưu thay đổi</button>
                    </div>
                </div>
                <a href="/change-password" style="margin-top: 30px">Đổi mật khẩu</a>
            </div>
        </form>

    </div>
</div>


<!--===============================================================================================-->

</body>

</html>
