<%--
  Created by IntelliJ IDEA.
  User: HUY
  Date: 12/30/2023
  Time: 12:38 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/common/taglib.jsp" %>
<html>
<head>
    <title>Nai - Cart</title>
</head>
<body>
<div class="container my-5">
    <!-- Section: Cart -->
    <section class="">
        <div class="row gx-lg-5">
            <c:if test="${not empty LIST_PRODUCT_OF_CART}">
                <div class="col-lg-8 mb-4 mb-md-0">
                    <!-- Section: Product list -->
                    <section class="mb-5">
                        <!-- Single item -->
                        <c:forEach var="product_item" items="${LIST_PRODUCT_OF_CART}" varStatus="loop">
                            <div class="row border-bottom mb-4" id="productCartItem${loop.index}">
                                <div class="col-md-2 mb-4 mb-md-0">
                                    <div class="
                            bg-image
                            rounded-5
                            mb-4
                            overflow-hidden
                            d-block
                            " data-mdb-ripple-init data-ripple-color="light"
                                         style="background: rgb(171,172,171);
                    background: linear-gradient(90deg, rgba(171,172,171,1) 35%, rgba(205,205,205,1) 100%);
                    cursor:pointer;"
                                    >
                                        <img src="${product_item.thumbnail}" class="w-100" alt="" />
                                        <a href="/product-details/${product_item.id}">
                                            <div class="hover-overlay">
                                                <div class="mask" style="background-color: hsla(0, 0%, 98.4%, 0.2)"></div>
                                            </div>
                                        </a>
                                    </div>
                                </div>

                                <div class="col-md-8 mb-4 mb-md-0">
                                    <a href="/product-details/${product_item.id}" style="text-decoration: none" class="fw-bold">${product_item.name}</a>
                                    <p class="mb-1">
                                        <span class="text-muted me-2">Size:</span><span>${product_item.sizeName}</span>
                                    </p>
                                    <p class="mb-4">
                                        <a href="" id="removeCart"  class="text-muted pe-3 border-end" data-index="${loop.index}" data-product-id="${product_item.id}" data-size-id="${product_item.sizeId}"><small><i
                                                class="fas fa-trash me-2"></i>Remove</small></a>
                                    </p>
                                </div>

                                <div class="col-md-2 mb-4 mb-md-0">
                                    <div class="form-outline mb-4" data-mdb-input-init>
                                        <input type="number" id="quantityProduct${product_item.id}"  class="form-control quantityProduct" value="${product_item.quantity}" data-index="${loop.index}" data-product-id="${product_item.id}" data-size-id="${product_item.sizeId}" min="1" max="20" />
                                        <label class="form-label" for="quantityProduct${product_item.id}">Quantity</label>
                                    </div>

                                    <h5 class="mb-2">
                                        <p class="align-middle" >$<span id="subTotal${loop.index}">${product_item.subTotal}</span></p>
                                    </h5>
                                </div>
                            </div>
                        </c:forEach>
                        <!-- Single item -->
                    </section>
                    <!-- Section: Product list -->

                    <!-- Section: Details -->
                    <section class="">
                        <div class="mb-5">
                            <p class="text-primary">
                                <i class="fas fa-info-circle mr-1"></i> Do not delay the
                                purchase, adding items to your cart does not mean booking
                                them.
                            </p>
                        </div>
                        <div>
                            <h5 class="mb-4">We accept</h5>

                            <img class="mr-2" width="45px"
                                 src="https://mdbootstrap.com/wp-content/plugins/woocommerce-gateway-stripe/assets/images/visa.svg"
                                 alt="Visa" />
                            <img class="mr-2" width="45px"
                                 src="https://mdbootstrap.com/wp-content/plugins/woocommerce-gateway-stripe/assets/images/amex.svg"
                                 alt="American Express" />
                            <img class="mr-2" width="45px"
                                 src="https://mdbootstrap.com/wp-content/plugins/woocommerce-gateway-stripe/assets/images/mastercard.svg"
                                 alt="Mastercard" />
                            <img class="mr-2" width="45px"
                                 src="https://mdbootstrap.com/wp-content/plugins/woocommerce/includes/gateways/paypal/assets/images/paypal.png"
                                 alt="PayPal acceptance mark" />
                        </div>
                    </section>
                    <!-- Section: Details -->
                </div>

                <div class="col-lg-4 mb-4 mb-md-0">
                    <!-- Section: Summary -->
                    <section class="shadow-4 p-4 rounded-5 mb-4">
                        <h5 class="mb-5">The total amount of</h5>
                        <c:set var="temporaryPrice" value="0" />

                        <c:forEach var="item" items="${LIST_PRODUCT_OF_CART}">
                            <c:set var="temporaryPrice" value="${temporaryPrice + item.subTotal}" />
                        </c:forEach>
                        <div class="d-flex justify-content-between mb-3">
                            <span>Temporary price</span>
                            <p>
                                $<span id="temporaryPrice">${temporaryPrice}</span>
                            </p>
                        </div>
                        <div class="d-flex justify-content-between">
                            <span>Shipping </span>
                            <span>$5</span>
                        </div>
                        <hr class="my-4" />
                        <div class="d-flex justify-content-between fw-bold mb-5">
                            <span>The total amount of (including VAT) </span>
                            <p>
                                $<span id="totalPrice">${temporaryPrice+5}</span>
                            </p>

                        </div>

                        <a href="/checkout" type="button" class="btn btn-primary btn-rounded w-100" data-mdb-ripple-init>
                            Got to checkout
                        </a>
                    </section>
                    <!-- Section: Summary -->

                    <!-- Section: Summary -->
                        <%--                <section class="shadow-4 p-4 rounded-5">--%>
                        <%--                    <h5 class="mb-4">Apply promo code</h5>--%>

                        <%--                    <div class="d-flex align-items-center">--%>
                        <%--                        <input type="text" class="form-control rounded me-1" placeholder="Promo code" />--%>
                        <%--                        <button type="button" class="btn btn-link btn-rounded overflow-visible" data-mdb-ripple-init>--%>
                        <%--                            Apply--%>
                        <%--                        </button>--%>
                        <%--                    </div>--%>
                        <%--                </section>--%>
                    <!-- Section: Summary -->
                </div>
            </c:if>
            <c:if test="${empty LIST_PRODUCT_OF_CART}">
                <div class="mx-auto" style="width: fit-content">Cart is empty! <a href="/shop">Shop now</a></div>
            </c:if>

        </div>
    </section>
    <!-- Section: Cart -->
