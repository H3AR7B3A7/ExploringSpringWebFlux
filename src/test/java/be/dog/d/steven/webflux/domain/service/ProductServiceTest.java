package be.dog.d.steven.webflux.domain.service;

import be.dog.d.steven.webflux.adapter.dto.CreateProductDto;
import be.dog.d.steven.webflux.adapter.dto.ProductDto;
import be.dog.d.steven.webflux.adapter.exception.ProductNotFoundException;
import be.dog.d.steven.webflux.domain.model.Product;
import be.dog.d.steven.webflux.domain.model.Rating;
import be.dog.d.steven.webflux.domain.repository.ProductRepository;
import be.dog.d.steven.webflux.domain.repository.RatingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;
    @Mock
    private RatingRepository ratingRepository;

    private ProductService productService;

    @BeforeEach
    void setup() {
        productService = new ProductService(productRepository, ratingRepository);
    }

    @Test
    void findAllWithRating() {
        Product product1 = new Product(1L, "prod1", "Product 1");
        Product product2 = new Product(2L, "prod2", "Product 2");
        Rating rating1 = new Rating(1L, 1L, 4);
        Rating rating2 = new Rating(2L, 1L, 5);
        Rating rating3 = new Rating(3L, 2L, 3);
        when(productRepository.findAll())
                .thenReturn(Flux.just(product1, product2));
        when(ratingRepository.findRatingsByProductId(1L))
                .thenReturn(Flux.just(rating1, rating2));
        when(ratingRepository.findRatingsByProductId(2L))
                .thenReturn(Flux.just(rating3));

        Flux<ProductDto> result = productService.findAllWithRating();

        StepVerifier.create(result)
                .expectNext(new ProductDto("prod1", "Product 1", List.of(4, 5)))
                .expectNext(new ProductDto("prod2", "Product 2", List.of(3)))
                .verifyComplete();

        verify(productRepository).findAll();
        verify(ratingRepository).findRatingsByProductId(1L);
        verify(ratingRepository).findRatingsByProductId(2L);
        verifyNoMoreInteractions(productRepository, ratingRepository);
    }

    @Test
    void findByIdWithRating_existingProduct() {
        Product product = new Product(1L, "prod1", "Product 1");
        Rating rating1 = new Rating(1L, 1L, 4);
        Rating rating2 = new Rating(2L, 1L, 5);
        when(productRepository.findProductByProductId("prod1"))
                .thenReturn(Mono.just(product));
        when(ratingRepository.findRatingsByProductId(1L))
                .thenReturn(Flux.just(rating1, rating2));

        Mono<ProductDto> result = productService.findByIdWithRating("prod1");

        StepVerifier.create(result)
                .expectNext(new ProductDto("prod1", "Product 1", List.of(4, 5)))
                .verifyComplete();

        verify(productRepository).findProductByProductId("prod1");
        verify(ratingRepository).findRatingsByProductId(1L);
        verifyNoMoreInteractions(productRepository, ratingRepository);
    }

    @Test
    void findByIdWithRating_missingProduct() {
        when(productRepository.findProductByProductId("prod1"))
                .thenReturn(Mono.empty());

        Mono<ProductDto> result = productService.findByIdWithRating("prod1");

        StepVerifier.create(result)
                .expectError(ProductNotFoundException.class)
                .verify();

        verify(ratingRepository, never()).findRatingsByProductId(anyLong());
    }

    @Test
    void findAllWithRating_whenNoProductsExist() {
        when(productRepository.findAll()).thenReturn(Flux.empty());

        Flux<ProductDto> result = productService.findAllWithRating();

        StepVerifier.create(result)
                .verifyComplete();
        verify(productRepository).findAll();
        verifyNoMoreInteractions(productRepository, ratingRepository);
    }

    @Test
    void findByIdWithRating_nonExistingProduct() {
        when(productRepository.findProductByProductId("non-existing-id"))
                .thenReturn(Mono.empty());

        Mono<ProductDto> result = productService.findByIdWithRating("non-existing-id");

        StepVerifier.create(result)
                .expectError(ProductNotFoundException.class)
                .verify();
        verify(productRepository).findProductByProductId("non-existing-id");
        verifyNoMoreInteractions(productRepository, ratingRepository);
    }

    @Test
    void saveProduct() {
        CreateProductDto productDto = new CreateProductDto("Product 1", List.of());
        when(productRepository.save(any(Product.class)))
                .thenReturn(Mono.just(new Product(1L, "123", "Product 1")));

        Mono<ProductDto> result = productService.save(productDto);

        StepVerifier.create(result)
                .expectNextMatches(p -> p.name().equals(productDto.name())
                        && p.productId().equals("123")
                        && p.ratings().isEmpty())
                .verifyComplete();
        verify(productRepository).save(any(Product.class));
        verifyNoMoreInteractions(productRepository, ratingRepository);
    }
}
