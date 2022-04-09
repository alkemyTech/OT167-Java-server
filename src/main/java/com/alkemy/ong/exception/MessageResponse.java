package com.alkemy.ong.exception;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
@Component
public class MessageResponse{
    public MessageInfo messageOk(String message, int statusCode, WebRequest request){
        return new MessageInfo(message, statusCode,
                ((ServletWebRequest)request).getRequest().getRequestURI());
    }
}
