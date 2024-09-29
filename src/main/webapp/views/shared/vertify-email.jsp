<%--
  Created by IntelliJ IDEA.
  User: HUY
  Date: 12/27/2023
  Time: 1:21 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/common/taglib.jsp" %>

<html>
<head>
    <title>Nai - Xác thực email</title>
</head>
<body>
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
                            <form method="POST" action="/vertify-email">
                                <!-- 2 column grid layout with text inputs for the first and last names -->

                                <div class="form-outline mb-4">
                                    <input type="number" id="otp" class="form-control" name="otp"/>
                                    <label class="form-label" for="otp">Mã OTP</label>
                                </div>
                                <button type="submit" class="btn btn-primary btn-block mb-4">
                                    Xác thực
                                </button>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <!-- Jumbotron -->
</section>
</body>
</html>
