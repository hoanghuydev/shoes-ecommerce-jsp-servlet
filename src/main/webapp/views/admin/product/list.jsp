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
            <a class="btn bg-gradient-dark mb-0" id="toggleAddProduct" data-bs-toggle="collapse" href="#formNewProduct"><i class="material-icons text-sm">add</i>&nbsp;&nbsp;Thêm sản phẩm mới</a>
          <div class="collapse multi-collapse my-3" id="formNewProduct">
            <div class="card py-2 px-4" >
              <form method="POST" enctype="multipart/form-data">
                <div class="input-group input-group-outline my-3">
                  <label class="form-label">Title</label>
                  <input type="text" class="form-control" name="name" required>
                </div>
                <div class="my-3">
                  <label>Nội dung</label>
                  <textarea type="text" class="" rows="10" style="height: 200px" name="content" id="content"></textarea>
                  <p class="text-sm">Nội dung không được chứa kí tự <code>'</code></p>
                </div>
                <div class="input-group input-group-outline my-3">
                  <label class="form-label">Mô tả ngắn</label>
                  <input type="text" class="form-control" name="shortDescription" required/>
                </div>
                <div class="input-group input-group-outline my-3">
                  <label class="form-label">Giá hiển thị</label>
                  <input type="number" id="price"  min="500000" step="1000" class="form-control" name="price" required>
                </div>
                <div class="input-group input-group-static mb-4">
                  <label for="category" class="ms-0">Danh mục</label>
                  <select class="form-control" id="category" name="categoryId" required>
                    <option value="" selected>-- Select category --</option>
                   <c:forEach var="category_item" items="${LIST_CATEGORY}">
                     <option value="${category_item.id}">${category_item.name}</option>
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
                  <input type="file" accept="image/*" id="thumbnailProduct" name="thumbnailProduct" required/>
                </div>
                <div class="row preview-thumbnail-product">

                </div>
                <div class="input-group input-group-static mb-4">
                  <label for="imageProduct" class="me-2">Ảnh mô tả sản phẩm:</label>
                  <input type="file" multiple accept="image/*" id="imageProduct" name="imageProduct" required/>
                </div>
                <div class="row preview-img-product">

                </div>
                <div class="input-group input-group-static mb-4">
                  <label for="model" class="me-2">Model :</label>
                  <input type="file" accept="" id="model" name="model"/>
                </div>
                <button class="btn btn-primary" type="submit">Thêm sản phẩm</button>

              </form>
            </div>
          </div>
        </div>
        <div class="row justify-content-center">
          <c:forEach var="product_item" items="${LIST_MODEL}">
            <div class="col-xl-3 col-md-4 col-sm-6 col-12 mb-4" id="productItem${product_item.id}">
              <div class="card py-3">
                <div class="card-header p-0  mx-3 border-radius-lg">
                  <a href="/product-details/${product_item.id}" style="background: rgb(171,172,171);
                     background: linear-gradient(125deg, rgba(171,172,171,1) 35%, rgba(205,205,205,1) 100%);
                     cursor:pointer;" class="d-block shadow-xl border-radius-lg">
                    <img loading="lazy" src="${product_item.thumbnail}" alt="Image Shoes" class="img-fluid shadow border-radius-lg">
                  </a>
                </div>
                <div class="card-body p-3">
                  <p class="mb-0 text-sm" >Shoes #${product_item.id}</p>
                  <a href="/product-details/${product_item.id}">
                    <h5 style="
                    line-height: 1.5em;
                    height: 2em;
                    overflow: hidden;
                    white-space: nowrap;
                    text-overflow: ellipsis;
                    width: 100%;
                    cursor: pointer;
                    ">
                        ${product_item.name}
                    </h5>
                  </a>
                  <p class="mb-2 text-sm" style="
                    line-height: 1.5em;
                    height: 2em;
                    overflow: hidden;
                    white-space: nowrap;
                    text-overflow: ellipsis;
                    width: 100%;
                    cursor: pointer;
                    " data-toggle="tooltip" data-placement="top" title="${product_item.shortDescription}"
                  >
                    ${product_item.shortDescription}
                  </p>
                  <div class="d-flex align-items-center ">
                    <a type="button" class="flex-grow-1 btn btn-outline-primary btn-sm mb-0 me-1 deleteProduct" data-product-id="${product_item.id}">Xóa</a>
                    <a href="<c:url value="/admin/product/update?id=${product_item.id}"/>" type="button" class="flex-grow-1 btn btn-outline-primary btn-sm mb-0  ms-1">Chỉnh sửa</a>
                  </div>
                </div>
              </div>
            </div>
          </c:forEach>
        </div>
    </div>
    <script>
      window.addEventListener("DOMContentLoaded", function () {
        // Handle delete product
        $(".deleteProduct").click(function() {
          const productId = $(this).data('product-id');
          $.ajax({
            url: '/ajax/admin/product',
            type: 'DELETE'
            data: {
              productId: productId
            },
            success: (data) => {
              showToastAdmin("success", "Đã xóa thành công", "Đã xóa thành công sản phẩm với id là " + productId, new Date());
              $("#productItem" + productId).remove();
            },
            error: (data) => {
              showToastAdmin("error", "Đã xảy ra lỗi", "Lỗi không thể xóa sản phẩm với id là " + productId, new Date());
            }
          });
        })
        // handle change size
        const listSize = [
          <c:forEach items="${LIST_SIZE}" var="item" varStatus="loop">
          {id: '${item.id}', name: '${item.name}'}<c:if test="${!loop.last}">,</c:if>
          </c:forEach>
        ];
        $('#size').change(function (){
          const arrListSizeSelected = $(this).val();
          $('.list-price-sizes').empty();
          const objListSizeSelected = listSize.filter((size)=> arrListSizeSelected.includes(size.id))
          const defaultPrice = $('#price').val()
          console.log(objListSizeSelected)
          for (let i = 0; i < objListSizeSelected.length ; i++) {
            const size = objListSizeSelected[i];
            $('.list-price-sizes').append(`
                <div class="my-3">
                    <label class="form-label bg-white">Nhập giá tiền cho kích thước `+size.name+`</label>
                    <input type="number" class="ms-3" min="10000" value="`+defaultPrice+`" step="1000" name="sizePrice[]" required/>
                    <input type="hidden" name="sizeIdForPrice[]" value="`+size.id+`" required/>
                  </div>
            `)
          }
        })
        ClassicEditor
                .create( document.querySelector( '#content' ))
                .catch( error => {
                  console.error( error );
                } );
        async function getBase64FromFile(file) {
          return new Promise((resolve, reject) => {
            fetch(window.URL.createObjectURL(file))
                    .then(res => res.blob())
                    .then(blob => {
                      let reader = new FileReader();
                      reader.onload = function () {
                        let base64 = reader.result;
                        resolve(base64);
                      };
                      reader.onerror = function (error) {
                        reject(error);
                      };
                      reader.readAsDataURL(blob);
                    })
                    .catch(error => reject(error));
          });
        }
        // Render preview image product
        $("#imageProduct").change(async function () {
          $(".preview-img-product").empty();
          // Convert FileList to an array
          const filesArray = Array.from($("#imageProduct").prop("files"));
          for (let file of filesArray) {
            const urlShowImg = URL.createObjectURL(file);
            const imgHtml = `
                <div class="col-xl-2 col-md-3 col-4">

                    <img style="width: 100%;" src="`+urlShowImg+`" class="card-body"/>
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
                    <img style="width: 100%;" src="`+urlShowImg+`" class="card-body"/>
                </div>
            `;
            $(".preview-thumbnail-product").append(imgThumbnailHtml);
          }
        });
      });

    </script>
</body>
</html>