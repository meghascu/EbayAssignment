package com.ebay.processor;


import com.ebay.dao.DataStore;
import io.swagger.model.EligibilityRequest;
import io.swagger.model.EligibilityResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


@Component
public class ItemProcessor {
    private static final Logger LOG = LoggerFactory.getLogger(ItemProcessor.class);

    public EligibilityResponse getEligibility(EligibilityRequest request){
        EligibilityResponse response = new EligibilityResponse();
        DataStore dataStore = DataStore.getInstance();

        try {
            int catId = request.getCategory();
            double price = request.getPrice();


            if (dataStore.isCategoryAllowed(catId) &&
                    dataStore.isSellerAllowed(request.getSeller()) &&
                    dataStore.isPriceValid(price)) {
                response.setEligible(true);
                LOG.info("Item " + request.getTitle() + "is eligible for new shipping program");
            } else {
                LOG.info("Item " + request.getTitle() + "is not eligible for new shipping program");
                response.setEligible(false);
            }
        }
        catch (NumberFormatException ex){
            LOG.info("Input is not correct "+ex.getMessage());
        }
        catch (Exception ex){
            LOG.info("Exception while calling " +ex.getMessage());
        }
        response.setTitle(request.getTitle());
        return response;
    }
}
