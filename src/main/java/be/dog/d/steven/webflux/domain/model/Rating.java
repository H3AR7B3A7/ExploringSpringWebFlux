package be.dog.d.steven.webflux.domain.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table
public record Rating(@Id Long id, Long productId, Integer rating) {
}
