<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/common/taglib.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <link href="<c:url value="/template/admin/assets/css/editImage.css"/>" rel="stylesheet">
    <link rel="shortcut icon" href="<c:url value="https://www.nike.com/favicon.ico"/> ">
    <link rel="stylesheet" type="text/css" href="https://fonts.googleapis.com/css?family=Roboto:300,400,500,700,900|Roboto+Slab:400,700" />
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css" integrity="sha512-DTOQO9RWCH3ppGqcWaEA1BIZOC6xxalwEsw9c2QQeAIftl+Vegovlnee1c9QX4TctnWMn13TZye+giMm8e2LwA==" crossorigin="anonymous" referrerpolicy="no-referrer" />
    <!-- Nucleo Icons -->
    <link href="/template/admin/assets/css/nucleo-icons.css" rel="stylesheet" />
    <link href="/template/admin/assets/css/nucleo-svg.css" rel="stylesheet" />
    <link rel="stylesheet" type="text/css" href="https://cdn.jsdelivr.net/npm/toastify-js/src/toastify.min.css">
    <script src="https://kit.fontawesome.com/42d5adcbca.js" crossorigin="anonymous"></script>
    <link href="<c:url value="/template/web/css/custom.css"/>" rel="stylesheet">

    <!-- Material Icons -->
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons+Round" rel="stylesheet">

    <!-- CSS Files -->
    <link id="pagestyle" href="/template/admin/assets/css/material-dashboard.css?v=3.1.0" rel="stylesheet" />

    <!-- Nepcha Analytics (nepcha.com) -->
    <!-- Nepcha is a easy-to-use web analytics. No cookies and fully compliant with GDPR, CCPA and PECR. -->
    <script defer data-site="http://localhost:8080" src="https://api.nepcha.com/js/nepcha-analytics.js"></script>
    <link href="https://cdn.datatables.net/1.13.7/css/dataTables.bootstrap4.min.css" rel="stylesheet"/>
    <script src="https://cdn.ckeditor.com/ckeditor5/40.2.0/classic/ckeditor.js"></script>
    <title><dec:title default="Admin Nai" /></title>
    <script src="<c:url value="/packages/node_modules/ffmpeg-old/assets/ffmpeg/package/dist/umd/ffmpeg.js"/>"></script>
    <script src="<c:url value="/packages/node_modules/ffmpeg-old/assets/util/package/dist/umd/index.js"/> "></script>
</head>
<body class="g-sidenav-show  bg-gray-100">
<%@ include file="/common/admin/sidebar.jsp" %>
    <main class="main-content border-radius-lg ">
        <%@ include file="/common/admin/header.jsp" %>
        <div class="container-fluid py-4">
            <dec:body/>
            <%@ include file="/common/admin/footer.jsp" %>
        </div>

    </main>
<%@ include file="/common/admin/plugin-setting.jsp" %>
<div class="position-fixed top-5 end-1 z-index-2">
    <div class="toast fade hide p-2 bg-white" role="alert" aria-live="assertive" id="successToast" aria-atomic="true">
        <div class="toast-header border-0">
            <i class="material-icons text-success me-2">
                check
            </i>
            <span class="me-auto font-weight-bold" id="successToastTitle">Material Dashboard </span>
            <small class="text-body" id="successToastTimeAgo">11 mins ago</small>
            <i class="fas fa-times text-md ms-3 cursor-pointer" data-bs-dismiss="toast" aria-label="Close"></i>
        </div>
        <hr class="horizontal dark m-0">
        <div class="toast-body" id="successToastMessage">
            Hello, world! This is a notification message.
        </div>
    </div>
    <div class="toast fade hide p-2 mt-2 bg-gradient-info" role="alert" aria-live="assertive" id="infoToast" aria-atomic="true">
        <div class="toast-header bg-transparent border-0">
            <i class="material-icons text-white me-2">
                notifications
            </i>
            <span class="me-auto text-white font-weight-bold" id="infoToastTitle">Material Dashboard </span>
            <small class="text-white" id="infoToastTimeAgo">11 mins ago</small>
            <i class="fas fa-times text-md text-white ms-3 cursor-pointer" data-bs-dismiss="toast" aria-label="Close"></i>
        </div>
        <hr class="horizontal light m-0">
        <div class="toast-body text-white" id="infoToastMessage">
            Hello, world! This is a notification message.
        </div>
    </div>
    <div class="toast fade hide p-2 mt-2 bg-white" role="alert" aria-live="assertive" id="warningToast" aria-atomic="true">
        <div class="toast-header border-0">
            <i class="material-icons text-warning me-2">
                travel_explore
            </i>
            <span class="me-auto font-weight-bold" id="warningToastTitle"> </span>
            <small class="text-body" id="warningToastTimeAgo">11 mins ago</small>
            <i class="fas fa-times text-md ms-3 cursor-pointer" data-bs-dismiss="toast" aria-label="Close"></i>
        </div>
        <hr class="horizontal dark m-0">
        <div class="toast-body" id="warningToastMessage">
            Hello, world! This is a notification message.
        </div>
    </div>
    <div class="toast fade hide p-2 mt-2 bg-white" role="alert" aria-live="assertive" id="dangerToast" aria-atomic="true">
        <div class="toast-header border-0">
            <i class="material-icons text-danger me-2">
                campaign
            </i>
            <span class="me-auto text-gradient text-danger font-weight-bold" id="dangerToastTitle">Material Dashboard </span>
            <small class="text-body" id="dangerToastTimeAgo">11 mins ago</small>
            <i class="fas fa-times text-md ms-3 cursor-pointer" data-bs-dismiss="toast" aria-label="Close"></i>
        </div>
        <hr class="horizontal dark m-0">
        <div class="toast-body" id="dangerToastMessage">
            Hello, world! This is a notification message.
        </div>
    </div>
