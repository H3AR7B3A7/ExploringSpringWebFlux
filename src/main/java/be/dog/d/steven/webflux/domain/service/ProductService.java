package be.dog.d.steven.webflux.domain.service;

import be.dog.d.steven.webflux.adapter.ProductDto;
import be.dog.d.steven.webflux.adapter.RatingDto;
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

    public Mono<ProductDto> findByIdWithRating(Long id) {
        return productRepository.findById(id).flatMap(
                p -> ratingRepository.findRatingsByProductId(p.id())
                        .map(Rating::rating)
                        .collectList()
                        .map(ratings -> new ProductDto(p.productId(), p.name(), ratings))
        );
    }

    public Mono<ProductDto> save(ProductDto productDto) {
        return productRepository.save(
                new Product(null, UUID.randomUUID().toString(), productDto.name())
        ).map(p -> new ProductDto(p.productId(), p.name(), List.of()));
    }

    public Mono<RatingDto> save(RatingDto ratingDto) {
        return productRepository.findProductByProductId(ratingDto.productId())
                .flatMap(p -> ratingRepository.save(new Rating(null, p.id(), ratingDto.rating()))
                        .map(r -> new RatingDto(p.productId(), r.rating()))
                );
    }
}
