<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html class="no-js" lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Groover - Online Shopping for Electronics, Apparel, Computers, Books, DVDs & more</title>
    <link rel="stylesheet" href="/user/css/dash.css">
    <!-- Standard Favicon -->
    <link href="/user/images/favicon.ico" rel="shortcut icon">
    <!-- Base Google Font for Web-app -->
    <link href="https://fonts.googleapis.com/css?family=Open+Sans:400,700" rel="stylesheet">
    <!-- Google Fonts for Banners only -->
    <link href="https://fonts.googleapis.com/css?family=Raleway:400,800" rel="stylesheet">
    <!-- Bootstrap 4 -->
    <link rel="stylesheet" href="/user/css/bootstrap.min.css">
    <!-- Font Awesome 5 -->
    <link rel="stylesheet" href="/user/css/fontawesome.min.css">
    <!-- Ion-Icons 4 -->
    <link rel="stylesheet" href="/user/css/ionicons.min.css">
    <!-- Animate CSS -->
    <link rel="stylesheet" href="/user/css/animate.min.css">
    <!-- Owl-Carousel -->
    <link rel="stylesheet" href="/user/css/owl.carousel.min.css">
    <!-- Jquery-Ui-Range-Slider -->
    <link rel="stylesheet" href="/user/css/jquery-ui-range-slider.min.css">
    <!-- Utility -->
    <link rel="stylesheet" href="/user/css/utility.css">
    <!-- Main -->
    <link rel="stylesheet" href="/user/css/bundle.css">
    <link rel="stylesheet" href="/user/css/dash-manage-order.css">

</head>


<body>
<div id="app">

    <!--====== Main Header ======-->
    <%@ include file="./common/header.jsp" %>
    <!--====== App Content ======-->
    <div class="container-content mt-5">
        <div class="app-content">
            <div class="u-s-p-b-60">

                <div class="section__content">
                    <div class="dash">
                        <div class="container">
                            <div class="row">
                                <div class="col-lg-12 col-md-12">
                                    <h1 class="dash__h1 u-s-m-b-30">ORDER DETAIL</h1>
                                    <div
                                            class="dash__box dash__box--shadow dash__box--radius dash__box--bg-white u-s-m-b-30">
                                        <div class="dash__pad-2">
                                            <div class="dash-l-r">
                                                <div>
                                                    <div class="manage-o__text-2 u-c-secondary">ORDER
                                                        #${requestScope.order.id}
                                                    </div>

                                                    <div class="manage-o__text u-c-silver">
                                                        <fmt:formatDate value="${requestScope.order.createdAt}"
                                                                        pattern=" YYYY-MM-dd hh:mma"/>
                                                    </div>
                                                </div>
                                                <div>
                                                    <div class="manage-o__text-2 u-c-silver">Total:
                                                        <span class="manage-o__text-2 u-c-secondary"><fmt:formatNumber
                                                                value="${order.total}" type="currency"/></span>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="dash__box dash__box--shadow dash__box--radius dash__box--bg-white u-s-m-b-30">
                                        <div class="dash__pad-2">
                                            <div class="manage-o">
                                                <div class="manage-o__timeline">
                                                    <div class="timeline-row">
                                                        <div class="col-lg-4 u-s-m-b-30">
                                                            <div class="timeline-step">
                                                                <div class="timeline-l-i timeline-l-i--finish">
                                                                    <span class="timeline-circle"></span>
                                                                </div>
                                                                <span class="timeline-text">Preparing</span>
                                                            </div>
                                                        </div>
                                                        <div class="col-lg-4 u-s-m-b-30">
                                                            <div class="timeline-step">
                                                                <div class="timeline-l-i">
                                                                    <span class="timeline-circle"></span>
                                                                </div>
                                                                <span class="timeline-text">Delivering</span>
                                                            </div>
                                                        </div>
                                                        <div class="col-lg-4 u-s-m-b-30">
                                                            <div class="timeline-step">
                                                                <div class="timeline-l-i">
                                                                    <span class="timeline-circle"></span>
                                                                </div>
                                                                <span class="timeline-text">Delivered</span>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                                <c:forEach var="item" items="${requestScope.order.orderItems}">
                                                    <div class="manage-o__description">
                                                        <div class="description__container">

                                                            <div class="description__img-wrap">
                                                                <a href="<c:url value="/products/${item.productSize.product.id}"/>">
                                                                    <img class="u-img-fluid"
                                                                         src="${item.productSize.product.thumbnail}"
                                                                         alt="">
                                                                </a>
                                                            </div>
                                                            <a href="<c:url value="/products/${item.productSize.product.id}"/>">
                                                                <div class="description-title">${item.productSize.product.name}
                                                                <span> - Size: ${item.productSize.size.name}</span></div>
                                                            </a>

                                                        </div>
                                                        <div class="description__info-wrap">
                                                            <div>
                                                                <span class="manage-o__text-2 u-c-silver">Quantity:
                                                                    <span class="manage-o__text-2 u-c-secondary">${item.quantity}</span></span>
                                                            </div>
                                                            <div>
                                                                <span class="manage-o__text-2 u-c-silver">Price :
                                                                    <span class="manage-o__text-2 u-c-secondary"><fmt:formatNumber
                                                                            value="${item.price * item.quantity}"
                                                                            type="currency"/></span></span>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </c:forEach>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col-lg-6">
                                            <div class="dash__box dash__box--bg-white dash__box--shadow u-h-100">
                                                <div class="dash__pad-3">
                                                    <h2 class="dash__h2 u-s-m-b-8"
                                                        style="font-weight: 600; padding-bottom: 5px">Address</h2>
                                                    <span class="dash__text-2 mt-2">Full name: ${requestScope.order.fullName}</span>
                                                    <span class="dash__text-2 mt-2">Phone: ${requestScope.order.phoneNumber}</span>
                                                    <span class="dash__text-2 mt-2">Address: ${requestScope.order.address}</span>
                                                    <span class="dash__text-2 mt-2">Note: ${requestScope.order.note}</span>

                                                </div>
                                            </div>

                                        </div>
                                        <div class="col-lg-6">
                                            <div class="dash__box dash__box--bg-white dash__box--shadow u-h-100">
                                                <div class="dash__pad-3">
                                                    <div class="dash-l-r u-s-m-b-8">
                                                        <div class="manage-o__text-2 u-c-secondary">Sub price</div>
                                                        <div class="manage-o__text-2 u-c-secondary"><fmt:formatNumber
                                                                value="${order.total}" type="currency"/></div>
                                                    </div>
                                                    <div class="dash-l-r u-s-m-b-8">
                                                        <div class="manage-o__text-2 u-c-secondary">Shipping fee
                                                        </div>
                                                        <div class="manage-o__text-2 u-c-secondary"><fmt:formatNumber
                                                                value="0" type="currency"/></div>
                                                    </div>
                                                    <div class="dash-l-r u-s-m-b-8">
                                                        <div class="manage-o__text-2 u-c-secondary">Total</div>
                                                        <div class="manage-o__text-2 u-c-secondary"><fmt:formatNumber
                                                                value="${order.total}" type="currency"/></div>
                                                    </div>
                                                    <span class="dash__text-2">Payment method: ${order.paymentMethod}</span>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <%@ include file="./common/footer.jsp" %>
