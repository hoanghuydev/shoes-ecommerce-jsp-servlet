package com.ltweb_servlet_ecommerce.controller.admin.importOrder;

import com.ltweb_servlet_ecommerce.constant.SystemConstant;
import com.ltweb_servlet_ecommerce.model.ImportOrderModel;
import com.ltweb_servlet_ecommerce.service.IImportOrderDetailService;
import com.ltweb_servlet_ecommerce.service.IImportOrderService;
import com.ltweb_servlet_ecommerce.utils.AuthRole;
import com.ltweb_servlet_ecommerce.utils.HttpUtil;
import com.ltweb_servlet_ecommerce.utils.NotifyUtil;

import javax.inject.Inject;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@MultipartConfig(
        maxFileSize = 1024 * 1024,// 1MB
        maxRequestSize = 1024 * 1024 * 10//10MB
)
@WebServlet(urlPatterns = "/admin/import-order")
public class ImportOrderController extends HttpServlet {
    @Inject
    private IImportOrderService importOrderService;
    @Inject
    private IImportOrderDetailService detailService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        NotifyUtil.setUp(req);
        List<ImportOrderModel> list = importOrderService.findAll(null);
        req.setAttribute(SystemConstant.LIST_MODEL, list);
        RequestDispatcher rd = req.getRequestDispatcher("/views/admin/import-order/list.jsp");
        rd.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        long start = System.currentTimeMillis();
        //validate
        boolean hasError = validate(request);

        // Check if there are no validation errors
        if (!hasError) {
            // Retrieve supplier and importId from the request parameters
            String supplier = request.getParameter("supplier");
            String importId = request.getParameter("importId");

            // Check if the importId already exists
            if (importOrderService.isExists(importId)) {
                // If it does, redirect to the error page and return
                redirectToErrorPage(response);
                return;
            }

            // Initialize importDate and parse it from the request parameter
            Date importDate = null;
            try {
                importDate = new SimpleDateFormat("yyyy-MM-dd").parse(request.getParameter("importDate"));
            } catch (ParseException e) {
                // If parsing fails, throw a RuntimeException
                throw new RuntimeException(e);
            }

            // Create a new ImportOrderModel with the supplier, set its id and createAt, and save it
            ImportOrderModel importOrderModel = ImportOrderModel.builder()
                    .id(importId)
                    .createAt(new Timestamp(importDate.getTime()))
                    .supplier(supplier)
                    .build();

//            importOrderModel.setId(importId);
//            importOrderModel.setCreateAt(new Timestamp(importDate.getTime()));
            importOrderModel = importOrderService.save(importOrderModel);

            // If the ImportOrderModel was saved successfully
            if (importOrderModel != null) {
                // Retrieve the importFile from the request part
                Part importFile = request.getPart("importFile");

                // Import the Excel file and check if it was imported successfully
                boolean isImported = detailService.importFileExcel(importOrderModel.getId(), importFile, request.getParts());
                if (!isImported) {
                    // If the import was not successful, redirect to the error page and return
                    redirectToErrorPage(response);
                    return;
                }
            } else {
                // If the ImportOrderModel was not saved successfully, redirect to the error page and return
                redirectToErrorPage(response);
                return;
            }
            System.out.println("Time main: " + (System.currentTimeMillis() - start) + "ms");
            // If everything was successful, redirect to the success page
            response.sendRedirect("/admin/import-order?message=add_success&toast=success");
        } else {
            // If there were validation errors, redirect to the error page
            redirectToErrorPage(response);
        }
    }

    private void redirectToErrorPage(HttpServletResponse response) throws IOException {
        response.sendRedirect("/admin/import-order?message=error&toast=danger");
    }

    private boolean validate(HttpServletRequest request) throws ServletException, IOException {
        return request.getParameter("supplier").isEmpty() ||
                request.getParameter("importId").isEmpty() ||
                request.getParameter("importDate").isEmpty() ||
                !isValidDate(request.getParameter("importDate")) ||
                request.getPart("importFile") == null ||
                request.getPart("importFile").getSize() == 0;
    }

    private boolean isValidDate(String value) {
        try {
            new SimpleDateFormat("yyyy-MM-dd").parse(value);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (!AuthRole.checkPermission(response, request, SystemConstant.ADMIN_ROLE)) return;
        String[] ids = HttpUtil.of(request.getReader()).toModel(String[].class);
        if (ids.length != 0) {
            boolean result = importOrderService.delete(ids);
            response.setStatus(result ? HttpServletResponse.SC_OK : HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}
