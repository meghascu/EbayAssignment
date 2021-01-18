package com.ebay.validator;

import io.swagger.model.Operation;
import io.swagger.model.RuleRequest;
import io.swagger.model.RuleType;
import org.springframework.stereotype.Component;

@Component
public class RuleResourceValidator implements IResourceValidator {

    /**
     *
     * Validates request for updating the rule
     */
    @Override
    public boolean validate(Object request) {
        RuleRequest req = (RuleRequest)request;
        if(req.getRuleType()!=RuleType.CATEGORY &&
                req.getRuleType()!=RuleType.SELLER && req.getRuleType()!=RuleType.PRICE)
            return false;
        if(req.getOperation()!=Operation.ADD &&
                req.getOperation()!=Operation.UPDATE && req.getOperation()!=Operation.DELETE)
            return false;
        if(req.getOperation()==Operation.UPDATE && req.getRuleType()!=RuleType.PRICE)
            return false;
        if(req.getRuleType()==RuleType.PRICE && req.getOperation()!=Operation.UPDATE)
            return false;
        if(req.getValue()==null)
            return false;
        return true;
    }
}
