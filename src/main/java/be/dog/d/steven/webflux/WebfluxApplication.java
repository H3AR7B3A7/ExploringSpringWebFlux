package be.dog.d.steven.webflux;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import reactor.blockhound.BlockHound;
import reactor.tools.agent.ReactorDebugAgent;

import java.util.zip.InflaterInputStream;

@SpringBootApplication
@EnableR2dbcRepositories
@OpenAPIDefinition(
        info = @Info(
                title = "Webflux Demo",
                version = "1.0",
                description = "A demo of a reactive service using Spring Webflux."))
public class WebfluxApplication {

    public static void main(String[] args) {
        // BlockHound helps to detect blocking calls
        BlockHound.install(
                // To allow a blocking call by openapi
                b -> b.allowBlockingCallsInside(InflaterInputStream.class.getName(), "read"));
        // Reactor debugging agent /w little overhead
        ReactorDebugAgent.init();
        SpringApplication.run(WebfluxApplication.class, args);
    }

}
