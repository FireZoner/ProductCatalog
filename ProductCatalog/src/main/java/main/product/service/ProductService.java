/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.product.service;

import main.product.domain.Product;
import main.product.domain.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 *
 * @author zubbo
 */
@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Transactional(readOnly = true)
    public List<Product> findProducts(String query) {
        if (query == null || query.isBlank()) {
            return productRepository.findByAvailableTrueOrderByCreatedAtDesc();
        }

        return productRepository
                .findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCaseOrderByCreatedAtDesc(
                        query.trim(),
                        query.trim()
                )
                .stream()
                .filter(Product::isAvailable)
                .toList();
    }

    @Transactional(readOnly = true)
    public Product findProductById(Long id) {
        return productRepository.findById(id)
                .filter(Product::isAvailable)
                .orElseThrow(() -> new ProductNotFoundException("Товар не найден"));
    }
}
