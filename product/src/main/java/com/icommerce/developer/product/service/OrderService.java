package com.icommerce.developer.product.service;

import com.icommerce.developer.product.domain.ActionId;
import com.icommerce.developer.product.domain.Cart;
import com.icommerce.developer.product.domain.Order;
import com.icommerce.developer.product.messaging.UserActivitiesHistoricalEventPublisher;
import com.icommerce.developer.product.repository.CartRepository;
import com.icommerce.developer.product.repository.OrderRepository;
import com.icommerce.developer.product.security.AuthoritiesConstants;
import com.icommerce.developer.product.security.SecurityUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    private final CartRepository cartRepository;

    private final UserActivitiesHistoricalEventPublisher userActivitiesHistoricalEventPublisher;

    public OrderService(OrderRepository orderRepository,
                        CartRepository cartRepository,
                        UserActivitiesHistoricalEventPublisher userActivitiesHistoricalEventPublisher) {
        this.orderRepository = orderRepository;
        this.cartRepository = cartRepository;
        this.userActivitiesHistoricalEventPublisher = userActivitiesHistoricalEventPublisher;
    }

    @Transactional
    public Order makeAnOrder() {
        String currentUser = SecurityUtils.getCurrentUserLogin().orElse("system");
        Cart cart = cartRepository.getCart(currentUser);
        if (CollectionUtils.isEmpty(cart.getSelectedProducts())) {
            throw new RuntimeException("Your cart is empty, you are not allow to make an order");
        }
        Order order = new Order();
        order.createdDate(LocalDate.now());
        order.setCart(cart);
        order.setDeliveryDate(LocalDate.now().plusDays(5));
        order.setPaymentType("CASH");
        order.setCreatedBy(currentUser);
        order = orderRepository.save(order);
        cartRepository.clearCart(currentUser);
        userActivitiesHistoricalEventPublisher.publish(ActionId.CHECKOUT_ORDER.name(),
            String.format("User %s view order detail %s", currentUser, order));
        // Remove the shopping cart
        return order;
    }

    @Transactional(readOnly = true)
    public Optional<Order> findBy(String id) {
        try {
            return orderRepository.findById(id);
        } finally {
            userActivitiesHistoricalEventPublisher.publish(ActionId.GET_ORDER.name(),
                String.format("User %s view order detail %s", SecurityUtils.getCurrentUserLogin().orElse("system"), id));
        }
    }

    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    @Transactional(readOnly = true)
    public Page<Order> findAll(Pageable pageable) {
        try {
            return orderRepository.findAll(pageable);
        } finally {
            userActivitiesHistoricalEventPublisher.publish(ActionId.GET_ALL_ORDERS.name(),
                String.format("User %s view list order, %s", SecurityUtils.getCurrentUserLogin().orElse("system"), pageable));
        }
    }
}
