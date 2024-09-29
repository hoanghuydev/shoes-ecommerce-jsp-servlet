<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="vi_VN"/>
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
    <title>Nai - Thanh toán</title>
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
                        <h5 class="mb-4">Thông tin của bạn</h5>
                        <div class="row">
                            <div class="col-md-6">
                                <div class="form-outline mb-4">
                                    <label class="form-label" for="fullName">Họ và tên<sup style="color :red;">*</sup></label>
                                    <input type="text" id="fullName" name="fullName" class="form-control" required />
                                </div>
                            </div>
                            <div class="col-md-6">
                                <div class="form-outline mb-4">
                                    <label class="form-label" for="phoneNumber">Số điện thoại<sup style="color :red;">*</sup></label>
                                    <input type="text" id="phoneNumber" name="phoneNumber" class="form-control" required />
                                    <p style="color : red; font-size: 12px" id="phoneNumberValidate"></p>
                                </div>
                            </div>
                        </div>
                        <div class="row justify-content-between">
                            <div class="col-md-4">
                                <label for="province" class="form-label">Tỉnh/ Thành phố<sup style="color :red;">*</sup></label>
                                <select class="form-select" name="province" id="province" required>

                                </select>
                            </div>
                            <div class="col-md-4">
                                <label for="district" class="form-label">Quận/ Huyện<sup style="color :red;">*</sup></label>
                                <select class="form-select" name="district" id="district" required disabled>

                                </select>
                            </div>
                            <div class="col-md-4">
                                <label for="ward" class="form-label">Phường/ Xã<sup style="color :red;">*</sup></label>
                                <select class="form-select" name="commune" id="ward" required disabled>

                                </select>
                            </div>
                        </div>
                        <div class="row justify-content-between mt-4">
                            <div class="col-md-4">
                                <label for="hamlet1" class="form-label">Ấp/ Đường<sup style="color :red;">*</sup></label>
                                <select class="form-select" name="hamlet1" id="hamlet1" required disabled>

                                </select>
                            </div>
                            <div class="col-md-8">
                                <label for="hamlet2" class="form-label">Thêm địa chỉ</label>
                                <input class="form-control" type="text" id="hamlet2" name="hamlet2" placeholder="Thêm địa chỉ...">
                                <p>Ví dụ : Số nhà 28/12</p>
                            </div>

                        </div>
                        <div class="form-group">
                            <label for="note" class="form-label">Ghi chú</label>
                            <input class="form-control" type="text" id="note" name="note" placeholder="Ghi chú của bạn...">
                        </div>

                        <c:forEach var="product_item" items="${LIST_PRODUCT_OF_CART}">
                            <input type="hidden" name="product[]" value="${product_item.id}-${product_item.sizeId}-${product_item.quantity}">
                        </c:forEach>
                        <div id="voucherApply"></div>
                        <div class="pt-1 my-3">
                            <button type="submit" class="btn btn-dark btn-lg px-5">Thanh toán đơn hàng</button>
<%--                            <p class="small text-muted mt-4 mb-0">Bằng cách nhấp vào "Tiếp tục với PayPal", tôi xác nhận rằng tôi đã đọc <a href="#!">Privacy Notice</a> và <a href="#!">Cookie Notice</a>. I agree to the <a href="#!">terms & conditions</a> of the store. "I also accept that the store will process my personal data to manage my order, in accordance with the store's <a href="#!">privacy notice</a>"</p>--%>
                                <p class="small text-muted mt-4 mb-0">Bằng cách nhấp vào "Tiếp tục với PayPal", tôi xác nhận rằng tôi đã đọc <a href="#!">Thông báo về Quyền riêng tư</a> và <a href="#!">Thông báo về Cookie</a>. Tôi đồng ý với <a href="#!">điều khoản & điều kiện</a> của cửa hàng. "Tôi cũng chấp nhận rằng cửa hàng sẽ xử lý dữ liệu cá nhân của tôi để quản lý đơn hàng, theo đúng <a href="#!">thông báo quyền riêng tư</a> của cửa hàng."</p>

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
                                <p class="mb-1"><fmt:formatNumber type="currency" value="${product_item.price}"/></p>
                                <p class="mb-0">Số lượng: <span>${product_item.quantity}</span></p>
                            </div>
                            <div class="d-flex flex-column ms-3">
                                <p class="mb-0">Giá</p>
                                <p class="mt-auto mb-0"><fmt:formatNumber type="currency" value="${product_item.subTotal}"/></p>
                            </div>
                        </div>
                    </div>
                </c:forEach>
                <c:set var="temporaryPrice" value="0" />

                <c:forEach var="item" items="${LIST_PRODUCT_OF_CART}">
                    <c:set var="temporaryPrice" value="${temporaryPrice + item.subTotal}" />
                </c:forEach>
                <div class="d-flex justify-content-between">
                    <div class="d-flex gap-3">
                        <i class="fa-solid fa-ticket my-auto" style="color : red"></i>
                        <p class="fw-bold fs-6 my-auto">Nai Voucher</p>
                    </div>
                    <p class="text-blue " style="color: #0c63e4; cursor: pointer" data-bs-toggle="modal" data-bs-target="#voucherModal">Chọn voucher</p>
