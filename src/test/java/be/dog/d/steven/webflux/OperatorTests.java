package be.dog.d.steven.webflux;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

public class OperatorTests {
    @Test
    void first() {
        Flux.range(1, 5)
                .map(i -> i * 10)
                .subscribe(System.out::println);
    }

    @Test
    void second() {
        Flux.range(1, 5)
                .flatMap(i -> Flux.range(1, i))
                .subscribe(System.out::println);
    }

    @Test
    void third() {
        Mono.just(3)
                .flatMapMany(i -> Flux.range(1, i))
                .subscribe(System.out::println);
    }

    @Test
    void fourth() throws InterruptedException {
        Flux<Integer> oneToFive = Flux.range(1, 5)
                .delayElements(Duration.ofMillis(200));
        Flux<Integer> sixToTen = Flux.range(6, 5)
                .delayElements(Duration.ofMillis(400));
        Flux.concat(oneToFive, sixToTen).subscribe(System.out::println);
        Thread.sleep(4000);
    }

    @Test
    void fifth() throws InterruptedException {
        Flux<Integer> oneToFive = Flux.range(1, 5)
                .delayElements(Duration.ofMillis(200));
        Flux<Integer> sixToTen = Flux.range(6, 5)
                .delayElements(Duration.ofMillis(400));
        Flux.merge(oneToFive, sixToTen).subscribe(System.out::println);
        Thread.sleep(4000);
    }

    @Test
    void sixth() throws InterruptedException {
        Flux<Integer> oneToFive = Flux.range(1, 5)
                .delayElements(Duration.ofMillis(200));
        Flux<Integer> sixToTen = Flux.range(6, 5)
                .delayElements(Duration.ofMillis(400));
        Flux.zip(oneToFive, sixToTen, (n, m) -> n + ", " + m).subscribe(System.out::println);
        Thread.sleep(4000);
    }
}
