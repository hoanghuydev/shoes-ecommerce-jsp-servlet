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
                <div class="bg-gradient-primary shadow-primary border-radius-lg pt-4 pb-3" style="z-index: 1;">
                    <h6 class="text-white text-capitalize ps-3">Bảng nhập hàng</h6>
                </div>
            </div>
            <div class="card-body px-0 pb-2">
                <div class="table-responsive p-0">
                    <div class="d-flex justify-content-end me-4">
                        <a class="btnDelete" data-bs-toggle="modal" href="#addModal" data-toggle="tooltip"
                           title='Thêm đơn hàng'>
                            <span><i class="fa-solid fa-file-import"></i></span>
                        </a>
                        <button id="btnDelete" type="button" class="btnDelete" data-toggle="tooltip"
                                title='Xóa đơn hàng'>
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
                                ID
                            </th>
                            <th class="text-uppercase text-secondary text-xxs font-weight-bolder opacity-7 text-center">
                                Nhà cung cấp
                            </th>
                            <th class="text-uppercase text-secondary text-xxs font-weight-bolder opacity-7 text-center">
                                Trị giá
                            </th>
                            <th class="text-center text-uppercase text-secondary text-xxs font-weight-bolder opacity-7 text-center">
                                Thời gian nhập
                            </th>
                            <th></th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="item" items="${LIST_MODEL}">
                            <tr>
                                <td class="text-center">

                                    <label class="pos-rel">
                                        <input type="checkbox" class="form-check-input checkbox" value="${item.id}"/>
                                        <span class="lbl"></span>
                                    </label>

                                </td>
                                <td>
                                    <div class="d-flex text-center">
                                        <p class="text-xs font-weight-bold mx-auto mb-0">${item.id}</p>
                                    </div>
                                </td>
                                <td>
                                    <div class="d-flex text-center">
                                        <p class="text-xs font-weight-bold mx-auto mb-0">${item.supplier}</p>
                                    </div>
                                </td>
                                <td data-bs-toggle="tooltip" class="text-center" data-bs-placement="top" title="">
                                    <p class="text-xs font-weight-bold mx-auto mb-0"><fmt:formatNumber type="currency"
                                                                                                       value="${item.totalPrice}"/></p>
                                </td>
                                <td class="align-middle text-center">
                                    <p class="text-xs font-weight-bold mx-auto mb-0">
                                        <fmt:formatDate value="${item.createAt}" pattern="yyy-MM-dd"/>
                                    </p>
                                </td>
                                <td class="align-middle text-center">
                                    <a href="<c:url value="/admin/import-order-details?id=${item.id}"/>"><i
                                            class="fa-solid fa-circle-info"></i></a>
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

<!-- Progress Bar -->
<div id="overlay" style="">
    <div class="progress-circle" style=""></div>
</div>

<%--    Modal--%>
<div class="modal fade" id="addModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel"
     aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title font-weight-normal" id="updateModalLabel">Chọn hóa đơn nhập hàng</h5>
                <button type="button" class="btn-close text-dark" data-bs-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <form id="importForm" method="POST" enctype="multipart/form-data"
                  action="<c:url value="/admin/import-order"/>">
                <div class="modal-body">
                    <div class="input-group input-group-outline my-3">
                        <input type="text" class="form-control" name="supplier" placeholder="Nhà cung cấp" required>
                    </div>
                    <div class="input-group input-group-outline my-3">
                        <input type="text" class="form-control" name="importId" placeholder="Mã hóa đơn" required>
                    </div>
                    <div class="input-group input-group-outline my-3">
                        <input type="date" class="form-control" name="importDate">
                    </div>
                    <label class="form-label">File hóa đơn (.xlsx)</label>
                    <div class="input-group input-group-outline mb-3">
                        <input type="file" accept=".xlsx" class="form-control" name="importFile" required>
                    </div>
                    <label class="form-label">Thư mục ảnh</label>
                    <div class="input-group input-group-outline mb-3">
                        <input type="file" class="form-control" name="imageProducts" webkitdirectory required>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-block btn-light" data-bs-dismiss="modal">Đóng</button>
                    <input type="hidden" name="action" value="update">
                    <input type="hidden" name="id" id="idUpdate">
                    <button type="submit" class="btn bg-gradient-danger">Lưu</button>
                </div>
            </form>

        </div>
    </div>
</div>
<script>
    window.addEventListener("DOMContentLoaded", function () {
        $(document).ready(function () {
            new DataTable('#dataTable', {
                order: [[4, 'desc']],
                pagingType: 'simple_numbers',
                language: {
                    paginate: {
                        previous: '<',
                        next: '>'
                    }
                }

            });


            $('#importForm').on('submit', function (e) {
                e.preventDefault();
                $('#addModal').modal('hide');
                $('#overlay').css({'visibility': 'visible', 'opacity': '1'});

                // Simulate form submission
                setTimeout(() => {
                    this.submit();
                }, 500); // Delay to ensure modal hides properly before form submits
            });

            //check all
            $("#checkAll").change(function () {
                $(".checkbox").prop('checked', $(this).prop("checked"));
            });

            $('#btnDelete').on('click', function () {
                Swal.fire({
                    title: "Xóa ?",
                    text: "Bạn có muốn xóa những hóa đơn này?",
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
                        $.ajax({
                            url: '<c:url value="/admin/import-order"/>',
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
                                    window.location.href = "<c:url value="/admin/import-order"/>";
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
