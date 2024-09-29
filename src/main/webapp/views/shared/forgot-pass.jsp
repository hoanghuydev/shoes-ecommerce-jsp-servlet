<%--
  Created by IntelliJ IDEA.
  User: Hung
  Date: 3/24/2024
  Time: 9:14 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/common/taglib.jsp" %>
<html>
<head>
    <title><dec:title default="Nai -  Forgot Password"/></title>
    <link rel="stylesheet" type="text/css" href="src/main/webapp/template/admin/assets/css/forgot-pass.css" />
</head>
<body>
    <div class="container h-100">
        <div class="row h-100">
            <div class="col-sm-10 col-md-8 col-lg-6 mx-auto d-table h-100">
                <div class="d-table-cell align-middle">

                    <div class="text-center mt-4">
                        <h1 class="h2">Đặt lại mật khẩu</h1>
                        <p class="lead">
                            Nhập email của tài khoản cần đặt lại mật khẩu
                        </p>
                    </div>
                    <div></div>

                    <div class="card">
                        <div class="card-body">
                            <div class="m-sm-4">
                                <form method="post" action="/forgot-password">
                                    <div class="form-group">
                                        <label>Email</label>
                                        <input class="form-control form-control-lg" type="email" name="email" placeholder="Nhập địa chỉ email ">
                                    </div>
                                    <div class="text-center mt-3">
<%--                                        <a href="#" class="btn btn-lg btn-primary">Reset password</a>--%>
                                        <button type="submit" class="btn btn-lg btn-primary">Đặt lại mật khẩu</button>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>

                </div>
            </div>
        </div>
    </div>
</body>
</html>
