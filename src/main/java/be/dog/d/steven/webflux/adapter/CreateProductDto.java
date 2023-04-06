package be.dog.d.steven.webflux.adapter;

import java.util.List;

public record CreateProductDto(String name, List<Integer> ratings) {
}
