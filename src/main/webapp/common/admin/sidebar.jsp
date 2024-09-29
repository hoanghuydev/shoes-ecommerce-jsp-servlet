<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<aside class="sidenav navbar navbar-vertical navbar-expand-xs border-0 border-radius-xl my-3 fixed-start ms-3   bg-gradient-dark"
       id="sidenav-main">

    <div class="sidenav-header">
        <i class="fas fa-times p-3 cursor-pointer text-white opacity-5 position-absolute end-0 top-0 d-none d-xl-none"
           aria-hidden="true" id="iconSidenav"></i>
        <a class="navbar-brand m-0" href="<c:url value="/admin/home"/> " target="_blank">
            <img src="https://www.nike.com/favicon.ico" class="navbar-brand-img h-100" alt="main_logo">
            <span class="ms-1 font-weight-bold text-white">Admin Nai</span>
        </a>
    </div>


    <hr class="horizontal light mt-0 mb-2">

    <div class="collapse navbar-collapse  w-auto " id="sidenav-collapse-main">
        <ul class="navbar-nav">

            <li class="nav-item">
                <a class="nav-link text-white " href="/admin/home">

                    <div class="text-white text-center me-2 d-flex align-items-center justify-content-center">
                        <i class="material-icons opacity-10">dashboard</i>
                    </div>

                    <span class="nav-link-text ms-1">Dashboard</span>
                </a>
            </li>


            <li class="nav-item">
                <a class="nav-link text-white " href="/admin/order/list">

                    <div class="text-white text-center me-2 d-flex align-items-center justify-content-center">
                        <i class="material-icons opacity-10">table_view</i>
                    </div>

                    <span class="nav-link-text ms-1">Đơn hàng</span>
                </a>
            </li>

            <li class="nav-item">
                <a class="nav-link text-white " href="/admin/product/list">

                    <div class="text-white text-center me-2 d-flex align-items-center justify-content-center">
                        <i class="fa-solid fa-boxes-stacked"></i>
                    </div>

                    <span class="nav-link-text ms-1">Sản phẩm</span>
                </a>
            </li>

            <li class="nav-item">
                <a class="nav-link text-white " href="/admin/repository/list">

                    <div class="text-white text-center me-2 d-flex align-items-center justify-content-center">
                        <i class="fa-solid fa-house"></i>
                    </div>

                    <span class="nav-link-text ms-1">Tồn kho</span>
                </a>
            </li>

            <li class="nav-item">
                <a class="nav-link text-white " href="/admin/category/list">

                    <div class="text-white text-center me-2 d-flex align-items-center justify-content-center">
                        <i class="fa-solid fa-list"></i>
                    </div>

                    <span class="nav-link-text ms-1">Thể loại</span>
                </a>
            </li>

            <li class="nav-item">
                <a class="nav-link text-white " href="/admin/size/list">

                    <div class="text-white text-center me-2 d-flex align-items-center justify-content-center">
                        <i class="fa-solid fa-maximize"></i>
                    </div>

                    <span class="nav-link-text ms-1">Kích thước giày</span>
                </a>
            </li>

            <li class="nav-item">
                <a class="nav-link text-white " href="/admin/user/list">
                    <div class="text-white text-center me-2 d-flex align-items-center justify-content-center">
                        <i class="fa-solid fa-users"></i>
                    </div>
                    <span class="nav-link-text ms-1">Người dùng</span>
                </a>
            </li>

            <li class="nav-item">
                <a class="nav-link text-white " href="<c:url value="/admin/logs"/> ">
                    <div class="text-white text-center me-2 d-flex align-items-center justify-content-center">
                        <i class="material-icons opacity-10">assignment</i>
                    </div>
                    <span class="nav-link-text ms-1">Nhật ký</span>
                </a>
            </li>

            <li class="nav-item">
                <a class="nav-link text-white " href="<c:url value="/admin/product/out-of-stock"/> ">
                    <div class="text-white text-center me-2 d-flex align-items-center justify-content-center">
<%--                        <i class="fa-solid fa-chart-line"></i>--%>
                        <i class="fa-solid fa-triangle-exclamation"></i>
                    </div>
                    <span class="nav-link-text ms-1">Sản phẩm sắp hết hàng</span>
                </a>
            </li>
            <li class="nav-item">
                <a class="nav-link text-white " href="/admin/voucher/list">
                    <div class="text-white text-center me-2 d-flex align-items-center justify-content-center">
                        <i class="fa-solid fa-ticket"></i>
                    </div>
                    <span class="nav-link-text ms-1">Mã giảm giá</span>
                </a>
            </li>

            <li class="nav-item">
                <a class="nav-link text-white " href="<c:url value="/admin/import-order"/>">

                    <div class="text-white text-center me-2 d-flex align-items-center justify-content-center">
                        <i class="material-icons opacity-10">receipt_long</i>
                    </div>

                    <span class="nav-link-text ms-1">Hóa đơn nhập</span>
                </a>
            </li>
            <%--            <li class="nav-item mt-3">--%>
            <%--                <h6 class="ps-4 ms-2 text-uppercase text-xs text-white font-weight-bolder opacity-8">Other</h6>--%>
            <%--            </li>--%>
            <%--            <li class="nav-item">--%>
            <%--                <a class="nav-link text-white " href="#">--%>

            <%--                    <div class="text-white text-center me-2 d-flex align-items-center justify-content-center">--%>
            <%--                        <i class="material-icons opacity-10">notifications</i>--%>
            <%--                    </div>--%>

            <%--                    <span class="nav-link-text ms-1">Notifications</span>--%>
            <%--                </a>--%>
            <%--            </li>--%>
            <li class="nav-item">
                <a class="nav-link text-white " href="/admin/edit-image">

                    <div class="text-white text-center me-2 d-flex align-items-center justify-content-center">
                        <i class="fa-solid fa-image"></i>
                    </div>

                    <span class="nav-link-text ms-1">Sửa ảnh</span>
                </a>
            </li>

        </ul>
    </div>
</aside>