</div>
<script src="https://code.jquery.com/jquery-3.7.0.js"></script>

<script src="/template/admin/assets/js/core/popper.min.js"></script>
<script src="/template/admin/assets/js/core/bootstrap.min.js"></script>
<script src="/template/admin/assets/js/plugins/perfect-scrollbar.min.js"></script>
<script src="/template/admin/assets/js/plugins/smooth-scrollbar.min.js"></script>
<script src="/template/admin/assets/js/plugins/chartjs.min.js"></script>
<script src="https://cdn.datatables.net/1.13.7/js/jquery.dataTables.min.js"></script>
<script src="https://cdn.datatables.net/1.13.7/js/dataTables.bootstrap4.min.js"></script>
<script type="text/javascript" src="https://cdn.jsdelivr.net/npm/toastify-js"></script>
<script src="https://unpkg.com/validator@latest/validator.min.js"></script>
<!-- Github buttons -->
<script async defer src="https://buttons.github.io/buttons.js"></script>
<!-- Control Center for Material Dashboard: parallax effects, scripts for the example pages etc -->
<script src="/template/admin/assets/js/material-dashboard.min.js?v=3.1.0"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/timeago.js/2.0.2/timeago.min.js" integrity="sha512-sl01o/gVwybF1FNzqO4NDRDNPJDupfN0o2+tMm4K2/nr35FjGlxlvXZ6kK6faa9zhXbnfLIXioHnExuwJdlTMA==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>
<script>
    var win = navigator.platform.indexOf('Win') > -1;
    if (win && document.querySelector('#sidenav-scrollbar')) {
        var options = {
            damping: '0.5'
        }
        Scrollbar.init(document.querySelector('#sidenav-scrollbar'), options);
    }
    function showToastAdmin(typeToast,title,message,time) {
        $("#"+typeToast+"Toast").addClass("show").removeClass("hide");
        $("#"+typeToast+"ToastTitle").text(title);
        $("#"+typeToast+"ToastMessage").text(message);
        const timeagoInstance = timeago();
        const notifyTimeAgo = timeagoInstance.format(new Date(time));
        $("#"+typeToast+"ToastTimeAgo").text(notifyTimeAgo);
    }
    const socket = new WebSocket("ws://localhost:8080/socket/opinion/0");
    // revice message from socket
    socket.onmessage = function onMessage(message) {
        const opinionData = JSON.parse(message.data);
        console.log(opinionData)
        if (opinionData.isDeleted) {
            showToastAdmin("warning","A Review Has Been Deleted","User "+opinionData.userName+ " deleted them review product id : "+opinionData.productId,opinionData.createAt);
        } else {
            showToastAdmin("info","New Review Has Been Added","User "+opinionData.userName+ " added them review product id : "+opinionData.productId,opinionData.createAt);
        }
    }
</script>

<%--Show toast--%>
<c:if test="${not empty toast && not empty message}">
    <script>
        window.addEventListener("DOMContentLoaded",function (){
            Toastify({
                text: '${message}',
                duration: 3000,
                className: "bg-${toast}",
                close: true,
                gravity: "top", // `top` or `bottom`
                position: "right",
            }).showToast();
        })
    </script>
</c:if>

</body>
</html>