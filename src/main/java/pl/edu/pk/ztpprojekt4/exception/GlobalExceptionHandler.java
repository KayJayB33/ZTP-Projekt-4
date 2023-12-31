package pl.edu.pk.ztpprojekt4.exception;

import org.springframework.ui.Model;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public String handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException ex, Model model) {
        model.addAttribute("errorMessage", ex.getMessage());
        return "error";
    }
}