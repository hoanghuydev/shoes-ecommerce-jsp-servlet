package com.ltweb_servlet_ecommerce.socket.configurator;

import com.ltweb_servlet_ecommerce.model.UserModel;

import javax.servlet.http.HttpSession;
import javax.websocket.HandshakeResponse;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.ServerEndpointConfig;

public class HttpSessionConfigurator extends ServerEndpointConfig.Configurator {
    @Override
    public void modifyHandshake(ServerEndpointConfig sec, HandshakeRequest request, HandshakeResponse response) {
        HttpSession httpSession = (HttpSession) request.getHttpSession();
        UserModel userModel = (UserModel) httpSession.getAttribute("USER_MODEL");
        if (userModel!=null) {
            sec.getUserProperties().put(UserModel.class.getName(), userModel);
        }
    }
}