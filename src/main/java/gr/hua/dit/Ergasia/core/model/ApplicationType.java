package gr.hua.dit.Ergasia.core.model;

public enum ApplicationType {
    RESIDENCE_CERTIFICATE("Permanent Residence Certificate"),
    SIDEWALK_OCCUPATION("Sidewalk Occupation Permit"),
    LIGHTING_ISSUE("Street Lighting Issue Report"),
    OTHER("Other");

    private final String description;

    ApplicationType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
