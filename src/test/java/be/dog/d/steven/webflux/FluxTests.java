package be.dog.d.steven.webflux;

import org.junit.jupiter.api.Test;
import org.reactivestreams.Subscription;
import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.Arrays;
import java.util.Random;
import java.util.stream.Stream;

public class FluxTests {
    @Test
    void first() {
        Flux.just("A", "B", "C")
                .log()
                .subscribe();
    }

    @Test
    void second() {
        Flux.just(Arrays.asList("A", "B", "C"))
                .log()
                .subscribe();
    }

    @Test
    void third() {
        Flux.fromIterable(Arrays.asList("A", "B", "C"))
                .log()
                .subscribe();
    }

    @Test
    void fourth() {
        Flux.fromArray(new String[]{"A", "B", "C"})
                .log()
                .subscribe();
    }

    @Test
    void fifth() {
        Flux.fromStream(Stream.of("A", "B", "C"))
                .log()
                .subscribe();
    }

    @Test
    void sixth() {
        Flux.range(1, 5)
                .log()
                .subscribe();
    }

    @Test
    void seventh() throws InterruptedException {
        Flux.interval(Duration.ofSeconds(1))
                .log()
                .take(3) // Without this the flux will never complete
                .subscribe();
        Thread.sleep(5000); // Without this the main thread is killed before other threads can execute
    }

    @Test
    void eight() {
        Flux.range(1, 5)
                .log()
                .subscribe(new BaseSubscriber<>() {
                    int elementsToProcess = 3;
                    int count = 0;

                    @Override
                    protected void hookOnSubscribe(Subscription subscription) {
                        System.out.println("Subscribed!");
                        request(elementsToProcess);
                    }

                    @Override
                    protected void hookOnNext(Integer value) {
                        count++;
                        if (count == elementsToProcess) {
                            count = 0;
                            Random r = new Random();
                            elementsToProcess = r.ints(1, 4)
                                    .findFirst().getAsInt();
                            request(elementsToProcess);
                        }
                    }
                });
    }

    @Test
    void ninth() {
        Flux.range(1, 5)
                .log()
                .limitRate(3)
                .subscribe();
    }
}
