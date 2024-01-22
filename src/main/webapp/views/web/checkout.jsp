<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: HUY
  Date: 12/30/2023
  Time: 12:21 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Nai - Checkout</title>
</head>
<body>
<!-- Container for demo purpose -->
<div class="container my-5">

    <!--Section: Design Block-->
    <section class="mb-10">
        <style>
            @media (max-width: 450px) {
                .product-image-2 {
                    width: 100%;
                }
            }
            @media (min-width: 451px) {
                .product-image-2 {
                    width: 100px;
                }
            }
        </style>
        <div class="row">

                <div class="col-lg-6 pe-lg-4 mb-5 mb-lg-0">
                    <form method="POST" id="formCheckout">
                        <h5 class="mb-4">Your information</h5>
                        <div class="row">
                            <div class="col-md-6">
                                <div class="form-outline mb-4">
                                    <label class="form-label" for="fullName">Full name<sup style="color :red;">*</sup></label>
                                    <input type="text" id="fullName" name="fullName" class="form-control" required />
                                </div>
                            </div>
                            <div class="col-md-6">
                                <div class="form-outline mb-4">
                                    <label class="form-label" for="phoneNumber">Phone number<sup style="color :red;">*</sup></label>
                                    <input type="text" id="phoneNumber" name="phoneNumber" class="form-control" required />
                                    <p style="color : red; font-size: 12px" id="phoneNumberValidate"></p>
                                </div>
                            </div>
                        </div>
                        <div class="row justify-content-between">
                            <div class="col-md-4">
                                <label for="province" class="form-label">Province<sup style="color :red;">*</sup></label>
                                <select class="form-select" name="province" id="province" required>

                                </select>
                            </div>
                            <div class="col-md-4">
                                <label for="district" class="form-label">District<sup style="color :red;">*</sup></label>
                                <select class="form-select" name="district" id="district" required disabled>

                                </select>
                            </div>
                            <div class="col-md-4">
                                <label for="commune" class="form-label">Commune<sup style="color :red;">*</sup></label>
                                <select class="form-select" name="commune" id="commune" required disabled>

                                </select>
                            </div>
                        </div>
                        <div class="row justify-content-between mt-4">
                            <div class="col-md-4">
                                <label for="hamlet1" class="form-label">Ward - Street<sup style="color :red;">*</sup></label>
                                <select class="form-select" name="hamlet1" id="hamlet1" required disabled>

                                </select>
                            </div>
                            <div class="col-md-8">
                                <label for="hamlet2" class="form-label">More Address</label>
                                <input class="form-control" type="text" id="hamlet2" name="hamlet2" placeholder="Enter your detail address...">
                                <p>Ex : House number 28/12</p>
                            </div>

                        </div>
                        <div class="form-group">
                            <label for="note" class="form-label">Note for shop</label>
                            <input class="form-control" type="text" id="note" name="note" placeholder="Enter your note..." required>
                        </div>

                        <c:forEach var="product_item" items="${LIST_PRODUCT_OF_CART}">
                            <input type="hidden" name="product[]" value="${product_item.id}-${product_item.sizeId}-${product_item.quantity}">
                        </c:forEach>
                        <div class="pt-1 mb-3">
                            <button type="submit" class="btn btn-dark btn-lg px-5">Pay order</button>
                            <p class="small text-muted mt-4 mb-0">By clicking "Proceed to PayPal" I confirm I have read <a href="#!">Privacy Notice</a> and <a href="#!">Cookie Notice</a>. I agree to the <a href="#!">terms & conditions</a> of the store. "I also accept that the store will process my personal data to manage my order, in accordance with the store's <a href="#!">privacy notice</a>"</p>
                        </div>
                    </form>

                </div>


            <div class="col-lg-6 ps-lg-4">
                <c:forEach var="product_item" items="${LIST_PRODUCT_OF_CART}">
                    <div class="d-md-flex border-bottom border-2 pb-4 mb-4" style="border-color: hsl(0,0%, 96%) !important;">
                        <div class="flex-shrink-0 mb-4 mb-md-0">
                            <div class="bg-image hover-overlay ripple shadow-4 rounded-5" data-mdb-ripple-color="light">
                                <a href="/product-details/${product_item.id}" target="_blank">
                                    <img src="${product_item.thumbnail}" style="background: rgb(171,172,171);
                     background: linear-gradient(125deg, rgba(171,172,171,1) 35%, rgba(205,205,205,1) 100%);
                     cursor:pointer;" class="product-image-2" alt="Item 1">
                                    <div class="mask" style="background-color: rgba(255, 255, 255, 0.2);"></div>
                                </a>
                            </div>
                        </div>
                        <div class="flex-grow-1 ms-md-3 d-flex justify-content-between h-100">
                            <div>
                                <p class="h6 mb-2">${product_item.name}</p>
                                <p class="mb-1">$${product_item.price}</p>
                                <p class="mb-0">Quantity: <span>${product_item.quantity}</span></p>
                            </div>
                            <div class="d-flex flex-column ms-3">
                                <p class="mb-0">Sub total</p>
                                <p class="mt-auto mb-0">${product_item.subTotal}</p>
                            </div>
                        </div>
                    </div>
                </c:forEach>
                <c:set var="temporaryPrice" value="0" />

                <c:forEach var="item" items="${LIST_PRODUCT_OF_CART}">
                    <c:set var="temporaryPrice" value="${temporaryPrice + item.subTotal}" />
                </c:forEach>
                <div class="d-flex justify-content-between">
                    <p class="mb-2">Order value</p>
                    <p class="mb-2">$${temporaryPrice}</p>
                </div>
                <div class="d-flex justify-content-between border-bottom border-2 pb-2 mb-4" style="border-color: hsl(0,0%, 96%) !important;">
                    <p>Delivery</p>
                    <p>5$</p>
                </div>
                <div class="d-flex justify-content-between">
                    <p class="h5 mb-5">Total</p>
                    <p class="h5 mb-5">$${temporaryPrice+5}</p>
                </div>
                <div class="small">
                    <p class="text-muted mb-4">Our returns are free and easy. If something isn't quite right, you have 14 days to send it back to us. Read more in our <a class="#!">return and refund policy</a>.</p>
                    <p class="text-warning mb-4">FREE SHIPPING ON ORDERS OVER €100 AND FREE RETURNS</p>
                    <p class="text-muted mb-0">In accordance with our <a class="#!">Privacy Notice</a>, if you are signed in to your store account, we will share personal data from your account with company for order checkout and payment purposes.</p>
                </div>
            </div>
        </div>
    </section>
    <!--Section: Design Block-->

