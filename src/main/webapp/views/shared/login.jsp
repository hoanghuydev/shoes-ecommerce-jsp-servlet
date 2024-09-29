<%--
  Created by IntelliJ IDEA.
  User: HUY
  Date: 12/26/2023
  Time: 11:05 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/common/taglib.jsp" %>
<html>
<head>
    <title>Nai - Đăng nhập</title>
    <style>
        .divider:after,
        .divider:before {
            content: "";
            flex: 1;
            height: 1px;
            background: #eee;
        }
        .h-custom {
            height: calc(100% - 73px);
        }
        @media (max-width: 450px) {
            .h-custom {
                height: 100%;
            }
        }
    </style>
</head>
<body>
<section class="vh-100 d-flex">
    <div class="container-fluid h-custom m-auto">
        <div class="row d-flex justify-content-center align-items-center h-100">
            <div class="col-md-9 col-lg-6 col-xl-5">
                <img src="https://mdbcdn.b-cdn.net/img/Photos/new-templates/bootstrap-login-form/draw2.webp"
                     class="img-fluid" alt="Sample image">
            </div>
            <div class="col-md-8 col-lg-6 col-xl-4 offset-xl-1">
                <form method="POST" action="/sign-in">
                    <div class="d-flex flex-row align-items-center justify-content-center justify-content-lg-start">
                        <p class="lead fw-normal mb-0 me-3">Đăng nhập với</p>
                        <button type="button" class="btn btn-primary btn-floating mx-1">
                            <i class="fab fa-facebook-f"></i>
                        </button>
                        <button type="button" class="btn btn-primary btn-floating mx-1">
                            <a href="https://accounts.google.com/o/oauth2/auth?client_id=618438047417-pnt76uvpvbdqm2h3a6jgriktdqv4vr4c.apps.googleusercontent.com&redirect_uri=http://localhost:8080/oauth2-google&scope=https://www.googleapis.com/auth/userinfo.email+https://www.googleapis.com/auth/userinfo.profile&response_type=code">
                                <i class="fab fa-google" style="color: white"></i>
                            </a>

                        </button>

                        <button type="button" class="btn btn-primary btn-floating mx-1">
                            <i class="fab fa-linkedin-in"></i>
                        </button>
                    </div>

                    <div class="divider d-flex align-items-center my-4">
                        <p class="text-center fw-bold mx-3 mb-0">hoặc</p>
                    </div>

                    <!-- Email input -->
                    <div class="form-outline mb-4">
                        <input type="email" id="inputEmail" class="form-control form-control-lg"
                               placeholder="Nhập địa chỉ email" autocomplete="off" name="email" required/>
                        <label class="form-label" for="inputEmail">Email</label>
                    </div>

                    <!-- Password input -->
                    <div class="form-outline mb-3">
                        <input type="password" id="inputPassword" class="form-control form-control-lg"
                               placeholder="Nhập mật khẩu " autocomplete="off" name="password" required/>
                        <label class="form-label" for="inputPassword">Mật khẩu </label>
                    </div>

                    <div class="d-flex justify-content-between align-items-center">
                        <!-- Checkbox -->
                        <div class="form-check mb-0">
                            <input class="form-check-input me-2" type="checkbox" value="" id="form2Example3" />
                            <label class="form-check-label" for="form2Example3">
                                Ghi nhớ
                            </label>
                        </div>
                        <a href="/forgot-password" class="text-body">Quên mật khẩu?</a>
                    </div>
                    <input type="hidden" name="action" value="login">
                    <div class="text-center text-lg-start mt-4 pt-2">
                        <button type="submit" class="btn btn-primary btn-lg"
                                style="padding-left: 2.5rem; padding-right: 2.5rem;">Đăng nhập</button>
                        <p class="small fw-bold mt-2 pt-1 mb-0">Chưa có tài khoản? <a href="/sign-up"
                                                                                          class="link-danger">Đăng ký ngay</a></p>
                    </div>

                </form>
            </div>
        </div>
    </div>
</section>
</body>
</html>
