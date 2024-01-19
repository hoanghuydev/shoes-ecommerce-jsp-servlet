<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/common/taglib.jsp" %>

<!doctype html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="author" content="Untree.co">
    <link rel="shortcut icon" href="<c:url value="https://www.nike.com/favicon.ico"/> ">

    <meta name="description" content="" />
    <meta name="keywords" content="bootstrap, bootstrap4" />

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" type="text/css" href="https://cdn.jsdelivr.net/npm/toastify-js/src/toastify.min.css">
    <link href="<c:url value="/template/web/css/bootstrap.min.css"/>" rel="stylesheet">
    <link href="<c:url value="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css"/>" rel="stylesheet">
    <link href="<c:url value="/template/web/css/tiny-slider.css"/>" rel="stylesheet">
    <link href="<c:url value="/template/web/css/custom.css"/>" rel="stylesheet">
    <link href="<c:url value="/template/web/css/style.css"/>" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/ms-dropdown@4.0.3/dist/css/dd.min.css">

    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css" integrity="sha512-DTOQO9RWCH3ppGqcWaEA1BIZOC6xxalwEsw9c2QQeAIftl+Vegovlnee1c9QX4TctnWMn13TZye+giMm8e2LwA==" crossorigin="anonymous" referrerpolicy="no-referrer" />
    <link href="
https://cdn.jsdelivr.net/npm/@splidejs/splide@4.1.4/dist/css/splide.min.css
" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/css/select2.min.css" rel="stylesheet" />
    <title><dec:title default="Nai Shoes & Sneakers"/></title>
</head>

<body>

<!-- Start Header/Navigation -->
<%@ include file="/common/web/header.jsp" %>
<!-- End Header/Navigation -->
<dec:body/>
<!-- Start Footer Section -->
<%@ include file="/common/web/footer.jsp" %>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.7.1/jquery.min.js" integrity="sha512-v2CJ7UaYy4JwqLDIrZUI/4hqeoQieOmAZNXBeQyjo21dadnwR+8ZaIJVT8EE2iyI61OV8e6M8PP2/4hpQINQ/g==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>
<!-- End Footer Section -->
<script src="<c:url value="/template/web/js/bootstrap.bundle.min.js"/>"></script>
<script src="<c:url value="/template/web/js/tiny-slider.js"/>"></script>
<script src="<c:url value="/template/web/js/custom.js"/>"></script>
<script src="https://unpkg.com/validator@latest/validator.min.js"></script>
<script type="text/javascript" src="https://cdn.jsdelivr.net/npm/toastify-js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/timeago.js/2.0.2/timeago.min.js" integrity="sha512-sl01o/gVwybF1FNzqO4NDRDNPJDupfN0o2+tMm4K2/nr35FjGlxlvXZ6kK6faa9zhXbnfLIXioHnExuwJdlTMA==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>
<script src="
https://cdn.jsdelivr.net/npm/@splidejs/splide@4.1.4/dist/js/splide.min.js
"></script>
<script src="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/js/select2.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/ms-dropdown@4.0.3/dist/js/dd.min.js"></script>

<script>
    function showToast(message,toast,destination="", avatar ="https://www.nike.com/favicon.ico "){
        Toastify({
            text: message,
            duration: 3000,
            className: "bg-"+toast,
            destination,
            avatar,
            close: true,
            gravity: "top", // `top` or `bottom`
            position: "right",
        }).showToast();
    }
    <c:if test="${not empty toast && not empty message}">
        showToast("${message}","${toast}");
    </c:if>
</script>


</body>

</html>

