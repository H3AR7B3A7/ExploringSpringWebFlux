package be.dog.d.steven.webflux.adapter.dto;

import java.util.List;

public record CreateProductDto(String name, List<Integer> ratings) {
}
