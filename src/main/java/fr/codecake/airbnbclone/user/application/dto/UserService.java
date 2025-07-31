package fr.codecake.airbnbclone.user.application.dto;

import fr.codecake.airbnbclone.infraestructure.config.SecurityUtils;
import fr.codecake.airbnbclone.user.domain.User;
import fr.codecake.airbnbclone.user.mapper.UserMapper;
import fr.codecake.airbnbclone.user.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private static final String UPDATED_AT_KEY = "updated_at";
    private final UserRepository userRepository;
    private final UserMapper userMapper;


    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public ReadUserDTO getAuthenticatedUserFromSecurityContext() {
        OAuth2User principal = (OAuth2User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = SecurityUtils.mapOauth2AttributesToUser(principal.getAttributes());
        return getByEmail(user.getEmail()).orElseThrow();
    }

    public Optional<ReadUserDTO> getByEmail(String email) {

    }
}
