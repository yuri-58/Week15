package pet.store.controller.error;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class GlobalErrorHandler {
	
	@ExceptionHandler(NoSuchElementException.class)
	@ResponseStatus(code = HttpStatus.NOT_FOUND)
	public Map<String, String> noSuchElementException(
			NoSuchElementException ex, WebRequest webRequest){
		return buildExceptionMessage(ex, HttpStatus.NOT_FOUND, webRequest);
	}

	private Map<String, String> buildExceptionMessage(NoSuchElementException ex, HttpStatus status,
			WebRequest webRequest) {		
		Map<String, String> exceptions = new HashMap<String, String>();
		exceptions.put("message", ex.toString());
		return exceptions;
	}
}
