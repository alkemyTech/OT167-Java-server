package com.alkemy.ong.exception;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

@Component
public class PaginationMessage {
    @Autowired
    private MessageSource messageSource;
    public MessagePag messageInfo(Page page, List<Object> dtoPageList, WebRequest request){
        String path = ((ServletWebRequest)request).getRequest().getRequestURI();
        List <Object> content = dtoPageList;
        String nextPath = messageSource.getMessage("page.dont.have.next", null, Locale.ENGLISH);
        String prevPath = messageSource.getMessage("page.dont.have.prev", null, Locale.ENGLISH);
        if(!page.isLast()) nextPath= path+ "?page=" + (page.getNumber()+1);
        if(!page.isFirst()) prevPath= path+ "?page=" + (page.getNumber()-1);
        if(page.getContent().isEmpty()) content = Collections.singletonList(messageSource.getMessage("page.empty", null, Locale.ENGLISH));
        return new MessagePag(content, HttpStatus.ACCEPTED.value(), nextPath, prevPath);
    }
}
