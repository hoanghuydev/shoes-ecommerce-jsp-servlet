<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Admin Nai - Sản phẩm</title>
</head>
<body>
<div class="col-12 mt-4">
    <div class="mb-2 ps-3">
        <div class="card py-2 px-4">
            <form method="POST" enctype="multipart/form-data" action="<c:url value="/admin/product/update"/> ">
                <div class="input-group input-group-outline my-3">
                    <label class="form-label">Title</label>
                    <input type="text" class="form-control" name="name" value="${product.name}" required>
                </div>
                <div class="my-3">
                    <label>Nội dung</label>
                    <textarea type="text" class="" rows="10" style="height: 200px" name="content"
                              id="content">${product.content}</textarea>
                    <p class="text-sm">Nội dung không được chứa kí tự <code>'</code></p>
                </div>
                <div class="input-group input-group-outline my-3">
                    <label class="form-label">Mô tả ngắn</label>
                    <input type="text" class="form-control" name="shortDescription" value="${product.shortDescription}"
                           required/>
                </div>
                <%--                <div class="input-group input-group-outline my-3">--%>
                <%--                    <label class="form-label">Giá hiển thị</label>--%>
                <%--                    <input type="number" id="price" min="500000" step="1000" class="form-control" name="price"--%>
                <%--                           required>--%>
                <%--                </div>--%>
                <div class="input-group input-group-static mb-4">
                    <label for="category" class="ms-0">Danh mục</label>
                    <select class="form-control" id="category" name="categoryId" required>
                        <option value="" selected>-- Select category --</option>
                        <c:forEach var="category_item" items="${LIST_CATEGORY}">
                            <option value="${category_item.id}" ${category_item.id == product.categoryId ? 'selected' : ''}>${category_item.name}</option>
                        </c:forEach>
                    </select>
                </div>

                <div class="input-group input-group-static mb-4">
                    <label for="size" class="ms-0">Kích thước</label>
                    <select multiple class="form-control" id="size" name="sizeId[]" required>
                        <c:forEach var="size_item" items="${LIST_SIZE}">
                            <option value="${size_item.id}">${size_item.name}</option>
                        </c:forEach>
                    </select>
                </div>
                <div class="list-price-sizes">

                </div>
                <div class="input-group input-group-static mb-4">
                    <label for="thumbnailProduct" class="me-2">Ảnh bia sản phẩm:</label>
                    <input type="file" accept="image/*" id="thumbnailProduct" name="thumbnailProduct"/>
                </div>
                <div class="row preview-thumbnail-product">
                    <c:if test="${product.thumbnail != null}">
                        <div class="col-xl-2 col-md-3 col-4">
                            <img style="width: 100%;" src="${product.thumbnail}" class="card-body"/>
                        </div>
                    </c:if>
                </div>
                <div class="input-group input-group-static mb-4">
                    <label for="imageProduct" class="me-2">Ảnh mô tả sản phẩm:</label>
                    <input type="file" multiple accept="image/*" id="imageProduct" name="imageProduct"/>
                </div>
                <div class="row preview-img-product">
                    <c:forEach items="${images}" var="image">
                        <div class="col-xl-2 col-md-3 col-4 position-relative" data-id="${image.id}">
                            <img style="width: 100%;" src="${image.imageUrl}" class="card-body"/>
                            <i class="fas fa-times-circle position-absolute top-0 end-0" style="cursor: pointer;"></i>
                        </div>
                    </c:forEach>
                </div>
                <input type="hidden" name="id" value="${product.id}">
                <input type="hidden" name="removeImgs" >
                <button class="btn btn-primary" type="submit">Lưu</button>
            </form>
        </div>
    </div>
