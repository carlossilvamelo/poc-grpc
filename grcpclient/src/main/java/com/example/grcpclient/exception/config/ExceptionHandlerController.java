package com.example.grcpclient.exception.config;

import java.net.ConnectException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;

import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@ControllerAdvice
public class ExceptionHandlerController {

    static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    static final Logger LOG = Logger.getLogger(ExceptionHandlerController.class.getName());


    @ExceptionHandler(StatusRuntimeException.class)
    public ResponseEntity<StandardError> statusRuntimeExceptionHandler(StatusRuntimeException exception, HttpServletRequest request) {

        LOG.info(String.format("Request '%s' threw the exception %s", request.getRequestURI(), exception.toString()));

        HttpStatus status = getHttpStatus(exception.getStatus());
        StandardError standardError = new StandardError(
                LocalDateTime.now().format(FORMATTER),
                status.value(),
                exception.getStatus().getCode().name(),
                exception.getMessage(),
                request.getRequestURI());

        return ResponseEntity.status(status).body(standardError);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<StandardError> runtimeExceptionHandler(RuntimeException exception, HttpServletRequest request) {

        LOG.info(String.format("Request '%s' threw the exception %s", request.getRequestURI(), exception.toString()));

        HttpStatus status = null;
        StandardError standardError = new StandardError(
                LocalDateTime.now().format(FORMATTER),
                status.value(),
                null,
                exception.getMessage(),
                request.getRequestURI());

        return ResponseEntity.status(status).body(standardError);
    }

    private HttpStatus getHttpStatus(Status grpcStatus){
        List<HttpStatus> httpStatusvalues = Arrays
                .asList(HttpStatus.values());
       return httpStatusvalues.stream().filter(httpStatus ->{
            String grpcStatusName = grpcStatus.getCode().name();
            return httpStatus.name().equalsIgnoreCase(grpcStatusName);
        }).findAny().orElse(HttpStatus.INTERNAL_SERVER_ERROR);
    }

}