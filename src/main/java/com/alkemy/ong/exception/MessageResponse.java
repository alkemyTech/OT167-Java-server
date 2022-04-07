package com.alkemy.ong.exception;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
@Component
public class MessageResponse{
    public MessageInfo messageOk(String message, WebRequest request){
        return new MessageInfo(message,
                HttpStatus.ACCEPTED.value(),
                ((ServletWebRequest)request).getRequest().getRequestURI());
    }
}
