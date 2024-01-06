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
                <table class="table align-items-center mb-0" id="dataTable">
                    <thead>
                    <tr>
                        <th class="text-uppercase text-secondary text-xxs font-weight-bolder opacity-7">ID</th>
                        <th class="text-uppercase text-secondary text-xxs font-weight-bolder opacity-7">Full Name</th>
                        <th class="text-uppercase text-secondary text-xxs font-weight-bolder opacity-7">Email</th>
                        <th class="text-uppercase text-secondary text-xxs font-weight-bolder opacity-7">Association</th>
                        <th class="text-uppercase text-secondary text-xxs font-weight-bolder opacity-7">Birthday</th>
                        <th class="text-uppercase text-secondary text-xxs font-weight-bolder opacity-7">Admin</th>
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
                                            <p class="text-xs font-weight-bold mx-auto mb-0">${item.admin}</p>
                                        </div>
                                    </div>
                                </td>
                                <!-- Action -->
                                <td class="align-middle">
                                    <a class="btn btn-link text-dark px-1 mb-0" ><i class="material-icons text-sm me-1">edit</i>Edit</a>
                                    <a class="btn btn-link text-danger text-gradient px-1 mb-0"  data-bs-toggle="modal" href="#deleteModal" onclick="setId(${item.id})"><i class="material-icons text-sm me-1">delete</i>Delete</a>
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
                Are you sure to delete?
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-block btn-light" data-bs-dismiss="modal">Close</button>
                <form method="POST">
                    <input type="hidden" name="action" value="delete">
                    <input type="hidden" name="id" id="idDelete">
                    <button type="submit" class="btn bg-gradient-danger">Delete</button>
                </form>
            </div>
        </div>
    </div>
</div>
<script>
    const setId = (id) => {
        $("#deleteModalLabel").text("Delete User Id "+id+"?");
        $("#idDelete").val(id);
    }
</script>
<!-- End Delete Modal -->
</body>
</html>
