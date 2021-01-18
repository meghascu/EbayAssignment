package com.ebay.validator;

import io.swagger.model.EligibilityRequest;
import io.swagger.model.RuleRequest;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

@Component
public class ResourceValidatorFactory {

    @Inject
    private EligibilityResourceValidator eligibilityResourceValidator;

    @Inject
    private RuleResourceValidator ruleResourceValidator;

    public IResourceValidator getValidator(Object request){
        if(request instanceof EligibilityRequest){
            return eligibilityResourceValidator;
        }else if(request instanceof RuleRequest){
            return ruleResourceValidator;
        }
        return null;
    }
}
