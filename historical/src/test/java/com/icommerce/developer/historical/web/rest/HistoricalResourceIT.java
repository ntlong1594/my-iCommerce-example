package com.icommerce.developer.historical.web.rest;

import com.icommerce.developer.historical.HistoricalApp;
import com.icommerce.developer.historical.config.TestSecurityConfiguration;
import com.icommerce.developer.historical.domain.UserActivitiesHistorical;
import com.icommerce.developer.historical.repository.UserActivitiesHistoricalRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link HistoricalResource} REST controller.
 */
@SpringBootTest(classes = {HistoricalApp.class, TestSecurityConfiguration.class})
@AutoConfigureMockMvc
@WithMockUser
public class HistoricalResourceIT {

    private static final String DEFAULT_USER_ID = "AAAAAAAAAA";

    private static final String DEFAULT_CORRELATION_ID = "AAAAAAAAAA";

    private static final String DEFAULT_ACTION_ID = "AAAAAAAAAA";

    private static final String DEFAULT_ACTION_DESCRIPTION = "AAAAAAAAAA";

    private static final LocalDate DEFAULT_ACTION_DATE = LocalDate.ofEpochDay(0L);

    @Autowired
    private UserActivitiesHistoricalRepository userActivitiesHistoricalRepository;

    @Autowired
    private MockMvc restHistoricalMockMvc;

    public static UserActivitiesHistorical createEntity() {
        return new UserActivitiesHistorical()
            .userId(DEFAULT_USER_ID)
            .actionId(DEFAULT_ACTION_ID)
            .actionDescription(DEFAULT_ACTION_DESCRIPTION)
            .actionDate(DEFAULT_ACTION_DATE);
    }

    @Test
    public void getAllHistoricals() throws Exception {
        // Initialize the database
        UserActivitiesHistorical userActivitiesHistorical = createEntity();
        userActivitiesHistoricalRepository.save(userActivitiesHistorical);

        // Get all the historicalList
        restHistoricalMockMvc.perform(get("/api/historicals?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userActivitiesHistorical.getId())))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID)))
            .andExpect(jsonPath("$.[*].actionId").value(hasItem(DEFAULT_ACTION_ID)))
            .andExpect(jsonPath("$.[*].actionDescription").value(hasItem(DEFAULT_ACTION_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].actionDate").value(hasItem(DEFAULT_ACTION_DATE.toString())));
    }

}
