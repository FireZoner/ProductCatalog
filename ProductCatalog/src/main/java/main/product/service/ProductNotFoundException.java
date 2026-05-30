package main.product.service;

/**
 *
 * @author zubbo
 */
public class ProductNotFoundException extends RuntimeException {

    public ProductNotFoundException(String message) {
        super(message);
    }
}
