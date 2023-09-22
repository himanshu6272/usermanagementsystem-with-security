package com.springboot.UserManagementSystem.configs;

import org.apache.log4j.Logger;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;

public class OauthCustomUserDetails implements OAuth2User {

    private static final Logger log = Logger.getLogger(OauthCustomUserDetails.class);

    private OAuth2User oAuth2User;

    public OauthCustomUserDetails(OAuth2User oAuth2User) {
        this.oAuth2User = oAuth2User;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return oAuth2User.getAttributes();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return oAuth2User.getAuthorities();
    }

    @Override
    public String getName() {
        log.info(oAuth2User.getAttribute("name"));
        return oAuth2User.getAttribute("name");
    }

    String getEmail(){
        log.info(oAuth2User.<String>getAttribute("email"));
        return oAuth2User.<String>getAttribute("email");
    }
}
