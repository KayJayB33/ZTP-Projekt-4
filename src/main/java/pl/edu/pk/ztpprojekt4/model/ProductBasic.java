package pl.edu.pk.ztpprojekt4.model;

import java.math.BigDecimal;

public record ProductBasic (
    String id,
    String name,
    BigDecimal price,
    ProductState productState
) {}
