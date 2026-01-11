package gr.hua.dit.Ergasia.web.dto;

import gr.hua.dit.Ergasia.core.model.Role;

public record UserView(
        String id,
        String username,
        String email,
        Role role
) {}