<%--                    Modal voucher--%>
                    <div class="modal fade" id="voucherModal" tabindex="-1" aria-labelledby="voucherModalLabel" aria-hidden="true">
                        <div class="modal-dialog">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <h5 class="modal-title" id="exampleModalLabel">Chọn Nai Voucher</h5>
                                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                </div>
                                <div class="modal-body overflow-auto" style="max-height: 300px">
                                    <c:forEach var="voucher_item" items="${LIST_VOUCHER}">
                                        <%--Voucher item--%>
                                        <div class="d-flex border-1 px-4 py-2 shadow-lg my-3 voucher-item" data-id-voucher="${voucher_item.id}" data-bs-dismiss="modal" style="border: 0.7px solid #8f8f8f; cursor: pointer">
                                            <div>
                                                <strong class="fs-4">${voucher_item.name}</strong>
                                                <p >Mã giảm giá : <strong>${voucher_item.code}</strong></p>
                                                <fmt:parseDate var="endDate" value="${voucher_item.endDate}" pattern="yyyy-MM-dd HH:mm:ss.S" />
                                                <fmt:formatDate value="${endDate}" pattern="H:m d/M/yyyy" var="formattedEndDate" />
                                                <p class="p-0">HSD : ${formattedEndDate} <span><a class="openModalConditionContent" data-bs-toggle="modal" href="#modalConditionContent" data-voucher-id="${voucher_item.id}" role="button" style="color: #0c63e4">Điều kiện</a></span></p>
                                            </div>
                                        </div>
                                        <%--End Voucher item--%>
                                    </c:forEach>

                                </div>
                                <div class="modal-footer">
                                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Trở lại</button>
                                    <button type="button" class="btn btn-primary">Ok</button>
                                </div>
                            </div>
                        </div>
                    </div>
<%--                    End modal voucher--%>
                </div>
                <div class="d-flex justify-content-between">
                    <p class="mb-2">Giá trị đơn hàng</p>
                    <p class="mb-2"> <fmt:formatNumber type="currency" value="${temporaryPrice}"/></p>
                </div>
                <div class="d-flex justify-content-between" id="orderVoucherDesc">

                </div>
                <div class="d-flex justify-content-between border-bottom border-2 pb-2 mb-4" style="border-color: hsl(0,0%, 96%) !important;">
                    <p>Vận chuyển</p>
                    <p> <fmt:formatNumber type="currency" value="20000"/></p>
                </div>

                <div class="d-flex justify-content-between">
                    <p class="h5 mb-5">Tổng</p>
                    <p class="h5 mb-5" id="totalPrice"> <fmt:formatNumber type="currency" value="${temporaryPrice+20000}"/></p>
                </div>
                <div class="small">
<%--                    <p class="text-muted mb-4">Our returns are free and easy. If something isn't quite right, you have 14 days to send it back to us. Read more in our <a class="#!">return and refund policy</a>.</p>--%>
<%--                    <p class="text-warning mb-4">FREE SHIPPING ON ORDERS OVER €100 AND FREE RETURNS</p>--%>
<%--                    <p class="text-muted mb-0">In accordance with our <a class="#!">Privacy Notice</a>, if you are signed in to your store account, we will share personal data from your account with company for order checkout and payment purposes.</p>--%>
                        <p class="text-muted mb-4">Chính sách đổi trả của chúng tôi miễn phí và dễ dàng. Nếu có điều gì không hoàn hảo, bạn có 14 ngày để gửi lại sản phẩm cho chúng tôi. Đọc thêm trong <a class="#!">chính sách đổi trả và hoàn tiền</a> của chúng tôi.</p>

                        <p class="text-muted mb-0">Theo đúng <a class="#!">Thông báo về Quyền riêng tư</a> của chúng tôi, nếu bạn đã đăng nhập vào tài khoản của cửa hàng, chúng tôi sẽ chia sẻ dữ liệu cá nhân từ tài khoản của bạn với công ty để thanh toán và thanh toán đơn hàng.</p>

                </div>
            </div>
        </div>
    </section>
    <!--Section: Design Block-->
    <div class="modal fade" id="modalConditionContent" tabindex="-1" aria-labelledby="modalConditionContent" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h1 class="modal-title fs-5" id="conditionContentLabel">Chi tiết mã giảm giá</h1>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body" id="conditionContentBody">

                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-primary">Ok</button>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- Container for demo purpose -->
