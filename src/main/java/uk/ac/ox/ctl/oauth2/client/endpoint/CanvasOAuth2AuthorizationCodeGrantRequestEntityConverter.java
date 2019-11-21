/*
 * Copyright 2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package uk.ac.ox.ctl.oauth2.client.endpoint;

import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.security.oauth2.client.endpoint.OAuth2AuthorizationCodeGrantRequest;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationExchange;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

/**
 * This class is so that we can ask Canvas to replace the existing OAuth tokens on a user's account.
 * If we don't do this then a user will end up with multiple Approved Integrations on their settings
 * page for the same application.
 */
public class CanvasOAuth2AuthorizationCodeGrantRequestEntityConverter
    implements Converter<OAuth2AuthorizationCodeGrantRequest, RequestEntity<?>> {

  public static final String REPLACE_TOKENS = "replace_tokens";
  public static final String REPLACE_TOKENS_VALUE = "true";

  /**
   * Returns the {@link RequestEntity} used for the Access Token Request.
   *
   * @param authorizationCodeGrantRequest the authorization code grant request
   * @return the {@link RequestEntity} used for the Access Token Request
   */
  @Override
  public RequestEntity<?> convert(
      OAuth2AuthorizationCodeGrantRequest authorizationCodeGrantRequest) {
    ClientRegistration clientRegistration = authorizationCodeGrantRequest.getClientRegistration();

    HttpHeaders headers =
        OAuth2AuthorizationGrantRequestEntityUtils.getTokenRequestHeaders(clientRegistration);
    MultiValueMap<String, String> formParameters =
        this.buildFormParameters(authorizationCodeGrantRequest);
    URI uri =
        UriComponentsBuilder.fromUriString(clientRegistration.getProviderDetails().getTokenUri())
            .build()
            .toUri();

    return new RequestEntity<>(formParameters, headers, HttpMethod.POST, uri);
  }

  /**
   * Returns a {@link MultiValueMap} of the form parameters used for the Access Token Request body.
   *
   * @param authorizationCodeGrantRequest the authorization code grant request
   * @return a {@link MultiValueMap} of the form parameters used for the Access Token Request body
   */
  private MultiValueMap<String, String> buildFormParameters(
      OAuth2AuthorizationCodeGrantRequest authorizationCodeGrantRequest) {
    ClientRegistration clientRegistration = authorizationCodeGrantRequest.getClientRegistration();
    OAuth2AuthorizationExchange authorizationExchange =
        authorizationCodeGrantRequest.getAuthorizationExchange();

    MultiValueMap<String, String> formParameters = new LinkedMultiValueMap<>();
    formParameters.add(
        OAuth2ParameterNames.GRANT_TYPE, authorizationCodeGrantRequest.getGrantType().getValue());
    formParameters.add(
        OAuth2ParameterNames.CODE, authorizationExchange.getAuthorizationResponse().getCode());
    formParameters.add(
        OAuth2ParameterNames.REDIRECT_URI,
        authorizationExchange.getAuthorizationRequest().getRedirectUri());
    // This is the special Canvas bit.
    formParameters.add(REPLACE_TOKENS, REPLACE_TOKENS_VALUE);
    if (ClientAuthenticationMethod.POST.equals(
        clientRegistration.getClientAuthenticationMethod())) {
      formParameters.add(OAuth2ParameterNames.CLIENT_ID, clientRegistration.getClientId());
      formParameters.add(OAuth2ParameterNames.CLIENT_SECRET, clientRegistration.getClientSecret());
    }

    return formParameters;
  }
}
