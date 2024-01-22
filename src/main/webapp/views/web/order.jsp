<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/common/taglib.jsp" %>
<html>
<head>
    <title>Nai - Order Details</title>
</head>
<body>
<div class="card" id="billImage">
    <div id="qrcode" class="mx-auto"></div>
    <div class="title">Purchase Reciept</div>
    <div class="info">
        <div class="row">
            <div class="col-7">
                <span id="headingDate">Date</span><br>
                <span id="detailsDate">${ORDER_MODEL.createAt}</span>
            </div>
            <div class="col-5 pull-right">
                <span id="headingOrderId">Order No.</span><br>
                <span id="detailsOrderId">${ORDER_MODEL.slug}</span>
            </div>
        </div>
    </div>
    <div style="padding: 5% 8%;">
        <p>Customer : ${ADDRESS_MODEL.fullName}</p>
        <p>Address : ****,${ADDRESS_MODEL.district},${ADDRESS_MODEL.province}</p>
    </div>
    <div class="pricing">
        <c:forEach var="product_item" items="${LIST_PRODUCT}">
            <div class="row" style="justify-content: space-between">
                <div class="col-9">
                    <span id="name">${product_item.name} - ${product_item.sizeName}</span>
                </div>
                <div class="col-3" style="text-align: right">
                    <span id="price">$${product_item.subTotal}</span>
                </div>
            </div>
        </c:forEach>


    </div>
    <div class="total">
        <div class="row" style="justify-content: space-between">
            <p class="col-5">Ship</p>
            <p class="col-5" style="text-align: right">$5</p>
        </div>
        <div class="row" style="justify-content: space-between">
            <p class="col-5"><big>Total</big></p>
            <p class="col-5" style="text-align: right"><big>$${ORDER_MODEL.totalAmount}</big></p>
        </div>
        <c:if test="${ORDER_MODEL.isPaid}">
            <div class="row" style="justify-content: right">
                <p class="col-5" style="text-align: right"><big>Paid</big></p>
            </div>
        </c:if>

    </div>

    <div class="tracking">
        <div class="title">Tracking Order</div>
    </div>
    <div class="progress-track">
        <ul id="progressbar">
            <li class="step0 active"  id="step1">Ordered</li>
            <li class="step0 <c:if test="${ORDER_MODEL.status >2}">active</c:if>" style="text-align: center" id="step2">Shipped</li>
            <li class="step0 <c:if test="${ORDER_MODEL.status >3}">active</c:if>"  style="text-align: right" id="step3">On the way</li>
            <li class="step0 <c:if test="${ORDER_MODEL.status >4}">active</c:if>" style="text-align: right" id="step4">Delivered</li>
        </ul>
    </div>
    <div class="footer">
        <div class="row">
            <div class="col-2"><img class="img-fluid" src="https://i.imgur.com/YBWc55P.png"></div>
            <div class="col-10">Want any help? Please &nbsp;<a href="/contact"> contact us</a></div>
        </div>
    </div>
</div>
<div style="display: flex">
    <div style="margin: 50px auto; width: fit-content">
        <a id="saveBill" style="padding: 16px 24px; background : rgb(252, 103, 49); color : white;border-radius: 10px">Save bill</a>
    </div>
</div>
<div id="previewImage" style="display: none"></div>
<script src=
                "https://cdnjs.cloudflare.com/ajax/libs/qrcodejs/1.0.0/qrcode.min.js">
</script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jspdf/1.3.2/jspdf.min.js"></script>
<script>
    window.addEventListener("DOMContentLoaded",function (){
        var qrcode = new QRCode("qrcode",
            {
                text: window.location.href,
                width: 100,
                height: 100,
                colorDark : "#000000",
                colorLight : "#ffffff",
                correctLevel : QRCode.CorrectLevel.H
            });
        const billImage = $("#billImage"); // global variable
        let getCanvas; // global variable
        $('document').ready(function(){
            html2canvas(billImage, {
                    onrendered: function (canvas) {
                    $("#previewImage").append(canvas);
                    getCanvas = canvas;
                }
            });
        });
        $("#saveBill").on('click', function () {
            var imageData = getCanvas.toDataURL("image/png");
            // Now browser starts downloading it instead of just showing it
            var newData = imageData.replace(/^data:image\/png/, "data:application/octet-stream");
            $("#saveBill").attr("download", "billOrder${ORDER_MODEL.slug}.png").attr("href", newData);
        });
    })
</script>
</body>
</html>
