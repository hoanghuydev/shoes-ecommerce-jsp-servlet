<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/common/taglib.jsp" %>
<nav class="custom-navbar navbar navbar navbar-expand-md navbar-dark bg-dark" arial-label="Nai navigation bar" style="position: fixed; left: 0; right: 0;top : 0;z-index: 100">

    <div class="container">
        <a class="navbar-brand" href="/home">Nai<span>.</span></a>

        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarsFurni" aria-controls="navbarsFurni" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>

        <div class="collapse navbar-collapse" id="navbarsFurni">
            <ul class="custom-navbar-nav navbar-nav ms-auto mb-2 mb-md-0">
                <li class="nav-item <c:if test="${tab eq 'Home'}">active</c:if>">
                    <a class="nav-link" href="/home">Trang chủ</a>
                </li>
                <li class="<c:if test="${tab eq 'Shop'}">active</c:if>"><a class="nav-link" href="/shop">Cửa hàng</a></li>
                <li class="<c:if test="${tab eq 'Services'}">active</c:if>"><a class="nav-link" href="/services">Dịch vụ</a></li>
                <li class="<c:if test="${tab eq 'Contact us'}">active</c:if>"><a class="nav-link" href="/contact">Liên hệ</a></li>
                <c:if test="${USER_MODEL.admin}">
                    <li><a class="nav-link" href="/admin">Admin</a></li>
                </c:if>
            </ul>
            <ul class="custom-navbar-cta navbar-nav mb-2 mb-md-0 ms-5">
                <li class="d-flex" id="openSearchModal">
                    <a href="nav-link my-auto" data-bs-toggle="modal" data-bs-target="#searchModal" style="color :white;font-size: 21.5px;margin: auto">
                        <i class="fa-solid fa-magnifying-glass"></i>
                    </a>
                </li>
                <li class="dropdown">
                    <a class="nav-link dropdown-toggle" href="#" id="dropdownMenuButton1" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                        <img src="/template/web/images/user.svg">
                    </a>
                    <ul class="dropdown-menu" aria-labelledby="dropdownMenuButton1">
                        <c:if test="${not empty USER_MODEL}">

                            <li class="m-0"><a class="dropdown-item" href="/user-info">${USER_MODEL.fullName}</a></li>
                            <li><a class="dropdown-item" href="<c:url value="/orders"/> "><i class="far fa-check-circle me-2"></i>Đơn hàng</a></li>

                            <li><a class="dropdown-item" href="/sign-out"><i class="fa-solid fa-person-walking-dashed-line-arrow-right me-2"></i>Đăng xuất</a></li>
                        </c:if>
                        <c:if test="${empty USER_MODEL}">
                            <li class="m-0"><a class="dropdown-item" href="/sign-in"><i class="fa-solid fa-right-to-bracket me-2"></i>Đăng nhập</a></li>
                            <li><a class="dropdown-item" href="/sign-up"><i class="fa-solid fa-user-pen me-2"></i>Đăng ký</a></li>
                        </c:if>
                    </ul>
                </li>
                <li><a class="nav-link" href="/cart"><img src="/template/web/images/cart.svg"></a></li>
            </ul>
        </div>
    </div>
</nav>
<div style="height: 80px;"></div>
<%--Modal search--%>
<div class="modal fade" id="searchModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true" style="padding: 0">
    <div class="modal-dialog modal-dialog-top m-0" style="width: 100%;max-width:unset">
        <div class="modal-content" style="border: unset; border-radius: 0">
            <div class="modal-header" style="display: flex; justify-content: space-around">
                <svg aria-hidden="true" class="pre-logo-svg" focusable="false" viewBox="0 0 24 24" role="img" width="55px" height="55px" fill="none"><path fill="currentColor" fill-rule="evenodd" d="M21 8.719L7.836 14.303C6.74 14.768 5.818 15 5.075 15c-.836 0-1.445-.295-1.819-.884-.485-.76-.273-1.982.559-3.272.494-.754 1.122-1.446 1.734-2.108-.144.234-1.415 2.349-.025 3.345.275.2.666.298 1.147.298.386 0 .829-.063 1.316-.19L21 8.719z" clip-rule="evenodd"></path></svg>
               <div style="display: flex; border-radius: 30px; background-color: #e5e5e5; width: 70%;padding: 5px 10px">
                   <i class="fa-solid fa-magnifying-glass m-2"></i>
                   <input id="nameProductSearch" placeholder="Nhập tên sản phẩm" style="outline: none; border: none;background-color: transparent;width: 100%"/>
               </div>
                <a data-bs-dismiss="modal" aria-label="Close" style="cursor: pointer">Hủy</a>
            </div>
            <div class="modal-body"  >
                <div class="row justify-content-around" id="resultSearch">

                </div>
            </div>
        </div>
    </div>
</div>
<script !src="">
    window.addEventListener("DOMContentLoaded",function (){
        let searchTimeout;
        const getProductSearch = (name) => {
            let result;
            $.ajax({
                url: "/search/product",
                method: "GET",
                async: false, // Set to synchronous
                data: {
                    page : 1,
                    maxPageItem: 4,
                    productName: name,
                    sortBy : "totalViewAndSearch",
                    sortBy : "desc"
                },
                success: function (listProduct) {
                    result = listProduct;
                }
            });
            return result;
        }
        const renderSearch = (name) => {
            $("#resultSearch").empty();
            let topSearch = ``;
            const listProduct = getProductSearch(name);
            if (listProduct.length==0){
                topSearch = `
                            <div class="d-flex">
                                <p class="mx-auto">Không tìm thấy sản phẩm có tên `+name+`</p>
                            </div>
                        `
            } else {
                if (name==="") {
                    topSearch += `
                    <div class="row">
                        <p style="font-weight: 600">Cụm từ tìm kiếm phổ biến</p>
                    </div>
                    `;
                }
                for (let i = 0; i < listProduct.length; i++) {
                    const product = listProduct[i];
                    topSearch+=`
                     <div class="col-6 col-md-3 col-lg-3 mb-1">
                        <a class="product-item" href="/product-details/`+product.id+`" style="text-decoration: none">
                            <img loading="lazy" src="`+product.thumbnail+`" class="img-fluid product-thumbnail" style="
                                max-width: 65%;
                            ">
                            <p class="product-title" style="
                                font-size: 15px;
                                font-weight: 600;
                                margin: 0;

                            ">`+product.name+`</p>

                            <p class="product-price" style="
                                font-weight: 600;
                            ">`+formatCurrencyVietnamese(product.price)+`</p>
                        </a>
                    </div>
                `
                }
            }
            $("#resultSearch").append(topSearch);
        }

        function formatCurrencyVietnamese(value) {
            // Convert the value to a string with Vietnamese locale formatting
            let formattedValue = value.toLocaleString('vi-VN', {
                style: 'currency',
                currency: 'VND',
                minimumFractionDigits: 0,
                maximumFractionDigits: 0
            });

            // Return the formatted value
            return formattedValue;
        }


        $("#openSearchModal").click(() => {
            $("#nameProductSearch").on("keyup", function (event) {
                if (event.keyCode === 13 || event.which === 13) {
                    window.location.href = "/shop?productName="+$(this).val();
                } else {
                    clearTimeout(searchTimeout);
                    searchTimeout = setTimeout(() => {
                        renderSearch($(this).val());
                    }, 600);
                }
            });
            renderSearch( $("#nameProductSearch").val());
        });
    })
</script>
