package pl.edu.pk.ztpprojekt4.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pl.edu.pk.ztpprojekt4.model.Product;
import pl.edu.pk.ztpprojekt4.model.ProductBasic;
import pl.edu.pk.ztpprojekt4.model.ProductRequest;
import pl.edu.pk.ztpprojekt4.repository.ProductRepository;

import java.util.List;

@Service
public class ProductService {
    private final ProductRepository repository;

    public ProductService(@Autowired ProductRepository repository) {
        this.repository = repository;
    }

    public List<ProductBasic> getAllProducts() {
        final ResponseEntity<List<ProductBasic>> response = repository.getAllProducts();
        if(response.getStatusCode().isError()) {
            throw new RuntimeException("Failed to fetch products: %s".formatted(response.getBody()));
        }

        return response.getBody();
    }

    public Product getProductById(String id) {
        final ResponseEntity<Product> response = repository.getProductById(id);
        if(response.getStatusCode().isError()) {
            throw new RuntimeException("Failed to fetch product with id {%s}: %s".formatted(id, response.getBody()));
        }

        return response.getBody();
    }

    public String insertProduct(ProductRequest request) {
        final ResponseEntity<String> response = repository.insertProduct(request);
        if(response.getStatusCode().isError()) {
            throw new RuntimeException("Failed to insert product: %s".formatted(response.getBody()));
        }

        return response.getBody();
    }

    public String updateProduct(String id, ProductRequest request) {
        final ResponseEntity<String> response = repository.updateProduct(id, request);
        if(response.getStatusCode().isError()) {
            throw new RuntimeException("Failed to update product with id {%s}: %s".formatted(id, response.getBody()));
        }

        return response.getBody();
    }

    public boolean deleteProduct(String id) {
        final ResponseEntity<String> response = repository.deleteProduct(id);
        if(response.getStatusCode().isError()) {
            throw new RuntimeException("Failed to delete product with id {%s}: %s".formatted(id, response.getBody()));
        }

        return response.getStatusCode().is2xxSuccessful();
    }
}
