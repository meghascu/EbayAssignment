package com.ebay.eligibilitysvc;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.ws.rs.ApplicationPath;

@Configuration
@ComponentScan({"com.ebay"})
@ApplicationPath("/v1")
public class ApplicationConfig extends ResourceConfig {

    public ApplicationConfig(){
        register(EligibilitySvc.class);
    }
}
