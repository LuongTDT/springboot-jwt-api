package com.cocarius.security.exceptions;

import org.springframework.http.ResponseEntity;

/**
 * @author LuongTDT
 */

public class ResponseEntityBuilder {
    public static ResponseEntity<Object> build(ApiError error){
        return new ResponseEntity<>(error, error.getStatus());
    }
}
