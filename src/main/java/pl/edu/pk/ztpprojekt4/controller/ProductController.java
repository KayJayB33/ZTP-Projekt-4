package pl.edu.pk.ztpprojekt4.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import pl.edu.pk.ztpprojekt4.model.ProductBasic;
import pl.edu.pk.ztpprojekt4.repository.ProductRepository;
import reactor.core.publisher.Mono;

import java.util.List;

@Controller
public class ProductController {

    private final ProductRepository repository;

    public ProductController(@Autowired ProductRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/products")
    public String listProducts(Model model) {
        List<ProductBasic> products = repository.getAllProducts();
        model.addAttribute("products", products);
        return "products";
    }
}
