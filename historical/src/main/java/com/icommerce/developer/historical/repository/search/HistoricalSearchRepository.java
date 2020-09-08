package com.icommerce.developer.historical.repository.search;

import com.icommerce.developer.historical.domain.Historical;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link Historical} entity.
 */
public interface HistoricalSearchRepository extends ElasticsearchRepository<Historical, String> {
}
