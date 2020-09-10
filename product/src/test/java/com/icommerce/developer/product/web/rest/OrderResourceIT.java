package com.icommerce.developer.product.web.rest;

import com.icommerce.developer.product.ProductApp;
import com.icommerce.developer.product.config.TestSecurityConfiguration;
import com.icommerce.developer.product.domain.Cart;
import com.icommerce.developer.product.domain.Order;
import com.icommerce.developer.product.domain.Product;
import com.icommerce.developer.product.repository.CartRepository;
import com.icommerce.developer.product.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link OrderResource} REST controller.
 */
@SpringBootTest(classes = {ProductApp.class, TestSecurityConfiguration.class})
@AutoConfigureMockMvc
@WithMockUser
public class OrderResourceIT {

    private static final String DEFAULT_PAYMENT_TYPE = "CASH";

    private static final LocalDate DEFAULT_CREATED_DATE = LocalDate.now();

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private MockMvc restOrderMockMvc;

    private Order order;

    /**
     * Create an entity for this test.
     * <p>
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Order createEntity() {
        Order order = new Order();
        order.setPaymentType(DEFAULT_PAYMENT_TYPE);
        order.setDeliveryDate(DEFAULT_CREATED_DATE.plusDays(5));
        order.setCart(new Cart());
        order.setCreatedBy("user");
        order.setCreatedDate(DEFAULT_CREATED_DATE);
        return order;
    }

    @BeforeEach
    public void initTest() {
        orderRepository.deleteAll();
        order = createEntity();
    }

    @Test
    public void createOrder() throws Exception {
        int databaseSizeBeforeCreate = orderRepository.findAll().size();
        Product product = new Product();
        product.setId(UUID.randomUUID().toString());
        product.setPrice(10000);
        cartRepository.addToCart("user", product, 5);
        // Create the Order
        restOrderMockMvc.perform(post("/api/orders").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(order)))
            .andExpect(status().isCreated());

        // Validate the Order in the database
        List<Order> orderList = orderRepository.findAll();
        assertThat(orderList).hasSize(databaseSizeBeforeCreate + 1);
        Order testOrder = orderList.get(orderList.size() - 1);
        assertThat(testOrder.getPaymentType()).isEqualTo(DEFAULT_PAYMENT_TYPE);
        assertThat(testOrder.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
    }

    @Test
    public void getAllOrders_should_throw_403() throws Exception {
        // Initialize the database
        orderRepository.save(order);

        // Get all the orderList
        restOrderMockMvc.perform(get("/api/orders?sort=id,desc"))
            .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    public void getAllOrders() throws Exception {
        // Initialize the database
        orderRepository.save(order);

        // Get all the orderList
        restOrderMockMvc.perform(get("/api/orders?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(order.getId())))
            .andExpect(jsonPath("$.[*].paymentType").value(hasItem(DEFAULT_PAYMENT_TYPE)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())));
    }

    @Test
    public void getOrder() throws Exception {
        // Initialize the database
        orderRepository.save(order);

        // Get the order
        restOrderMockMvc.perform(get("/api/orders/{id}", order.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(order.getId()))
            .andExpect(jsonPath("$.paymentType").value(DEFAULT_PAYMENT_TYPE))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()));
    }

    @Test
    public void getNonExistingOrder() throws Exception {
        // Get the order
        restOrderMockMvc.perform(get("/api/orders/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }
}
