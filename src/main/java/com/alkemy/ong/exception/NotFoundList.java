package com.alkemy.ong.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.OK)
public class NotFoundList extends RuntimeException {
    public NotFoundList(String msg) {
        super(msg);
    }
}
