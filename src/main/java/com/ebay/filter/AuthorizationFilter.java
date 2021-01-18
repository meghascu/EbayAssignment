package com.ebay.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.model.ApiAuthToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Set;


/**
 * Here we will check if the caller has privilege to call the service.
 * this callerId can be cross verified with some data store already configured.
 * currently we will just allow callerId "DUMMY_APP".
 * {
 *      "creationTs":473684,
 *      "ttl":39485345,
 *      "callerId":"DUMMY_APP",
 *      "token":"hgfujhdkfjsdfoeurfhioedv" <- alphanumeric minted token for the call this token will be live for ttl period this is avoid caching this on attacker side.
 * }
 *
 * Note: Currently for simplicity purpose we will just match callerId with "DUMMY_APP".
 */

@Component
@Order(1)
public class AuthorizationFilter implements Filter {

    private static final Logger LOG = LoggerFactory.getLogger(AuthorizationFilter.class);

    private static final Set<String> allowedCallers = Set.of("DUMMY_APP","ADMIN_APP");

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        LOG.info("In Authorization Filter...");
        HttpServletRequest request = (HttpServletRequest)servletRequest;
        try {
            byte[] byteArr = Base64.getDecoder().decode(request.getHeader("Authorization"));
            String strJson = new String(byteArr, StandardCharsets.UTF_8);
            ApiAuthToken authToken = new ObjectMapper().readValue(strJson, ApiAuthToken.class);
            if (authToken != null &&
                    authToken.getCallerId() != null &&
                    allowedCallers.contains(authToken.getCallerId())) {
                filterChain.doFilter(servletRequest, servletResponse);
            } else {
                HttpServletResponse resp = (HttpServletResponse) servletResponse;
                resp.setContentType("application/json");
                resp.setStatus(403);
            }
        }catch (Exception ex){
            HttpServletResponse resp = (HttpServletResponse) servletResponse;
            resp.setContentType("application/json");
            resp.setStatus(403);
        }
    }
}
