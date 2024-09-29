<%--
  Created by IntelliJ IDEA.
  User: HUY
  Date: 1/1/2024
  Time: 2:53 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/common/taglib.jsp" %>

<html>
<head>
    <title>Admin Nai - User</title>
</head>
<body>
<div class="col-12 mt-4">

    <!-- List User -->
    <div class="card my-4">
        <div class="card-header p-0 position-relative mt-n4 mx-3 z-index-2">
            <div class="bg-gradient-primary shadow-primary border-radius-lg pt-4 pb-3">
                <h6 class="text-white text-capitalize ps-3">Users Table</h6>
            </div>
        </div>

        <div class="card-body px-0 pb-2">
            <div class="table-responsive p-0">
                <div class="d-flex justify-content-end me-4">
                    <button id="btnDelete" type="button" class="btnDelete" data-toggle="tooltip" title='Delete Logs'>
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
                        <th class="text-uppercase text-secondary text-xxs font-weight-bolder opacity-7">ID</th>
                        <th class="text-uppercase text-secondary text-xxs font-weight-bolder opacity-7">Họ và tên</th>
                        <th class="text-uppercase text-secondary text-xxs font-weight-bolder opacity-7">Email</th>
                        <th class="text-uppercase text-secondary text-xxs font-weight-bolder opacity-7">Liên kết</th>
                        <th class="text-uppercase text-secondary text-xxs font-weight-bolder opacity-7">Ngày sinh</th>
                        <th class="text-uppercase text-secondary text-xxs font-weight-bolder opacity-7">Vai trò</th>
                        <th class="text-secondary opacity-7"></th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:if test="${not empty LIST_MODEL}">
                        <c:forEach var="item" items="${LIST_MODEL}">
                            <tr>
                                <td class="text-center">
                                    <label class="pos-rel">
                                        <input type="checkbox" class="form-check-input checkbox" value="${item.id}"/>
                                        <span class="lbl"></span>
                                    </label>
                                </td>
                                <!-- Id -->
                                <td>
                                    <div class="d-flex">
                                        <p class="text-xs font-weight-bold mx-auto mb-0">${item.id}</p>
                                    </div>
                                </td>
                                <!-- End Id -->
                                <!-- User Name -->
                                <td>
                                    <div class="d-flex px-2 py-1">
                                        <div class="d-flex flex-column justify-content-center">
                                            <p class="text-xs font-weight-bold mx-auto mb-0">${item.fullName}</p>
                                        </div>
                                    </div>
                                </td>
                                <td>
                                    <div class="d-flex px-2 py-1">
                                        <div class="d-flex flex-column justify-content-center">
                                            <p class="text-xs font-weight-bold mx-auto mb-0">${item.email}</p>
                                        </div>
                                    </div>
                                </td>
                                <!-- End User Association -->
                                <td>
                                    <div class="d-flex px-2 py-1">
                                        <div class="d-flex flex-column justify-content-center">
                                            <p class="text-xs font-weight-bold mx-auto mb-0">${item.association}</p>
                                        </div>
                                    </div>
                                </td>

                                <td>
                                    <div class="d-flex px-2 py-1">
                                        <div class="d-flex flex-column justify-content-center">
                                            <p class="text-xs font-weight-bold mx-auto mb-0">${item.birthDay}</p>
                                        </div>
                                    </div>
                                </td>

                                <td>
                                    <div class="d-flex px-2 py-1">
                                        <div class="d-flex flex-column justify-content-center">
                                            <p class="text-xs font-weight-bold mx-auto mb-0">${item.role.value}</p>
                                        </div>
                                    </div>
                                </td>
                                <!-- Action -->
                                <td class="align-middle">
                                    <a class="btn btn-link text-dark px-1 mb-0" data-bs-toggle="modal" href="#roleModal" onclick="setIdUserUpdateRole(${item.id},'${item.role.value}')"><i class="material-icons text-sm me-1">edit</i>Thay đổi vai trò</a>
                                    <a class="btn btn-link text-danger text-gradient px-1 mb-0"  data-bs-toggle="modal" href="#changePassModal" onclick="setIdUserChangePass(${item.id})"><i class="material-icons text-sm me-1">edit</i>Đổi mật khẩu</a>
                                </td>
                                <!-- End action -->
                            </tr>
                        </c:forEach>
                    </c:if>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>
<!-- change password Modal -->
<div class="modal fade" id="changePassModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title font-weight-normal" id="updateModalLabel"></h5>
                <button type="button" class="btn-close text-dark" data-bs-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <form id="changPassForm">
                <div class="modal-body">
                    <div class="input-group input-group-outline my-3">
                        <label class="form-label">Mật khẩu mới</label>
                        <input type="password" class="form-control" id="password">
                    </div>
                    <div class="input-group input-group-outline my-3">
                        <label class="form-label">Nhập lại mật khẩu mới</label>
                        <input type="password" class="form-control" id="confirmPassword">
                    </div>
                    <input type="hidden" id="userIdChangePass">
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-block btn-light" data-bs-dismiss="modal">Đóng</button>
                    <button type="submit" class="btn bg-gradient-danger">Lưu</button>
                </div>
            </form>
        </div>
    </div>
