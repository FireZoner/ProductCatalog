package main.product.web;

import main.product.domain.Product;
import main.product.service.ProductNotFoundException;
import main.product.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 *
 * @author zubbo
 */
@Controller
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public String showProducts(@RequestParam(required = false) String query, Model model) {
        List<Product> products = productService.findProducts(query);

        model.addAttribute("products", products);
        model.addAttribute("query", query);

        return "products/list";
    }

    @GetMapping("/{id}")
    public String showProductDetails(@PathVariable Long id, Model model) {
        Product product = productService.findProductById(id);
        model.addAttribute("product", product);

        return "products/details";
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public String handleProductNotFound(Model model) {
        model.addAttribute("errorMessage", "Товар не найден или недоступен");
        return "error";
    }
}
