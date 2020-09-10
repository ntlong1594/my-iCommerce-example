package com.icommerce.developer.product.service;

import com.icommerce.developer.product.domain.ActionId;
import com.icommerce.developer.product.domain.Product;
import com.icommerce.developer.product.messaging.ProductChangelogHistoricalEventPublisher;
import com.icommerce.developer.product.messaging.UserActivitiesHistoricalEventPublisher;
import com.icommerce.developer.product.repository.ProductRepository;
import com.icommerce.developer.product.security.AuthoritiesConstants;
import com.icommerce.developer.product.service.dto.ProductDTO;
import com.icommerce.developer.product.service.dto.SearchCriteria;
import com.icommerce.developer.product.service.supporter.ProductSearchCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final List<ProductSearchCriteria> criteriaList;
    private final MongoTemplate mongoTemplate;
    private final UserActivitiesHistoricalEventPublisher userActivitiesHistoricalEventPublisher;
    private final ProductChangelogHistoricalEventPublisher productChangelogHistoricalEventPublisher;

    @Autowired
    public ProductService(ProductRepository productRepository,
                          List<ProductSearchCriteria> criteriaList,
                          MongoTemplate mongoTemplate,
                          UserActivitiesHistoricalEventPublisher userActivitiesHistoricalEventPublisher,
                          ProductChangelogHistoricalEventPublisher productChangelogHistoricalEventPublisher) {
        this.productRepository = productRepository;
        this.criteriaList = criteriaList;
        this.mongoTemplate = mongoTemplate;
        this.userActivitiesHistoricalEventPublisher = userActivitiesHistoricalEventPublisher;
        this.productChangelogHistoricalEventPublisher = productChangelogHistoricalEventPublisher;
    }

    @Transactional
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public Product save(ProductDTO productDTO) {
        Product product = new Product();
        product.setBrand(productDTO.getBrand());
        product.setTitle(productDTO.getTitle());
        product.setPrice(productDTO.getPrice());
        product = productRepository.save(product);
        productChangelogHistoricalEventPublisher.publish(product);
        userActivitiesHistoricalEventPublisher.publish(ActionId.CREATE_NEW_PRODUCT.name(), productDTO.toString());
        return product;
    }

    @Transactional
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public Product update(ProductDTO productDTO) {
        try {
            Product existing = productRepository.findById(productDTO.getId())
                .orElseThrow(() -> new RuntimeException("Could not found product with id " + productDTO.getId()));
            productChangelogHistoricalEventPublisher.publish(existing); // keep the old version of product
            existing.setPrice(productDTO.getPrice());
            existing.setTitle(productDTO.getTitle());
            existing.setBrand(productDTO.getBrand());
            existing = productRepository.save(existing);
            return existing;
        } finally {
            userActivitiesHistoricalEventPublisher.publish(ActionId.UPDATE_PRODUCT.name(), productDTO.toString());
        }
    }

    @Transactional
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public void deleteBy(String id) {
        try {
            productRepository.deleteById(id);
        } finally {
            userActivitiesHistoricalEventPublisher.publish(ActionId.DELETE_PRODUCT.name(), "id=" + id);
        }
    }

    @Transactional(readOnly = true)
    public Optional<Product> findDetailBy(String id) {
        try {
            return productRepository.findById(id);
        } finally {
            userActivitiesHistoricalEventPublisher.publish(ActionId.GET_PRODUCT.name(), "id=" + id);
        }
    }

    @Transactional(readOnly = true)
    public Page<Product> search(SearchCriteria searchCriteria, Pageable pageable) {
        try {
            Query query = new Query();
            List<Criteria> criteriaCondition = new ArrayList<>();
            criteriaList.forEach(criteria -> {
                if (criteria.shouldUsethisCriteria(searchCriteria)) {
                    criteriaCondition.add(criteria.buildCriteriaLikeCondition(searchCriteria));
                }
            });
            if (!CollectionUtils.isEmpty(criteriaCondition)) {
                query.addCriteria(new Criteria().andOperator(criteriaCondition.toArray(new Criteria[criteriaCondition.size()])));
            }
            query.with(pageable);
            return new PageImpl<>(mongoTemplate.find(query, Product.class));
        } finally {
            userActivitiesHistoricalEventPublisher.publish(ActionId.SEARCH_PRODUCTS.name(), searchCriteria == null ?
                "" : searchCriteria.toString());
        }
    }
}
