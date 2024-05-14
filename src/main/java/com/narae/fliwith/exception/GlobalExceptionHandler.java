package com.narae.fliwith.exception;

import com.narae.fliwith.dto.base.ErrorRes;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.regex.PatternSyntaxException;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<ErrorRes> handleApplicationException(ApplicationException ex) {
        return ResponseEntity.status(ex.getHttpStatus()).body(new ErrorRes(ex.getErrorCode(), ex.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorRes> methodArgumentNotValidException(MethodArgumentNotValidException ex) {
        String errorCode = "400";
        String message = ex.getBindingResult().getFieldErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining(", "));
        ErrorRes errorResponse = new ErrorRes(errorCode, message);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body(errorResponse);
    }

    @ExceptionHandler(PatternSyntaxException.class)
    public ResponseEntity<ErrorRes> handlePatternSyntaxException(PatternSyntaxException ex) {
        String errorCode = "400";
        String message = "올바른 패턴을 입력하세요. ";
        ErrorRes errorResponse = new ErrorRes(errorCode, message);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body(errorResponse);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorRes> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException ex) {
        String errorCode = "405";
        String message = "클라이언트가 사용한 HTTP 메서드가 리소스에서 허용되지 않습니다.";
        ErrorRes errorResponse = new ErrorRes(errorCode, message);
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED.value()).body(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorRes> handleException(Exception ex) {
        String errorCode = "500";
        String message = "서버에서 요청을 처리하는 동안 오류가 발생했습니다.";
        ErrorRes errorResponse = new ErrorRes(errorCode, ex.getClass().getSimpleName() + ":" + message);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR.value()).body(errorResponse);
    }

}
