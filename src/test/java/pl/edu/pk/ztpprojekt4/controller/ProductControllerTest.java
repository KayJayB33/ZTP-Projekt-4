package pl.edu.pk.ztpprojekt4.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import pl.edu.pk.ztpprojekt4.model.Product;
import pl.edu.pk.ztpprojekt4.model.ProductBasic;
import pl.edu.pk.ztpprojekt4.model.ProductRequest;
import pl.edu.pk.ztpprojekt4.model.ProductState;
import pl.edu.pk.ztpprojekt4.service.ProductService;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class ProductControllerTest {

    @Mock
    ProductService productService;

    @InjectMocks
    ProductController controller;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void shouldShowLandingPage() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"));
    }

    @Test
    void shouldListProducts() throws Exception {
        List<ProductBasic> products = Arrays.asList(
                new ProductBasic("1",
                        "Product 1",
                        BigDecimal.TEN,
                        ProductState.AVAILABLE),
                new ProductBasic("2",
                        "Product 2",
                        BigDecimal.ZERO,
                        ProductState.OUT_OF_STOCK));
        when(productService.getAllProducts()).thenReturn(products);

        mockMvc.perform(get("/products"))
                .andExpect(status().isOk())
                .andExpect(view().name("products-list"))
                .andExpect(model().attribute("products", products));

        verify(productService, times(1)).getAllProducts();
    }

    @Test
    void shouldShowProductDetails() throws Exception {
        Product product = new Product("1",
                "Product 1",
                "Description 1",
                BigDecimal.TEN,
                10,
                ProductState.AVAILABLE,
                null,
                null);
        when(productService.getProductById("1")).thenReturn(product);

        mockMvc.perform(get("/products/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("product-details"))
                .andExpect(model().attribute("product", product));

        verify(productService, times(1)).getProductById("1");
    }

    @Test
    void shouldDeleteProduct() throws Exception {
        when(productService.deleteProduct("1")).thenReturn(true);

        mockMvc.perform(post("/products/1/delete"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/products"))
                .andExpect(flash().attributeExists("message"))
                .andExpect(flash().attribute("message", "Product with id {1} deleted successfully"));

        verify(productService, times(1)).deleteProduct("1");
    }

    @Test
    void shouldShowAddProductForm() throws Exception {
        mockMvc.perform(get("/products/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("product-form"))
                .andExpect(model().attributeExists("productRequest"))
                .andExpect(model().attribute("productRequest", new ProductRequest()));
    }

    @Test
    void shouldShowEditProductForm() throws Exception {
        Product product = new Product("1",
                "Product 1",
                "Description 1",
                BigDecimal.TEN,
                10,
                ProductState.AVAILABLE,
                null,
                null);
        when(productService.getProductById("1")).thenReturn(product);

        mockMvc.perform(get("/products/1/edit"))
                .andExpect(status().isOk())
                .andExpect(view().name("product-form"))
                .andExpect(model().attribute("productId", "1"))
                .andExpect(model().attributeExists("productRequest"))
                .andExpect(model().attribute("productRequest",
                        new ProductRequest(product.name(),
                            product.description(),
                            product.price(),
                            product.availableQuantity())));

        verify(productService, times(1)).getProductById("1");
    }

    @Test
    void shouldAddProduct() throws Exception {
        ProductRequest productRequest = new ProductRequest("Product 1",
                "Description 1",
                BigDecimal.TEN,
                10);

        when(productService.insertProduct(productRequest)).thenReturn("1");

        mockMvc.perform(post("/products")
                .flashAttr("productRequest", productRequest))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/products"))
                .andExpect(flash().attributeExists("message"))
                .andExpect(flash().attribute("message",
                        "Product added successfully. New product id is {1}"));

        verify(productService, times(1)).insertProduct(productRequest);
    }

    @Test
    void shouldEditProduct() throws Exception {
        String id = "1";
        ProductRequest productRequest = new ProductRequest("Updated Product",
                "Updated Description",
                BigDecimal.valueOf(20),
                20);

        Product newProduct = new Product(id,
                "Updated Product",
                "Updated Description",
                BigDecimal.valueOf(20),
                20,
                ProductState.AVAILABLE,
                null,
                null);
        when(productService.updateProduct(id, productRequest)).thenReturn(newProduct);

        mockMvc.perform(post("/products/" + id)
                .flashAttr("productRequest", productRequest))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/products"))
                .andExpect(flash().attributeExists("message"))
                .andExpect(flash().attribute("message", """
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
                        newProduct.id(),
                        newProduct.name(),
                        newProduct.description(),
                        newProduct.price(),
                        newProduct.availableQuantity(),
                        newProduct.productState(),
                        newProduct.createdDate(),
                        newProduct.lastModifiedDate())));

        verify(productService, times(1)).updateProduct(id, productRequest);
    }
}