<%--
  Created by IntelliJ IDEA.
  User: HUY
  Date: 12/27/2023
  Time: 12:02 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/common/taglib.jsp" %>
<html>
<head>
    <title>Nai - Đăng ký</title>
</head>
<body>
<!-- Section: Design Block -->
<section class="vh-100 d-flex">
    <!-- Jumbotron -->
    <div class="px-4 py-5 px-md-5 text-center text-lg-start m-auto" style="background-color: hsl(0, 0%, 96%)">
        <div class="container">
            <div class="row gx-lg-5 align-items-center">
                <div class="col-lg-6 mb-5 mb-lg-0">
                    <h1 class="my-5 display-3 fw-bold ls-tight">
                        Đôi giày tốt nhất <br />
                        <span class="text-primary">cho phong cách của bạn</span>
                    </h1>
                    <p style="color: hsl(217, 10%, 50.8%)">
                        Lorem ipsum dolor sit amet consectetur adipisicing elit.
                        Eveniet, itaque accusantium odio, soluta, corrupti aliquam
                        quibusdam tempora at cupiditate quis eum maiores libero
                        veritatis? Dicta facilis sint aliquid ipsum atque?
                    </p>
                </div>

                <div class="col-lg-6 mb-5 mb-lg-0">
                    <div class="card">
                        <div class="card-body py-5 px-md-5">
                            <form method="POST" action="/sign-up">
                                <!-- 2 column grid layout with text inputs for the first and last names -->
                                <div class="form-outline mb-4">
                                    <input type="text" id="fullName" class="form-control" name="fullName"/>
                                    <label class="form-label" for="fullName">Họ và tên</label>
                                </div>
                                <div class="form-outline mb-4">
                                    <input type="text" id="userName" class="form-control" name="userName"/>
                                    <label class="form-label" for="userName">Tên đăng nhập</label>
                                </div>

                                <!-- Email input -->
                                <div class="form-outline mb-4">
                                    <input type="email" id="email" name="email" class="form-control" />
                                    <label class="form-label" for="email">Email</label>
                                </div>

                                <!-- Password input -->
                                <div class="form-outline mb-4">
                                    <input type="password" id="password" name="password" class="form-control" />
                                    <label class="form-label" for="password">Mật khẩu</label>
                                </div>

                                <!-- Re-Password input -->
                                <div class="form-outline mb-4">
                                    <input type="password" id="repassword" name="repassword" class="form-control" />
                                    <label class="form-label" for="password">Nhập Lại Mật khẩu</label>
                                </div>

                                <!-- Checkbox -->
                                <div class="form-check d-flex justify-content-center mb-4">
                                    <input class="form-check-input me-2" type="checkbox" value="" name="subcribeNewLetter" id="form2Example33" checked />
                                    <label class="form-check-label" for="form2Example33">
                                        Tôi đã đọc và đồng ý với các điều khoản
                                    </label>
                                </div>
                                <input type="hidden" value="register" name="action">
                                <!-- Submit button -->
                                <button type="submit" class="btn btn-primary btn-block mb-4">
                                    Đăng ký
                                </button>

                                <!-- Register buttons -->
                                <div class="text-center">
                                    <p>hoặc đăng ký với:</p>
                                    <button type="button" class="btn btn-link btn-floating mx-1">
                                        <i class="fab fa-facebook-f"></i>
                                    </button>

                                    <button type="button" class="btn btn-link btn-floating mx-1">
                                        <a href="https://accounts.google.com/o/oauth2/auth?scope=email%20profile%20https://www.googleapis.com/auth/userinfo.profile&redirect_uri=http://localhost:8080/oauth2-google&response_type=code
		   &client_id=618438047417-pnt76uvpvbdqm2h3a6jgriktdqv4vr4c.apps.googleusercontent.com&approval_prompt=force">
                                            <i class="fab fa-google"></i>
                                        </a>
                                    </button>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <!-- Jumbotron -->
</section>

<!-- Section: Design Block -->
</body>
</html>
