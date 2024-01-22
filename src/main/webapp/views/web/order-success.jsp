<%--
  Created by IntelliJ IDEA.
  User: HUY
  Date: 1/20/2024
  Time: 12:09 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<div class="vh-100 d-flex justify-content-center align-items-center">
    <div>
        <div class="mb-4 text-center">
            <svg xmlns="http://www.w3.org/2000/svg" class="text-success bi bi-check-circle-fill" width="75" height="75"
                 fill="currentColor" viewBox="0 0 16 16">
                <path
                        d="M16 8A8 8 0 1 1 0 8a8 8 0 0 1 16 0zm-3.97-3.03a.75.75 0 0 0-1.08.022L7.477 9.417 5.384 7.323a.75.75 0 0 0-1.06 1.06L6.97 11.03a.75.75 0 0 0 1.079-.02l3.992-4.99a.75.75 0 0 0-.01-1.05z" />
            </svg>
        </div>
        <div class="text-center">
            <h1>Your order is succes!</h1>
            <p>Order Code : ${orderSlug}</p>
            <p>The order has been placed, thank you for trusting us. Your order will be delivered as soon as possible. You can save your order path to easily track order progress or log in to record your order history </p>
            <a href="/order-details/${orderSlug}" class="btn btn-primary">View order details</a>
            <p class="mt-2">or pay your order online</p>
            <div class="text-center">
                <form method="POST" class="text-center">
                    <div class="accordion accordion-flush mb-4" id="accordionFlushExampleX0">
                        <h5 class="mt-1">Payment method</h5>
                        <select class="form-select selectpicker"  data-live-search="true" aria-label="Select payment method" is="ms-dropdown" name="payment">
                            <option value="cod" selected data-image="/template/shared/images/cod.png" data-description="Pay at your doorstep...">COD</option>
                            <option value="paypal" data-image="/template/shared/images/logoPaypal.png" data-description="Pay and get paid...">PayPal</option>
                            <option value="zalopay" data-image="/template/shared/images/logoZalopay.png" data-description="Touch to tranfer money..."><a href="/payment/zaloPay">Zalo Pay</a></option>
                            <option value="momo" data-image="/template/shared/images/logoMomo.svg" data-description="Pay fast, get cutes gifts...">Momo</option>
                            <option value="vnpay" data-image="/template/shared/images/logoVnpay.png" data-description="All you need...">VnPay</option>
                        </select>
                        <p>The default payment method will be cod, if you want to pay online, choose the method you want</p>
                        <input type="hidden" value="${orderSlug}" name="orderSlug"/>
                    </div>
                    <button class="btn btn-primary btn-sm">Pay</button>
                </form>

            </div>
        </div>
    </div>
</div>
</body>
</html>
