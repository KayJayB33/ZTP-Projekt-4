package pl.edu.pk.ztpprojekt4.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.edu.pk.ztpprojekt4.model.Product;
import pl.edu.pk.ztpprojekt4.model.ProductBasic;
import pl.edu.pk.ztpprojekt4.model.ProductRequest;
import pl.edu.pk.ztpprojekt4.service.ProductService;

import java.util.List;

@Controller
public class ProductController {

    private final ProductService productService;

    public ProductController(@Autowired ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/")
    public String showLandingPage() {
        return "index";
    }

    @GetMapping("/products")
    public String listProducts(Model model) {
        List<ProductBasic> products = productService.getAllProducts();
        model.addAttribute("products", products);
        return "products-list";
    }

    @GetMapping("/products/{id}")
    public String showProductDetails(@PathVariable String id, Model model) {
        Product product = productService.getProductById(id);
        model.addAttribute("product", product);
        return "product-details";
    }

    @PostMapping("/products/{id}/delete")
    public String deleteProduct(@PathVariable String id, RedirectAttributes redirectAttributes) {
        final boolean result = productService.deleteProduct(id);
        if(!result) {
            redirectAttributes.addFlashAttribute("message", "Failed to delete product with id {%s}".formatted(id));
        } else {
            redirectAttributes.addFlashAttribute("message", "Product with id {%s} deleted successfully".formatted(id));
        }
        return "redirect:/products";
    }

    @GetMapping("/products/new")
    public String showAddProductForm(Model model) {
        model.addAttribute("productRequest", new ProductRequest());
        return "product-form";
    }

    @GetMapping("/products/{id}/edit")
    public String showEditProductForm(@PathVariable String id, Model model) {
        Product product = productService.getProductById(id);
        model.addAttribute("productId", id);
        model.addAttribute("productRequest", new ProductRequest(product.name(), product.description(), product.price(), product.availableQuantity()));
        return "product-form";
    }

    @PostMapping("/products")
    public String addProduct(@Valid @ModelAttribute ProductRequest productRequest, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "product-form";
        }
        final String result = productService.insertProduct(productRequest);
        redirectAttributes.addFlashAttribute("message", "Product added successfully. New product id is {%s}".formatted(result));
        return "redirect:/products";
    }

    @PostMapping("/products/{id}")
    public String editProduct(@PathVariable String id, @Valid @ModelAttribute ProductRequest productRequest, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "product-form";
        }
        final Product result = productService.updateProduct(id, productRequest);
        if (result == null) {
            redirectAttributes.addFlashAttribute("message", "Failed to update product with id {%s}".formatted(id));
            return "redirect:/products";
        }

        redirectAttributes.addFlashAttribute("message", """
                    Product updated successfully:
                    {
                        "id": "%s",
                        "name": "%s",
                        "description": "%s",
                        "price": %s,
                        "availableQuantity": %s,
                        "productStatus": "%s",
                        "createdDate": "%s",
                        "lastModifiedDate": "%s"
                    }
                """.formatted(
                    result.id(),
                    result.name(),
                    result.description(),
                    result.price(),
                    result.availableQuantity(),
                    result.productState(),
                    result.createdDate(),
                    result.lastModifiedDate()
        ));
        return "redirect:/products";
    }
}
