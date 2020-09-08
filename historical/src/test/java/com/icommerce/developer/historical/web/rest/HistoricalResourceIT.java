package com.icommerce.developer.historical.web.rest;

import com.icommerce.developer.historical.HistoricalApp;
import com.icommerce.developer.historical.config.TestSecurityConfiguration;
import com.icommerce.developer.historical.domain.Historical;
import com.icommerce.developer.historical.repository.HistoricalRepository;
import com.icommerce.developer.historical.repository.search.HistoricalSearchRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link HistoricalResource} REST controller.
 */
@SpringBootTest(classes = { HistoricalApp.class, TestSecurityConfiguration.class })
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class HistoricalResourceIT {

    private static final String DEFAULT_USER_ID = "AAAAAAAAAA";
    private static final String UPDATED_USER_ID = "BBBBBBBBBB";

    private static final String DEFAULT_CORRELATION_ID = "AAAAAAAAAA";
    private static final String UPDATED_CORRELATION_ID = "BBBBBBBBBB";

    private static final String DEFAULT_ACTION_ID = "AAAAAAAAAA";
    private static final String UPDATED_ACTION_ID = "BBBBBBBBBB";

    private static final String DEFAULT_ACTION_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_ACTION_DESCRIPTION = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_ACTION_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_ACTION_DATE = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private HistoricalRepository historicalRepository;

    /**
     * This repository is mocked in the com.icommerce.developer.historical.repository.search test package.
     *
     * @see com.icommerce.developer.historical.repository.search.HistoricalSearchRepositoryMockConfiguration
     */
    @Autowired
    private HistoricalSearchRepository mockHistoricalSearchRepository;

    @Autowired
    private MockMvc restHistoricalMockMvc;

    private Historical historical;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Historical createEntity() {
        Historical historical = new Historical()
            .userId(DEFAULT_USER_ID)
            .correlationId(DEFAULT_CORRELATION_ID)
            .actionId(DEFAULT_ACTION_ID)
            .actionDescription(DEFAULT_ACTION_DESCRIPTION)
            .actionDate(DEFAULT_ACTION_DATE);
        return historical;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Historical createUpdatedEntity() {
        Historical historical = new Historical()
            .userId(UPDATED_USER_ID)
            .correlationId(UPDATED_CORRELATION_ID)
            .actionId(UPDATED_ACTION_ID)
            .actionDescription(UPDATED_ACTION_DESCRIPTION)
            .actionDate(UPDATED_ACTION_DATE);
        return historical;
    }

    @BeforeEach
    public void initTest() {
        historicalRepository.deleteAll();
        historical = createEntity();
    }

    @Test
    public void createHistorical() throws Exception {
        int databaseSizeBeforeCreate = historicalRepository.findAll().size();
        // Create the Historical
        restHistoricalMockMvc.perform(post("/api/historicals").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(historical)))
            .andExpect(status().isCreated());

        // Validate the Historical in the database
        List<Historical> historicalList = historicalRepository.findAll();
        assertThat(historicalList).hasSize(databaseSizeBeforeCreate + 1);
        Historical testHistorical = historicalList.get(historicalList.size() - 1);
        assertThat(testHistorical.getUserId()).isEqualTo(DEFAULT_USER_ID);
        assertThat(testHistorical.getCorrelationId()).isEqualTo(DEFAULT_CORRELATION_ID);
        assertThat(testHistorical.getActionId()).isEqualTo(DEFAULT_ACTION_ID);
        assertThat(testHistorical.getActionDescription()).isEqualTo(DEFAULT_ACTION_DESCRIPTION);
        assertThat(testHistorical.getActionDate()).isEqualTo(DEFAULT_ACTION_DATE);

        // Validate the Historical in Elasticsearch
        verify(mockHistoricalSearchRepository, times(1)).save(testHistorical);
    }

    @Test
    public void createHistoricalWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = historicalRepository.findAll().size();

        // Create the Historical with an existing ID
        historical.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restHistoricalMockMvc.perform(post("/api/historicals").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(historical)))
            .andExpect(status().isBadRequest());

        // Validate the Historical in the database
        List<Historical> historicalList = historicalRepository.findAll();
        assertThat(historicalList).hasSize(databaseSizeBeforeCreate);

        // Validate the Historical in Elasticsearch
        verify(mockHistoricalSearchRepository, times(0)).save(historical);
    }


    @Test
    public void checkUserIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = historicalRepository.findAll().size();
        // set the field null
        historical.setUserId(null);

        // Create the Historical, which fails.


        restHistoricalMockMvc.perform(post("/api/historicals").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(historical)))
            .andExpect(status().isBadRequest());

        List<Historical> historicalList = historicalRepository.findAll();
        assertThat(historicalList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkCorrelationIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = historicalRepository.findAll().size();
        // set the field null
        historical.setCorrelationId(null);

        // Create the Historical, which fails.


        restHistoricalMockMvc.perform(post("/api/historicals").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(historical)))
            .andExpect(status().isBadRequest());

        List<Historical> historicalList = historicalRepository.findAll();
        assertThat(historicalList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkActionIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = historicalRepository.findAll().size();
        // set the field null
        historical.setActionId(null);

        // Create the Historical, which fails.


        restHistoricalMockMvc.perform(post("/api/historicals").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(historical)))
            .andExpect(status().isBadRequest());

        List<Historical> historicalList = historicalRepository.findAll();
        assertThat(historicalList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkActionDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = historicalRepository.findAll().size();
        // set the field null
        historical.setActionDate(null);

        // Create the Historical, which fails.


        restHistoricalMockMvc.perform(post("/api/historicals").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(historical)))
            .andExpect(status().isBadRequest());

        List<Historical> historicalList = historicalRepository.findAll();
        assertThat(historicalList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllHistoricals() throws Exception {
        // Initialize the database
        historicalRepository.save(historical);

        // Get all the historicalList
        restHistoricalMockMvc.perform(get("/api/historicals?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(historical.getId())))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID)))
            .andExpect(jsonPath("$.[*].correlationId").value(hasItem(DEFAULT_CORRELATION_ID)))
            .andExpect(jsonPath("$.[*].actionId").value(hasItem(DEFAULT_ACTION_ID)))
            .andExpect(jsonPath("$.[*].actionDescription").value(hasItem(DEFAULT_ACTION_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].actionDate").value(hasItem(DEFAULT_ACTION_DATE.toString())));
    }
    
    @Test
    public void getHistorical() throws Exception {
        // Initialize the database
        historicalRepository.save(historical);

        // Get the historical
        restHistoricalMockMvc.perform(get("/api/historicals/{id}", historical.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(historical.getId()))
            .andExpect(jsonPath("$.userId").value(DEFAULT_USER_ID))
            .andExpect(jsonPath("$.correlationId").value(DEFAULT_CORRELATION_ID))
            .andExpect(jsonPath("$.actionId").value(DEFAULT_ACTION_ID))
            .andExpect(jsonPath("$.actionDescription").value(DEFAULT_ACTION_DESCRIPTION))
            .andExpect(jsonPath("$.actionDate").value(DEFAULT_ACTION_DATE.toString()));
    }
    @Test
    public void getNonExistingHistorical() throws Exception {
        // Get the historical
        restHistoricalMockMvc.perform(get("/api/historicals/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateHistorical() throws Exception {
        // Initialize the database
        historicalRepository.save(historical);

        int databaseSizeBeforeUpdate = historicalRepository.findAll().size();

        // Update the historical
        Historical updatedHistorical = historicalRepository.findById(historical.getId()).get();
        updatedHistorical
            .userId(UPDATED_USER_ID)
            .correlationId(UPDATED_CORRELATION_ID)
            .actionId(UPDATED_ACTION_ID)
            .actionDescription(UPDATED_ACTION_DESCRIPTION)
            .actionDate(UPDATED_ACTION_DATE);

        restHistoricalMockMvc.perform(put("/api/historicals").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedHistorical)))
            .andExpect(status().isOk());

        // Validate the Historical in the database
        List<Historical> historicalList = historicalRepository.findAll();
        assertThat(historicalList).hasSize(databaseSizeBeforeUpdate);
        Historical testHistorical = historicalList.get(historicalList.size() - 1);
        assertThat(testHistorical.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testHistorical.getCorrelationId()).isEqualTo(UPDATED_CORRELATION_ID);
        assertThat(testHistorical.getActionId()).isEqualTo(UPDATED_ACTION_ID);
        assertThat(testHistorical.getActionDescription()).isEqualTo(UPDATED_ACTION_DESCRIPTION);
        assertThat(testHistorical.getActionDate()).isEqualTo(UPDATED_ACTION_DATE);

        // Validate the Historical in Elasticsearch
        verify(mockHistoricalSearchRepository, times(1)).save(testHistorical);
    }

    @Test
    public void updateNonExistingHistorical() throws Exception {
        int databaseSizeBeforeUpdate = historicalRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHistoricalMockMvc.perform(put("/api/historicals").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(historical)))
            .andExpect(status().isBadRequest());

        // Validate the Historical in the database
        List<Historical> historicalList = historicalRepository.findAll();
        assertThat(historicalList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Historical in Elasticsearch
        verify(mockHistoricalSearchRepository, times(0)).save(historical);
    }

    @Test
    public void deleteHistorical() throws Exception {
        // Initialize the database
        historicalRepository.save(historical);

        int databaseSizeBeforeDelete = historicalRepository.findAll().size();

        // Delete the historical
        restHistoricalMockMvc.perform(delete("/api/historicals/{id}", historical.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Historical> historicalList = historicalRepository.findAll();
        assertThat(historicalList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Historical in Elasticsearch
        verify(mockHistoricalSearchRepository, times(1)).deleteById(historical.getId());
    }

    @Test
    public void searchHistorical() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        historicalRepository.save(historical);
        when(mockHistoricalSearchRepository.search(queryStringQuery("id:" + historical.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(historical), PageRequest.of(0, 1), 1));

        // Search the historical
        restHistoricalMockMvc.perform(get("/api/_search/historicals?query=id:" + historical.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(historical.getId())))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID)))
            .andExpect(jsonPath("$.[*].correlationId").value(hasItem(DEFAULT_CORRELATION_ID)))
            .andExpect(jsonPath("$.[*].actionId").value(hasItem(DEFAULT_ACTION_ID)))
            .andExpect(jsonPath("$.[*].actionDescription").value(hasItem(DEFAULT_ACTION_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].actionDate").value(hasItem(DEFAULT_ACTION_DATE.toString())));
    }
}
