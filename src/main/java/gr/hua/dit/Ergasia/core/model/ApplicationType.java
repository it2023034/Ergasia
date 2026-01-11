package gr.hua.dit.Ergasia.core.model;

public enum ApplicationType {
    RESIDENCE_CERTIFICATE("Veveosi Monimis Katoikias"),
    SIDEWALK_OCCUPATION("Adeia Katalipsis Pezodromiou"),
    LIGHTING_ISSUE("Anafora Provlimatos Fotismou"),
    OTHER("Allo");

    private final String description;

    ApplicationType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
