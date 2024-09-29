<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.ltweb_servlet_ecommerce.service.impl.OrderService" %>
<%@ page import="java.util.List" %>
<%@ page import="com.google.gson.Gson" %>
<%@ page import="com.ltweb_servlet_ecommerce.service.impl.ImportOrderService" %>
<%@ page import="java.text.DecimalFormat" %>
<%@ page import="com.ltweb_servlet_ecommerce.service.impl.ProductSizeService" %>
<%@ page import="com.ltweb_servlet_ecommerce.service.impl.UserService" %>
<%@ page import="java.text.NumberFormat" %>
<%@ page import="java.util.Locale" %>
<fmt:setLocale value="vi_VN"/>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">

    <title>Admin Nai - Dashboard</title>
    <script>
        // Set new default font family and font color for charts
        Chart.defaults.global.defaultFontFamily = 'Nunito, -apple-system,system-ui,BlinkMacSystemFont,"Segoe UI",Roboto,"Helvetica Neue",Arial,sans-serif';
        Chart.defaults.global.defaultFontColor = '#858796';
    </script>

</head>
<body>

<!-- Earnings (Monthly) Card Example -->
<div class="row">
    <div class="col-xl-3 col-md-6 mb-4">
        <div class="card border-left-primary shadow h-100 py-2">
            <div class="card-body">
                <div class="row no-gutters align-items-center">
                    <div class="col mr-2">
                        <div class="text-xs font-weight-bold text-primary text-uppercase mb-1">
                            Tổng Doanh thu</div>
                        <%  OrderService orderService = new OrderService();
                            double totalOrderPrice = orderService.getTotalPrice();
                            NumberFormat vndFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
                            String formattedOrderPrice = vndFormat.format(totalOrderPrice);
                           %>
                        <div class="h5 mb-0 font-weight-bold text-gray-800"><%=formattedOrderPrice%></div>
                    </div>
                    <div class="col-auto">
                        <i class="fas fa-calendar fa-2x text-gray-300"></i>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <!-- Earnings (Annual) Card Example -->
    <div class="col-xl-3 col-md-6 mb-4">
        <div class="card border-left-success shadow h-100 py-2">
            <div class="card-body">
                <div class="row no-gutters align-items-center">
                    <div class="col mr-2">
                        <div class="text-xs font-weight-bold text-success text-uppercase mb-1">
                            Tổng Chi phí nhập hàng</div>
                        <%  ImportOrderService importOrderService = new ImportOrderService();
                            double totalImportPrice = importOrderService.getTotalImportPrice();
                            String formattedImportPrice = vndFormat.format(totalImportPrice);%>
                        <div class="h5 mb-0 font-weight-bold text-gray-800"><%=formattedImportPrice%></div>
                    </div>
                    <div class="col-auto">
                        <i class="fas fa-dollar-sign fa-2x text-gray-300"></i>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <!-- Earnings (Monthly) Card Example -->
    <div class="col-xl-3 col-md-6 mb-4">
        <div class="card border-left-primary shadow h-100 py-2">
            <div class="card-body">
                <div class="row no-gutters align-items-center">
                    <div class="col mr-2">
                        <div class="text-xs font-weight-bold text-primary text-uppercase mb-1">
                            Tổng Lợi Nhuận</div>
                        <%
                            ProductSizeService productSizeService = new ProductSizeService();
                            double totalProfit = productSizeService.getTotalProfit();
                            String formattedProfitPrice = vndFormat.format(totalProfit);
                        %>
                        <div class="h5 mb-0 font-weight-bold text-gray-800"><%=formattedProfitPrice%></div>
                    </div>
                    <div class="col-auto">
                        <i class="fas fa-calendar fa-2x text-gray-300"></i>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <!-- Earnings (Annual) Card Example -->
    <div class="col-xl-3 col-md-6 mb-4">
        <div class="card border-left-success shadow h-100 py-2">
            <div class="card-body">
                <div class="row no-gutters align-items-center">
                    <div class="col mr-2">
                        <div class="text-xs font-weight-bold text-success text-uppercase mb-1">
                            Tổng số người dùng</div>

                        <div class="h5 mb-0 font-weight-bold text-gray-800"><%=new UserService().getUserCount()%></div>
                    </div>
                    <div class="col-auto">
                        <i class="fas fa-users fa-2x text-gray-300"></i>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<!-- Bar Chart -->
<div class="card shadow mb-4">
    <div class="card-header py-3">
        <h6 class="m-0 font-weight-bold text-primary">Thống kê số đơn hàng:</h6>
        <hr>
        <div>
            <label for="yearSelect">Chọn năm:</label>
<%--            <form action="/admin/dashboard" method="post">--%>

            <form id="yearForm">
                <select id="yearSelect" name="year">
                    <option value="">Năm</option>
                    <c:forEach var="year" items="${listYear}">
                        <c:choose>
                            <c:when test="${year == currentYear}">
                                <option value="${year}" selected>${year}</option>
                            </c:when>
                            <c:otherwise>
                                <option value="${year}">${year}</option>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                </select>
            </form>
        </div>
        <hr>
    </div>
    <div class="card-body">
        <div class="chart-bar">
            <canvas id="myBarChart2"></canvas>
        </div>

    </div>
