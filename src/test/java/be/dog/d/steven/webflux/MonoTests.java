package be.dog.d.steven.webflux;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;

public class MonoTests {
    @Test
    void first() {
        Mono.just("A")
                .log()
                .subscribe();
    }

    @Test
    void second() {
        Mono.just("A")
                .log()
                .subscribe(System.out::println);
    }

    @Test
    void third() {
        Mono.just("A")
                .log()
                .doOnSubscribe(sub -> System.out.printf("Sub: %s \n", sub))
                .doOnRequest(req -> System.out.printf("Req: %s \n", req))
                .doOnSuccess(complete -> System.out.printf("Complete: %s \n", complete))
                .subscribe(System.out::println);
    }

    @Test
    void fourth() {
        Mono.empty()
                .log()
                .subscribe(System.out::println);
    }

    @Test
    void fifth() {
        Mono.empty()
                .log()
                .subscribe(System.out::println, null, () -> System.out.println("Done"));
    }

    @Test
    void sixth() {
        Mono.error(new RuntimeException())
                .log()
                .subscribe();
    }

    @Test
    void seventh() {
        Mono.error(new RuntimeException())
                .log()
                .subscribe(System.out::println, e -> System.out.printf("Error: %s \n", e));
    }

    @Test
    void eighth() {
        Mono.error(new RuntimeException())
                .doOnError(e -> System.out.printf("Error: %s \n", e))
                .log()
                .subscribe();
    }

    @Test
    void ninth() {
        Mono.error(new RuntimeException())
                .onErrorResume(e -> {
                    System.out.printf("Error: %s \n", e);
                    return Mono.just("A");
                })
                .log()
                .subscribe();
    }
}
