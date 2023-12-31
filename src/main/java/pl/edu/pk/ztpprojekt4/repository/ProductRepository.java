package pl.edu.pk.ztpprojekt4.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.client.WebClient;
import pl.edu.pk.ztpprojekt4.model.Product;
import pl.edu.pk.ztpprojekt4.model.ProductBasic;
import pl.edu.pk.ztpprojekt4.model.ProductRequest;

import java.time.Duration;
import java.util.List;

@Repository
public class ProductRepository {
    private static final Duration TIMEOUT_MS = Duration.ofSeconds(5);
    private final WebClient webClient;

    @Autowired
    public ProductRepository(WebClient webClient) {
        this.webClient = webClient;
    }

    public ResponseEntity<List<ProductBasic>> getAllProducts() {
        return webClient.get()
                .uri("/products")
                .retrieve()
                .toEntityList(ProductBasic.class)
                .block(TIMEOUT_MS);
    }

    public ResponseEntity<Product> getProductById(String id) {
        return webClient.get()
                .uri("/products/{id}", id)
                .retrieve()
                .toEntity(Product.class)
                .block(TIMEOUT_MS);
    }

    public ResponseEntity<String> insertProduct(ProductRequest request) {
        return webClient.post()
                .uri("/products")
                .bodyValue(request)
                .retrieve()
                .toEntity(String.class)
                .block(TIMEOUT_MS);
    }

    public ResponseEntity<Product> updateProduct(String id, ProductRequest request) {
        return webClient.put()
                .uri("/products/{id}", id)
                .bodyValue(request)
                .retrieve()
                .toEntity(Product.class)
                .block(TIMEOUT_MS);
    }

    public ResponseEntity<String> deleteProduct(String id) {
        return webClient.delete()
                .uri("/products/{id}", id)
                .retrieve()
                .toEntity(String.class)
                .block(TIMEOUT_MS);
    }
}
