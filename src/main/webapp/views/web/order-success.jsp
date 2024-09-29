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
            <h1>Đơn hàng của bạn đã thành công!</h1>
            <p>Mã đặt hàng : ${orderSlug}</p>
            <p>Đơn hàng đã được đặt, cảm ơn bạn đã tin tưởng chúng tôi. Đơn hàng của bạn sẽ được giao trong thời gian sớm nhất. Bạn có thể lưu đường dẫn đặt hàng để dễ dàng theo dõi tiến trình đặt hàng hoặc đăng nhập để ghi lại lịch sử đơn hàng. </p>
            <a href="/order-details/${orderSlug}" class="btn btn-primary">xem chi tiết đơn hàng</a>
            <p class="mt-2">hoặc thanh toán đơn hàng trực tuyến</p>
            <div class="text-center">
                <form method="POST" class="text-center">
                    <div class="accordion accordion-flush mb-4" id="accordionFlushExampleX0">
                        <h5 class="mt-1">Phương thức thanh toán</h5>
                        <select class="form-select selectpicker"  data-live-search="true" aria-label="Select payment method" is="ms-dropdown" name="payment">
                            <option value="cod" selected data-image="/template/shared/images/cod.png" data-description="Pay at your doorstep...">COD</option>
                            <option value="paypal" data-image="/template/shared/images/logoPaypal.png" data-description="Pay and get paid...">PayPal</option>
                            <option value="zalopay" data-image="/template/shared/images/logoZalopay.png" data-description="Touch to tranfer money..."><a href="/payment/zaloPay">Zalo Pay</a></option>
                            <option value="momo" data-image="/template/shared/images/logoMomo.svg" data-description="Pay fast, get cutes gifts...">Momo</option>
                            <option value="vnpay" data-image="/template/shared/images/logoVnpay.png" data-description="All you need...">VnPay</option>
                        </select>
                        <p>Phương thức thanh toán mặc định sẽ là cod, nếu bạn muốn thanh toán trực tuyến thì hãy chọn phương thức bạn muốn</p>
                        <input type="hidden" value="${orderSlug}" name="orderSlug"/>
                    </div>
                    <button class="btn btn-primary btn-sm">Thanh toán</button>
                </form>

            </div>
        </div>
    </div>
</div>
</body>
</html>
