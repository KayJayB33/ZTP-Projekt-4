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

    public List<ProductBasic> getAllProducts() {
        return webClient.get()
                .uri("/products")
                .retrieve()
                .bodyToFlux(ProductBasic.class)
                .collectList()
                .block(TIMEOUT_MS);
    }

    public boolean deleteProduct(String id) {
        ResponseEntity<String> response = webClient.delete()
                .uri("/products/{id}", id)
                .retrieve()
                .toEntity(String.class)
                .block(TIMEOUT_MS);

        if(response == null) {
            return false;
        }

        return response.getStatusCode().is2xxSuccessful();
    }

    public Product getProductById(String id) {
        return webClient.get()
                .uri("/products/{id}", id)
                .retrieve()
                .bodyToMono(Product.class)
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

    public ResponseEntity<String> updateProduct(String id, ProductRequest request) {
        return webClient.put()
                .uri("/products/{id}")
                .bodyValue(request)
                .retrieve()
                .toEntity(String.class)
                .block(TIMEOUT_MS);
    }
}
