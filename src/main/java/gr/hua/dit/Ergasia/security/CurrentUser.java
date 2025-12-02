package gr.hua.dit.Ergasia.security;

import gr.hua.dit.Ergasia.model.Role;

public record CurrentUser(String id, String username, Role role) {}