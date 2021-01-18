package com.ebay.validator;

import io.swagger.model.EligibilityRequest;
import org.springframework.stereotype.Component;

@Component
public class EligibilityResourceValidator implements IResourceValidator{

    /**
     * Validates request for item eligibility.
     *
     */
    @Override
    public boolean validate(Object request) {
        EligibilityRequest eligibilityRequest = (EligibilityRequest) request;
        if(eligibilityRequest.getTitle()==null || eligibilityRequest.getTitle().isEmpty())
            return false;
        if(eligibilityRequest.getCategory()==null)
            return false;
        if(eligibilityRequest.getSeller()==null || eligibilityRequest.getSeller().isEmpty())
            return false;
        if(eligibilityRequest.getPrice()==null)
            return false;
        return true;
    }
}
