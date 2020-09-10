package com.icommerce.developer.historical.web.rest;

import com.icommerce.developer.historical.domain.ProductChangelogHistorical;
import com.icommerce.developer.historical.domain.UserActivitiesHistorical;
import com.icommerce.developer.historical.repository.ProductChangelogHistoricalRepository;
import com.icommerce.developer.historical.repository.UserActivitiesHistoricalRepository;
import com.icommerce.developer.historical.security.AuthoritiesConstants;
import io.github.jhipster.web.util.PaginationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

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
    private final ProductChangelogHistoricalRepository productChangelogHistoricalRepository;

    public HistoricalResource(UserActivitiesHistoricalRepository userActivitiesHistoricalRepository,
                              ProductChangelogHistoricalRepository productChangelogHistoricalRepository) {
        this.userActivitiesHistoricalRepository = userActivitiesHistoricalRepository;
        this.productChangelogHistoricalRepository = productChangelogHistoricalRepository;
    }

    /**
     * {@code GET  /historicals} : get all the historicals.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of historicals in body.
     */
    @GetMapping("/historicals/user-activities")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<List<UserActivitiesHistorical>> getAllHistoricals(Pageable pageable) {
        log.debug("REST request to get a page of User Activities Historical");
        Page<UserActivitiesHistorical> page = userActivitiesHistoricalRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/historicals/product/{id}")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<List<ProductChangelogHistorical>> getProductsHistoricalUpdate(@PathVariable("id") String id) {
        return ResponseEntity.ok().body(productChangelogHistoricalRepository.findByProductId(id));
    }

}
