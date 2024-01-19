package com.ltweb_servlet_ecommerce.socket;

import com.ltweb_servlet_ecommerce.dao.IOpinionDAO;
import com.ltweb_servlet_ecommerce.dao.IUserDAO;
import com.ltweb_servlet_ecommerce.dao.impl.OpinionDAO;
import com.ltweb_servlet_ecommerce.dao.impl.UserDAO;
import com.ltweb_servlet_ecommerce.model.OpinionModel;
import com.ltweb_servlet_ecommerce.model.UserModel;
import com.ltweb_servlet_ecommerce.service.IOpinionService;
import com.ltweb_servlet_ecommerce.service.IUserService;
import com.ltweb_servlet_ecommerce.service.impl.UserService;
import com.ltweb_servlet_ecommerce.socket.configurator.HttpSessionConfigurator;
import com.ltweb_servlet_ecommerce.socket.decoders.OpinionDecoder;
import com.ltweb_servlet_ecommerce.socket.encoders.OpinionEncoder;
import com.ltweb_servlet_ecommerce.utils.SessionUtil;
import org.apache.commons.text.StringEscapeUtils;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@ServerEndpoint(value = "/socket/opinion/{productId}",encoders = {OpinionEncoder.class}, decoders = {OpinionDecoder.class}, configurator = HttpSessionConfigurator.class)
public class OpinionSocketEndpoint {
    static Map<String, Set<Session>> room = new HashMap<>();
    @OnOpen
    public void onOpen(Session userSession,  @PathParam("productId") String productId, EndpointConfig config ) {
        System.out.println("Connect to product "+productId);
        UserModel userModel = (UserModel) config.getUserProperties().get(UserModel.class.getName());
        if (userModel!=null) {
            userSession.getUserProperties().put("userId", userModel.getId());
            userSession.getUserProperties().put("admin", userModel.getAdmin());
        }
        if (room.containsKey(productId)) {
            room.get(productId).add(userSession);
        } else {
            room.put(productId, new HashSet<>());
            room.get(productId).add(userSession);
        }
    }
    @OnMessage
    public void onMessage(OpinionModel inOpinionModelMsg, Session userSession, @PathParam("productId") String productId) throws SQLException {
        OpinionModel outOpinionModelMsg = new OpinionModel();
        Long userId = (Long) userSession.getUserProperties().get("userId");
        boolean admin =  (boolean) userSession.getUserProperties().get("admin");
        IOpinionDAO opinionDAO = new OpinionDAO();
        if (userId!=null) {
            IUserDAO userDAO = new UserDAO();

//            Xóa nếu là deleted = true
            if (inOpinionModelMsg.getIsDeleted() && inOpinionModelMsg.getId()!=null) {
                inOpinionModelMsg = opinionDAO.findById(inOpinionModelMsg.getId());
                if (inOpinionModelMsg != null && (inOpinionModelMsg.getUserId().equals(userId) || admin) ) {
                    opinionDAO.softDelete(inOpinionModelMsg.getId());
                    outOpinionModelMsg = inOpinionModelMsg;
                    outOpinionModelMsg.setIsDeleted(true);
                    String userName = userDAO.findById(userId).getFullName();
                    outOpinionModelMsg.setUserName(userName);
                    broadcastComment(outOpinionModelMsg,productId);
                    sendNotifyOpinionForAdmin(outOpinionModelMsg);
                }
//                Nếu không thì thêm opinion
            } else {
                try {
                    inOpinionModelMsg.setUserId(userId);
                    Long opinionId = opinionDAO.save(inOpinionModelMsg);
                    outOpinionModelMsg.setId(opinionId);
                    outOpinionModelMsg = opinionDAO.findWithFilter(outOpinionModelMsg);
                    String userName = userDAO.findById(userId).getFullName();
                    outOpinionModelMsg.setUserName(userName);
                    broadcastComment(outOpinionModelMsg,productId);
                    sendNotifyOpinionForAdmin(outOpinionModelMsg);
                } catch (Exception e) {
                    String t = e.toString();
                }
            }

        }
    }
    public void sendNotifyOpinionForAdmin(OpinionModel opinionModelMsg) {
        Set<Session> sessions = room.get("0");
        if (sessions != null) {
            for (Session session : sessions) {
                if (session.isOpen()  && (boolean) session.getUserProperties().get("admin")) {
                    try {
                        session.getBasicRemote().sendObject(opinionModelMsg);
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (EncodeException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }
    public void broadcastComment(OpinionModel opinionModelMsg, String productId) {
        Set<Session> sessions = room.get(productId);
        if (sessions != null) {
            for (Session session : sessions) {
                if (session.isOpen()) {
                    try {
                        session.getBasicRemote().sendObject(opinionModelMsg);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
    @OnClose
    public void onClose(Session userSession,  @PathParam("productId") String productId) {
        Set<Session> sessions = room.get(productId);
        if (sessions != null) {
            sessions.remove(userSession);
            if (sessions.isEmpty()) {
                room.remove(productId);
            }
        }
    }

}
