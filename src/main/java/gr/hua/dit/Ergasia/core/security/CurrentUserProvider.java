package gr.hua.dit.Ergasia.core.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CurrentUserProvider {

    public Optional<CurrentUser> getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            return Optional.empty();
        }

        Object principal = authentication.getPrincipal();

        if (principal instanceof ApplicationUserDetails userDetails) {
            return Optional.of(new CurrentUser(userDetails.getUserId(), userDetails.getUsername(), userDetails.getRole()));
        }

        return Optional.empty();
    }
}