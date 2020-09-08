package com.icommerce.developer.product.repository;

import com.icommerce.developer.product.domain.Cart;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the Cart entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CartRepository extends MongoRepository<Cart, String> {
}
