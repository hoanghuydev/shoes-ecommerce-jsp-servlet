package com.ltweb_servlet_ecommerce.socket.encoders;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ltweb_servlet_ecommerce.model.OpinionModel;
import com.ltweb_servlet_ecommerce.utils.HttpUtil;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

public class OpinionEncoder implements Encoder.Text<OpinionModel> {
    @Override
    public String encode(OpinionModel opinionModel) throws EncodeException {
        try {
            return HttpUtil.toJson(opinionModel);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void init(EndpointConfig endpointConfig) {

    }

    @Override
    public void destroy() {

    }
}
