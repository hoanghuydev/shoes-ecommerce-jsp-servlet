<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: HUY
  Date: 1/3/2024
  Time: 1:35 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/common/taglib.jsp" %>
<html>
<head>
    <title>Nai - Shop</title>
</head>
<body>
<!-- Start Hero Section -->
<div class="hero" style="
    background-image: url(https://brand.assets.adidas.com/image/upload/f_auto,q_auto,fl_lossy/if_w_gt_1920,w_1920/running_ss24_adizero_introduce_plp_women_masthead_d_de1007a210.jpg);
    background-repeat: no-repeat;
    background-size: 100% 100%;
">
    <div class="container">
        <div class="row justify-content-between">
            <div class="col-lg-5">
                <div class="intro-excerpt">
                    <h1>Shop</h1>
                    <p class="mb-4">Push your pace and stride with precision to reach your best 10k yet.</p>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- End Hero Section -->
<div class="untree_co-section product-section before-footer-section">
    <div class="container">
        <c:if test="${not empty productName}">
            <div>
                <p style="font-weight: 600; font-size: ">Result for "${productName}"</p>
            </div>
        </c:if>
        <div class="row" id="listProduct">
            <c:if test="${not empty LIST_MODEL}">
                <c:forEach var="product_item" items="${LIST_MODEL}">
                    <div class="col-12 col-md-4 col-lg-3 mb-5">
                        <a class="product-item" href="/product-details/${product_item.id}">
                            <img loading="lazy" src="${product_item.thumbnail}" class="img-fluid product-thumbnail">
                            <h3 class="product-title">${product_item.name}</h3>
                            <strong class="product-price">$${product_item.price}</strong>
                            <span class="icon-cross"><img loading="lazy" src="/template/web/images/cross.svg" class="img-fluid"></span>
                        </a>
                    </div>
                </c:forEach>
            </c:if>
            <c:if test="${empty LIST_MODEL}">
                    <div class="d-flex">
                        <p class="mx-auto">Not found product</p>
                    </div>
            </c:if>

            </div>
    </div>
</div>
</body>
</html>
