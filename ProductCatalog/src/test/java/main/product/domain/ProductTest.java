/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.product.domain;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author zubbo
 */
public class ProductTest {

    @Test
    void shouldReturnFullDescriptionWhenDescriptionIsShort() {
        Product product = new Product(
                "Ноутбук",
                "Короткое описание",
                BigDecimal.valueOf(1000),
                "image.jpg",
                true
        );

        assertEquals("Короткое описание", product.getShortDescription());
    }

    @Test
    void shouldReturnShortDescriptionWhenDescriptionIsLong() {
        String longDescription = "А".repeat(200);

        Product product = new Product(
                "Ноутбук",
                longDescription,
                BigDecimal.valueOf(1000),
                "image.jpg",
                true
        );

        assertTrue(product.getShortDescription().endsWith("..."));
        assertTrue(product.getShortDescription().length() < longDescription.length());
    }

    @Test
    void shouldMatchSearchQueryByTitle() {
        Product product = new Product(
                "Игровой ноутбук",
                "Описание товара",
                BigDecimal.valueOf(1000),
                "image.jpg",
                true
        );

        assertTrue(product.matchesSearchQuery("ноутбук"));
    }
}
