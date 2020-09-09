package com.icommerce.developer.product.service;

import com.icommerce.developer.product.domain.ActionId;
import com.icommerce.developer.product.domain.Cart;
import com.icommerce.developer.product.domain.Product;
import com.icommerce.developer.product.messaging.UserActivitiesHistoricalEventPublisher;
import com.icommerce.developer.product.repository.CartRepository;
import org.springframework.stereotype.Service;

@Service
public class CartService {

    private final CartRepository cartRepository;

    private final ProductService productService;

    private final UserActivitiesHistoricalEventPublisher userActivitiesHistoricalEventPublisher;

    public CartService(CartRepository cartRepository, ProductService productService,
                       UserActivitiesHistoricalEventPublisher userActivitiesHistoricalEventPublisher) {
        this.cartRepository = cartRepository;
        this.productService = productService;
        this.userActivitiesHistoricalEventPublisher = userActivitiesHistoricalEventPublisher;
    }

    public Cart addToCart(String user, String productId, Integer quantity) {
        try {
            Product product = productService.findDetailBy(productId).orElseThrow(() ->
                new RuntimeException("Could not found product with id " + productId));
            return cartRepository.addToCart(user, product, quantity);
        } finally {
            userActivitiesHistoricalEventPublisher.publish(ActionId.ADD_TO_CART.name(), String.format("User adding to cart product id %s and %s units", user, productId, quantity));
        }
    }

    public Cart getCart(String user) {
        try {
            return cartRepository.getCart(user);
        } finally {
            userActivitiesHistoricalEventPublisher.publish(ActionId.VIEW_CART.name(), String.format("User %s views cart detail", user));
        }
    }
}
