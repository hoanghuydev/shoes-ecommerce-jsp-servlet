<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="vi_VN"/>
<html>
<head>
    <title>Hóa đơn nhập hàng</title>
</head>
<body>
<div class="row">
    <div class="col-12">
        <div class="card my-4">
            <div class="card-header p-0 position-relative mt-n4 mx-3 z-index-2">
                <div class="bg-gradient-primary shadow-primary border-radius-lg pt-4 pb-3">
                    <h6 class="text-white text-capitalize ps-3">Chi tiết hóa đơn nhập hàng #${importId}</h6>
                </div>
            </div>
            <div class="card-body px-0 pb-2">
                <div class="table-responsive p-0">
                    <table class="table align-items-center mb-0" id="dataTable">
                        <thead>
                        <tr>
                            <th class="text-uppercase text-secondary text-xxs font-weight-bolder opacity-7 text-center">Mã sản phẩm</th>
                            <th class="text-uppercase text-secondary text-xxs font-weight-bolder opacity-7 text-center">Tên sản phẩm</th>
                            <th class="text-uppercase text-secondary text-xxs font-weight-bolder opacity-7 text-center">Kích thước</th>
                            <th class="text-uppercase text-secondary text-xxs font-weight-bolder opacity-7 text-center">Số lượng</th>
                            <th class="text-uppercase text-secondary text-xxs font-weight-bolder opacity-7 text-center">Giá</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="item" items="${LIST_MODEL}">
                            <tr>
                                <td>
                                    <div class="d-flex text-center">
                                        <p class="text-xs font-weight-bold mx-auto mb-0">${item.productId}</p>
                                    </div>
                                </td>
                                <td>
                                    <div class="d-flex text-center">
                                        <p class="text-xs font-weight-bold mx-auto mb-0">${item.productName}</p>
                                    </div>
                                </td>
                                <td>
                                    <div class="d-flex text-center">
                                        <p class="text-xs font-weight-bold mx-auto mb-0">${item.size}</p>
                                    </div>
                                </td>
                                <td>
                                    <div class="d-flex text-center">
                                        <p class="text-xs font-weight-bold mx-auto mb-0">${item.quantityImport}</p>
                                    </div>
                                </td>
                                <td data-bs-toggle="tooltip" class="text-center" data-bs-placement="top" title="" >
                                    <p class="text-xs font-weight-bold mx-auto mb-0"><fmt:formatNumber type="currency" value="${item.priceImport}"/></p>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>

<script >
    window.addEventListener("DOMContentLoaded",function (){
        $(document).ready(function(){
            new DataTable('#dataTable', {
                pagingType: 'simple_numbers',
                language: {
                    paginate: {
                        previous: '<',
                        next: '>'
                    }
                }

            });
        });
    })
</script>
</body>
</html>