</div>
<script>
    window.addEventListener("DOMContentLoaded", function () {
        const listSize = [
            <c:forEach items="${LIST_SIZE}" var="item" varStatus="loop">
            {id: '${item.id}', name: '${item.name}'}<c:if test="${!loop.last}">, </c:if>
            </c:forEach>
        ];

        const renderSize = () => {
            const productSizeSelected = [
                <c:forEach items="${productSizes}" var="item" varStatus="loop">
                {id: '${item.id}', sizeId: '${item.sizeId}', price: '${item.price}'}<c:if test="${!loop.last}">, </c:if>
                </c:forEach>
            ];

            let options = document.getElementById('size').options;
            for (let i = 0; i < options.length; i++) {
                options[i].selected = productSizeSelected.some((size) => size.sizeId === options[i].value);
            }

            console.log('productSizeSelected', productSizeSelected)
            for (let i = 0; i < productSizeSelected.length; i++) {
                const productSize = productSizeSelected[i];
                // get size name by sizeId from listSize
                const sizeName = listSize.find((size) => size.id === productSize.sizeId).name;
                $('.list-price-sizes').append(`
                <div class="my-3">
                    <label class="form-label bg-white">Nhập giá tiền cho kích thước ` + sizeName + `</label>
                    <input type="number" class="ms-3" min="10000" max="100000000" value="` + productSize.price + `" step="1000" name="sizePrice[]" required/>
                    <input type="hidden" name="sizeIdForPrice[]" value="` + productSize.sizeId + `" required/>
                  </div>
            `)
            }
        }
        renderSize();

        // handle change size
        console.log(listSize)
        $('#size').change(function () {
            const arrListSizeSelected = $(this).val();
            $('.list-price-sizes').empty();
            const productSizeSelected = [
                <c:forEach items="${productSizes}" var="item" varStatus="loop">
                {id: '${item.id}', sizeId: '${item.sizeId}', price: '${item.price}'}<c:if test="${!loop.last}">, </c:if>
                </c:forEach>
            ];
            const objListSizeSelected = listSize.filter((size) => arrListSizeSelected.includes(size.id))
            // const defaultPrice = $('#price').val()
            // console.log('objListSizeSelected',objListSizeSelected)
            for (let i = 0; i < objListSizeSelected.length; i++) {
                const size = objListSizeSelected[i];
                // get price by sizeId from productSizeSelected, if not found, set default price is 0
                const productSize = productSizeSelected.find((productSize) => productSize.sizeId === size.id) || {price: 0};
                $('.list-price-sizes').append(`
                <div class="my-3">
                    <label class="form-label bg-white">Nhập giá tiền cho kích thước ` + size.name + `</label>
                    <input type="number" class="ms-3" min="10000" max="100000000" value="` + productSize.price + `" step="1000" name="sizePrice[]" required/>
                    <input type="hidden" name="sizeIdForPrice[]" value="` + size.id + `" required/>
                  </div>
            `)
            }
        })

        ClassicEditor
            .create(document.querySelector('#content'))
            .catch(error => {
                console.error(error);
            });

        $(document).ready(function() {
            $('.preview-img-product').on('click', '.fa-times-circle', function() {
                // Remove the parent div of the clicked close icon
                const parentDiv = $(this).parent();
                let values = $('input[name="removeImgs"]').val();
                if (values === '') {
                    values = parentDiv.data('id');
                } else {
                    values += ',' + parentDiv.data('id');
                }
                $('input[name="removeImgs"]').val(values);
                parentDiv.remove();
            });
        });

        // Render preview image product
        $("#imageProduct").change(async function () {
            // $(".preview-img-product").empty();
            // Convert FileList to an array
            const filesArray = Array.from($("#imageProduct").prop("files"));
            for (let file of filesArray) {
                const urlShowImg = URL.createObjectURL(file);
                const imgHtml = `
                <div class="col-xl-2 col-md-3 col-4">
                    <img style="width: 100%;" src="` + urlShowImg + `" class="card-body"/>
                </div>
            `;
                $(".preview-img-product").append(imgHtml);
            }
        });
        //   Render preview thumbnail product
        $("#thumbnailProduct").change(async function () {
            $(".preview-thumbnail-product").empty();
            const filesArray = Array.from($("#thumbnailProduct").prop("files"));
            for (let file of filesArray) {
                const urlShowImg = URL.createObjectURL(file);
                const imgThumbnailHtml = `
                <div class="col-xl-2 col-md-3 col-4">
                    <img style="width: 100%;" src="` + urlShowImg + `" class="card-body"/>
                </div>
            `;
                $(".preview-thumbnail-product").append(imgThumbnailHtml);
            }
        });
    });

</script>
</body>
</html>