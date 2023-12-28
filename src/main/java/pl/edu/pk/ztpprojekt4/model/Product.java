package pl.edu.pk.ztpprojekt4.model;

import java.math.BigDecimal;
import java.time.Instant;

public record Product(
        String id,
        String name,
        String description,
        BigDecimal price,
        int availableQuantity,
        ProductState productState,
        Instant createdDate,
        Instant lastModifiedDate
) {
}
