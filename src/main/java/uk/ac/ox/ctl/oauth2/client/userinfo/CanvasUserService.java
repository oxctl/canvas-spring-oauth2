package uk.ac.ox.ctl.oauth2.client.userinfo;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2UserAuthority;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

/**
 * This is useful when performing OAuth2 logins against Canvas with the scope of /auth/userinfo
 * In this situation when the user is returned from Canvas details about them are included in the response.
 */
public class CanvasUserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    @SuppressWarnings("unchecked")
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        Object object = userRequest.getAdditionalParameters().get("user");
        if (object instanceof Map) {
            Map<String, Object> user = (Map<String, Object>)object;
            OAuth2UserAuthority oAuth2UserAuthority = new OAuth2UserAuthority(user);
            Set<GrantedAuthority> authorities = Collections.singleton(oAuth2UserAuthority);
            return new DefaultOAuth2User(authorities, user, "id");
        } else {
            throw new IllegalArgumentException("Cannot find map of user attributes.");
        }
    }
}
