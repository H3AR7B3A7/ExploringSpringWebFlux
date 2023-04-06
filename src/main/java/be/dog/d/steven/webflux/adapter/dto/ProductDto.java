package be.dog.d.steven.webflux.adapter.dto;

import java.util.List;

public record ProductDto(String productId, String name, List<Integer> ratings) {
}
