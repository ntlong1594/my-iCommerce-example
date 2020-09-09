package com.icommerce.developer.product.service.supporter;

import com.icommerce.developer.product.service.dto.SearchCriteria;
import org.springframework.data.mongodb.core.query.Criteria;

public abstract class ProductSearchCriteria {
    public abstract boolean shouldUsethisCriteria(SearchCriteria searchCriteria);
    public abstract Criteria buildCriteriaLikeCondition(SearchCriteria criteria);

}