</div>
<%-- Change Role Model--%>
<div class="modal fade" id="roleModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <form method="POST">
        <div class="modal-dialog modal-dialog-centered" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title font-weight-normal" id="changePassModalLabel">Đổi vai trò người dùng</h5>
                    <button type="button" class="btn-close text-dark" data-bs-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <p>
                        Vai trò hiện tại của người dùng là <strong id="currentRole">user</strong>. Chọn vai trò muốn thay đổi:
                    </p>
                    <div class="input-group mb-3">
                        <select name="roleId" class="form-select" aria-label="Select role">
                            <option selected>Chọn vai trò</option>
                            <c:if test="${not empty LIST_ROLE}">
                                <c:forEach var="item" items="${LIST_ROLE}">
                                    <option value="${item.id}" class="text-uppercase">${item.value}</option>
                                </c:forEach>
                            </c:if>
                        </select>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-block btn-light" data-bs-dismiss="modal">Đóng</button>
                    <input type="hidden" name="action" value="put">
                    <input type="hidden" name="id" id="idUser">
                    <input type="hidden" name="detailAccount" value="changeRole">
                    <button type="submit" class="btn bg-gradient-primary">Lưu thay đổi</button>
                </div>
            </div>
        </div>
    </form>
</div>
<script>
    const setIdUserChangePass = (id) => {
        $("#updateModalLabel").text("Đổi mật khẩu cho người dùng có id "+id);
        $("#userIdChangePass").val(id);
    }
    const setIdUserUpdateRole = (id,currentRole) => {
        $("#currentRole").text(currentRole);
        $("#updateModalLabel").text("Cập nhật vai trò người dùng id "+id+"?");
        $("#roleModal #idUser").val(id);
    }
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

            $('#changePassModal').on('shown.bs.modal', function () {
                // Xóa tất cả các giá trị trong các input
                $('#password').val('');
                $('#confirmPassword').val('');
            });


            $('#changPassForm').submit(function (event) {
                event.preventDefault();
                // Hàm kiểm tra mật khẩu
                function validatePassword(field) {
                    let value = field.val();
                    let passwordRegex = /^(?=.*[!@#$%^&*])(?=.*[0-9])(?=.*[A-Z])(?=.*[a-z]).{8,}$/;
                    if (value === '' || value.length < 8 || !passwordRegex.test(value))
                        return true;
                    return false;
                }
                function require(field) {
                    return field.val().trim() === '';
                }

                let error = false;
                if (validatePassword($('#password'))) {
                    showError( "Mật khẩu chứa ít nhất 8 ký tự, bao gồm ký tự đặc biệt.");
                    error = true;
                } else if (require($('#confirmPassword'))) {
                    showError( "Vui lòng nhập lại mật khẩu.");
                    error = true;
                } else if ($('#password').val() !== $('#confirmPassword').val()) {
                    showError( "Mật khẩu không khớp.");
                    error = true;
                }
                if (!error) {
                    $.ajax({
                        url: '<c:url value="/admin/user/password"/>',
                        type: 'POST',
                        contentType: 'application/json',
                        data: JSON.stringify({
                            id: parseInt($('#userIdChangePass').val()),
                            password: $('#password').val().trim(),
                            confirmPassword: $('#confirmPassword').val().trim(),
                        }),
                        success: function (data) {
                            showSuccess( "Đổi mật khẩu thành công", 1000);
                            $('#password').val('');
                            $('#confirmPassword').val('');
                            $('#changePassModal').modal('hide');
                        },
                        error: function (data) {
                            showError( "Đổi mật khẩu thất bại");
                        }
                    });
                }

            });

            //check all
            $("#checkAll").change(function() {
                $(".checkbox").prop('checked', $(this).prop("checked"));
            });

            $('#btnDelete').on('click', function () {
                Swal.fire({
                    title: "Xóa ?",
                    text: "Bạn có muốn xóa những người dùng này?",
                    icon: "warning",
                    showCancelButton: true,
                    confirmButtonColor: "#3085d6",
                    cancelButtonColor: "#d33",
                    cancelButtonText: "Hủy",
                    confirmButtonText: "Xóa"
                }).then((result) => {
                    if (result.isConfirmed) {
                        var ids = [];
                        $('input[type="checkbox"].checkbox:checked').each(function() {
                            if ($(this).attr('id') !== 'checkAll') {
                                ids.push($(this).val()); // Thêm giá trị của checkbox được chọn vào mảng
                            }
                        });
                        console.log(ids)
                        $.ajax({
                            url: '<c:url value="/admin/user/list"/>',
                            method: 'DELETE',
                            contentType: "application/json; charset=utf-8",
                            // dataType: "json",
                            data: JSON.stringify(ids),
                            success: function (response) {
                                showSuccess("Xóa thành công", 1000)
                                setTimeout(function () {
                                    window.location.reload();
                                }, 1000);
                            },
                            error: function (error) {
                                console.log('that bai')
                                showError("Xóa thất bại")
                            }
                        })

                    }
                });
            });
            function showSuccess(mess, time) {
                Swal.fire({
                    icon: "success",
                    title: mess,
                    toast: true,
                    position: "top-end",
                    showConfirmButton: false,
                    timer: time,
                    timerProgressBar: true,
                    didOpen: (toast) => {
                        toast.onmouseenter = Swal.stopTimer;
                        toast.onmouseleave = Swal.resumeTimer;
                    }
                });
            }

            function showError(mess) {
                Swal.fire({
                    icon: "warning",
                    title: mess,
                    toast: true,
                    position: "top-end",
                    showConfirmButton: false,
                    timer: 1500,
                    timerProgressBar: true,
                    didOpen: (toast) => {
                        toast.onmouseenter = Swal.stopTimer;
                        toast.onmouseleave = Swal.resumeTimer;
                    }
                });
            }
        });


    })
</script>
</body>
</html>
