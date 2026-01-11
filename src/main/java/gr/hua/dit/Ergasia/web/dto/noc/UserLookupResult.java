package gr.hua.dit.Ergasia.web.dto.noc;

public record UserLookupResult(
        String raw,
        boolean exists,
        String huaId,
        String type // Αντιστοιχεί στο UserType enum του noc
) {}