</div>

<div class="card shadow mb-4">
    <div class="card-header py-3">
        <h6 class="m-0 font-weight-bold text-primary">Thống kê đơn hàng theo tháng</h6>

        <form id="form2">
            <div class="">
                <select class=" form-select-lg mb-3" style="margin-left: 5px; padding: 5px" id="year2" name="year">
                    <option value="">Năm</option>
                    <option value="2020">2020</option>
                    <option value="2021">2021</option>
                    <option value="2022">2022</option>
                    <option value="2023">2023</option>
                    <option value="2024">2024</option>
                </select>

                <select class=" form-select-lg mb-3" style="margin-left: 5px; padding: 5px" id="month2" name="month">
                    <option value="">Tháng</option>
                    <option value="1">1</option>
                    <option value="2">2</option>
                    <option value="3">3</option>
                    <option value="4">4</option>
                    <option value="5">5</option>
                    <option value="6">6</option>
                    <option value="7">7</option>
                    <option value="8">8</option>
                    <option value="9">9</option>
                    <option value="10">10</option>
                    <option value="11">11</option>
                    <option value="12">12</option>
                </select>
                <button class="form-select-lg mb-3" id="staticticsBtn" style="margin-left: 5px; padding: 3px" type="submit">Thống kê</button>
            </div>
        </form>





    </div>

    <div class="card-body px-0 pb-2">
        <div class="table-responsive p-0">
            <table class="table align-items-center mb-0" id="dataTable">
                <thead>
                <tr>
                    <th class="text-uppercase text-secondary text-xxs font-weight-bolder opacity-7" >STT</th>
                    <th class="text-uppercase text-secondary text-xxs font-weight-bolder opacity-7" >ID người dùng</th>
                    <th class="text-uppercase text-secondary text-xxs font-weight-bolder opacity-7" >Tên người dùng</th>
                    <th class="text-uppercase text-secondary text-xxs font-weight-bolder opacity-7">ID đơn hàng</th>
                    <th class="text-uppercase text-secondary text-xxs font-weight-bolder opacity-7">Tổng giá tiền đơn hàng</th>
                    <th class="text-uppercase text-secondary text-xxs font-weight-bolder opacity-7" >Thời gian</th>

                </tr>
                </thead>
                <tbody>
<%--                <c:if test="${not empty LIST_MODEL}">--%>
<%--                    <c:forEach var="item" items="${LIST_MODEL}">--%>
<%--                        <tr>--%>
<%--                            <!-- Name -->--%>
<%--                            <td>--%>
<%--                                <div class="d-flex px-2 py-1">--%>
<%--                                    <p class="text-xs font-weight-bold mx-auto mb-0">${item.productName}</p>--%>
<%--                                </div>--%>
<%--                            </td>--%>
<%--                            <!-- End name -->--%>
<%--                            <!-- Size -->--%>
<%--                            <td>--%>
<%--                                <div class="d-flex px-2 py-1">--%>
<%--                                    <div class="d-flex flex-column justify-content-center">--%>
<%--                                        <p class="text-xs font-weight-bold mx-auto mb-0">${item.size}</p>--%>
<%--                                    </div>--%>
<%--                                </div>--%>
<%--                            </td>--%>
<%--                            <!-- end size-->--%>
<%--                            <!-- quantity-->--%>
<%--                            <td>--%>
<%--                                <div class="d-flex px-2 py-1">--%>
<%--                                    <div class="d-flex flex-column justify-content-center">--%>
<%--                                        <p class="text-xs font-weight-bold mx-auto mb-0">${item.quantity}</p>--%>
<%--                                    </div>--%>
<%--                                </div>--%>
<%--                            </td>--%>
<%--                        </tr>--%>
<%--                    </c:forEach>--%>
<%--                </c:if>--%>
                </tbody>
            </table>
        </div>
    </div>
</div>


<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script type="text/javascript" charset="utf8" src="https://cdn.datatables.net/1.11.5/js/jquery.dataTables.js"></script>
<link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.11.5/css/jquery.dataTables.css">
<link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/buttons/2.2.3/css/buttons.dataTables.min.css">
<script type="text/javascript" charset="utf8" src="https://cdn.datatables.net/buttons/2.2.3/js/dataTables.buttons.min.js"></script>
<script type="text/javascript" charset="utf8" src="https://cdnjs.cloudflare.com/ajax/libs/jszip/3.1.3/jszip.min.js"></script>
<script type="text/javascript" charset="utf8" src="https://cdn.datatables.net/buttons/2.2.3/js/buttons.html5.min.js"></script>


<script>
    var currentYear = new Date().getFullYear();
</script>

