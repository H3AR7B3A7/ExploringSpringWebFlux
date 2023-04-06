package be.dog.d.steven.webflux.adapter;

import be.dog.d.steven.webflux.adapter.dto.CreateProductDto;
import be.dog.d.steven.webflux.adapter.dto.ProductDto;
import be.dog.d.steven.webflux.adapter.dto.RatingDto;
import be.dog.d.steven.webflux.domain.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping("/product")
    Flux<ProductDto> findAll() {
        return productService.findAllWithRating();
    }

    @GetMapping("/product/{productId}")
    Mono<ProductDto> findById(@PathVariable String productId) {
        return productService.findByIdWithRating(productId);
    }

    @PostMapping("/product")
    Mono<ProductDto> save(@RequestBody CreateProductDto productDto) {
        return productService.save(productDto);
    }

    @PostMapping("/rating")
    Mono<RatingDto> save(@RequestBody RatingDto ratingDto) {
        return productService.save(ratingDto);
    }
}
