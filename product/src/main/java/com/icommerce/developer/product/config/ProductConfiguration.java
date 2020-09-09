package com.icommerce.developer.product.config;

import com.icommerce.developer.product.domain.Cart;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Configuration
public class ProductConfiguration {

    /**
     * Simulate caching shopping cart
     *
     * @return
     */
    @Bean
    public Map<String, Cart> shoppingCart() {
        return new ConcurrentHashMap<>();
    }
}
