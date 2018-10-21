package com.evolvestage.api.controllers;

import com.evolvestage.api.dtos.ErrorDetailDto;
import com.evolvestage.api.exceptions.BadRequestException;
import com.evolvestage.api.exceptions.ConflictException;
import com.evolvestage.api.exceptions.UnauthorizedException;
import com.evolvestage.api.utils.TimeProvider;
import lombok.AllArgsConstructor;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.EntityNotFoundException;
import javax.validation.ValidationException;
import java.util.Objects;


@CommonsLog
@RestController
@ControllerAdvice
@AllArgsConstructor
public class ErrorHandlingController extends ResponseEntityExceptionHandler {

    private final TimeProvider timeProvider;

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorDetailDto exceptionHandler(Exception e) {
        return ErrorDetailDto.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .cause(e)
                .timeStamp(timeProvider.getDate())
                .build();
    }

    @ExceptionHandler(InternalAuthenticationServiceException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ErrorDetailDto exceptionHandler(InternalAuthenticationServiceException e) {
        ErrorDetailDto errorDetailDto = ErrorDetailDto.builder()
                .status(HttpStatus.UNPROCESSABLE_ENTITY)
                .cause(e)
                .timeStamp(timeProvider.getDate())
                .build();
        errorDetailDto.setDetail("login is not provided");
        return errorDetailDto;
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDetailDto exceptionHandler(BadRequestException e) {
        return ErrorDetailDto.builder()
                .status(HttpStatus.BAD_REQUEST)
                .cause(e)
                .timeStamp(timeProvider.getDate())
                .build();
    }

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorDetailDto exceptionHandler(EntityNotFoundException e) {
        return ErrorDetailDto.builder()
                .status(HttpStatus.NOT_FOUND)
                .cause(e)
                .timeStamp(timeProvider.getDate())
                .build();
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorDetailDto exceptionHandler(AccessDeniedException e) {
        return ErrorDetailDto.builder()
                .status(HttpStatus.FORBIDDEN)
                .cause(e)
                .timeStamp(timeProvider.getDate())
                .build();
    }

    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorDetailDto exceptionHandler(BadCredentialsException e) {
        return ErrorDetailDto.builder()
                .status(HttpStatus.UNAUTHORIZED)
                .cause(e)
                .timeStamp(timeProvider.getDate())
                .build();
    }

    @ExceptionHandler(UnauthorizedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorDetailDto exceptionHandler(UnauthorizedException e) {
        return ErrorDetailDto.builder()
                .status(HttpStatus.UNAUTHORIZED)
                .cause(e)
                .timeStamp(timeProvider.getDate())
                .build();
    }

    @ExceptionHandler(ConflictException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorDetailDto exceptionHandler(ConflictException e) {
        return ErrorDetailDto.builder()
                .status(HttpStatus.CONFLICT)
                .cause(e)
                .timeStamp(timeProvider.getDate())
                .build();
    }

    @ExceptionHandler(DisabledException.class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public ErrorDetailDto exceptionHandler(DisabledException e) {
        ErrorDetailDto errorDetailDto = ErrorDetailDto.builder()
                .status(HttpStatus.METHOD_NOT_ALLOWED)
                .cause(e)
                .timeStamp(timeProvider.getDate())
                .build();
        return errorDetailDto;
    }

    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ErrorDetailDto exceptionHandler(ValidationException e) {
        return ErrorDetailDto.builder()
                .status(HttpStatus.UNPROCESSABLE_ENTITY)
                .cause(e)
                .timeStamp(timeProvider.getDate())
                .build();
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest request) {
        FieldError fieldError = ex.getBindingResult().getFieldError();
        ex.getBindingResult().getFieldErrors().get(0).getDefaultMessage();
        ErrorDetailDto errorDetailDto = ErrorDetailDto.builder()
                .status(HttpStatus.UNPROCESSABLE_ENTITY)
                .cause(ex)
                .timeStamp(timeProvider.getDate())
                .build();

        if (Objects.nonNull(fieldError)) {
            if (Objects.nonNull(fieldError.getDefaultMessage())) {
                errorDetailDto.setDetail(fieldError.getField() + ": " + fieldError.getDefaultMessage());
            }
        }
        return new ResponseEntity<>(errorDetailDto, HttpStatus.UNPROCESSABLE_ENTITY);
    }
}
