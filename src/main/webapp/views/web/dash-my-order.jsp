<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.ltweb_servlet_ecommerce.constant.SystemConstant" %>
<%@ page import="com.ltweb_servlet_ecommerce.utils.StatusMapUtil" %>
<fmt:setLocale value="vi_VN"/>
<html lang="en">

<head>
    <title>Đơn hàng</title>
</head>

<body>
<div id="app">

    <div class="container-content mt-5 ">
        <!--====== Section 1 ======-->
        <div class="u-s-p-b-60">

            <!--====== Section Content ======-->
            <div class="section__content">
                <div class="dash">
                    <div class="d-flex justify-content-center">
                        <table class="dash__table" id="dataTable">
                            <thead>
                            <tr>
                                <th class="text-center">#</th>
                                <th class="text-center">Thời gian</th>
                                <th class="text-center">Trạng thái</th>
                                <th class="text-center">Trị giá</th>
                                <th class="text-center"></th>
                            </tr>
                            </thead>
                            <tbody>

                            <c:forEach var="order" items="${requestScope.orders}">
                                <tr>
                                    <td class="text-center">${order.slug}</td>
                                    <td class="text-center">
                                        <fmt:formatDate value="${order.createAt}"
                                                        pattern="yyy-MM-dd hh:mm:ssa"/>
                                    </td>
                                    <td class="text-center">
                                            <c:if test="${order.status eq StatusMapUtil.getStatusKey(SystemConstant.ORDER_DELIVERED)}">
                                                <c:set var="contextual" value="success"/>
                                            </c:if>
                                            <c:if test="${order.status eq StatusMapUtil.getStatusKey(SystemConstant.ORDER_TRANSPORTING)}">
                                                <c:set var="contextual" value="info"/>
                                            </c:if>
                                            <c:if test="${order.status eq StatusMapUtil.getStatusKey(SystemConstant.ORDER_CANCEL)}">
                                                <c:set var="contextual" value="danger"/>
                                            </c:if>
                                            <c:if test="${order.status eq StatusMapUtil.getStatusKey(SystemConstant.ORDER_PROCESSING)}">
                                                <c:set var="contextual" value="primary"/>
                                            </c:if>
                                        <span class="badge bg-${contextual}" style="font-size: inherit">${StatusMapUtil.getStatusValue(order.status)}</span>
                                    </td>
                                    <td class="text-center">
                                        <div class="dash__table-total">
                                            <span><fmt:formatNumber type="currency" value="${order.totalAmount}"/></span>
                                        </div>
                                    </td>
                                    <td class="text-center">
                                        <div class="dash__link dash__link--brand">
                                            <a style="font-weight: 700; font-style: italic" href="<c:url value="/order-details/${order.slug}"/>">Chi tiết</a>
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
</div>
</body>


<%--<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.7.1/jquery.min.js"--%>
<%--        integrity="sha512-v2CJ7UaYy4JwqLDIrZUI/4hqeoQieOmAZNXBeQyjo21dadnwR+8ZaIJVT8EE2iyI61OV8e6M8PP2/4hpQINQ/g=="--%>
<%--        crossorigin="anonymous" referrerpolicy="no-referrer"></script>--%>
<%--<script src="https://cdn.datatables.net/1.13.7/js/jquery.dataTables.min.js"></script>--%>
<%--<script src="https://cdn.datatables.net/1.13.7/js/dataTables.bootstrap4.min.js"></script>--%>
<script>
    window.addEventListener("DOMContentLoaded", function () {
        $(document).ready(function () {
            // new DataTable('#dataTable', {
            //     pagingType: 'simple_numbers',
            //     language: {
            //         paginate: {
            //             previous: '<',
            //             next: '>'
            //         }
            //     }
            // });
            $("table").each(function () {
                new DataTable(this);
            })
        });

    });
</script>
</html>