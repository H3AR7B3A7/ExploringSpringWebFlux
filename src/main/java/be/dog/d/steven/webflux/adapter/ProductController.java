package be.dog.d.steven.webflux.adapter;

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

    @GetMapping("/product/{id}")
    Mono<ProductDto> findById(@PathVariable Long id) {
        return productService.findByIdWithRating(id);
    }

    @PostMapping("/product")
    Mono<ProductDto> save(@RequestBody ProductDto productDto) {
        return productService.save(productDto);
    }

    @PostMapping("/rating")
    Mono<RatingDto> save(@RequestBody RatingDto ratingDto) {
        return productService.save(ratingDto);
    }
}
