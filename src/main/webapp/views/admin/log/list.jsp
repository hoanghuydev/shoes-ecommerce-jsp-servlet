<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page import="com.ltweb_servlet_ecommerce.constant.SystemConstant" %>
<%@page import="com.ltweb_servlet_ecommerce.utils.StringUtilsHelper" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Nhật ký</title>
</head>
<body>
<div class="row">
    <div class="col-12">
        <div class="card my-4">
            <div class="card-header p-0 position-relative mt-n4 mx-3 z-index-2">
                <div class="bg-gradient-primary shadow-primary border-radius-lg pt-4 pb-3">
                    <h6 class="text-white text-capitalize ps-3">Bảng nhật ký</h6>
                </div>
            </div>
            <div class="card-body px-0 pb-2">
                <div class="table-responsive p-0">
                    <div class="d-flex justify-content-end me-4">
                        <button id="btnDelete" type="button" class="btnDelete" data-toggle="tooltip"
                                title='Delete Logs'>
                            <span><i class="fa-solid fa-trash"></i></span>
                        </button>
                    </div>
                    <table class="table align-items-center mb-0" id="dataTable">
                        <thead>
                        <tr>
                            <th class="text-center">
                                <label class="pos-rel">
                                    <input type="checkbox" class="form-check-input checkbox" id="checkAll"/>
                                    <span class="lbl"></span>
                                </label>
                            </th>
                            <th class="text-uppercase text-secondary text-xxs font-weight-bolder opacity-7 text-center">
                                IP
                            </th>
                            <th class="text-uppercase text-secondary text-xxs font-weight-bolder opacity-7 text-center">
                                Quốc gia
                            </th>
                            <th class="text-uppercase text-secondary text-xxs font-weight-bolder opacity-7 text-center">
                                Cấp độ
                            </th>
                            <th class="text-uppercase text-secondary text-xxs font-weight-bolder opacity-7 text-center">
                                Hành động
                            </th>
                            <th class="text-uppercase text-secondary text-xxs font-weight-bolder opacity-7 ps-2 text-center">
                                Giá trị cũ
                            </th>
                            <th class="text-center text-uppercase text-secondary text-xxs font-weight-bolder opacity-7 text-center">
                                Giá trị mới
                            </th>
                            <th class="text-center text-uppercase text-secondary text-xxs font-weight-bolder opacity-7 text-center">
                                Thời gian tạo
                            </th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="item" items="${logs}">
                            <tr>
                                <td class="text-center">
                                    <label class="pos-rel">
                                        <input type="checkbox" class="form-check-input checkbox" value="${item.id}"/>
                                        <span class="lbl"></span>
                                    </label>
                                </td>
                                <td>
                                    <div class="d-flex text-center">
                                        <p class="text-xs font-weight-bold mx-auto mb-0">${item.ip}</p>
                                    </div>
                                </td>
                                <td>
                                    <div class="d-flex text-center">
                                        <p class="text-xs font-weight-bold mx-auto mb-0">${item.location}</p>
                                    </div>
                                </td>
                                <td>
                                    <div class="d-flex px-2 py-1 text-center">
                                        <c:if test="${item.level.equals(SystemConstant.DANGER_LEVEL) || item.level.equals(SystemConstant.ERROR_LEVEL)}">
                                            <c:set var="alert" value="danger" scope="page"/>
                                        </c:if>
                                        <c:if test="${item.level.equals(SystemConstant.INFO_LEVEL)}">
                                            <c:set var="alert" value="success" scope="page"/>
                                        </c:if>
                                        <c:if test="${item.level.equals(SystemConstant.WARN_LEVEL)}">
                                            <c:set var="alert" value="warning" scope="page"/>
                                        </c:if>
                                        <span class="badge badge-sm bg-gradient-${alert}">${item.level}</span>
                                    </div>
                                </td>
                                <td data-bs-toggle="tooltip" class="text-center" data-bs-placement="top" title="">
                                    <p class="text-xs font-weight-bold mx-auto mb-0">${item.action}</p>
                                </td>
                                <td class="align-middle text-sm">
                                    <c:if test="${item.preValue!=null}">
                                        <p class="text-xs font-weight-bold mx-auto mb-0">
                                                ${StringUtilsHelper.insertHtmlBreaks(item.preValue.get(SystemConstant.VALUE_LOG).toString(), 50)}
                                        </p>
                                    </c:if>
                                </td>
                                <td class="align-middle ">
                                    <p class="text-xs font-weight-bold mx-auto mb-0">${StringUtilsHelper.insertHtmlBreaks(item.value.get(SystemConstant.STATUS_LOG), 50)}</p>
                                    <c:if test="${item.value ne null and item.value.has(SystemConstant.VALUE_LOG)}">
                                        <p class="text-xs font-weight-bold mb-0">
                                                ${StringUtilsHelper.insertHtmlBreaks(item.value.get(SystemConstant.VALUE_LOG), 50)}
                                        </p>
                                    </c:if>

                                </td>
                                <td class="align-middle text-center">
                                    <p class="text-xs font-weight-bold mx-auto mb-0">
                                        <fmt:formatDate value="${item.createAt}" pattern="yyy-MM-dd hh:mm:ssa"/>
                                    </p>
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
                order: [[7, 'desc']],
                pagingType: 'simple_numbers',
                language: {
                    paginate: {
                        previous: '<',
                        next: '>'
                    }
                }

            });

            //check all
            $("#checkAll").change(function () {
                $(".checkbox").prop('checked', $(this).prop("checked"));
            });

            $('#btnDelete').on('click', function () {
                Swal.fire({
                    title: "Xóa ?",
                    text: "Bạn có muốn xóa những nhật ký này?",
                    icon: "warning",
                    showCancelButton: true,
                    confirmButtonColor: "#3085d6",
                    cancelButtonColor: "#d33",
                    cancelButtonText: "Hủy",
                    confirmButtonText: "Xóa"
                }).then((result) => {
                    if (result.isConfirmed) {
                        var ids = [];
                        $('input[type="checkbox"].checkbox:checked').each(function () {
                            if ($(this).attr('id') !== 'checkAll') {
                                ids.push($(this).val()); // Thêm giá trị của checkbox được chọn vào mảng
                            }
                        });
                        console.log(ids)
                        $.ajax({
                            url: '<c:url value="/admin/logs"/>',
                            method: 'DELETE',
                            contentType: "application/json; charset=utf-8",
                            // dataType: "json",
                            data: JSON.stringify(ids),
                            success: function (response) {
                                Swal.fire({
                                    icon: "success",
                                    title: "Thành công",
                                    toast: true,
                                    position: "top-end",
                                    showConfirmButton: false,
                                    timer: 600,
                                    timerProgressBar: true,
                                    didOpen: (toast) => {
                                        toast.onmouseenter = Swal.stopTimer;
                                        toast.onmouseleave = Swal.resumeTimer;
                                    }
                                });
                                setTimeout(function () {
                                    window.location.href = "<c:url value="/admin/logs"/>";
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
                                    timer: 600,
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
            });

        });


    })
</script>
</body>
</html>
