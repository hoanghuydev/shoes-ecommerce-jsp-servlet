package com.ltweb_servlet_ecommerce.controller.web.ajax.voucher;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.ltweb_servlet_ecommerce.model.ProductModel;
import com.ltweb_servlet_ecommerce.model.UserModel;
import com.ltweb_servlet_ecommerce.model.VoucherConditionModel;
import com.ltweb_servlet_ecommerce.model.VoucherModel;
import com.ltweb_servlet_ecommerce.service.IVoucherConditionService;
import com.ltweb_servlet_ecommerce.service.IVoucherService;
import com.ltweb_servlet_ecommerce.utils.CheckVoucher;
import com.ltweb_servlet_ecommerce.utils.HttpUtil;
import com.ltweb_servlet_ecommerce.utils.SessionUtil;
import lombok.Data;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;

@WebServlet(urlPatterns = "/ajax/voucher/validate")
public class CheckValidVoucher extends HttpServlet {
    @Inject
    IVoucherConditionService voucherConditionService;
    @Inject
    IVoucherService voucherService;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ObjectMapper mapper = new ObjectMapper();
        try {
            StringBuilder sb = new StringBuilder();
            try (BufferedReader reader = req.getReader()) {
                String line;
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
            } catch (IOException e) {
                throw new ServletException("Error reading request body", e);
            }
            String jsonData = sb.toString();
            CheckValidVoucherModel checkModel = mapper.readValue(jsonData, CheckValidVoucherModel.class);
            VoucherModel voucher = voucherService.findById(checkModel.idVoucher);
            VoucherConditionModel voucherConditionModel = new VoucherConditionModel();
            voucherConditionModel.setVoucherId(checkModel.idVoucher);
            List<VoucherConditionModel> voucherConditions = voucherConditionService.findAllWithFilter(voucherConditionModel, null);
            UserModel user = (UserModel) SessionUtil.getInstance().getValue(req, "USER_MODEL");
            CheckUseVoucherResp checkResp;
            if (voucher.getEndDate().after(new Timestamp(System.currentTimeMillis()))) {
                if (voucher.getStartDate().before(new Timestamp(System.currentTimeMillis()))) {
                    if (CheckVoucher.canApplyVoucher(checkModel.getProducts(), voucherConditions, user)) {
                        checkResp = new CheckUseVoucherResp(0, "You can use this voucher", true, voucher);
                    } else {
                        checkResp = new CheckUseVoucherResp(1, "You can't use this voucher", false, voucher);
                    }
                } else {
                    checkResp = new CheckUseVoucherResp(1, "Voucher has not been applied yet", false, voucher);
                }
            } else {
                checkResp = new CheckUseVoucherResp(1, "voucher expired", false, voucher);
            }

            Gson gson = new Gson();
            String jsonResp = gson.toJson(checkResp);
            resp.setContentType("application/json");
            resp.getWriter().write(jsonResp);
        } catch (Exception e) {
            HttpUtil.returnError500Json(mapper, resp, e.getMessage());
        }
    }


}

@Data
class CheckValidVoucherModel {
    List<ProductModel> products;
    Long idVoucher;

}

@Data
class CheckUseVoucherResp {
    int err;
    String message;
    boolean canUseVoucher;
    VoucherModel voucher;

    public CheckUseVoucherResp(int err, String message, boolean canUseVoucher, VoucherModel voucher) {
        this.err = err;
        this.message = message;
        this.canUseVoucher = canUseVoucher;
        this.voucher = voucher;
    }
}
