package com.ltweb_servlet_ecommerce.controller.web.shared;

import com.ltweb_servlet_ecommerce.model.UserModel;
import com.ltweb_servlet_ecommerce.service.IUserService;
import com.ltweb_servlet_ecommerce.utils.HttpUtil;
import com.ltweb_servlet_ecommerce.utils.SessionUtil;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.cloudinary.json.JSONObject;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.ResourceBundle;

@WebServlet(urlPatterns = {"/oauth2-google"})
public class OAuth2GoogleController extends HttpServlet {
    @Inject
    IUserService userService;
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Lấy mã xác thực từ Google sau khi người dùng đăng nhập
        String authCode = request.getParameter("code");

        ResourceBundle rb = ResourceBundle.getBundle("env");
        // Gọi API Google để lấy thông tin người dùng
        HttpClient httpClient = HttpClients.createDefault();
        String clientId = rb.getString("GOOGLE_CLIENT_ID");
        String clientSecret = rb.getString("GOOGLE_CLIENT_SECRET");
        String redirectUri = rb.getString("GOOGLE_REDIRECT_URI");
        String tokenEndpoint = "https://oauth2.googleapis.com/token";
        String userInfoEndpoint = "https://www.googleapis.com/oauth2/v1/userinfo?access_token=";
        // Gửi mã xác thực để nhận mã truy cập
        HttpPost tokenRequest = new HttpPost(tokenEndpoint + "?code=" + authCode +
                "&client_id=" + clientId + "&client_secret=" + clientSecret + "&redirect_uri=" + redirectUri +
                "&grant_type=authorization_code");
        HttpResponse tokenResponse = httpClient.execute(tokenRequest);
        BufferedReader readerToken = new BufferedReader(new InputStreamReader(tokenResponse.getEntity().getContent()));
        JSONObject tokenJson = HttpUtil.of(readerToken).toJSONObject();
        // Lấy access token
        String accessToken = tokenJson.getString("access_token");
        HttpGet userInfoRequest = new HttpGet(userInfoEndpoint+accessToken);
        HttpResponse userInfoResponse = httpClient.execute(userInfoRequest);
        BufferedReader reader = new BufferedReader(new InputStreamReader(userInfoResponse.getEntity().getContent()));
        JSONObject userInfoJson = HttpUtil.of(reader).toJSONObject();
        UserModel userModel = new UserModel();
        userModel.setEmail(userInfoJson.getString("email"));
        userModel.setFullName(userInfoJson.getString("name"));
        userModel.setUserName(userInfoJson.getString("id"));
        userModel.setAssociation("google");

        try {
            checkUserAndSignIn(request,response,userModel);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private void checkUserAndSignIn(HttpServletRequest req, HttpServletResponse resp, UserModel userModel) throws SQLException, IOException {
       UserModel tmpUser = new UserModel();
       tmpUser.setEmail(userModel.getEmail());
       tmpUser.setAssociation(userModel.getAssociation());
       tmpUser = userService.findWithFilter(tmpUser);
       if (tmpUser==null) {
//       If not exist user then register new user
           userModel = userService.save(userModel);
           SessionUtil.getInstance().putValue(req,"USER_MODEL",userModel);
           resp.sendRedirect(req.getContextPath()+"/home?message=welcome&toast=success");
       } else if (tmpUser!=null && tmpUser.getAssociation().equals("google")) {
//           If have user then login
           SessionUtil.getInstance().putValue(req,"USER_MODEL",tmpUser);
           resp.sendRedirect(req.getContextPath()+"/home");

       } else if (tmpUser!=null && !tmpUser.getAssociation().equals("google")) {
           resp.sendRedirect(req.getContextPath()+req.getRequestURI()+"?message=exist_user&toast=danger");
       }
    }

}