</div>
<!-- Container for demo purpose -->
<script !src="">

    window.addEventListener("DOMContentLoaded",function () {
        const listInfoAddress = [
            {
                typeAddress : "province",
                url : () => "https://vnprovinces.pythonanywhere.com/api/provinces/?basic=true&limit=100"
            },
            {
                typeAddress : "district",
                url : (idProvince) => "https://vnprovinces.pythonanywhere.com/api/districts/?province_id="+idProvince+"&basic=true&limit=100"
            },
            {
                typeAddress : "commune",
                url : (idDistrict) => "https://vnprovinces.pythonanywhere.com/api/wards/?district_id="+idDistrict+"&basic=true&limit=100"
            },
            {
                typeAddress: "hamlet1",
                url : (province,district,commune) => '/address/4?province='+province+'&district='+district+'&commune='+commune
            }
        ]
        const loadDataAddress = (typeAddress,id) => {
            let dataListAddress = [];
            const infoAddress = listInfoAddress.filter((address) => address.typeAddress === typeAddress)[0];
            const url = infoAddress.typeAddress === "province" ? infoAddress.url() : infoAddress.typeAddress === "hamlet1" ? infoAddress.url($("#province").val(),$("#district").val(),$("#commune").val()) : infoAddress.url(id);
            $.ajax({
                url,
                method : "GET",
                success : function (dataResp) {
                    dataListAddress = infoAddress.typeAddress === "hamlet1" ? JSON.parse(dataResp).data : dataResp.results;
                    updateDOMAddressSelect(typeAddress,dataListAddress);
                }
            });
        }
        const updateDOMAddressSelect = (typeAddress,data) => {
            $("#"+typeAddress).empty();
            let dataAdressHtml = '<option value="" selected>-- Select '+typeAddress+' --</option>';
            if (typeAddress === "hamlet1") {
                for (const address of data) {
                    dataAdressHtml += `<option data-id="`+address+`" value="`+address+`">`+address+`</option>`
                }
            } else {
                for (const address of data) {
                    dataAdressHtml += `<option data-id="`+address.id+`" value="`+address.name+`">`+address.name+`</option>`
                }
            }

            $("#"+typeAddress).append(dataAdressHtml);
            $("#"+typeAddress).prop('disabled', false);
        }
        const addEventGetNextAddress = () => {
            for (const address of listInfoAddress) {
                $("#"+address.typeAddress).change(function () {
                    const idValue = $(this).find("option:selected").attr("data-id");
                    const index = listInfoAddress.indexOf(address);
                    if (index+1<listInfoAddress.length) {
                        const nextAddress = listInfoAddress[index+1];
                        loadDataAddress(nextAddress.typeAddress,idValue);
                    }
                })
            }
        }
        addEventGetNextAddress();
        const loadSelect2 = () => {
            for (const address of listInfoAddress) {
                $('#'+address.typeAddress).select2();
            }
        }
        loadDataAddress("province",0);
        loadSelect2();
    //     Form

        $("#formCheckout").submit(function (e) {
            e.preventDefault();
            let phoneExp = /^(^\+251|^251|^0)?(9|7)\d{8}$/;
            if (!phoneExp.test($("#phoneNumber").val())) {
                $("#phoneNumberValidate").text("Please enter valid phone number");
                $('html, body').animate({
                    scrollTop: $('#'+id).offset().top
                }, 150);
                return;
            } else {
                $("#phoneNumberValidate").text("");
            }

            for (const address of listInfoAddress) {
                console.log($('#'+address.typeAddress).val());
            }
            this.submit();

        })
    })
</script>
</body>
</html>
