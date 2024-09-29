<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/common/taglib.jsp" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="vi_VN"/>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>${MODEL.name}</title>
</head>
<body>
<!-- Container for demo purpose -->
<div class="container my-5 pb-3">

    <!--Section: Design Block-->

    <!-- content -->
    <section class="py-5">
        <div class="container">
            <div class="row gx-5">
                <aside class="col-lg-6 allImageProduct">
                    <div class="border rounded-4 mb-3 d-flex justify-content-center mainDivImageProduct">
                        <c:forEach var="image_product" items="${LIST_IMAGE}" varStatus="loop">
                            <c:if test="${loop.index eq 0}">
                                <a data-fslightbox="mygalley" class="rounded-4 preview-image-product " target="_blank" data-type="image" id="mainImageProduct${loop.index}">
                                    <input type="checkbox" class="zoomImageCheck" id="zoomImageCheck${loop.index}">
                                    <label for="zoomImageCheck${loop.index}">
                                        <img style="max-width: 100%; max-height: 100vh; margin: auto;  object-fit: cover; object-position: center; cursor: zoom-in"  class="rounded-4 fit" src="${image_product.imageUrl}" />
                                    </label>
                                </a>
                            </c:if>
                            <c:if test="${loop.index ne 0}">
                                <a style="display: none" data-fslightbox="mygalley" class="rounded-4 preview-image-product " target="_blank" data-type="image" id="mainImageProduct${loop.index}">
                                    <input type="checkbox" class="zoomImageCheck" id="zoomImageCheck${loop.index}">
                                    <label for="zoomImageCheck${loop.index}">
                                        <img style="max-width: 100%; max-height: 100vh; margin: auto;  object-fit: cover; object-position: center; cursor: zoom-in"  class="rounded-4 fit" src="${image_product.imageUrl}" />
                                    </label>
                                </a>
                            </c:if>
                        </c:forEach>

<%--                        <a style="display: none" data-fslightbox="mygalley" class="rounded-4 preview-image-product " target="_blank" data-type="image"  id="mainImageProduct2">--%>
<%--                            <input type="checkbox" class="zoomImageCheck" id="zoomImageCheck2">--%>
<%--                            <label for="zoomImageCheck2">--%>
<%--                                <img style="max-width: 100%; max-height: 100vh; margin: auto;  object-fit: cover; object-position: center; cursor: zoom-in"  class="rounded-4 fit" src="https://static.nike.com/a/images/t_PDP_1728_v1/f_auto,q_auto:eco/579bf166-69db-4dfc-81be-3077434f456d/sabrina-1-west-coast-roots-basketball-shoes-f8jr2H.png" />--%>
<%--                            </label>--%>
<%--                        </a>--%>
<%--                        <a style="display: none" data-fslightbox="mygalley" class="rounded-4 preview-image-product " target="_blank" data-type="image" id="mainImageProduct3" >--%>
<%--                            <input type="checkbox" class="zoomImageCheck" id="zoomImageCheck3">--%>
<%--                            <label for="zoomImageCheck3">--%>
<%--                                <img style="max-width: 100%; max-height: 100vh; margin: auto;  object-fit: cover; object-position: center; cursor: zoom-in" class="rounded-4 fit" src="https://static.nike.com/a/images/t_PDP_1728_v1/f_auto,q_auto:eco/a161ba37-e866-4e5f-8221-529add4d03ad/sabrina-1-west-coast-roots-basketball-shoes-f8jr2H.png" />--%>
<%--                            </label>--%>
<%--                        </a>--%>
                    </div>
                    <div class="d-flex justify-content-center mb-3 otherImageProduct">
                        <c:forEach var="image_product" items="${LIST_IMAGE}" varStatus="loop">
                            <a data-fslightbox="mygalley" class="border mx-1 rounded-2 item-thumb" target="_blank" data-bs-mainImageProduct="mainImageProduct${loop.index}" data-type="image">
                                <img width="60" height="60" class="rounded-2"  style="object-fit: cover; object-position: center;cursor: pointer" src="${image_product.imageUrl}" />
                            </a>
                        </c:forEach>
                    </div>
                    <!-- thumbs-wrap.// -->
                    <!-- gallery-wrap .end// -->
                </aside>
                <main class="col-lg-6">
                    <div class="ps-lg-3">
                        <h4 class="title text-dark">
                            ${MODEL.name}
                        </h4>
                        <div class="d-flex flex-row my-3">
                            <div class="text-warning d-flex mb-1 me-2">
                                <div class="averageRatingStar">
                                    <i class="fa fa-star text-primary"></i>
                                    <i class="fa fa-star text-primary"></i>
                                    <i class="fa fa-star text-primary"></i>
                                    <i class="fa fa-star text-primary"></i>
                                    <i class="fa fa-star text-primary"></i>
                                </div>
                                <span class="ms-1 text-primary averageRating">5</span>
                            </div>
