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
    <title>Admin Nai - Danh mục</title>
</head>
<body>
<div class="col-12 mt-4">
    <!-- Add new category -->
    <div class="mb-5 ps-3">
        <a class="btn bg-gradient-dark mb-0" id="toggleAddProduct" data-bs-toggle="collapse" href="#formNewProduct"><i class="material-icons text-sm">add</i>&nbsp;&nbsp;Thêm thể loại</a>
        <div class="collapse multi-collapse my-3" id="formNewProduct">
            <div class="card py-2 px-4" >
                <form id="saveNewCategory" method="POST">
                    <div class="input-group input-group-outline my-3">
                        <label class="form-label">Tên danh mục</label>
                        <input type="text" class="form-control" name="name">
                    </div>
                    <div class="input-group input-group-outline my-3">
                        <label class="form-label">Mã danh mục</label>
                        <input type="text" class="form-control" name="code">
                    </div>
                    <input type="hidden" name="action" value="add">
                    <button class="btn btn-primary" type="submit">Thêm danh mục</button>
                </form>
            </div>
        </div>
    </div>
    <!-- End add new categorys -->
    <!-- List Size -->
    <div class="card my-4">
        <div class="card-header p-0 position-relative mt-n4 mx-3 z-index-2">
            <div class="bg-gradient-primary shadow-primary border-radius-lg pt-4 pb-3">
                <h6 class="text-white text-capitalize ps-3">Bảng các danh mục</h6>
            </div>
        </div>

        <div class="card-body px-0 pb-2">
            <div class="table-responsive p-0">
                <table class="table align-items-center mb-0" id="dataTable">
                    <thead>
                    <tr>
                        <th class="text-uppercase text-secondary text-xxs font-weight-bolder opacity-7">ID</th>
                        <th class="text-uppercase text-secondary text-xxs font-weight-bolder opacity-7">Tên danh mục</th>
                        <th class="text-uppercase text-secondary text-xxs font-weight-bolder opacity-7">Mã danh mục</th>
                        <th class="text-uppercase text-secondary text-xxs font-weight-bolder opacity-7">Cập nhật lúc</th>
                        <th class="text-secondary opacity-7"></th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:if test="${not empty LIST_MODEL}">
                        <c:forEach var="item" items="${LIST_MODEL}">
                            <tr>
                                <!-- Id -->
                                <td>
                                    <div class="d-flex">
                                        <p class="text-xs font-weight-bold mx-auto mb-0">${item.id}</p>
                                    </div>
                                </td>
                                <!-- End Id -->
                                <!-- Category Name -->
                                <td>
                                    <div class="d-flex px-2 py-1">
                                        <div class="d-flex flex-column justify-content-center">
                                            <p class="text-xs font-weight-bold mx-auto mb-0">${item.name}</p>
                                        </div>
                                    </div>
                                </td>
                                <td>
                                    <div class="d-flex px-2 py-1">
                                        <div class="d-flex flex-column justify-content-center">
                                            <p class="text-xs font-weight-bold mx-auto mb-0">${item.code}</p>
                                        </div>
                                    </div>
                                </td>
                                <!-- End Category Code -->
                                <td>
                                    <div class="d-flex px-2 py-1">
                                        <div class="d-flex flex-column justify-content-center">
                                            <p class="text-xs font-weight-bold mx-auto mb-0">${item.updateAt}</p>
                                        </div>
                                    </div>
                                </td>
                                <!-- Action -->
                                <td class="align-middle">
                                    <a class="btn btn-link text-dark px-1 mb-0" data-bs-toggle="modal" href="#updateModal" onclick="setIdUpdate(${item.id},'${item.name}','${item.code}')"><i class="material-icons text-sm me-1">edit</i>Sửa</a>
                                    <a class="btn btn-link text-danger text-gradient px-1 mb-0"  data-bs-toggle="modal" href="#deleteModal" onclick="setIdDelete(${item.id},'${item.name}')"><i class="material-icons text-sm me-1">delete</i>Xóa</a>
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
<%--    Modal update--%>
<div class="modal fade" id="updateModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title font-weight-normal" id="updateModalLabel"></h5>
                <button type="button" class="btn-close text-dark" data-bs-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <form method="POST">
                <div class="modal-body">
                    <div class="input-group input-group-outline my-3">
                        <label class="form-label">Tên danh mục</label>
                        <input type="text" class="form-control" name="name" id="categoryNameUpdate">
                    </div>
                    <div class="input-group input-group-outline my-3">
                        <label class="form-label">Mã danh mục</label>
                        <input type="text" class="form-control" name="code" id="categoryCodeUpdate">
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
<!-- Delete Modal -->
<div class="modal fade" id="deleteModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title font-weight-normal" id="deleteModalLabel"></h5>
                <button type="button" class="btn-close text-dark" data-bs-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                Bạn có chắc chắn muốn xóa danh mục này?
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-block btn-light" data-bs-dismiss="modal">Close</button>
                <form method="POST">
                    <input type="hidden" name="action" value="delete">
                    <input type="hidden" name="id" id="idDelete">
                    <button type="submit" class="btn bg-gradient-danger">Xóa</button>
                </form>
            </div>
        </div>
    </div>
</div>
<script>
    const setIdDelete = (id,name) => {
        $("#deleteModalLabel").text("Xóa danh mục "+name+"?");
        $("#idDelete").val(id);
    }
    const setIdUpdate = (id,name,code) => {
        $("#updateModalLabel").text("Sửa danh mục "+name);
        $("#idUpdate").val(id);
        $("#categoryNameUpdate").val(name);
        $("#categoryCodeUpdate").val(code);
    }
</script>
<!-- End Delete Modal -->
</body>
</html>
