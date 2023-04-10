package be.dog.d.steven.webflux;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Hooks;
import reactor.tools.agent.ReactorDebugAgent;

public class DebuggingTests {

    @Test
    void first() {
        Flux.just("A", "B")
                .single()
                .log()
                .subscribe();
    }

    @Test
    void second() {
        Hooks.onOperatorDebug(); // A lot of overhead
        Flux.just("A", "B")
                .single()
                .log()
                .subscribe();
    }

    @Test
    void third() {
        Flux.just("A", "B")
                .single()
                .checkpoint("afterSingle") // A single checkpoint
                .log()
                .subscribe();
    }

    @Test
    void fourth() {
        ReactorDebugAgent.init(); // Debugging agent goes in psvm of the app
        Flux.just("A", "B")
                .single()
                .log()
                .subscribe();
    }
}
