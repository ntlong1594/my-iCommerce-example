package com.icommerce.developer.historical.repository;

import com.icommerce.developer.historical.domain.UserActivitiesHistorical;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the Historical entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UserActivitiesHistoricalRepository extends MongoRepository<UserActivitiesHistorical, String> {
}
