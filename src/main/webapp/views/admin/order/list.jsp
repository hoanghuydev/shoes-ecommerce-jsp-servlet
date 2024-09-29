<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.ltweb_servlet_ecommerce.constant.SystemConstant" %>
<%@ page import="com.ltweb_servlet_ecommerce.utils.StatusMapUtil" %>
<fmt:setLocale value="vi_VN"/>
<html>
<head>
    <title>Admin Nai - Danh sách đơn hàng</title>

</head>
<body>
<div class="row">
    <div class="col-12">
        <div class="card my-4">
            <div class="card-header p-0 position-relative mt-n4 mx-3 z-index-2">
                <div class="bg-gradient-primary shadow-primary border-radius-lg pt-4 pb-3">
                    <h6 class="text-white text-capitalize ps-3">Danh sách đơn hàng</h6>
                </div>
            </div>
            <div class="card-body px-0 pb-2">
                <div class="table-responsive p-0">
                    <table class="table align-items-center mb-0" id="dataTable">
                        <thead>
                        <tr>
                            <th class="text-uppercase text-secondary text-xxs font-weight-bolder opacity-7">ID</th>
                            <th class="text-uppercase text-secondary text-xxs font-weight-bolder opacity-7">Thông tin
                                khách hàng
                            </th>
                            <th class="text-uppercase text-secondary text-xxs font-weight-bolder opacity-7 ps-2">Địa
                                chỉ
                            </th>
                            <th class="text-center text-uppercase text-secondary text-xxs font-weight-bolder opacity-7">
                                Trạng thái
                            </th>
                            <th class="text-center text-uppercase text-secondary text-xxs font-weight-bolder opacity-7">
                                Sản phẩm
                            </th>
                            <th class="text-center text-uppercase text-secondary text-xxs font-weight-bolder opacity-7">
                                Tổng tiền
                            </th>
                            <th class="text-center text-uppercase text-secondary text-xxs font-weight-bolder opacity-7">
                                Ghi chú
                            </th>
                            <th class="text-center text-uppercase text-secondary text-xxs font-weight-bolder opacity-7">
                                Được tạo lúc
                            </th>
                            <th class="text-secondary opacity-7"></th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="order_item" items="${LIST_ORDER}">
                            <tr>
                                <!-- <%--                            Id--%> -->
                                <td>
                                    <div class="d-flex">
                                        <p class="text-xs font-weight-bold mx-auto mb-0">${order_item.id}</p>
                                    </div>
                                </td>
                                <!-- <%--                            End Id--%> -->
                                <!-- Customer -->
                                <td>
                                    <div class="d-flex px-2 py-1">
                                        <div class="d-flex flex-column justify-content-center">
                                            <h6 class="mb-0 text-sm">${order_item.addressModel.fullName}</h6>
                                            <p class="text-xs text-secondary mb-0">
                                                0${order_item.addressModel.phoneNumber}</p>
                                        </div>
                                    </div>
                                </td>
                                <!-- End Customer -->

                                <td style="cursor: pointer;" data-bs-toggle="tooltip" data-bs-placement="top"
                                    title="${order_item.addressModel.hamlet},${order_item.addressModel.commune},${order_item.addressModel.district},${order_item.addressModel.province}">
                                    <p class="text-xs font-weight-bold mb-0">${order_item.addressModel.province}</p>
                                    <p class="text-xs text-secondary mb-0">${order_item.addressModel.district}</p>
                                </td>
                                <td class="align-middle text-center text-sm">
                                    <c:if test="${order_item.status eq StatusMapUtil.getStatusKey(SystemConstant.ORDER_DELIVERED)}">
                                        <c:set var="contextual" value="success"/>
                                    </c:if>
                                    <c:if test="${order_item.status eq StatusMapUtil.getStatusKey(SystemConstant.ORDER_TRANSPORTING)}">
                                        <c:set var="contextual" value="info"/>
                                    </c:if>
                                    <c:if test="${order_item.status eq StatusMapUtil.getStatusKey(SystemConstant.ORDER_CANCEL)}">
                                        <c:set var="contextual" value="danger"/>
                                    </c:if>
                                    <c:if test="${order_item.status eq StatusMapUtil.getStatusKey(SystemConstant.ORDER_PROCESSING)}">
                                        <c:set var="contextual" value="primary"/>
                                    </c:if>
                                    <span class="badge badge-sm bg-gradient-${contextual}">${StatusMapUtil.getStatusValue(order_item.status)}</span>
                                </td>
                                <!-- Product list -->
                                <td class="align-middle text-center">
                                    <c:forEach var="product_item" items="${order_item.listProduct}">
                                        <p class="text-xs font-weight-bold mb-0">${product_item.quantity}
                                            - ${product_item.name} - ${product_item.sizeName}</p>
                                    </c:forEach>
                                </td>
                                <!-- End Product list -->
                                <!-- Total -->
                                <td class="align-middle text-center">
                                    <p class="text-xs text-secondary mb-0">
                                        <fmt:formatNumber type="currency" value="${order_item.totalAmount}"/>
                                    </p>
                                </td>
                                <!-- End total -->
                                <!-- Note -->
                                <td style="cursor: pointer;" data-bs-toggle="tooltip" data-bs-placement="top"
                                    title="${order_item.note}">
                                    <div class="d-flex">
                                        <p class="text-xs font-weight-bold mx-auto mb-0">
                                            <c:if test="${not empty order_item.note || order_item.note != ''}">
                                                Chi tiết ghi chú...
                                            </c:if>
                                            <c:if test="${ empty order_item.note || order_item.note == ''}">
                                                Không có ghi chú
                                            </c:if>
                                        </p>
                                    </div>
                                </td>
                                <!-- End Note -->
                                <td>
                                    <div class="d-flex">
                                        <p class="text-xs font-weight-bold mx-auto mb-0">
                                            <fmt:formatDate value="${order_item.createAt}"
                                                            pattern="yyy-MM-dd hh:mm:ssa"/>
                                        </p>
                                    </div>
                                </td>
                                <!-- Action -->
                                <td class="align-middle">
                                    <c:if test="${order_item.status eq StatusMapUtil.getStatusKey(SystemConstant.ORDER_PROCESSING)}">
                                        <p class="m-0 p-0">GHTK</p>
                                        <div class="d-flex">
                                            <a class="btn btn-link text-success px-1 mb-0"
                                               id="pushOrder${order_item.id}"
                                               href="javascript:;"><i class="fa-solid fa-truck me-1"></i>Đẩy đơn</a>
                                            <a class="btn btn-link text-primary px-1 mb-0"
                                               id="pushOrder${order_item.id}"
                                               href="javascript:;"><i class="fa-solid fa-rectangle-xmark me-1"></i>Hủy
                                                đơn</a>
                                        </div>
                                    </c:if>

