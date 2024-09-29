<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Sản phẩm hết hàng</title>
</head>
<body>
<div class="row">
    <div class="col-12">
        <div class="card my-4">
            <div class="card-header p-0 position-relative mt-n4 mx-3 z-index-2">
                <div class="bg-gradient-primary shadow-primary border-radius-lg pt-4 pb-3">
                    <h6 class="text-white text-capitalize ps-3">Sản phẩm hết hàng</h6>
                </div>
            </div>
            <div class="card-body px-0 pb-2">
                <div class="table-responsive p-0">
                    <table class="table align-items-center mb-0" id="dataTable">
                        <thead>
                        <tr>
                            <th style="width: 150px !important;"></th>
                            <th class="text-uppercase text-secondary text-xxs font-weight-bolder opacity-7 text-center">
                                Tên sản phẩm
                            </th>
                            <th class="text-uppercase text-secondary text-xxs font-weight-bolder opacity-7 text-center">
                                Kích thước
                            </th>
                            <th class="text-uppercase text-secondary text-xxs font-weight-bolder opacity-7 text-center">
                                Trạng thái
                            </th>
                            <th class="text-uppercase text-secondary text-xxs font-weight-bolder opacity-7 ps-2 text-center">
                                Ghi chú
                            </th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="item" items="${products}">
                            <tr>
                                <td class="text-center">
                                    <img src="${item.product.thumbnail}" alt="img" class="w-40">
                                </td>
                                <td>
                                    <div class="d-flex text-center align-items-center">
                                        <p class="text-xs font-weight-bold mx-auto mb-0">${item.product.name}</p>
                                    </div>
                                </td>
                                <td>
                                    <div class="d-flex text-center align-items-center">
                                        <p class="text-xs font-weight-bold mx-auto mb-0">${item.product.sizeName}</p>
                                    </div>
                                </td>
                                <td>
                                    <div class="d-flex px-2 py-1 justify-content-center align-items-center">
                                        <c:set var="alert" value="warning" scope="page"/>
                                        <c:if test="${item.isOutOfStock}">
                                            <c:set var="alert" value="danger" scope="page"/>
                                        </c:if>
                                        <span class="badge badge-sm bg-gradient-${alert}">
                                                ${item.isOutOfStock ? 'Hết hàng' : 'Còn hàng'}
                                        </span>
                                    </div>
                                </td>
                                <td>
                                    <div class="d-flex justify-content-center align-items-center">
                                        <p class="text-xs font-weight-bold text-center">${item.note}</p>
                                    </div>
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
<script>
    window.addEventListener("DOMContentLoaded", function () {
        $(document).ready(function () {
            new DataTable('#dataTable', {
                columnDefs: [
                    { orderable: false, targets: 0 }
                ],
                pagingType: 'simple_numbers',
                language: {
                    paginate: {
                        previous: '<',
                        next: '>'
                    }
                }

            });
        });
    });
</script>
</body>
</html>
