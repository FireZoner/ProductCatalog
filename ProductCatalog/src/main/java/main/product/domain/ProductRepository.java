/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.product.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

/**
 *
 * @author zubbo
 */
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByAvailableTrueOrderByCreatedAtDesc();

    List<Product> findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCaseOrderByCreatedAtDesc(
            String titleQuery,
            String descriptionQuery
    );
}
