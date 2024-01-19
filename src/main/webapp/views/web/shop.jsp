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
        <div class="row" id="listProduct"></div>
    </div>
</div>
<script !src="">
    window.addEventListener("DOMContentLoaded",function (){
        let page = 1;
        let isDone = false;
        const productName = "${productName}";
        const loadMoreProduct = (name) => {
            let result;
            $.ajax({
                url: "/search/product",
                method: "GET",
                async: false, // Set to synchronous
                data: {
                    page : page,
                    maxPageItem: 8,
                    productName: name,
                    sortBy : "createAt",
                    sortBy : "desc"
                },
                success: function (listProduct) {
                    page++;
                    result = listProduct;
                }
            });
            return result;
        }
        const renderMoreProduct = (name) => {
            let listProductHtml = ``;
            const listProduct = loadMoreProduct(name);
            if (listProduct.length>0){
                for (let i = 0; i < listProduct.length; i++) {
                    const product = listProduct[i];
                    listProductHtml+=`
                     <div class="col-12 col-md-4 col-lg-3 mb-5">
                        <a class="product-item" href="/product-details/`+product.id+`">
                            <img loading="lazy" src="`+product.thumbnail+`" class="img-fluid product-thumbnail">
                            <h3 class="product-title">`+product.name+`</h3>
                            <strong class="product-price">$`+product.price+`</strong>
                            <span class="icon-cross"><img loading="lazy" src="/template/web/images/cross.svg" class="img-fluid"></span>
                        </a>
                    </div>
                `
                }
            } else if (listProduct.length==0 && productName!="" && page==2) {
                const notFoundHtml = `<div class="d-flex">
                        <p class="mx-auto">Not found product</p>
                    </div>`
                $("#listProduct").append(notFoundHtml);
            } else if (listProduct.length==0) {
                isDone = true;
            }
            $("#listProduct").append(listProductHtml);
        }
        $(window).scroll(function() {
            if (!isDone){
                const scrollContainer = $('#listProduct');
                const scrollPosition = $(window).scrollTop() + $(window).height();
                const containerPosition = scrollContainer.offset().top + 500; // Adjust as needed
                if (scrollPosition >= containerPosition) {
                    renderMoreProduct(productName);
                }
            }

        });
        renderMoreProduct(productName);
    })
</script>
</body>
</html>
