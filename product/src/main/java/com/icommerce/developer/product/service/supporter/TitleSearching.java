package com.icommerce.developer.product.service.supporter;

import com.icommerce.developer.product.service.dto.SearchCriteria;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

@Service
public class TitleSearching extends ProductSearchCriteria {

    @Override
    public boolean shouldUsethisCriteria(SearchCriteria searchCriteria) {
        return StringUtils.isNotBlank(searchCriteria.getTitle());
    }

    @Override
    public Criteria buildCriteriaLikeCondition(SearchCriteria criteria) {
        return Criteria.where("title").regex(criteria.getTitle());
    }
}
