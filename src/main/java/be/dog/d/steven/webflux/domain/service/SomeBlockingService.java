package be.dog.d.steven.webflux.domain.service;

import org.springframework.stereotype.Service;

@Service
public class SomeBlockingService {

    public String blockingCall() throws InterruptedException {
        Thread.sleep(500);
        return "Hello world";
    }
}
