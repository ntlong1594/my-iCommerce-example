package com.icommerce.developer.historical.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of {@link HistoricalSearchRepository} to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class HistoricalSearchRepositoryMockConfiguration {

    @MockBean
    private HistoricalSearchRepository mockHistoricalSearchRepository;

}
