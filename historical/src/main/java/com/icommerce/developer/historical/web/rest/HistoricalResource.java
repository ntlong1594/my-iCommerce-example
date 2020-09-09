package com.icommerce.developer.historical.web.rest;

import com.icommerce.developer.historical.domain.UserActivitiesHistorical;
import com.icommerce.developer.historical.repository.UserActivitiesHistoricalRepository;

import io.github.jhipster.web.util.PaginationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing {@link UserActivitiesHistorical}.
 */
@RestController
@RequestMapping("/api")
public class HistoricalResource {

    private final Logger log = LoggerFactory.getLogger(HistoricalResource.class);

    private static final String ENTITY_NAME = "historicalHistorical";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UserActivitiesHistoricalRepository userActivitiesHistoricalRepository;

    public HistoricalResource(UserActivitiesHistoricalRepository userActivitiesHistoricalRepository) {
        this.userActivitiesHistoricalRepository = userActivitiesHistoricalRepository;
    }

    /**
     * {@code GET  /historicals} : get all the historicals.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of historicals in body.
     */
    @GetMapping("/historicals")
    public ResponseEntity<List<UserActivitiesHistorical>> getAllHistoricals(Pageable pageable) {
        log.debug("REST request to get a page of Historicals");
        Page<UserActivitiesHistorical> page = userActivitiesHistoricalRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
