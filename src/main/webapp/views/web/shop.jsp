<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="vi_VN"/>
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
    <title>Nai - Cửa hàng</title>
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
                    <h1>Cửa hàng</h1>
                    <p class="mb-4">Đẩy nhanh tốc độ và bước chân của bạn một cách chính xác để đạt được kỷ lục tốt nhất!</p>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- End Hero Section -->
<div class="untree_co-section product-section before-footer-section">
    <div class="container">
        <div class="row">
            <div class="col-12 col-md-3">
                <div class="position-sticky bg-transparent rounded-3 overflow-hidden" style="top: 90px;">
                    <div class="d-flex gap-3 py-3 bg-white rounded-3" style="margin-bottom: 2px">
                        <i class="fa-solid fa-bars my-auto ps-2"></i>
                        <p class="my-0 fw-bold">Bộ lọc</p>
                    </div>
                    <div class="bg-white rounded-3 overflow-y-auto" style="overflow-y: auto; max-height: 500px ">
                        <div class="accordion" id="accordionPanelsStayOpenExample">
                            <div class="accordion-item">
                                <h2 class="accordion-header" id="panelsStayOpen-headingOne">
                                    <button class="accordion-button" type="button" data-bs-toggle="collapse" data-bs-target="#panelsStayOpen-collapseOne" aria-expanded="true" aria-controls="panelsStayOpen-collapseOne">
                                        Danh mục
                                    </button>
                                </h2>
                                <div id="panelsStayOpen-collapseOne" class="accordion-collapse collapse show" aria-labelledby="panelsStayOpen-headingOne">
                                    <div class="accordion-body">
                                        <div class="form-check">
                                            <c:forEach var="category_item" items="${LIST_CATEGORY}">
                                                <div style="cursor: pointer">
                                                    <input class="form-check-input categoryFilter" type="checkbox" value="${category_item.id}" id="categoryCheck${category_item.id}">
                                                    <label class="form-check-label" for="categoryCheck${category_item.id}">
                                                            ${category_item.name}
                                                    </label>
                                                </div>

                                            </c:forEach>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="accordion-item">
                                <h2 class="accordion-header" id="panelsStayOpen-headingTwo">
                                    <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#panelsStayOpen-collapseTwo" aria-expanded="false" aria-controls="panelsStayOpen-collapseTwo">
                                        Kích thước
                                    </button>
                                </h2>
                                <div id="panelsStayOpen-collapseTwo" class="accordion-collapse collapse" aria-labelledby="panelsStayOpen-headingTwo">
                                    <div class="accordion-body">
                                        <c:forEach var="size_item" items="${LIST_SIZE}">
                                            <div style="cursor: pointer">
                                                <input class="form-check-input sizeFilter" type="checkbox" value="${size_item.id}" id="sizeCheck${size_item.id}">
                                                <label class="form-check-label" for="sizeCheck${size_item.id}">
                                                        ${size_item.name}
                                                </label>
                                            </div>
                                        </c:forEach>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-12 col-md-9">
                <div>
                    <div class="d-flex justify-content-between">
                        <c:if test="${not empty productName}">
                            <p style="font-weight: 600; font-size: 30px">Kết quả cho "${productName}"</p>
                        </c:if>
                        <c:if test="${ empty productName}">
                            <p style="font-weight: 600; font-size: 30px">Danh sách sản phẩm</p>
                        </c:if>
                        <div>
                            <select class="form-select" id="sortFilter" style="max-width: 150px" aria-label="Default select example">
                                <option selected value="createAt-DESC">Mới nhất</option>
                                <option value="totalViewAndSearch-DESC">Được quan tâm nhiều nhất</option>
                                <option value="price-ASC">Giá từ thấp đến cao</option>
                                <option value="price-DESC">Giá từ cao đến thấp</option>
                            </select>
                        </div>

                    </div>
                    <div class="row" id="listProduct"></div>
                </div>
            </div>


        </div>
    </div>
