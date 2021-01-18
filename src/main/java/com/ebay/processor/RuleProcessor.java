package com.ebay.processor;

import com.ebay.dao.DataStore;
import io.swagger.model.Operation;

import io.swagger.model.RuleRequest;
import io.swagger.model.RuleType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;



@Component
public class RuleProcessor {

    private static final Logger LOG = LoggerFactory.getLogger(RuleProcessor.class);

    DataStore dataStore = DataStore.getInstance();


    public boolean updateRule(RuleRequest ruleRequest){
        RuleType ruleType = ruleRequest.getRuleType();
        switch (ruleType){
            case SELLER:
                return updateEnrolledSeller(ruleRequest);
            case CATEGORY:
               return updateItemCategory(ruleRequest);
            case PRICE:
               return  updateMinPrice(ruleRequest);
            default:
                break;
        }
        return false;
    }

    private boolean updateMinPrice(RuleRequest ruleRequest) {
        if(ruleRequest.getOperation()==Operation.UPDATE){
            try {
                return dataStore.updateMinPrice(Double.parseDouble(ruleRequest.getValue().toString()));
            }
            catch (NumberFormatException ex){
                LOG.info("Item price should be a float value "+ex.getMessage());
            }
            catch (Exception ex){
                LOG.info("Exception while updating price "+ex.getMessage());
            }
        }
        return  false;
    }

    private boolean updateItemCategory(RuleRequest ruleRequest) {
        try{
            int catId = Integer.valueOf(ruleRequest.getValue().toString());
            if(ruleRequest.getOperation()==Operation.ADD){
                return dataStore.addCategoryId(catId);
            }
            else if(ruleRequest.getOperation()==Operation.DELETE){
                return dataStore.removeCategory(catId);
            }
        }
        catch (NumberFormatException ex){
            LOG.info("item category should be numeric "+ex.getMessage());
        }
        catch (Exception ex){
            LOG.info("Exception while updating category "+ex.getMessage());
        }
        return false;
    }

    private boolean updateEnrolledSeller(RuleRequest ruleRequest) {
        if(ruleRequest.getOperation()==Operation.ADD){
            return dataStore.addSeller(ruleRequest.getValue().toString());
        }
        else if (ruleRequest.getOperation()==Operation.DELETE){
           return dataStore.removeSeller(ruleRequest.getValue().toString());
        }
        return false;
    }
}