</div>
<script !src="">
    window.addEventListener("DOMContentLoaded",function () {
        // Update quantity product cart item
        let changeQuantityTimeout;
        const updateDOMQuantity = (data,index,oldSubTotal) => {
            data = JSON.parse(data);
            const subTotal = data.subTotal;
            const temporaryPrice = parseFloat($("#temporaryPrice").text()) - oldSubTotal + (data.subTotal);
            const totalPrice = parseFloat($("#totalPrice").text()) - oldSubTotal + (data.subTotal);
            $("#subTotal"+index).text(subTotal);
            $("#temporaryPrice").text(temporaryPrice);
            $("#totalPrice").text(totalPrice);
        }
        const updateQuantity = (productId,sizeId,quantity,index,oldSubTotal)=> {
            $.ajax({
                url : "/ajax/cart",
                method : "PUT",
                data : JSON.stringify({
                    productId,
                    sizeId,
                    quantity
                }),
                success : function (data) {
                    updateDOMQuantity(data,index,oldSubTotal);
                },
                error : function (error) {
                    showToast("Error : "+error.error_desc+"","danger");
                }
            })
        }
        $('.quantityProduct').change(function () {
            clearTimeout(changeQuantityTimeout);
            changeQuantityTimeout = setTimeout(()=> {
                const productId = parseInt($(this).attr("data-product-id"));
                const sizeId = parseInt($(this).attr("data-size-id"));
                const quantity = parseInt($(this).val());
                const index = $(this).attr("data-index");
                const oldSubTotal = parseFloat($("#subTotal"+index).text());
                updateQuantity(productId,sizeId,quantity,index,oldSubTotal);
            },700);
        })
        // Delete product cart item
        const deleteProductCartItem = (productId,sizeId,quantity,index) => {
            $.ajax({
                url : "/ajax/cart",
                method: "DELETE",
                data : JSON.stringify({
                    productId,
                    sizeId,
                    quantity
                }),
                success : function (data) {
                   window.location.reload();
                },
                error : function (errorData) {
                    console.log(errorData);
                    showToast("Error : "+errorData.error_desc+"","danger");
                }
            })
        }
        $("#removeCart").click(function (e) {
            e.preventDefault();
            const productId = parseInt($(this).attr("data-product-id"));
            const sizeId = parseInt($(this).attr("data-size-id"));
            const quantity = 1;
            const index = $(this).attr("data-index");
            console.log("Index" + index);
            deleteProductCartItem(productId,sizeId,quantity,index);
        })

    })
</script>
</body>
</html>
