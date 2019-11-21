package uk.ac.ox.ctl.oauth2.client.web.method.annotation;

import org.springframework.security.core.Authentication;

/**
 * This allows the custom mapping of a principal (authentication) to a client ID. This is useful in cases where
 * the application is multi-tenanted and you can find out the tenant from the principal.
 */
public interface PrincipalClientIdResolver {

    String findClientId(Authentication authentication);
}