</div>
<script !src="">
    window.addEventListener("DOMContentLoaded",function (){
        let page = 1;
        let isDone = false;
        let isLoadProduct = false;
        const productName = "${productName}";
        const loadMoreProduct = async (name) => {
            const sortFilter = $('#sortFilter').val().split('-');
            const sortName = sortFilter[0]
            const sortBy = sortFilter[1]
            const categories = $(".categoryFilter:checked").map(function() {
                return $(this).val();
            }).get();
            const sizes = $(".sizeFilter:checked").map(function() {
                return $(this).val();
            }).get();
            return await new Promise((resolve,reject) => {
                setTimeout(()=>{
                    $.ajax({
                        url: "/search/product",
                        method: "GET",
                        async: true,
                        data: {
                            page: page,
                            maxPageItem: 8,
                            productName: name,
                            categories : categories,
                            sizes : sizes,
                            sortName : sortName,
                            sortBy : sortBy
                        },
                        success: function (listProduct) {
                            page++;
                            resolve(listProduct)
                        }
                    });
                }, 500)
            });
        }
        const renderMoreProduct = async (name,reRender= false) => {
            isLoadProduct = true
            const listProductDOM = $("#listProduct");
            if (reRender) {
                listProductDOM.empty()
                page =1
            }
            listProductDOM.append(`
            <div class="d-flex" id="loadingProduct">
                <div class="spinner-grow text-success m-auto" role="status">
                  <span class="visually-hidden">Loading...</span>
                </div>
            </div>
            `)
            let listProductHtml = ``;
            let listProduct = await loadMoreProduct(name);
            if (listProduct.length>0){
                for (let i = 0; i < listProduct.length; i++) {
                    const product = listProduct[i];
                    const productItemStr = `
                        <div class="col-12 col-md-4 col-lg-3 mb-5">
                            <a class="product-item" href="/product-details/` + product.id + `">
                                <img loading="lazy" src="` + product.thumbnail + `" class="img-fluid product-thumbnail">
                                <h3 class="product-title"
                                    style="max-height: 40px; min-height: 40px;display: -webkit-box;
                                        -webkit-box-orient: vertical;
                                        -webkit-line-clamp: 2;
                                        overflow: hidden;
                                        text-overflow: ellipsis;
                                        white-space: normal;
                                    "
                                    >` + product.name + `</h3>
                                <strong class="product-price">` + new Intl.NumberFormat('vi-VN', { style: 'currency', currency: 'VND' }).format(product.price) + `</strong>
                                <span class="icon-cross"><img loading="lazy" src="/template/web/images/cross.svg" class="img-fluid"></span>
                            </a>
                        </div>
                    `
                    listProductHtml+=productItemStr;
                }
            } else if (listProduct.length == 0 && productName!="" && page==2) {
                listProductHtml = `<div class="d-flex">
                        <p class="mx-auto">Không tìm thấy sản phẩm</p>
                    </div>`
            } else if (listProduct.length==0) {
                isDone = true;
            }
            isLoadProduct = false;
            $("#loadingProduct").remove();
            listProductDOM.append(listProductHtml);
        }
        $(window).scroll(async function() {
            if (!isDone && !isLoadProduct){
                const scrollContainer = $('#listProduct');
                const scrollPosition = $(window).scrollTop() + $(window).height();
                const containerPosition = scrollContainer.offset().top + scrollContainer.height() + 150;
                if (scrollPosition >= containerPosition) {
                    await renderMoreProduct(productName);
                }
            }
        });
        let debounceGetProduct;
        $('#sortFilter, .categoryFilter, .sizeFilter').change(() => {
            clearTimeout(debounceGetProduct)
            debounceGetProduct = setTimeout(()=> {
                renderMoreProduct(productName, true);
            },400)
        });
        renderMoreProduct(productName);
    })
</script>
</body>
</html>
