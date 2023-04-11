package be.dog.d.steven.webflux.adapter.client;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PrintProductRunner implements ApplicationRunner {

    private final ProductClient productClient;

    @Override
    public void run(ApplicationArguments args) {
        productClient.all().subscribe(System.out::println);
    }
}
