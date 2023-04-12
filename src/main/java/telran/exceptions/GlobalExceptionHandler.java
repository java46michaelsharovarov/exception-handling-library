package telran.exceptions;

import java.util.*;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	ResponseEntity<String> handlerMethodArgument(MethodArgumentNotValidException e) {
		List<ObjectError> errors = e.getAllErrors();
		String body = errors.stream().map(err -> err.getDefaultMessage()).collect(Collectors.joining("\n"));
		return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(ConstraintViolationException.class)
	ResponseEntity<String> handlerConstraintViolation(ConstraintViolationException e) {
		Set<ConstraintViolation<?>> constraints = e.getConstraintViolations();
		String body = constraints.stream().map(constraint -> constraint.getMessage()).collect(Collectors.joining("\n"));
		return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(IllegalStateException.class)
	ResponseEntity<String> handlerIllegalArgument(IllegalStateException e) {
		String msg = e.getMessage();
		return new ResponseEntity<>(msg, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(NoSuchElementException.class)
	ResponseEntity<String> handlerNoSuchElement(NoSuchElementException e) {
		String msg = e.getMessage();
		return new ResponseEntity<>(msg, HttpStatus.NOT_FOUND);
	}
	
}
