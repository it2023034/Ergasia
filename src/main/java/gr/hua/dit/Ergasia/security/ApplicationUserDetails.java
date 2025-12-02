package gr.hua.dit.Ergasia.security;

import gr.hua.dit.Ergasia.model.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public class ApplicationUserDetails implements UserDetails {

    private final String userId;
    private final String username; // Θα κρατάμε το username ή το email εδώ
    private final String passwordHash;
    private final Role role;

    public ApplicationUserDetails(String userId, String username, String passwordHash, Role role) {
        this.userId = userId;
        this.username = username;
        this.passwordHash = passwordHash;
        this.role = role;
    }

    public String getUserId() {
        return userId;
    }

    public Role getRole() {
        return role;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Μετατρέπει το Enum Role (π.χ. ADMIN) σε ROLE_ADMIN
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + this.role.name()));
    }

    @Override
    public String getPassword() {
        return passwordHash;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() { return true; }

    @Override
    public boolean isAccountNonLocked() { return true; }

    @Override
    public boolean isCredentialsNonExpired() { return true; }

    @Override
    public boolean isEnabled() { return true; }
}