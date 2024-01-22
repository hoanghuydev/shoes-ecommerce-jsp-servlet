<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: HUY
  Date: 12/30/2023
  Time: 10:05 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<div class="row">
    <div class="col-12">
        <div class="card my-4">
            <div class="card-header p-0 position-relative mt-n4 mx-3 z-index-2">
                <div class="bg-gradient-primary shadow-primary border-radius-lg pt-4 pb-3">
                    <h6 class="text-white text-capitalize ps-3">Orders Table</h6>
                </div>
            </div>
            <div class="card-body px-0 pb-2">
                <div class="table-responsive p-0">
                    <table class="table align-items-center mb-0" id="dataTable">
                        <thead>
                        <tr>
                            <th class="text-uppercase text-secondary text-xxs font-weight-bolder opacity-7">ID</th>
                            <th class="text-uppercase text-secondary text-xxs font-weight-bolder opacity-7">Customer Info</th>
                            <th class="text-uppercase text-secondary text-xxs font-weight-bolder opacity-7 ps-2">Address</th>
                            <th class="text-center text-uppercase text-secondary text-xxs font-weight-bolder opacity-7">Status</th>
                            <th class="text-center text-uppercase text-secondary text-xxs font-weight-bolder opacity-7">Product</th>
                            <th class="text-center text-uppercase text-secondary text-xxs font-weight-bolder opacity-7">Total</th>
                            <th class="text-center text-uppercase text-secondary text-xxs font-weight-bolder opacity-7">Note</th>
                            <th class="text-center text-uppercase text-secondary text-xxs font-weight-bolder opacity-7">Create At</th>
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
                                            <p class="text-xs text-secondary mb-0">0${order_item.addressModel.phoneNumber}</p>
                                        </div>
                                    </div>
                                </td>
                                <!-- End Customer -->

                                <td style="cursor: pointer;" data-bs-toggle="tooltip" data-bs-placement="top" title="${order_item.addressModel.hamlet},${order_item.addressModel.commune},${order_item.addressModel.district},${order_item.addressModel.province}" >
                                    <p class="text-xs font-weight-bold mb-0">${order_item.addressModel.province}</p>
                                    <p class="text-xs text-secondary mb-0">${order_item.addressModel.district}</p>
                                </td>
                                <td class="align-middle text-center text-sm">
                                    <span class="badge badge-sm bg-gradient-success">${order_item.status}</span>
                                </td>
                                <!-- Product list -->
                                <td class="align-middle text-center">
                                    <c:forEach var="product_item" items="${order_item.listProduct}">
                                        <p class="text-xs font-weight-bold mb-0">${product_item.quantity} - ${product_item.name} - ${product_item.sizeName}</p>
                                    </c:forEach>
                                </td>
                                <!-- End Product list -->
                                <!-- Total -->
                                <td class="align-middle text-center">
                                    <p class="text-xs text-secondary mb-0">$${order_item.totalAmount}</p>
                                </td>
                                <!-- End total -->
                                <!-- Note -->
                                <td style="cursor: pointer;" data-bs-toggle="tooltip" data-bs-placement="top" title="${order_item.note}" >
                                    <div class="d-flex">
                                        <p class="text-xs font-weight-bold mx-auto mb-0">
                                            <c:if test="${not empty order_item.note || order_item.note != ''}">
                                                Detail note...
                                            </c:if>
                                            <c:if test="${ empty order_item.note || order_item.note == ''}">
                                                Empty note...
                                            </c:if>
                                        </p>
                                    </div>
                                </td>
                                <!-- End Note -->
                                <td >
                                    <div class="d-flex">
                                        <p class="text-xs font-weight-bold mx-auto mb-0">${order_item.createAt}</p>
                                    </div>
                                </td>
                                <!-- Action -->
                                <td class="align-middle">
                                    <p class="m-0 p-0">GHTK</p>
                                    <div class="d-flex">
                                        <a class="btn btn-link text-success px-1 mb-0" id="pushOrder${order_item.id}" href="javascript:;"><i class="fa-solid fa-truck me-1"></i>Push</a>
                                        <a class="btn btn-link text-primary px-1 mb-0" id="pushOrder${order_item.id}" href="javascript:;"><i class="fa-solid fa-rectangle-xmark me-1"></i>Cancel</a>
                                    </div>
                                    <p class="m-0 p-0">CRM</p>
                                    <div class="d-flex">
                                        <a class="btn btn-link text-dark px-1 mb-0" id="editOrder${order_item.id}" href="javascript:;"><i class="material-icons text-sm me-1">edit</i>Edit</a>
                                        <a class="btn btn-link text-danger text-gradient px-1 mb-0" id="removeOrder${order_item.id}" href="javascript:;"><i class="material-icons text-sm me-1">delete</i>Delete</a>
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

<script >
    window.addEventListener("DOMContentLoaded",function (){
        $("table").each(function (){
            new DataTable(this);
        })
    })
</script>
</body>
</html>
