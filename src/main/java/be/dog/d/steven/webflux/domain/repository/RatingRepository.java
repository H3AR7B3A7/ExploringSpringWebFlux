package be.dog.d.steven.webflux.domain.repository;

import be.dog.d.steven.webflux.domain.model.Rating;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface RatingRepository extends ReactiveCrudRepository<Rating, Long> {
    Flux<Rating> findRatingsByProductId(Long productId);
}
