package main.product.domain;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 *
 * @author zubbo
 */
@Entity
@Table(name = "products")
public class Product {

    private static final int SHORT_DESCRIPTION_LIMIT = 140;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false, length = 150)
    private String title;

    @Column(name = "description", nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(name = "price", nullable = false, precision = 12, scale = 2)
    private BigDecimal price;

    @Column(name = "image_url", nullable = false, length = 500)
    private String imageUrl;

    @Column(name = "available", nullable = false)
    private boolean available;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    protected Product() {
    }

    public Product(String title, String description, BigDecimal price, String imageUrl, boolean available) {
        this.title = title;
        this.description = description;
        this.price = price;
        this.imageUrl = imageUrl;
        this.available = available;
        this.createdAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getShortDescription() {
        if (description.length() <= SHORT_DESCRIPTION_LIMIT) {
            return description;
        }

        return description.substring(0, SHORT_DESCRIPTION_LIMIT) + "...";
    }

    public BigDecimal getPrice() {
        return price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public boolean isAvailable() {
        return available;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public boolean matchesSearchQuery(String query) {
        if (query == null || query.isBlank()) {
            return true;
        }

        String normalizedQuery = query.toLowerCase();
        return title.toLowerCase().contains(normalizedQuery)
                || description.toLowerCase().contains(normalizedQuery);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product product)) return false;
        return Objects.equals(id, product.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
