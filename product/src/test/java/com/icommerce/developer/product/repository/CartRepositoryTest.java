package com.icommerce.developer.product.repository;

import com.icommerce.developer.product.domain.Cart;
import com.icommerce.developer.product.domain.Product;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CartRepositoryTest {

    @Mock
    Map<String, Cart> shoppingCart;

    @InjectMocks
    CartRepository cartRepository;

    @Test
    public void addToCart_should_work_correctly() {
        // Given
        Product product = new Product();
        product.setPrice(1000);
        product.setId("123");
        Cart cart = new Cart();
        cart.setTotalAmount(0);
        cart.setSelectedProducts(new HashMap<>());
        when(shoppingCart.get("userId")).thenReturn(cart);
        // When
        Cart result = cartRepository.addToCart("userId", product, 5);
        // Then
        assertThat(result.getSelectedProducts().containsKey("123")).isTrue();
        assertThat(result.getSelectedProducts().get("123").getPricePerUnit()).isEqualTo(1000);
        assertThat(result.getSelectedProducts().get("123").getQuantity()).isEqualTo(5);
        assertThat(result.getTotalAmount()).isEqualTo(5000);
        verify(shoppingCart).put(eq("userId"), any(Cart.class));
    }

    @Test
    public void getCart_should_create_empty_if_missing() {
        // Given
        when(shoppingCart.get("userId")).thenReturn(null);
        // When
        Cart result = cartRepository.getCart("userId");
        // Then
        verify(shoppingCart).get("userId");
        verify(shoppingCart).put(eq("userId"), any(Cart.class));
    }

    @Test
    public void getCart_should_get_current_if_exists() {
        // Given
        when(shoppingCart.get("userId")).thenReturn(null);
        // When
        Cart result = cartRepository.getCart("userId");
        //
        assertThat(result.getTotalAmount()).isEqualTo(0);
        assertThat(result.getCreatedDate()).isEqualTo(LocalDate.now());
        assertThat(result.getSelectedProducts().size()).isEqualTo(0);
        verify(shoppingCart).put(eq("userId"), any(Cart.class));
        verify(shoppingCart).get("userId");
    }
}
