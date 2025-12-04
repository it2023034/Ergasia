package gr.hua.dit.Ergasia.model;

public enum ApplicationType {
    RESIDENCE_CERTIFICATE("Βεβαίωση Μόνιμης Κατοικίας"),
    SIDEWALK_OCCUPATION("Άδεια Κατάληψης Πεζοδρομίου"),
    LIGHTING_ISSUE("Αναφορά Προβλήματος Φωτισμού"),
    OTHER("Άλλο");

    private final String description;

    ApplicationType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
