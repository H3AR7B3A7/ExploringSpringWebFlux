package be.dog.d.steven.webflux.adapter;

import java.util.List;

public record ProductDto(String productId, String name, List<Integer> ratings) {
}
