package com.icommerce.developer.historical.web.rest;

import com.icommerce.developer.historical.domain.Historical;
import com.icommerce.developer.historical.repository.HistoricalRepository;
import com.icommerce.developer.historical.repository.search.HistoricalSearchRepository;
import com.icommerce.developer.historical.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing {@link com.icommerce.developer.historical.domain.Historical}.
 */
@RestController
@RequestMapping("/api")
public class HistoricalResource {

    private final Logger log = LoggerFactory.getLogger(HistoricalResource.class);

    private static final String ENTITY_NAME = "historicalHistorical";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final HistoricalRepository historicalRepository;

    private final HistoricalSearchRepository historicalSearchRepository;

    public HistoricalResource(HistoricalRepository historicalRepository, HistoricalSearchRepository historicalSearchRepository) {
        this.historicalRepository = historicalRepository;
        this.historicalSearchRepository = historicalSearchRepository;
    }

    /**
     * {@code POST  /historicals} : Create a new historical.
     *
     * @param historical the historical to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new historical, or with status {@code 400 (Bad Request)} if the historical has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/historicals")
    public ResponseEntity<Historical> createHistorical(@Valid @RequestBody Historical historical) throws URISyntaxException {
        log.debug("REST request to save Historical : {}", historical);
        if (historical.getId() != null) {
            throw new BadRequestAlertException("A new historical cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Historical result = historicalRepository.save(historical);
        historicalSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/historicals/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /historicals} : Updates an existing historical.
     *
     * @param historical the historical to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated historical,
     * or with status {@code 400 (Bad Request)} if the historical is not valid,
     * or with status {@code 500 (Internal Server Error)} if the historical couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/historicals")
    public ResponseEntity<Historical> updateHistorical(@Valid @RequestBody Historical historical) throws URISyntaxException {
        log.debug("REST request to update Historical : {}", historical);
        if (historical.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Historical result = historicalRepository.save(historical);
        historicalSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, historical.getId()))
            .body(result);
    }

    /**
     * {@code GET  /historicals} : get all the historicals.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of historicals in body.
     */
    @GetMapping("/historicals")
    public ResponseEntity<List<Historical>> getAllHistoricals(Pageable pageable) {
        log.debug("REST request to get a page of Historicals");
        Page<Historical> page = historicalRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /historicals/:id} : get the "id" historical.
     *
     * @param id the id of the historical to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the historical, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/historicals/{id}")
    public ResponseEntity<Historical> getHistorical(@PathVariable String id) {
        log.debug("REST request to get Historical : {}", id);
        Optional<Historical> historical = historicalRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(historical);
    }

    /**
     * {@code DELETE  /historicals/:id} : delete the "id" historical.
     *
     * @param id the id of the historical to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/historicals/{id}")
    public ResponseEntity<Void> deleteHistorical(@PathVariable String id) {
        log.debug("REST request to delete Historical : {}", id);
        historicalRepository.deleteById(id);
        historicalSearchRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }

    /**
     * {@code SEARCH  /_search/historicals?query=:query} : search for the historical corresponding
     * to the query.
     *
     * @param query the query of the historical search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/historicals")
    public ResponseEntity<List<Historical>> searchHistoricals(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Historicals for query {}", query);
        Page<Historical> page = historicalSearchRepository.search(queryStringQuery(query), pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
        }
}
