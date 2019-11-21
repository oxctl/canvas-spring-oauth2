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
package uk.ac.ox.ctl.oauth2.client.web.method.annotation;

import org.springframework.security.core.Authentication;

/**
 * This allows the custom mapping of a principal (authentication) to a client ID. This is useful in cases where
 * the application is multi-tenanted and you can find out the tenant from the principal.
 */
public interface PrincipalClientIdResolver {

    String findClientId(Authentication authentication);
}
