package gr.hua.dit.Ergasia.core.security;

import gr.hua.dit.Ergasia.core.model.Role;

public record CurrentUser(String id, String username, Role role) {}