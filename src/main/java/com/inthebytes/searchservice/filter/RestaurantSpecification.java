package com.inthebytes.searchservice.filter;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.inthebytes.searchservice.entity.Restaurant;

public class RestaurantSpecification implements Specification<Restaurant>{

	private static final long serialVersionUID = -7254492317884489581L;
	
	private FilterCriteria criteria;

	@Override
	public Predicate toPredicate(Root<Restaurant> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

		if (criteria.getOperation().equalsIgnoreCase(">")) {
			return criteriaBuilder.greaterThanOrEqualTo(
					root.<String> get(criteria.getKey()),
					criteria.getValue().toString()
					);
		}
		else if (criteria.getOperation().equalsIgnoreCase("<")) {
			return criteriaBuilder.lessThanOrEqualTo(
					root.<String> get(criteria.getKey()),
					criteria.getValue().toString()
					);
		}
		else if (criteria.getOperation().equalsIgnoreCase(":")) {
			return criteriaBuilder.equal(
					root.get(criteria.getKey()),
					criteria.getValue()
					);
		}
		return null;
	}

}
