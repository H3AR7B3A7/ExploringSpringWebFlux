package be.dog.d.steven.webflux;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import reactor.blockhound.BlockHound;
import reactor.tools.agent.ReactorDebugAgent;

@SpringBootApplication
@EnableR2dbcRepositories
public class WebfluxApplication {

    public static void main(String[] args) {
        BlockHound.install(); // BlockHound helps to detect blocking calls
        ReactorDebugAgent.init(); // Reactor debugging agent /w little overhead
        SpringApplication.run(WebfluxApplication.class, args);
    }

}
