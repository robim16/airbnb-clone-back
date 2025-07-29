package fr.codecake.airbnbclone.infraestructure.config;

import fr.codecake.airbnbclone.user.domain.Authority;
import fr.codecake.airbnbclone.user.domain.User;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class SecurityUtils {
    public static final String ROLE_TENANT = "ROLE_TENANT";
    public static final String ROLE_LANDLORD = "ROLE_LANDLORD";
    public static final String CLAIMS_NAMESPACE = "https://codecake.fr/roles";


    public static User mapOauth2AttributesToUser(Map<String, Object> attributes) {
        User user = new User();
        String sub = String.valueOf(attributes.get("sub"));

        String username = null;

        if (attributes.get("preferred_username") != null) {
            username = ((String) attributes.get("preferred_username")).toLowerCase();
        }

        if (attributes.get("given_name") != null) {
            user.setFirstName(((String) attributes.get("given_name")));
        } else if ((attributes.get("nickname") != null)) {
            user.setFirstName(((String) attributes.get("nickname")));
        }

        if (attributes.get("family_name") != null) {
            user.setLastName(((String) attributes.get("family_name")));
        }

        if (attributes.get("email") != null) {
            user.setEmail(((String) attributes.get("email")));
        } else if (sub.contains("|") && (username != null && username.contains("@"))) {
            user.setEmail(username);
        } else {
            user.setEmail(sub);
        }

        if (attributes.get("picture") != null) {
            user.setImageUrl(((String) attributes.get("picture")));
        }

        if(attributes.get(CLAIMS_NAMESPACE) != null) {
            List<String> authoritiesRaw = (List<String>) attributes.get(CLAIMS_NAMESPACE);
            Set<Authority> authorities = authoritiesRaw.stream()
                    .map(authority -> {
                        Authority auth = new Authority();
                        auth.setName(authority);
                        return auth;
                    }).collect(Collectors.toSet());
            user.setAuthorities(authorities);
        }
        return user;
    }

}
