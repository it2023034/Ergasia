package gr.hua.dit.Ergasia.dto;

import gr.hua.dit.Ergasia.model.ApplicationType;

public class ApplicationRequest {
    private ApplicationType type;
    private String description;

    // Getters & Setters
    public ApplicationType getType() { return type; }
    public void setType(ApplicationType type) { this.type = type; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}