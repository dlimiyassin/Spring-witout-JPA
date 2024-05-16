package com.example.resume_management.exceptions;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

@ControllerAdvice
public class CustomExceptionHandler {

    /* 401 */
    @ExceptionHandler(UnauthorizedException.class)
    @ResponseBody
    public ResponseEntity<ErrorBody> handleUnauthorizedException(UnauthorizedException ex) {
        String status = "Unauthorized";
        Date time = new Date();
        String message = ex.getMessage();
        ErrorBody errorBody = new ErrorBody(status, time.toString(),message);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorBody);
    }
    
    /* 404 */
    @ExceptionHandler(RecordNotFoundException.class)
    @ResponseBody
    public ResponseEntity<ErrorBody> handleRecordNotFoundException(RecordNotFoundException ex) {
        String status = "RecordNotFound";
        Date time = new Date();
        String message = ex.getMessage();
        ErrorBody errorBody = new ErrorBody(status,time.toString(),message);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorBody);
    }
    /* 302 */
    @ExceptionHandler(RecordAlreadyExistsException.class)
    @ResponseBody
    public ResponseEntity<ErrorBody> handleRecordFoundException(RecordAlreadyExistsException ex) {
        String status = "RecordAlreadyExist";
        Date time = new Date();
        String message = ex.getMessage();
        ErrorBody errorBody = new ErrorBody(status,time.toString(),message);
        return ResponseEntity.status(HttpStatus.FOUND).body(errorBody);
    }

    /* 405 - Method Not Supported */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseBody
    public ResponseEntity<ErrorBody> handleMethodNotSupportedException(HttpRequestMethodNotSupportedException ex) {
        String status = "Method Not Supported";
        Date time = new Date();
        String message = ex.getMessage();
        ErrorBody errorBody = new ErrorBody(status, time.toString(), message);
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(errorBody);
    }

    /* 500 */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseEntity<ErrorBody> handleGenericException(Exception ex) {
        String status = "Internal server error";
        Date time = new Date();
        String message = ex.getMessage();
        ErrorBody errorBody = new ErrorBody(status,time.toString(),message);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorBody);
    }

}

