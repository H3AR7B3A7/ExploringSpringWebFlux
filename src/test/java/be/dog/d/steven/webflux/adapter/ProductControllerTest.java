package be.dog.d.steven.webflux.adapter;

import be.dog.d.steven.webflux.domain.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.reactive.server.FluxExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.List;

import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class ProductControllerTest {
    @Mock
    private ProductService productService;

    @InjectMocks
    private ProductController productController;

    private WebTestClient webTestClient;

    @BeforeEach
    void setUp() {
        webTestClient = WebTestClient.bindToController(productController).build();
    }

    @Test
    void findAll() {
        // Arrange
        List<ProductDto> products = Arrays.asList(new ProductDto("1", "Product 1", Arrays.asList(1, 2, 3)),
                new ProductDto("2", "Product 2", Arrays.asList(4, 5, 6)));
        given(productService.findAllWithRating()).willReturn(Flux.fromIterable(products));

        // Act
        FluxExchangeResult<ProductDto> result = webTestClient.get().uri("/api/v1/product").exchange()
                .expectStatus().isOk()
                .returnResult(ProductDto.class);

        // Assert
        StepVerifier.create(result.getResponseBody())
                .expectNextSequence(products)
                .verifyComplete();
    }

    @Test
    void findById() {
        // Arrange
        String productId = "1";
        ProductDto product = new ProductDto(productId, "Product 1", Arrays.asList(1, 2, 3));
        given(productService.findByIdWithRating(productId)).willReturn(Mono.just(product));

        // Act
        FluxExchangeResult<ProductDto> result = webTestClient.get().uri("/api/v1/product/" + productId).exchange()
                .expectStatus().isOk()
                .returnResult(ProductDto.class);

        // Assert
        StepVerifier.create(result.getResponseBody())
                .expectNext(product)
                .verifyComplete();
    }

    @Test
    void saveProduct() {
        // Arrange
        ProductDto product = new ProductDto(null, "New Product", Arrays.asList(1, 2, 3));
        given(productService.save(product)).willReturn(Mono.just(new ProductDto("1", "New Product", List.of())));

        // Act
        FluxExchangeResult<ProductDto> result = webTestClient.post().uri("/api/v1/product")
                .body(BodyInserters.fromValue(product))
                .exchange()
                .expectStatus().isOk()
                .returnResult(ProductDto.class);

        // Assert
        StepVerifier.create(result.getResponseBody())
                .expectNextMatches(p -> p.productId().equals("1") && p.name().equals("New Product") && p.ratings().isEmpty())
                .verifyComplete();
    }

    @Test
    void saveRating() {
        // Arrange
        RatingDto rating = new RatingDto("1", 5);
        given(productService.save(rating)).willReturn(Mono.just(new RatingDto("1", 5)));

        // Act
        FluxExchangeResult<RatingDto> result = webTestClient.post().uri("/api/v1/rating")
                .body(BodyInserters.fromValue(rating))
                .exchange()
                .expectStatus().isOk()
                .returnResult(RatingDto.class);

        // Assert
        StepVerifier.create(result.getResponseBody())
                .expectNext(rating)
                .verifyComplete();
    }
}
