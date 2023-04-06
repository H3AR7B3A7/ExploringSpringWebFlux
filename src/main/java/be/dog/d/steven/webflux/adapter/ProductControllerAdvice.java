package be.dog.d.steven.webflux.adapter;

import be.dog.d.steven.webflux.adapter.exception.ProductNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.net.URI;

@ControllerAdvice
public class ProductControllerAdvice {

    @ExceptionHandler
    ProblemDetail handleNotFoundException(ProductNotFoundException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);
        problemDetail.setTitle("Product not found");
        problemDetail.setType(URI.create("https://example.com/problems/product"));
        problemDetail.setDetail(String.format("Product with id %s was not found", ex.getMessage()));
        return problemDetail;
    }
}
