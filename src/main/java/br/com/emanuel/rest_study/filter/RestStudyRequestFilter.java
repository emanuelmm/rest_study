package br.com.emanuel.rest_study.filter;

import br.com.emanuel.rest_study.security.Autenticador;
import br.com.emanuel.rest_study.security.RestStudyHTTPHeaderNames;

import java.io.IOException;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

/**
 * 
 * @author Max Lam - http://www.developerscrappad.com/1814/java/java-ee/rest-jax-rs/java-ee-7-jax-rs-2-0-simple-rest-api-authentication-authorization-with-custom-http-header/
 * @author Emanuel Cordeiro
 */

@Provider
@PreMatching
public class RestStudyRequestFilter implements ContainerRequestFilter {

    private final static Logger log = Logger.getLogger( RestStudyRequestFilter.class.getName() );
    
    @Inject
    private Autenticador autenticador;

    @Override
    public void filter( ContainerRequestContext requestCtx ) throws IOException {

        String path = requestCtx.getUriInfo().getPath();
        log.info( "Filtering request path: " + path );

        // IMPORTANT!!! First, Acknowledge any pre-flight test from browsers for this case before validating the headers (CORS stuff)
        if ( requestCtx.getRequest().getMethod().equals( "OPTIONS" ) ) {
            requestCtx.abortWith( Response.status( Response.Status.OK ).build() );
            return;
        }

        // Then check is the service key exists and is valid.        
        String serviceKey = requestCtx.getHeaderString( RestStudyHTTPHeaderNames.SERVICE_KEY );
        if ( !autenticador.isServiceKeyValid( serviceKey ) ) {
            // Kick anyone without a valid service key
            requestCtx.abortWith( Response.status( Response.Status.UNAUTHORIZED ).build() );
            return;
        }

        // For any pther methods besides login, the authToken must be verified
        System.out.println("path" + path);
        if ( !path.contains("login" ) ) {
            String authToken = requestCtx.getHeaderString( RestStudyHTTPHeaderNames.AUTH_TOKEN );
            // if it isn't valid, just kick them out.
            if ( !autenticador.isAuthTokenValid( serviceKey, authToken ) ) {
                requestCtx.abortWith( Response.status( Response.Status.UNAUTHORIZED ).build() );
            }
        }
    }
}
