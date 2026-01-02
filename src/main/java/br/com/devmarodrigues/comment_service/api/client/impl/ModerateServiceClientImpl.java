package br.com.devmarodrigues.comment_service.api.client.impl;

import br.com.devmarodrigues.comment_service.api.client.ModerateServiceClient;
import br.com.devmarodrigues.comment_service.api.client.RestClientFactory;
import br.com.devmarodrigues.comment_service.api.model.ModerationOutput;
import br.com.devmarodrigues.comment_service.api.model.ModerationResult;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

//@Component
public class ModerateServiceClientImpl implements ModerateServiceClient {

    private final RestClient restClient;

    public ModerateServiceClientImpl(RestClientFactory factory) {
        this.restClient = factory.moderateServiceRestClient();
    }

    @Override
    public ModerationResult moderate(ModerationOutput moderation) {
        return restClient.post()
            .uri("/api/moderate")
            .body(moderation)
            .retrieve()
            .body(ModerationResult.class);
    }
}
