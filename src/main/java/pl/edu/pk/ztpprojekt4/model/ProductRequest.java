package pl.edu.pk.ztpprojekt4.model;

import java.math.BigDecimal;

public record ProductRequest(
        String name,
        String description,
        BigDecimal price,
        int availableQuantity
) {}
