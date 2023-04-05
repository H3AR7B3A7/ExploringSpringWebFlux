package be.dog.d.steven.webflux;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;

public class OperatorTests {
    @Test
    void first() {
        Flux.range(1, 5)
                .map(i -> i * 10)
                .subscribe(System.out::println);
    }

    @Test
    void second() {

    }
}
