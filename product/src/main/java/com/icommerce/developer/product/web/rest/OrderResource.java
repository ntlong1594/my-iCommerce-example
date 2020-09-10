package com.icommerce.developer.product.web.rest;

import com.icommerce.developer.product.domain.Order;
import com.icommerce.developer.product.security.AuthoritiesConstants;
import com.icommerce.developer.product.service.OrderService;
import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.icommerce.developer.product.domain.Order}.
 */
@RestController
@RequestMapping("/api")
public class OrderResource {

    private final Logger log = LoggerFactory.getLogger(OrderResource.class);

    private static final String ENTITY_NAME = "productOrder";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OrderService orderService;

    public OrderResource(OrderService orderService) {
        this.orderService = orderService;
    }

    /**
     * Make an order when user want to purchase their cart
     *
     * @return
     */
    @PostMapping("/orders")
    public ResponseEntity<Order> makeOrder() throws URISyntaxException {
        Order result = orderService.makeAnOrder();
        return ResponseEntity.created(new URI("/api/orders/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * {@code GET  /orders} : get all the orders.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of orders in body.
     */
    @GetMapping("/orders")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public List<Order> getAllOrders(Pageable pageable) {
        log.debug("REST request to get all Orders");
        return orderService.findAll(pageable).getContent();
    }

    /**
     * {@code GET  /orders/:id} : get the "id" order.
     *
     * @param id the id of the order to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the order, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/orders/{id}")
    public ResponseEntity<Order> getOrder(@PathVariable String id) {
        log.debug("REST request to get Order : {}", id);
        Optional<Order> order = orderService.findBy(id);
        return ResponseUtil.wrapOrNotFound(order);
    }

}
