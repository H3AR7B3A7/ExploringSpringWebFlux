package be.dog.d.steven.webflux.adapter.client;

import be.dog.d.steven.webflux.adapter.dto.ProductDto;
import org.springframework.web.service.annotation.GetExchange;
import reactor.core.publisher.Flux;

public interface ProductClient {

    @GetExchange("/product")
    Flux<ProductDto> all();
}
