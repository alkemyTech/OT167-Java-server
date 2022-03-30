package com.alkemy.ong.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)

public class IncorrectPatternExeption extends Exception {

public class IncorrectPatternExeption extends Exception{


    public IncorrectPatternExeption(String msg) {
        super(msg);
    }

}


}