<script !src="">
    const totalPrice = ${temporaryPrice+20000};
    const reviver = (key, value) => {
        return value;
    };
    const listVoucher = JSON.parse('${JSON_LIST_VOUCHER}',reviver);
    console.log(listVoucher)
    const listProduct = JSON.parse(`${JSON_LIST_PRODUCT_OF_CART}`,reviver)
    console.table(listProduct)
    window.addEventListener("DOMContentLoaded",function () {
        $(".openModalConditionContent").on("click", function (event) {
            event.stopPropagation();
            const idVoucher = $(this).attr("data-voucher-id");
            const voucherInfo = listVoucher.find((voucher) => voucher.id == idVoucher);
            const contentVoucher = '<div>' + voucherInfo.content + '</div>';
            $("#conditionContentBody").html(contentVoucher);
            $("#conditionContentLabel").text("Chi tiết mã giảm giá "+voucherInfo.name);
        });
        $('.voucher-item').click(function () {
            const idVoucher = $(this).attr("data-id-voucher")
            $.ajax({
                url : '/ajax/voucher/validate',
                method : "POST",
                headers: {
                    'Content-Type': 'application/json'
                },
                data : JSON.stringify({
                    idVoucher,
                    products : listProduct
                }),
                beforeSend: function() {
                    if ($("#voucherApply")) $("#voucherApply").empty();
                    $("#orderVoucherDesc").empty();
                    swal({
                        title: "Đang xử lý",
                        text: "Đang kiểm tra điều kiện sử dụng voucher...",
                        icon: "info",
                    });
                },
                success : function (data) {
                    swal.close();
                    if (data.canUseVoucher) {
                        swal({
                            title: "Bạn có thể sử dụng voucher này",
                            text: "Nhấn ok để sử dụng voucher",
                            icon: "success",
                            button: "Ok!",
                        });
                        $("#totalPrice").text(new Intl.NumberFormat('vi-VN', { style: 'currency', currency: 'VND' }).format(totalPrice*(1-data.voucher.discount/100)));
                        $("#orderVoucherDesc").html(`
                            <p>Giảm giá</p>
                            <p>-`+data.voucher.discount+`%</p>
                        `)
                       if ($("#voucherApply")) $("#voucherApply").empty();
                        $("#voucherApply").append(`
                        <div class="px-4 py-2 shadow-sm my-3 bg-white rounded-3"  style="border: 0.4px solid #8f8f8f;">
                           <div class="d-flex justify-content-between">
                               <strong class="fs-5">`+data.voucher.name+`</strong>
                               <strong style="color : #50d106">-`+data.voucher.discount+`%</strong>
                           </div>
                           <p class="m-0 p-0">Mã giảm giá : <strong>`+data.voucher.code+`</strong></p>
                           <input type="hidden" name="voucherApply" value="`+data.voucher.id+`"/>
                        </div>
                        `)
                    } else {
                        if ($("#voucherApply")) $("#voucherApply").empty();
                        swal({
                            title: "Lỗi",
                            text: "Bạn chưa thỏa mãn các điều kiện để sử dụng voucher này hoặc điều kiện về thời gian voucher. Vui lòng đọc lại điều kiện sử dụng voucher",
                            icon: "error",
                            button: "Thoát",
                        });
                    }
                },
                error : function (err) {
                    $("#orderVoucherDesc").empty();
                    swal.close();
                    swal({
                        title: "Lỗi",
                        text: "Đã có lỗi xảy ra vui lòng thử lại",
                        icon: "error",
                        button: "Thoát",
                    });
                },


            })
        })
        const listInfoAddress = [
            {
                typeAddress : "province",
                url : () => "https://vapi.vnappmob.com/api/province/"
            },
            {
                typeAddress : "district",
                url : (idProvince) => "https://vapi.vnappmob.com/api/province/district/"+idProvince
            },
            {
                typeAddress : "ward",
                url : (idDistrict) => "https://vapi.vnappmob.com/api/province/ward/"+idDistrict
            },
            {
                typeAddress: "hamlet1",
                url : (province,district,commune) => '/address/4?province='+province+'&district='+district+'&commune='+commune
            }
        ]
        const loadDataAddress = (typeAddress,id) => {
            let dataListAddress = [];
            const infoAddress = listInfoAddress.filter((address) => address.typeAddress === typeAddress)[0];
            const url = infoAddress.typeAddress === "province" ? infoAddress.url() : infoAddress.typeAddress === "hamlet1" ? infoAddress.url($("#province").val(),$("#district").val(),$("#ward").val()) : infoAddress.url(id);
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
                    dataAdressHtml += `<option data-id="`+address[typeAddress+"_id"]+`" value="`+address[typeAddress+"_name"]+`">`+address[typeAddress+"_name"]+`</option>`
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
            let phoneExp = /^(0[3-9])+([0-9]{8})$/;
            if (!phoneExp.test($("#phoneNumber").val())) {
                $("#phoneNumberValidate").text("SĐT không hợp lệ");
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
