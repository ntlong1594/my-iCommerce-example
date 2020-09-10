package com.icommerce.developer.product.repository;

import com.icommerce.developer.product.domain.Cart;
import com.icommerce.developer.product.domain.Product;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Cart Repository does not interact with MongoDB
 * It just work with an in memory ConCurrentHashMap to store and retrieve the shopping cart
 */
@Component
public class CartRepository {

    private final Map<String, Cart> shoppingCart;

    public CartRepository(Map<String, Cart> shoppingCart) {
        this.shoppingCart = shoppingCart;
    }

    public Cart addToCart(String userId, Product product, Integer quantity) {
        Cart cart = getCart(userId);
        cart.getSelectedProducts().put(product.getId(), quantity);
        cart.setTotalAmount(calculateTotalAmount(product, quantity, cart.getTotalAmount()));
        shoppingCart.put(userId, cart);
        return cart;
    }

    private Integer calculateTotalAmount(Product product, Integer quantity, Integer currentAmount) {
        currentAmount += (quantity * product.getPrice());
        return currentAmount;
    }

    public Cart getCart(String userId) {
        if (shoppingCart.get(userId) == null) {
            Cart cart = new Cart();
            cart.setId(UUID.randomUUID());
            cart.setUserId(userId);
            cart.setCreatedDate(LocalDate.now());
            cart.setTotalAmount(0);
            cart.setSelectedProducts(new HashMap<>());
            shoppingCart.put(userId, cart);
        }
        return shoppingCart.get(userId);
    }

}
