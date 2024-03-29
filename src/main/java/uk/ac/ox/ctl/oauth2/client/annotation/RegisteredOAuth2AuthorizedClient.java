/*
 * Copyright 2002-2018 the original author or authors.
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
package uk.ac.ox.ctl.oauth2.client.annotation;

import org.springframework.core.annotation.AliasFor;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.web.method.annotation.OAuth2AuthorizedClientArgumentResolver;

import java.lang.annotation.*;

/**
 * This annotation may be used to resolve a method parameter to an argument value of type {@link
 * OAuth2AuthorizedClient}.
 *
 * <p>For example:
 *
 * <pre>
 * &#64;Controller
 * public class MyController {
 *     &#64;GetMapping("/authorized-client")
 *     public String authorizedClient(@RegisteredOAuth2AuthorizedClient("login-client") OAuth2AuthorizedClient authorizedClient) {
 *         // do something with authorizedClient
 *     }
 * }
 * </pre>
 *
 * @author Joe Grandja
 * @see OAuth2AuthorizedClientArgumentResolver
 * @since 5.1
 */
@Target({ElementType.PARAMETER, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RegisteredOAuth2AuthorizedClient {

  /**
   * Sets the client registration identifier.
   *
   * @return the client registration identifier
   */
  @AliasFor("value")
  String registrationId() default "";

  /**
   * The default attribute for this annotation. This is an alias for {@link #registrationId()}. For
   * example, {@code @RegisteredOAuth2AuthorizedClient("login-client")} is equivalent to
   * {@code @RegisteredOAuth2AuthorizedClient(registrationId="login-client")}.
   *
   * @return the client registration identifier
   */
  @AliasFor("registrationId")
  String value() default "";

  /**
   * Set if this OAuth2AuthorizedClient is required.
   * @return if the parameter is required.
   */
  boolean required() default true;

  /**
   * Set if the existing authorized client should be deleted so we get another one.
   * @return if any existing authorized client should be deleted.
   */
  boolean renew() default false;
}
