package be.dog.d.steven.webflux.domain.service;

import be.dog.d.steven.webflux.adapter.dto.CreateProductDto;
import be.dog.d.steven.webflux.adapter.dto.ProductDto;
import be.dog.d.steven.webflux.adapter.dto.RatingDto;
import be.dog.d.steven.webflux.adapter.exception.ProductNotFoundException;
import be.dog.d.steven.webflux.domain.model.Product;
import be.dog.d.steven.webflux.domain.model.Rating;
import be.dog.d.steven.webflux.domain.repository.ProductRepository;
import be.dog.d.steven.webflux.domain.repository.RatingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final RatingRepository ratingRepository;

    public Flux<Product> findAll() {
        return productRepository.findAll().delayElements(Duration.ofSeconds(1));
    }

    public Flux<ProductDto> findAllWithRating() {
        return productRepository.findAll()
                .delayElements(Duration.ofSeconds(1))
                .flatMap(p -> ratingRepository.findRatingsByProductId(p.id())
                        .map(Rating::rating)
                        .collectList()
                        .map(ratings -> new ProductDto(p.productId(), p.name(), ratings))
                );
    }

    public Mono<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    public Mono<ProductDto> findByIdWithRating(String productId) {
        return productRepository.findProductByProductId(productId)
                .switchIfEmpty(Mono.error(new ProductNotFoundException("Product not found for id: " + productId)))
                .flatMap(
                p -> ratingRepository.findRatingsByProductId(p.id())
                        .map(Rating::rating)
                        .collectList()
                        .map(ratings -> new ProductDto(p.productId(), p.name(), ratings))
        );
    }

    public Mono<ProductDto> save(CreateProductDto createProductDto) {
        return productRepository.save(
                new Product(null, UUID.randomUUID().toString(), createProductDto.name())
        ).map(p -> new ProductDto(p.productId(), p.name(), List.of()));
    }

    public Mono<RatingDto> save(RatingDto ratingDto) {
        return productRepository.findProductByProductId(ratingDto.productId())
                .switchIfEmpty(Mono.error(new ProductNotFoundException("Product not found for id: " + ratingDto.productId())))
                .flatMap(p -> ratingRepository.save(new Rating(null, p.id(), ratingDto.rating()))
                        .map(r -> new RatingDto(p.productId(), r.rating()))
                );
    }
}
