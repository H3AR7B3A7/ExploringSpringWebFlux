package be.dog.d.steven.webflux.adapter.client;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class ProductClientConfiguration {

    @Bean
    ProductClient productClient(WebClient.Builder builder) {
        // WARNING: Typically the client will access an API of another application, NOT its own.
        //          For demo purpose we just access the Product API of this application.
        return HttpServiceProxyFactory
                .builder(WebClientAdapter.forClient(builder.baseUrl("http://localhost:8080/api/v1").build()))
                .build()
                .createClient(ProductClient.class);
    }

}