</div>
<!--====== End - Main App ======-->
<!-- Modernizr-JS -->
<script type="text/javascript" src="/user/js/vendor/modernizr-custom.min.js"></script>
<!-- NProgress -->
<script type="text/javascript" src="/user/js/nprogress.min.js"></script>
<!-- jQuery -->
<script type="text/javascript" src="/user/js/jquery.min.js"></script>
<!-- Bootstrap JS -->
<script type="text/javascript" src="/user/js/bootstrap.min.js"></script>
<!-- Popper -->
<script type="text/javascript" src="/user/js/popper.min.js"></script>
<!-- ScrollUp -->
<script type="text/javascript" src="/user/js/jquery.scrollUp.min.js"></script>
<!-- Elevate Zoom -->
<script type="text/javascript" src="/user/js/jquery.elevatezoom.min.js"></script>
<!-- jquery-ui-range-slider -->
<script type="text/javascript" src="/user/js/jquery-ui.range-slider.min.js"></script>
<!-- jQuery Slim-Scroll -->
<script type="text/javascript" src="/user/js/jquery.slimscroll.min.js"></script>
<!-- jQuery Resize-Select -->
<script type="text/javascript" src="/user/js/jquery.resize-select.min.js"></script>
<!-- jQuery Custom Mega Menu -->
<script type="text/javascript" src="/user/js/jquery.custom-megamenu.min.js"></script>
<!-- jQuery Countdown -->
<script type="text/javascript" src="/user/js/jquery.custom-countdown.min.js"></script>
<!-- Owl Carousel -->
<script type="text/javascript" src="/user/js/owl.carousel.min.js"></script>
<!-- Main -->
<script type="text/javascript" src="/user/js/app.js"></script>
</body>

</html>