<%--                                    <p class="m-0 p-0">CRM</p>--%>
                                    <div class="d-flex">
                                        <a class="btn btn-link text-dark px-1 mb-0"
                                           href="<c:url value="/admin/orders/detail?id=${order_item.id}"/> "><i
                                                class="material-icons text-sm me-1">edit</i>Sửa</a>
                                        <button class="btn btn-link text-danger text-gradient px-1 mb-0"
                                           id="removeOrder" data-id="${order_item.id}"><i
                                                class="material-icons text-sm me-1">delete</i>Xóa</button>
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
        $("table").each(function () {
            new DataTable(this, {
                //sort column 7 desc
                order: [[7, 'desc']],
            });
        })

        // Remove order
        $(document).on('click', '#removeOrder', function () {
            Swal.fire({
                title: "Xóa ?",
                text: "Bạn có muốn xóa hóa đơn này?",
                icon: "warning",
                showCancelButton: true,
                confirmButtonColor: "#3085d6",
                cancelButtonColor: "#d33",
                cancelButtonText: "Hủy",
                confirmButtonText: "Xóa"
            }).then((result) => {
                if (result.isConfirmed) {
                    let id = $(this).data('id');
                    $.ajax({
                        url: '<c:url value="/admin/order/list"/>',
                        method: 'DELETE',
                        contentType: "application/json; charset=utf-8",
                        data: JSON.stringify(id),
                        success: function (response) {
                            Swal.fire({
                                icon: "success",
                                title: "Thành công",
                                toast: true,
                                position: "top-end",
                                showConfirmButton: false,
                                timer: 700,
                                timerProgressBar: true,
                                didOpen: (toast) => {
                                    toast.onmouseenter = Swal.stopTimer;
                                    toast.onmouseleave = Swal.resumeTimer;
                                }
                            });
                            setTimeout(function () {
                                window.location.reload();
                            }, 700);
                        },
                        error: function (error) {
                            console.log('that bai')
                            Swal.fire({
                                icon: "warning",
                                title: "Có lỗi xảy ra!",
                                toast: true,
                                position: "top-end",
                                showConfirmButton: false,
                                timer: 700,
                                timerProgressBar: true,
                                didOpen: (toast) => {
                                    toast.onmouseenter = Swal.stopTimer;
                                    toast.onmouseleave = Swal.resumeTimer;
                                }
                            });
                        }
                    })

                }
            });
        })
    })
</script>
</body>
</html>
