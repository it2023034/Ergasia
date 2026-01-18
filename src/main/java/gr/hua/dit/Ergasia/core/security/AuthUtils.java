package gr.hua.dit.Ergasia.core.security;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;

public final class AuthUtils {

    private AuthUtils() {
        throw new UnsupportedOperationException();
    }

    public static boolean isAuthenticated(final Authentication auth) {
        if (auth == null) return false;
        if (auth instanceof AnonymousAuthenticationToken) return false;
        return auth.isAuthenticated();
    }
}
