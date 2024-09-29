<%--
  Created by IntelliJ IDEA.
  User: Noguu
  Date: 6/5/2024
  Time: 8:53 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/common/taglib.jsp" %>

<html>
<head>
    <title>Admin Nai - Tồn Kho</title>
</head>
<body>
    <div class="col-12 mt-4">

        <div class="card my-4">
            <div class="card-header p-0 position-relative mt-n4 mx-3 z-index-2">
                <div class="bg-gradient-primary shadow-primary border-radius-lg pt-4 pb-3">
                    <h6 class="text-white text-capitalize ps-3">Danh sách tồn kho</h6>
                </div>
            </div>

            <div class="card-body px-0 pb-2">
                <div class="table-responsive p-0">
                    <table class="table align-items-center mb-0" id="dataTable">
                        <thead>
                        <tr>
                            <th class="text-uppercase text-secondary text-xxs font-weight-bolder opacity-7" style="text-align: center">Tên sản phẩm</th>
                            <th class="text-uppercase text-secondary text-xxs font-weight-bolder opacity-7">Kích thước</th>
                            <th class="text-uppercase text-secondary text-xxs font-weight-bolder opacity-7">Số lượng tồn kho</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:if test="${not empty LIST_MODEL}">
                            <c:forEach var="item" items="${LIST_MODEL}">
                                <tr>
                                    <!-- Name -->
                                    <td>
                                        <div class="d-flex px-2 py-1">
                                            <p class="text-xs font-weight-bold mx-auto mb-0">${item.productName}</p>
                                        </div>
                                    </td>
                                    <!-- End name -->
                                    <!-- Size -->
                                    <td>
                                        <div class="d-flex px-2 py-1">
                                            <div class="d-flex flex-column justify-content-center">
                                                <p class="text-xs font-weight-bold mx-auto mb-0">${item.size}</p>
                                            </div>
                                        </div>
                                    </td>
                                    <!-- end size-->
                                    <!-- quantity-->
                                    <td>
                                        <div class="d-flex px-2 py-1">
                                            <div class="d-flex flex-column justify-content-center">
                                                <p class="text-xs font-weight-bold mx-auto mb-0">${item.quantity}</p>
                                            </div>
                                        </div>
                                    </td>
                                    <!-- End quantity  -->
<%--                                    <!-- Action -->--%>
<%--                                    <td class="align-middle">--%>
<%--                                        <a class="btn btn-link text-dark px-1 mb-0" data-bs-toggle="modal" href="#updateModal" onclick="setIdUpdate(${item.id},'${item.name}')"><i class="material-icons text-sm me-1">edit</i>Sửa</a>--%>
<%--                                        <a class="btn btn-link text-danger text-gradient px-1 mb-0"  data-bs-toggle="modal" href="#deleteModal" onclick="setIdDelete(${item.id},'${item.name}')"><i class="material-icons text-sm me-1">delete</i>Xóa</a>--%>
<%--                                    </td>--%>
<%--                                    <!-- End action -->--%>
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
                            <label class="form-label">Tên kích thước</label>
                            <input type="text" class="form-control" name="name" id="sizeNameUpdate">
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
              Bạn có chắc chắn muốn xóa?
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
            $("#deleteModalLabel").text("Xóa kích thước "+name+"?");
            $("#idDelete").val(id);
        }
        const setIdUpdate = (id,name) => {
            $("#updateModalLabel").text("Cập nhật kích thước "+name);
            $("#idUpdate").val(id);
            $("#sizeNameUpdate").val(name);
        }
      </script>
      <!-- End Delete Modal -->
</body>
</html>
