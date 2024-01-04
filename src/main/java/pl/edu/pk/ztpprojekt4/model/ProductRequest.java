package pl.edu.pk.ztpprojekt4.model;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.util.Objects;

public class ProductRequest {
    @NotNull(message = "Name may not be null")
    @NotBlank(message = "Name may not be blank")
    @NotEmpty(message = "Name may not be empty")
    @Size(min = 2, max = 32, message = "Name must be between 2 and 32 characters long")
    private String name;
    @NotNull(message = "Description may not be null")
    @NotBlank(message = "Description may not be blank")
    @NotEmpty(message = "Description may not be empty")
    private String description;
    @NotNull(message = "Price may not be null")
    @Positive(message = "Price must be positive")
    private BigDecimal price;
    @NotNull(message = "Available quantity may not be null")
    @PositiveOrZero(message = "Available quantity me greater than or equals to 0")
    private int availableQuantity;

    public ProductRequest(String name, String description, BigDecimal price, int availableQuantity) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.availableQuantity = availableQuantity;
    }

    public ProductRequest() {
        this.name = "";
        this.description = "";
        this.price = BigDecimal.ZERO;
        this.availableQuantity = 0;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getAvailableQuantity() {
        return availableQuantity;
    }

    public void setAvailableQuantity(int availableQuantity) {
        this.availableQuantity = availableQuantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductRequest that = (ProductRequest) o;
        return availableQuantity == that.availableQuantity && Objects.equals(name, that.name) && Objects.equals(description, that.description) && Objects.equals(price, that.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, price, availableQuantity);
    }
}
