package com.works.config;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalException {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Object handle(MethodArgumentNotValidException ex) {
        List<FieldError> errors = ex.getFieldErrors();
        return parseError(errors);
    }

    private List parseError(List<FieldError> errors) {
        List ls = new ArrayList();
        for (FieldError error : errors) {
            Map<String, Object> map = new HashMap<>();
            map.put("field", error.getField());
            map.put("rejectedValue", error.getRejectedValue());
            map.put("message", error.getDefaultMessage());
            ls.add(map);
        }
        return ls;
    }


}
