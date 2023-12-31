package pl.edu.pk.ztpprojekt4.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.edu.pk.ztpprojekt4.model.Product;
import pl.edu.pk.ztpprojekt4.model.ProductBasic;
import pl.edu.pk.ztpprojekt4.model.ProductRequest;
import pl.edu.pk.ztpprojekt4.repository.ProductRepository;

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

    @GetMapping("/products/{id}")
    public String showProductDetails(@PathVariable String id, Model model) {
        Product product = repository.getProductById(id);
        model.addAttribute("product", product);
        return "product-details";
    }

    @PostMapping("/products/{id}/delete")
    public String deleteProduct(@PathVariable String id) {
        repository.deleteProduct(id);
        return "redirect:/products";
    }

    @GetMapping("/products/new")
    public String showAddProductForm(Model model) {
        model.addAttribute("productRequest", new ProductRequest());
        return "product-form";
    }

    @GetMapping("/products/{id}/edit")
    public String showEditProductForm(@PathVariable String id, Model model) {
        Product product = repository.getProductById(id);
        model.addAttribute("productId", id);
        model.addAttribute("productRequest", new ProductRequest(
                product.name(),
                product.description(),
                product.price(),
                product.availableQuantity()));
        return "product-form";
    }

    @PostMapping("/products")
    public String addProduct(@Valid @ModelAttribute ProductRequest productRequest, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return "product-form";
        }
        repository.insertProduct(productRequest);
        return "redirect:/products";
    }

    @PostMapping("/products/{id}")
    public String editProduct(@PathVariable String id, @Valid @ModelAttribute ProductRequest productRequest, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return "product-form";
        }
        repository.updateProduct(id, productRequest);
        return "redirect:/products";
    }
}
