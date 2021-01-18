package com.ebay.eligibilitysvc;

import com.ebay.processor.ItemProcessor;
import com.ebay.processor.RuleProcessor;
import com.ebay.validator.IResourceValidator;
import com.ebay.validator.ResourceValidatorFactory;
import io.swagger.model.ApiError;
import io.swagger.model.EligibilityRequest;
import io.swagger.model.EligibilityResponse;

import io.swagger.model.RuleRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@Component
@Path("/")
@Scope("request")
public class EligibilitySvc {

     @Inject
     private ItemProcessor itemProcessor;

     @Inject
     private RuleProcessor ruleProcessor;

     @Inject
     private ResourceValidatorFactory resourceValidatorFactory;

     private static final Logger LOG = LoggerFactory.getLogger(EligibilitySvc.class);

     @POST
     @Path("/eligible/item")
     @Consumes({ "application/json" })
     @Produces({ "application/json" })
     @Operation(summary = "Get item's eligibility", description = "Get item's eligibility", tags={  })
     @ApiResponses(value = {
             @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = EligibilityResponse.class))),
             @ApiResponse(responseCode = "400", description = "Invalid Input", content = @Content(schema = @Schema(implementation = ApiError.class)))
     })
     public Response getEligibility(@Valid EligibilityRequest request) {
          try{
               IResourceValidator validator = resourceValidatorFactory.getValidator(request);
               if(validator.validate(request)){
                    EligibilityResponse response = itemProcessor.getEligibility(request);
                    return Response.ok(response, MediaType.APPLICATION_JSON).build();
               }
               else{
                    LOG.error("Bad Request");
                    return Response.status(HttpStatus.SC_BAD_REQUEST).build();
               }
          }
          catch (Exception ex){
               LOG.error("Error in calling the service "+ex.getMessage());
               return Response.serverError().build();
          }
     }



     @POST
     @Path("/rules")
     @Consumes({ "application/json" })
     @Produces({ "application/json" })
     @Operation(summary = "Edit rule", description = "Single end point for adding , removing and updating entries in rule.", tags={  })
     @ApiResponses(value = {
             @ApiResponse(responseCode = "200", description = "OK"),
             @ApiResponse(responseCode = "400", description = "Invalid Input")
     })
     public Response updateRule(@Valid RuleRequest request) {
          try{
               IResourceValidator validator = resourceValidatorFactory.getValidator(request);
               if(validator.validate(request)){
                    if(ruleProcessor.updateRule(request))
                         return Response.ok().build();
                    return Response.serverError().build();
               } else {
                    LOG.error("Bad Request");
                    return Response.status(HttpStatus.SC_BAD_REQUEST).build();
               }
          }
          catch (Exception ex){
               LOG.error("Error in calling the service "+ex.getMessage());
               return  Response.serverError().build();
          }
     }
}
