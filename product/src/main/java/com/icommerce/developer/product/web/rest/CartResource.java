package com.icommerce.developer.product.web.rest;

import com.icommerce.developer.product.domain.Cart;
import com.icommerce.developer.product.security.SecurityUtils;
import com.icommerce.developer.product.service.CartService;
import io.github.jhipster.web.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * REST controller for managing {@link com.icommerce.developer.product.domain.Cart}.
 */
@RestController
@RequestMapping("/api")
public class CartResource {

    private final Logger log = LoggerFactory.getLogger(CartResource.class);

    private static final String ENTITY_NAME = "productCart";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CartService cartService;

    public CartResource(CartService cartService) {
        this.cartService = cartService;
    }

    /**
     * Add products to shopping cart
     * @param productId
     * @param quantity
     * @return
     * @throws URISyntaxException
     */
    @PostMapping("/carts/{productId}/{quantity}")
    public ResponseEntity<Cart> addToCart(@PathVariable("productId") String productId,
                                          @PathVariable("quantity") Integer quantity) throws URISyntaxException {
        log.debug("REST request to save Cart product Id : {} , {} units", productId, quantity);
        String user = SecurityUtils.getCurrentUserLogin().orElse("");
        Cart result = cartService.addToCart(user, productId, quantity);
        return ResponseEntity.created(new URI("/api/carts/" + productId))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, productId.toString()))
            .body(result);
    }

    /**
     * Get current user shoppnig cart
     *
     * @return
     */
    @GetMapping("/carts")
    public ResponseEntity<Cart> getCart() {
        String user = SecurityUtils.getCurrentUserLogin().orElse("");
        return ResponseEntity.ok(cartService.getCart(user));
    }

}
