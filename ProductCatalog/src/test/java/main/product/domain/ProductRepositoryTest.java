package main.product.domain;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author zubbo
 */
@DataJpaTest
@ActiveProfiles("test")
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    void shouldSaveAndFindAvailableProducts() {
        Product product = new Product(
                "Ноутбук",
                "Описание ноутбука",
                BigDecimal.valueOf(50000),
                "image.jpg",
                true
        );

        productRepository.save(product);

        List<Product> products = productRepository.findByAvailableTrueOrderByCreatedAtDesc();

        assertEquals(1, products.size());
        assertEquals("Ноутбук", products.getFirst().getTitle());
    }

    @Test
    void shouldFindProductBySearchQuery() {
        Product product = new Product(
                "Смартфон",
                "Телефон с хорошей камерой",
                BigDecimal.valueOf(30000),
                "image.jpg",
                true
        );

        productRepository.save(product);

        List<Product> products =
                productRepository.findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCaseOrderByCreatedAtDesc(
                        "смартфон",
                        "смартфон"
                );

        assertEquals(1, products.size());
        assertEquals("Смартфон", products.getFirst().getTitle());
    }
}