<script>
    // Set new default font family and font color to mimic Bootstrap's default styling

    function number_format(number, decimals, dec_point, thousands_sep) {
        // *     example: number_format(1234.56, 2, ',', ' ');
        // *     return: '1 234,56'
        number = (number + '').replace(',', '').replace(' ', '');
        var n = !isFinite(+number) ? 0 : +number,
            prec = !isFinite(+decimals) ? 0 : Math.abs(decimals),
            sep = (typeof thousands_sep === 'undefined') ? ',' : thousands_sep,
            dec = (typeof dec_point === 'undefined') ? '.' : dec_point,
            s = '',
            toFixedFix = function(n, prec) {
                var k = Math.pow(10, prec);
                return '' + Math.round(n * k) / k;
            };
        // Fix for IE parseFloat(0.55).toFixed(0) = 0;
        s = (prec ? toFixedFix(n, prec) : '' + Math.round(n)).split('.');
        if (s[0].length > 3) {
            s[0] = s[0].replace(/\B(?=(?:\d{3})+(?!\d))/g, sep);
        }
        if ((s[1] || '').length < prec) {
            s[1] = s[1] || '';
            s[1] += new Array(prec - s[1].length + 1).join('0');
        }
        return s.join(dec);
    }


    // Mã JavaScript cho biểu đồ cột 2
    var ctx2 = document.getElementById("myBarChart2");
    var myBarChart2 = new Chart(ctx2, {
        type: 'bar',
        data: {
            labels: ["Tháng 1", "Tháng 2", "Tháng 3", "Tháng 4","Tháng 5","Tháng 6","Tháng 7","Tháng 8","Tháng 9","Tháng 10","Tháng 11","Tháng 12",],
            datasets: [{
                label: "Số đơn hàng ",
                backgroundColor: "#e74a3b",
                hoverBackgroundColor: "#e74a3b",
                borderColor: "#e74a3b",
                data: [0,0,0,0,0,0,0,0,0,0,0,0],
            }],
        },
        options: {
            // Các tùy chọn...
            maintainAspectRatio: false,
            layout: {
                padding: {
                    left: 10,
                    right: 25,
                    top: 25,
                    bottom: 0
                }
            },
            scales: {
                xAxes: [{
                    time: {
                        unit: 'month'
                    },
                    gridLines: {
                        display: false,
                        drawBorder: false
                    },
                    ticks: {
                        maxTicksLimit: 6
                    },
                    maxBarThickness: 25,
                }],
                yAxes: [{
                    ticks: {
                        min: 0,
                        max: 15000,
                        maxTicksLimit: 5,
                        padding: 10,
                        // Include a dollar sign in the ticks
                        callback: function(value, index, values) {
                            return  number_format(value);
                        }
                    },
                    gridLines: {
                        color: "rgb(234, 236, 244)",
                        zeroLineColor: "rgb(234, 236, 244)",
                        drawBorder: false,
                        borderDash: [2],
                        zeroLineBorderDash: [2]
                    }
                }],
            },
            legend: {
                display: false
            },
            tooltips: {
                titleMarginBottom: 10,
                titleFontColor: '#6e707e',
                titleFontSize: 14,
                backgroundColor: "rgb(255,255,255)",
                bodyFontColor: "#858796",
                borderColor: '#dddfeb',
                borderWidth: 1,
                xPadding: 15,
                yPadding: 15,
                displayColors: false,
                caretPadding: 10,
                callbacks: {
                    label: function(tooltipItem, chart) {
                        var datasetLabel = chart.datasets[tooltipItem.datasetIndex].label || '';
                        return datasetLabel + ': $' + number_format(tooltipItem.yLabel);
                    }
                }
            },
        }
    });

    $(document).ready(function() {
        // Update chart on page load
        var initialYear = $('#yearSelect').val();
        updateBarChart(initialYear);

        // Handle year selection change
        $('#yearSelect').change(function() {
            var selectedYear = $(this).val();
            updateBarChart(selectedYear);
        });
    });

    function updateBarChart(year) {
        $.ajax({
            url: '/admin/dashboard/barchart',
            type: 'POST',
            data: { year: year },
            success: function(data) {
                myBarChart2.data.datasets[0].data = data;
                myBarChart2.update();
            },
            error: function(error) {
                console.error('Error fetching data', error);
            }
        });
    }

</script>
<script>
    $(document).ready(function () {
        $('#form2').on('submit', function (e) {
            e.preventDefault();

            let year = $('#year2').val();
            let month = $('#month2').val();

            if (!year || !month) {
                alert("Vui lòng chọn cả năm và tháng!");
                return;
            }

            if ($.fn.DataTable.isDataTable('#dataTable')) {
                $('#dataTable').DataTable().destroy();
            }

            $('#dataTable').DataTable({
                "ajax": {
                    "url": "/admin/dashboard/statictics",
                    "type": "GET",
                    "data": {
                        "year": year,
                        "month": month
                    },
                    "dataSrc": "",
                },
                "columns": [
                    { "data": "stt" },
                    { "data": "userId" },
                    { "data": "userFullName" },
                    { "data": "orderId" },
                    { "data": "orderPrice" },
                    { "data": "orderTime" }
                ],
                "dom": 'Bfrtip',
                "buttons": [
                    {
                        extend: 'excelHtml5',
                        title: 'Statistics Data',
                        text: 'Export to Excel'
                    }
                ]
            });
        });
    });
</script>
</body>
</html>