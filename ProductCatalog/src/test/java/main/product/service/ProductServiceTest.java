package main.product.service;

import main.product.domain.Product;
import main.product.domain.ProductRepository;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 *
 * @author zubbo
 */
public class ProductServiceTest {

    private final ProductRepository productRepository = mock(ProductRepository.class);
    private final ProductService productService = new ProductService(productRepository);

    @Test
    void shouldReturnAvailableProductsWhenQueryIsBlank() {
        Product product = new Product(
                "Ноутбук",
                "Описание",
                BigDecimal.valueOf(50000),
                "image.jpg",
                true
        );

        when(productRepository.findByAvailableTrueOrderByCreatedAtDesc())
                .thenReturn(List.of(product));

        List<Product> result = productService.findProducts("");

        assertEquals(1, result.size());
        assertEquals("Ноутбук", result.get(0).getTitle());

        verify(productRepository).findByAvailableTrueOrderByCreatedAtDesc();
        verifyNoMoreInteractions(productRepository);
    }

    @Test
    void shouldReturnProductByIdWhenProductIsAvailable() {
        Product product = new Product(
                "Смартфон",
                "Описание",
                BigDecimal.valueOf(30000),
                "image.jpg",
                true
        );

        when(productRepository.findById(1L))
                .thenReturn(Optional.of(product));

        Product result = productService.findProductById(1L);

        assertEquals("Смартфон", result.getTitle());

        verify(productRepository).findById(1L);
        verifyNoMoreInteractions(productRepository);
    }

    @Test
    void shouldThrowExceptionWhenProductNotFound() {
        when(productRepository.findById(999L))
                .thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class, () -> productService.findProductById(999L));
        verify(productRepository).findById(999L);
        verifyNoMoreInteractions(productRepository);
    }
}
