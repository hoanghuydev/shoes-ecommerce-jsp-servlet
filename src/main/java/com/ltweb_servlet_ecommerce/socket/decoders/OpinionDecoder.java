package com.ltweb_servlet_ecommerce.socket.decoders;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ltweb_servlet_ecommerce.model.OpinionModel;
import com.ltweb_servlet_ecommerce.utils.HttpUtil;
import org.apache.commons.lang3.StringEscapeUtils;

import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;
import java.io.StringReader;
import java.util.Map;

public class OpinionDecoder implements Decoder.Text<OpinionModel> {
    @Override
    public OpinionModel decode(String s) throws DecodeException {
        ObjectMapper mapper = new ObjectMapper();
        OpinionModel opinionModel;
        try {
            opinionModel = mapper.readValue(s, OpinionModel.class);
            opinionModel.setTitle(StringEscapeUtils.escapeHtml4(opinionModel.getTitle()));
            opinionModel.setContent(StringEscapeUtils.escapeHtml4(opinionModel.getContent()));
        } catch (JsonProcessingException e) {
            return null;
        }
        return opinionModel;
    }

    @Override
    public boolean willDecode(String s) {
       boolean flag = true;
        try {
            ObjectMapper mapper = new ObjectMapper();
            OpinionModel test = mapper.readValue(s, OpinionModel.class);
        } catch (JsonProcessingException e) {
            flag = false;
        }
        return flag;
    }

    @Override
    public void init(EndpointConfig endpointConfig) {

    }

    @Override
    public void destroy() {

    }
}
