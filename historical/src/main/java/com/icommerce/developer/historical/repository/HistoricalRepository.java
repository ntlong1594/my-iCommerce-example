package com.icommerce.developer.historical.repository;

import com.icommerce.developer.historical.domain.Historical;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the Historical entity.
 */
@SuppressWarnings("unused")
@Repository
public interface HistoricalRepository extends MongoRepository<Historical, String> {
}
