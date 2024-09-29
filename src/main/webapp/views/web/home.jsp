<%--
  Created by IntelliJ IDEA.
  User: HUY
  Date: 12/26/2023
  Time: 11:06 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/common/taglib.jsp" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="vi_VN"/>
<html>
<head>
    <title>Nai Shoes & Sneakers</title>
</head>
<body>

<!-- Start Hero Section -->
<div class="hero" style="
    background-image: url(https://brand.assets.adidas.com/image/upload/f_auto,q_auto,fl_lossy/if_w_gt_1920,w_1920/running_ss24_adizero_introduce_plp_women_masthead_d_de1007a210.jpg);
    background-repeat: no-repeat;
    background-size: 100% 100%;
">
    <div class="container">
        <div class="row justify-content-between">
            <div class="col-lg-5">
                <div class="intro-excerpt">
                    <h1>Nhanh Chóng. Tiện lợi. Tuyệt vời!</h1>
                    <p class="mb-4">Đẩy nhanh tốc độ và bước chân của bạn một cách chính xác để đạt được kỷ lục tốt nhất!</p>
                    <p><a href="/shop" class="btn btn-secondary me-2">Mua ngay</a></p>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- End Hero Section -->
<!-- Start Product Section -->
<div class="product-section">
    <div class="container">
        <div class="row">

            <!-- Start Column 1 -->
            <div class="col-md-12 col-lg-3 mb-5 mb-lg-0">
                <h2 class="mb-4 section-title">Được chế tạo bằng vật liệu tuyệt vời.</h2>
                <p class="mb-4">mang đến cho bạn những sản phẩm chất lượng và đa dạng. Với sự kết hợp hoàn hảo giữa phong cách và sự thoải mái, chúng tôi tự hào giới thiệu những đôi giày phong cách phù hợp với mọi hoàn cảnh. </p>
                <p><a href="/shop" class="btn">Khám phá ngay</a></p>
            </div>
            <!-- End Column 1 -->

            <!-- Start Column 2 -->
            <c:if test="${not empty LIST_MODEL}">
                <c:forEach var="product_item" items="${LIST_MODEL}">
                    <div class="col-12 col-md-4 col-lg-3 mb-5 mb-md-0">
                        <a class="product-item" href="/product-details/${product_item.id}">
                            <img loading="lazy" src="${product_item.thumbnail}" class="img-fluid product-thumbnail">
                            <h3 class="product-title">${product_item.name}</h3>
                            <strong class="product-price"> <fmt:formatNumber type="currency" value="${product_item.price}"/></strong>
                            <span class="icon-cross"><img loading="lazy" src="/template/web/images/cross.svg" class="img-fluid"></span>
                        </a>
                    </div>
                </c:forEach>
            </c:if>

        </div>
    </div>
</div>
<!-- End Product Section -->

<!-- Start Why Choose Us Section -->
<div class="why-choose-section">
    <div class="container">
        <div class="row justify-content-between">
            <div class="col-lg-6">
                <h2 class="section-title">Tại sao nên chọn chúng tôi</h2>
                <p>Vì chúng tôi không chỉ là một trang web bán giày thông thường, mà còn là điểm đến của sự tin cậy và sự đảm bảo chất lượng.</p>

                <div class="row my-5">

                    <div class="col-6 col-md-6">
                        <div class="feature">
                            <div class="icon">
                                <img src="/template/web/images/truck.svg" alt="Image" class="imf-fluid">
                            </div>
                            <h3>Chất lượng</h3>
                            <p>Chúng tôi cam kết đem đến cho khách hàng những sản phẩm chất lượng tốt nhất. Tất cả các sản phẩm trên trang web của chúng tôi đều được lựa chọn kỹ lưỡng từ những thương hiệu uy tín và chất lượng nhất trên thị trường.</p>
                        </div>
                    </div>

                    <div class="col-6 col-md-6">
                        <div class="feature">
                            <div class="icon">
                                <img src="/template/web/images/bag.svg" alt="Image" class="imf-fluid">
                            </div>
                            <h3>Phong phú</h3>
                            <p>Chúng tôi mang đến sự đa dạng và phong phú. Với một bộ sưu tập đa dạng các loại giày từ sneakers thời trang đến giày công sở đẳng cấp, bạn có thể dễ dàng tìm thấy đôi giày phù hợp với phong cách và nhu cầu của mình.</p>
                        </div>
                    </div>

                    <div class="col-6 col-md-6">
                        <div class="feature">
                            <div class="icon">
                                <img src="/template/web/images/support.svg" alt="Image" class="imf-fluid">
                            </div>
                            <h3>Chuyên nghiệp</h3>
                            <p>Chúng tôi cam kết mang đến cho khách hàng trải nghiệm mua sắm trực tuyến tốt nhất. Từ quy trình đặt hàng đơn giản đến dịch vụ khách hàng thân thiện và chuyên nghiệp, chúng tôi luôn lắng nghe và đáp ứng mọi nhu cầu của bạn.</p>
                        </div>
                    </div>

                    <div class="col-6 col-md-6">
                        <div class="feature">
                            <div class="icon">
                                <img src="/template/web/images/return.svg" alt="Image" class="imf-fluid">
                            </div>
                            <h3>Giá tốt nhất</h3>
                            <p>Chúng tôi luôn mang đến cho khách hàng những sản phẩm tốt nhất với giá cả tốt nhất. Tạo trải nghiệm tuyệt vời cho khách hàng.</p>
                        </div>
                    </div>

                </div>
            </div>

            <div class="col-lg-5">
                <div class="img-wrap">
                    <img src="/template/web/images/why-choose-us-img.jpg" alt="Image" class="img-fluid">
                </div>
            </div>

        </div>
    </div>
</div>
<!-- End Why Choose Us Section -->

<!-- Start We Help Section -->
<div class="we-help-section">
    <div class="container">
        <div class="row justify-content-between">
            <div class="col-lg-7 mb-5 mb-lg-0">
                <div class="imgs-grid">
                    <div class="grid grid-1"><img src="/template/web/images/img-grid-1.jpg" alt="Untree.co"></div>
                    <div class="grid grid-2"><img src="/template/web/images/img-grid-2.jpg" alt="Untree.co"></div>
                    <div class="grid grid-3"><img src="/template/web/images/img-grid-3.jpg" alt="Untree.co"></div>
                </div>
            </div>
            <div class="col-lg-5 ps-lg-5">
                <h2 class="section-title mb-4">Chúng tôi mang đến cho bạn những thiết kế hiện đại</h2>
                <p>Chúng tôi tự hào luôn đi đầu xu hướng về các mẫu giày thời thượng.</p>

                <ul class="list-unstyled custom-list my-4">
                    <li>Chúng tôi không chỉ là địa điểm mua sắm giày, mà còn là nguồn cảm hứng cho phong cách cá nhân của bạn. Tại đây, chúng tôi mang đến cho bạn không chỉ là những đôi giày, mà còn là một trải nghiệm mua sắm đích thực.</li>
                    <li>Với một bộ sưu tập đa dạng từ những thương hiệu hàng đầu thế giới, chúng tôi tự tin rằng bạn sẽ tìm thấy đôi giày phản ánh phong cách và cá nhân của bạn. Từ những đôi sneakers thời thượng đến những đôi boots bền bỉ, mỗi sản phẩm đều được chọn lựa kỹ càng để đảm bảo chất lượng và phong cách.</li>
                    <li>Chúng tôi cũng cam kết mang đến cho bạn dịch vụ mua sắm trực tuyến dễ dàng và tiện lợi nhất. Từ quy trình đặt hàng đơn giản đến dịch vụ hỗ trợ khách hàng thân thiện, chúng tôi luôn sẵn lòng đồng hành và hỗ trợ bạn trong mọi thời điểm.</li>
                    <li>Hãy để chúng tôi là người bạn đồng hành đáng tin cậy trên hành trình tìm kiếm đôi giày hoàn hảo cho bạn, với sứ mệnh không chỉ là về giày, mà còn là về phong cách và sự tự tin mỗi ngày.</li>
                </ul>
                <p><a herf="/shop" class="btn">Khám phá ngay</a></p>
            </div>
        </div>
    </div>
</div>
<!-- End We Help Section -->

<!-- Start Popular Product -->
<div class="popular-product">
    <div class="container">
        <div class="row">
            <c:if test="${not empty LIST_MODEL}">
                <c:forEach var="product_item" items="${LIST_MODEL}">
                    <div class="col-12 col-md-6 col-lg-4 mb-4 mb-lg-0">
                        <div class="product-item-sm d-flex">
                            <div class="thumbnail">
                                <img src="${product_item.thumbnail}" alt="Image" class="img-fluid">
                            </div>
                            <div class="pt-3">
                                <h3>${product_item.name}</h3>
                                <p>${product_item.shortDescription}</p>
                                <p><a href="/product-details/${product_item.id}">Đọc thêm</a></p>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </c:if>
        </div>
    </div>
</div>
<!-- End Popular Product -->

<!-- Start Testimonial Slider -->
<div class="testimonial-section">
    <div class="container">
        <div class="row">
            <div class="col-lg-7 mx-auto text-center">
                <h2 class="section-title">Nhận xét của khách hàng</h2>
            </div>
        </div>

        <div class="row justify-content-center">
            <div class="col-lg-12">
                <div class="testimonial-slider-wrap text-center">
                    <div id="testimonial-nav">
                        <span class="prev" data-controls="prev"><span class="fa fa-chevron-left"></span></span>
                        <span class="next" data-controls="next"><span class="fa fa-chevron-right"></span></span>
                    </div>

                    <div class="testimonial-slider">

                        <div class="item">
                            <div class="row justify-content-center">
                                <div class="col-lg-8 mx-auto">

                                    <div class="testimonial-block text-center">
                                        <blockquote class="mb-5">
                                            <p>&ldquo;Tôi đã mua một đôi giày từ trang web này và tôi thực sự rất hài lòng với chất lượng và phong cách của chúng. Đôi giày này không chỉ rất thoải mái mà còn rất đẹp mắt, phản ánh sự chăm chút và tinh tế trong từng đường nét thiết kế. Tôi đã nhận được nhiều lời khen từ bạn bè và đồng nghiệp khi mang chúng. Tôi chắc chắn sẽ quay lại mua sắm ở đây và sẽ giới thiệu cho mọi người biết về trang web này. Cảm ơn bạn đã mang lại cho tôi một trải nghiệm mua sắm tuyệt vời&rdquo;</p>
                                        </blockquote>

                                        <div class="author-info">
                                            <div class="author-pic">
                                                <img src="/template/web/images/person-1.png" alt="Maria Jones" class="img-fluid">
                                            </div>
                                            <h3 class="font-weight-bold">Maria Jones</h3>
                                            <span class="position d-block mb-3">CEO, Co-Founder, XYZ Inc.</span>
                                        </div>
                                    </div>

                                </div>
                            </div>
                        </div>
                        <!-- END item -->

                        <div class="item">
                            <div class="row justify-content-center">
                                <div class="col-lg-8 mx-auto">

                                    <div class="testimonial-block text-center">
                                        <blockquote class="mb-5">
                                            <p>"Đôi giày mà tôi mua từ trang web này thực sự vượt qua mong đợi của tôi. Chất liệu và cách thiết kế rất chất lượng, đem lại sự thoải mái và phong cách cho mỗi bước đi của tôi. Tôi đã trải qua nhiều đôi giày trong quá khứ, nhưng đây thực sự là một trong những đôi tuyệt vời nhất mà tôi từng sở hữu. Rất cảm ơn!"</p>
                                        </blockquote>

                                        <div class="author-info">
                                            <div class="author-pic">
                                                <img src="/template/web/images/person-1.png" alt="Maria Jones" class="img-fluid">
                                            </div>
                                            <h3 class="font-weight-bold">Maria Jones</h3>
                                            <span class="position d-block mb-3">CEO, Co-Founder, XYZ Inc.</span>
                                        </div>
                                    </div>

                                </div>
                            </div>
                        </div>
                        <!-- END item -->

                        <div class="item">
                            <div class="row justify-content-center">
                                <div class="col-lg-8 mx-auto">

                                    <div class="testimonial-block text-center">
                                        <blockquote class="mb-5">
                                            <p>"Tôi đã lạc quan khi đặt hàng từ trang web này, và tôi không hề thất vọng. Đôi giày của tôi không chỉ đẹp mắt mà còn rất êm ái và dễ đi. Tôi rất ấn tượng với sự chuyên nghiệp của dịch vụ khách hàng, họ luôn sẵn lòng giúp đỡ và trả lời mọi thắc mắc của tôi một cách nhanh chóng và hiệu quả. Tôi sẽ chắc chắn quay lại mua sắm ở đây trong tương lai!"</p>
                                        </blockquote>

                                        <div class="author-info">
                                            <div class="author-pic">
                                                <img src="/template/web/images/person-1.png" alt="Maria Jones" class="img-fluid">
                                            </div>
                                            <h3 class="font-weight-bold">Taylor Swift</h3>
                                            <span class="position d-block mb-3">Singer</span>
                                        </div>
                                    </div>

                                </div>
                            </div>
                        </div>
                        <!-- END item -->

                    </div>

                </div>
            </div>
        </div>
    </div>
</div>



</body>
</html>
