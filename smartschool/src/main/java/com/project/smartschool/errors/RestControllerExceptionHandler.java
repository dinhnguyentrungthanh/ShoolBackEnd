package com.project.smartschool.errors;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import io.jsonwebtoken.ExpiredJwtException;

@RestControllerAdvice
public class RestControllerExceptionHandler extends ResponseEntityExceptionHandler {
	
	public static final String msgMethodArgumentNotValid = "Dữ liệu không hợp lệ!";

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		Map<String, String> errors = new HashMap<>();
		for (FieldError error : ex.getBindingResult().getFieldErrors()) {
			errors.put(error.getField(), error.getDefaultMessage());
		}
		for (ObjectError error : ex.getBindingResult().getGlobalErrors()) {
			errors.put(error.getObjectName(), error.getDefaultMessage());
		}

		ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, msgMethodArgumentNotValid, errors);
		return handleExceptionInternal(ex, apiError, headers, apiError.getStatus(), request);
	}
	
	@Override
	protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		Map<String, String> errors = new HashMap<>();
			errors.put(ex.getParameterName(), "Missing parameter");

		ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, msgMethodArgumentNotValid, errors);
		return handleExceptionInternal(ex, apiError, headers, apiError.getStatus(), request);
	}

	@ExceptionHandler({ MethodArgumentTypeMismatchException.class })
	public ResponseEntity<Object> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex,
			WebRequest request) {
		String error = ex.getName() + " should be of type " + ex.getRequiredType().getName();

		ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST,  error);
		return buildResponseEntity(apiError);
	}

	@Override
	protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		String error = "No handler found for " + ex.getHttpMethod() + " " + ex.getRequestURL();

		ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, error);
		return buildResponseEntity(apiError);
	}

	@Override
	protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		StringBuilder builder = new StringBuilder();
		builder.append(ex.getMethod());
		builder.append(" method is not supported for this request. Supported methods are ");
		ex.getSupportedHttpMethods().forEach(t -> builder.append(t + " "));

		ApiError apiError = new ApiError(HttpStatus.METHOD_NOT_ALLOWED, builder.toString());
		return buildResponseEntity(apiError);
	}

	@Override
	protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		StringBuilder builder = new StringBuilder();
		builder.append(ex.getContentType());
		builder.append(" media type is not supported. Supported media types are ");
		ex.getSupportedMediaTypes().forEach(t -> builder.append(t + ", "));

		ApiError apiError = new ApiError(HttpStatus.UNSUPPORTED_MEDIA_TYPE, builder.substring(0, builder.length() - 2));
		return buildResponseEntity(apiError);
	}

	@ExceptionHandler({ UsernameNotFoundException.class })
	public ResponseEntity<Object> handleUsernameNotFound(UsernameNotFoundException ex, WebRequest request) {
		ApiError apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, "Not found the resource");
		return buildResponseEntity(apiError);
	}
	
	@ExceptionHandler({ Exception.class })
	public ResponseEntity<Object> handleAll(Exception ex, WebRequest request) {
		ApiError apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, ex.getLocalizedMessage());
		return buildResponseEntity(apiError);
	}

	@ExceptionHandler({ ValidateException.class })
	public ResponseEntity<Object> handleValidateException(ValidateException ex, WebRequest request) {
		ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage());
		return buildResponseEntity(apiError);
	}
	
	@ExceptionHandler({ NotFoundException.class })
	public ResponseEntity<Object> handleNotFoundException(NotFoundException ex, WebRequest request) {
		ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, ex.getLocalizedMessage());
		return buildResponseEntity(apiError);
	}
	
	@ExceptionHandler({ AccessDeniedException.class })
	public ResponseEntity<Object> handleServletException(AccessDeniedException ex, WebRequest request) {
		ApiError apiError = new ApiError(HttpStatus.FORBIDDEN, "Don't have permission");
		return buildResponseEntity(apiError);
	}
	
	@ExceptionHandler({ GlobalException.class })
	public ResponseEntity<Object> handleGlobalException(Exception ex, WebRequest request) {
		ApiError apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, ex.getLocalizedMessage());
		return buildResponseEntity(apiError);
	}
	
	@ExceptionHandler({ ExpiredJwtException.class })
	public ResponseEntity<Object> handleExpiredJwtException(ExpiredJwtException ex, WebRequest request) {
		ApiError apiError = new ApiError(HttpStatus.UNAUTHORIZED, "Don't have Permission");
		return buildResponseEntity(apiError);
	}
	
	@ExceptionHandler({ ServletException.class })
	public ResponseEntity<Object> handleExpiredJwtException(ServletException ex, WebRequest request) {
		ApiError apiError = new ApiError(HttpStatus.UNAUTHORIZED, "Don't have Permission");
		return buildResponseEntity(apiError);
	}
	
	@ExceptionHandler({ IOException.class })
	public ResponseEntity<Object> handleIOException(IOException ex, WebRequest request) {
		ApiError apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, "Error occurred Filestorage");
		return buildResponseEntity(apiError);
	}		
	
	private ResponseEntity<Object> buildResponseEntity(ApiError apiError) {
		return new ResponseEntity<>(apiError, apiError.getStatus());
	}
	
	
}