<%--                            <span class="text-muted">${CATEGORY.name}</span>--%>
                        </div>
                        <p class="text-muted" style="font-size: 16px; font-weight: 600;">Loại: <span>${CATEGORY.name}</span></p>
                        <div class="mb-3">

                            <span class="product-price h5"><fmt:formatNumber type="currency" value="${MODEL.price}"/> đ</span>
                            <span class="text-muted">/ đôi</span>

                        </div>

                        <c:out value="${MODEL.content}" escapeXml="false"/>
                        <hr />
                            <div class="row mb-4">
                                <div class="col-md-4 col-6">
                                    <label class="mb-2">Kích cỡ</label>
                                    <select class="form-select border border-secondary" name="sizeId" id="sizeId" style="height: 35px;" required>
                                        <c:forEach var="size_item" items="${LIST_SIZE}">
                                            <option value="${size_item.id}">${size_item.name}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <input type="hidden" name="productId" value="${MODEL.id}">
                            </div>
                        <div class="d-flex justify-content-between">
                            <div id="inOfStock">
                                <button class="btn btn-warning shadow-0" id="buyNow"> Mua ngay </button>
                                <button  class="btn btn-primary shadow-0" id="addToCart"> <i class="me-1 fa fa-shopping-basket"></i> Thêm vào giỏ hàng </button>
                            </div>
                            <div id="outOfStock" class="hidden">
                                <button class="btn shadow-0" style="opacity: 0.8; cursor: no-drop;"> Out of stock </button>
                            </div>
<%--                            <a href="#" class="btn btn-light border border-secondary py-2 icon-hover px-3"> <i class="me-1 fa fa-heart fa-lg"></i> Lưu </a>--%>

                        </div>
                    </div>
                </main>
            </div>
        </div>
    </section>
    <!-- content -->
    <!--Section: Design Block-->
    <section class="mb-5">
        <div class="row justify-content-center mx-auto">
            <div class="col" style="z-index: 10;">
                <h4 class="fw-bold mb-2">Đánh giá (<span id="amountOpinion">0</span>)</h4>
                <div class="d-flex align-items-center mb-4">
                    <h5 class="mb-0" ><span class="averageRating" id="averageRating">5</span><span class="text-muted">/5</span></h5>
                    <div class="ms-2 averageRatingStar">
                        <i class="fa fa-star text-primary"></i>
                        <i class="fa fa-star text-primary"></i>
                        <i class="fa fa-star text-primary"></i>
                        <i class="fa fa-star text-primary"></i>
                        <i class="fa fa-star text-primary"></i>
                    </div>
                </div>
                <div class="opinion-list">

                </div>
                <div class="loadMoreOpinion" style="cursor: pointer">
                    <p>Load more<i class=" ms-2 fa-solid fa-chevron-down"></i></p>
                </div>

            </div>
        </div>
    </section>
    <c:if test="${not empty USER_MODEL}">
        <div class="mt-2 ps-3">
            <a class="btn btn-warning mb-0" id="toggleAddProduct" data-bs-toggle="collapse" href="#formNewProduct"><i class="material-icons text-sm"></i>&nbsp;&nbsp;Write a review</a>
            <div class="collapse multi-collapse my-3" id="formNewProduct">
                <div class="card py-3 px-4 rounded-3" >
                    <form method="POST" id="addNewOpinion">
                            <%--                    Star--%>
                        <div class="form-group mb-3">
                            <label class="text-black">Đánh giá tổng thể<span style="color :red"><sup>*</sup></span></label>
                            <div class="row mt-1">
                                <div class="col-md-12">
                                    <div class="stars">
                                        <input class="star star-5" value="5" id="star-5" type="radio" name="star" required/>

                                        <label class="star star-5" for="star-5"></label>

                                        <input class="star star-4" value="4" id="star-4" type="radio" name="star"/>

                                        <label class="star star-4" for="star-4"></label>

                                        <input class="star star-3" value="3" id="star-3" type="radio" name="star"/>

                                        <label class="star star-3" for="star-3"></label>

                                        <input class="star star-2" value="2" id="star-2" type="radio" name="star"/>

                                        <label class="star star-2" for="star-2"></label>

                                        <input class="star star-1" value="1" id="star-1" type="radio" name="star"/>

                                        <label class="star star-1" for="star-1"></label>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="form-group mb-5">
                            <label class="text-black" for="contentOpinion">Đánh giá của bạn<span style="color :red"><sup>*</sup></span></label>
                            <textarea class="form-control" id="contentOpinion" name="content" rows="5" required></textarea>
                        </div>
                        <div class="form-group mb-5">
                            <label class="text-black" for="titleOpinion">Tiêu đề của bạn<span style="color :red"><sup>*</sup></span></label>
                            <input type="text" class="form-control" id="titleOpinion" name="title" required>
                        </div>

                        <button class="btn btn-primary mt-2" type="submit">Gửi</button>
                    </form>
                </div>
            </div>
        </div>
