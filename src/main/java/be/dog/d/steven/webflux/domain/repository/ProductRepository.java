package be.dog.d.steven.webflux.domain.repository;

import be.dog.d.steven.webflux.domain.model.Product;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface ProductRepository extends ReactiveCrudRepository<Product, Long> {
    Mono<Product> findProductByProductId(String productId);
}
