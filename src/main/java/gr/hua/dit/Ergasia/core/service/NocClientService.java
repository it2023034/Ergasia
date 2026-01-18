package gr.hua.dit.Ergasia.core.service;

import gr.hua.dit.Ergasia.web.dto.noc.SendSmsRequest;
import gr.hua.dit.Ergasia.web.dto.noc.UserLookupResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.http.MediaType;

@Service
public class NocClientService {

    private final RestClient restClient;

    public NocClientService(@Value("${noc.api.url}") String baseUrl) {
        this.restClient = RestClient.builder().baseUrl(baseUrl).build();
    }

    public void sendSms(String phoneNumber, String message) {
        restClient.post()
                .uri("/sms") // Αλλαγή από "/api/v1/sms" σε "/sms"
                .contentType(MediaType.APPLICATION_JSON)
                .body(new SendSmsRequest(phoneNumber, message))
                .retrieve()
                .toBodilessEntity();
    }
}