<%--        Modal Soft Delete--%>
        <div class="modal fade" id="deleteModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
            <div class="modal-dialog modal-dialog-centered">
                <div class="modal-content">
                    <div class="modal-header">
                        <h1 class="modal-title fs-5" id="deleteModalLabel">Xóa đánh giá của bạn?</h1>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        Bạn có chắc chắn xóa không? Hành động này không thể hoàn tác.
                    </div>
                    <div class="modal-footer" style="background-color: #e5e5e5">
                        <button type="button" class="btn btn-white-outline" data-bs-dismiss="modal">Đóng</button>
                        <input type="hidden" id="idOpinionDelete"/>
                        <button type="button" id="btnDeleteOpinion" class="btn btn-secondary" data-bs-dismiss="modal">Xóa</button>
                    </div>
                </div>
            </div>
        </div>
    </c:if>
    <script !src="">
        window.addEventListener("DOMContentLoaded",function (){
            // Update total view product to stat
            $.ajax({
                url : '/product/view/${MODEL.id}',
                method : 'POST'
            })
            // Update price
            const listProductSize = JSON.parse(('${LIST_PRODUCT_SIZE}'));
            function updatePriceWhenChangeSize() {
                const sizeId =  $("#sizeId").val();
                const productSizePrice =listProductSize.filter((productSize) => productSize.sizeId+'' ==sizeId)[0];
                console.log(productSizePrice)
                $(".product-price").text(new Intl.NumberFormat('vi-VN', { style: 'currency', currency: 'VND' }).format(productSizePrice.price));
            }
            $("#sizeId").on("change",function () {
                updatePriceWhenChangeSize();
                checkQuantity();
            })
            updatePriceWhenChangeSize();


            // checking quantity in DB
            const checkQuantity = ()=> {
                const sizeId = parseInt($("#sizeId").val());
                $.ajax({
                    url : '<c:url value="/ajax/product-quantity"/>',
                    type : 'GET',
                    data : {
                        productId: ${MODEL.id},
                        sizeId: sizeId
                    },
                    success : function (data) {
                        const inStockElement = $('#inOfStock');
                        const outStockElement = $('#outOfStock');
                        if(data > 0) {
                            inStockElement.show();
                            outStockElement.hide();
                        }else {
                            outStockElement.show();
                            inStockElement.hide();
                        }
                    },
                    error : function (e) {
                        console.log('error', e)
                    }
                })
            }
            checkQuantity();



            const maxPageItemOpinion = 3;
            let totalCurrentItem = 0;
            let nextPage = 1;
            //     Add to cart
            $('#addToCart').click(function () {
                $.ajax({
                    url : '/ajax/cart',
                    method : 'POST',
                    data : JSON.stringify({
                        productId : ${MODEL.id},
                        sizeId : parseInt($('#sizeId').val()),
                        quantity : 1,
                    }),
                    success : function (data) {
                        showToast("Added product to your cart.Check here!","success","/cart");
                    },
                    error : function (error) {
                        showToast("Error : "+error.error_desc+"","danger");
                    }
                })
            })
            $('#buyNow').click(function () {
                window.location.href = "/checkout?productId=${MODEL.id}&quantity=1&sizeId="+$('#sizeId').val();
            })
            // Get opinion and render
            const getOpinion = ()=> {
                $.ajax({
                    url : '/ajax/product/opinion',
                    method : 'GET',
                    data : {
                        productId: ${MODEL.id},
                        page : nextPage,
                        maxPageItem : maxPageItemOpinion
                    },
                    success : function (data) {
                        nextPage++;
                        totalCurrentItem += data.opinions.length;
                        if (totalCurrentItem==data.countRating) {
                            $(".loadMoreOpinion").empty();
                        }
                        let average = (data.totalRating/data.countRating);
                        $('.averageRating').text(average.toFixed(2));
                        $('#amountOpinion').text(data.countRating)
                        renderRatingStar("averageRatingStar",average);
                        for (let opinion of data.opinions){
                            appendOpionion(opinion.id,opinion.title,opinion.content,opinion.rating,opinion.userId,opinion.userName,opinion.createAt,"bottom");
                        }
                    },
                    error : function (e) {
                        $(".loadMoreOpinion").empty();
                    }
                })
            }
            $(".loadMoreOpinion").click(()=> {
                getOpinion();
            })
            getOpinion();
            // Render star css
            const renderRatingStar = (className,averageRating) =>  {
                $("."+className).empty();
                let averageRatingHtml = '';
                for (let i =0 ; i < 5; i++) {
                    if (averageRating > i && averageRating < i+1)
                        averageRating
                    if (averageRating > i && averageRating <i+1) {
                        averageRatingHtml+=`<i class="fa fa-star-half-alt text-primary"></i>`;
                    } else if (averageRating > i) {
                        averageRatingHtml+=`<i class="fa fa-star text-primary"></i>`;
                    } else if (averageRating <= i) {
                        averageRatingHtml+=`<i class="far fa-star text-primary"></i>`;
                    }
                }
                $("."+className).append(averageRatingHtml);
            }
            // append opinon html
            const appendOpionion = (id,title,content,rating,userId,userName,createAt,position)=> {
                console.log("test");
                let starHtml = ``;
                for (let i=0; i<5;i++) {
                    if (i<rating) {
                        starHtml+=` <li><i class="fas fa-star text-primary"></i></li>`;
                    } else {
                        starHtml+=` <li><i class="far fa-star text-primary"></i></li>`;
                    }
                }
                let deleteHtml =``;
                <c:if test="${not empty USER_MODEL}">
                    if (userId==${USER_MODEL.id} || ${USER_MODEL.admin}) {
                        deleteHtml=`<a class="ms-3 deleteOpinion" id=""
                                       style="height: fit-content; cursor: pointer"
                                       data-bs-toggle="modal" data-bs-target="#deleteModal"
                                       data-idopinion="`+id+`">
                                        <i class="fa-solid fa-trash-can" style="color : red"></i>
                                    </a>`;
                    }
                </c:if>
                const timeagoInstance = timeago();
                const createAtAgo = timeagoInstance.format(new Date(createAt));
                const opinionHtml = `
            <div class="d-flex justify-content-between  py-4 border-top " id="opinion`+id+`">
                    <div>
                        <h5>`+userName+`</h5>
                       <div class="d-flex">
                             <p class="text-muted">Opinion added: `+createAtAgo+`</p>
                             `+deleteHtml+`
                        </div>
                        <ul class="list-unstyled d-flex flex-row mb-4">
                            `+starHtml+`
                        </ul>
                        <strong>`+title+`</strong>
                        <p class="mb-0">`+content+`</p>
                    </div>
                </div>
           `;
                if (position==="top") {
                    $(".opinion-list").prepend(opinionHtml);
                } else if (position==="bottom") {
                    $(".opinion-list").append(opinionHtml);
                }
                $(".deleteOpinion").click(function (){
                        $("#idOpinionDelete").val($(this).attr("data-idopinion"))
                        console.log($("#idOpinionDelete").val());
                    }
                );
            }
            // Connect socket
            const socket = new WebSocket("ws://localhost:8080/socket/opinion/${MODEL.id}");
            // revice message from socket
            socket.onmessage = function onMessage(message) {
                const opinionData = JSON.parse(message.data);
                if (opinionData.isDeleted) {
                    $("#opinion"+opinionData.id).remove();
                    let averageRating = parseFloat($('#averageRating').text());
                    let amountOpinion = parseInt($('#amountOpinion').text());
                    let amountOpinionNew = (amountOpinion-1);
                    let averageRatingNew = 5;
                    if (amountOpinionNew!=0) {
                        averageRatingNew = ((averageRating*amountOpinion)-opinionData.rating)/amountOpinionNew;
                    }
                    $('.averageRating').text(averageRatingNew.toFixed(2));
                    $('#amountOpinion').text(amountOpinionNew);
                    renderRatingStar("averageRatingStar",averageRatingNew);
                    <c:if test="${not empty USER_MODEL}">
                    if (opinionData.userId == ${USER_MODEL.id}) {
                        showToast("Deleted your review","success");
                    }
                    </c:if>
                } else {
                    appendOpionion(opinionData.id,opinionData.title,opinionData.content,opinionData.rating,opinionData.userId,opinionData.userName,opinionData.createAt,"top");
                    let averageRating = parseFloat($('#averageRating').text());
                    let amountOpinion = parseInt($('#amountOpinion').text());
                    let amountOpinionNew = amountOpinion+1;
                    let averageRatingNew = ((averageRating*amountOpinion)+opinionData.rating)/amountOpinionNew;
                    $('.averageRating').text(averageRatingNew.toFixed(2));
                    $('#amountOpinion').text(amountOpinionNew);
                    renderRatingStar("averageRatingStar",averageRatingNew);
                    <c:if test="${not empty USER_MODEL}">
                    if (opinionData.userId == ${USER_MODEL.id}) {
                        $("#titleOpinion").val("");
                        $("#contentOpinion").val("")
                        $("input[name='star']:checked").attr("checked",false);
                        document.querySelector("#toggleAddProduct").click();
                        $([document.documentElement, document.body]).animate({
                            scrollTop: $("#opinion"+opinionData.id).offset().top
                        }, 0);
                        showToast("Added your review","success");
                    }
                    </c:if>

                }
            }
            // send message to socket
            $("form#addNewOpinion").submit(function (e) {
                e.preventDefault();
                const title = $("#titleOpinion").val();
                const content = $("#contentOpinion").val()
                const rating = $("input[name='star']:checked").val();
                if (!validator.isEmpty(title) && !validator.isEmpty(content) && rating!=undefined && rating!=null ) {
                    const opinionData = {
                        title,
                        content,
                        rating,
                        productId: ${MODEL.id}
                    }
                    socket.send(JSON.stringify(opinionData));

                }
            })
            $("#btnDeleteOpinion").click(function (){
                const id = parseInt($("#idOpinionDelete").val());
                const opinionData = {
                    id,
                    isDeleted : true
                }
                socket.send(JSON.stringify(opinionData));
            })

        })
    </script>
<%--    Render recommed product--%>
    <c:if test="${not empty LIST_RECOMMED_PRODUCT}">
        <c:if test="${fn:length(LIST_RECOMMED_PRODUCT) > 3}">
            <div class="recommend-shoes blog-section">
                <div class="container" >
                    <div class="row mb-5">
                        <div class="col-md-6">
                            <h2 class="section-title">Bạn cũng có thể thích</h2>
                        </div>
                        <div class="col-md-6 text-start text-md-end">
                            <a href="#" class="more">Xem tất cả</a>
                        </div>
                    </div>
                    <div class="splide ">
                        <div class="splide__track">
                            <ul class="splide__list ">
                                <c:forEach var="product_item" items="${LIST_RECOMMED_PRODUCT}">
                                    <li class="splide__slide" style="max-width: 340px; min-width: 250px;">
                                        <div class="mb-4 mb-md-0" style="padding: 0 12px; max-width: 340px; min-width: 250px;">
                                            <div class="post-entry">
                                                <a href="/product-details/${product_item.id}" class="post-thumbnail" style="
                    background: rgb(171,172,171);
                    background: linear-gradient(90deg, rgba(171,172,171,1) 35%, rgba(205,205,205,1) 100%);"
                                                >
                                                    <img src="${product_item.thumbnail}" alt="Image" class="img-fluid">
                                                </a>
                                                <div class="post-content-entry">
                                                    <h3><a href="#">${product_item.name}</a></h3>
                                                    <div class="meta">
                                                        <span><fmt:formatNumber type="currency" value="${product_item.price} "/></span>

                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </li>
                                </c:forEach>
                            </ul>
                        </div>
                    </div>
                    <script !src="">
                        window.addEventListener("DOMContentLoaded", function (){
                            var splide = new Splide( '.splide', {
                                perPage: 4,
                                pagination: false,
                                focus  : 0,
                            } );

                            splide.mount();
                        })
                    </script>
                </div>

            </div>
        </c:if>
    </c:if>
</div>


<%--Socket Opinion Real-time--%>
<script>
    window.addEventListener("DOMContentLoaded",function (){

        $(".item-thumb").hover(function (){
            const idMainImage = $(this).attr("data-bs-mainimageproduct");
            $(".preview-image-product").css("display","none");
            $("#"+idMainImage).css("display","block")
        })
    })
</script>


</body>
</html>