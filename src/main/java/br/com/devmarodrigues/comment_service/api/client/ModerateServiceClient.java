package br.com.devmarodrigues.comment_service.api.client;

import br.com.devmarodrigues.comment_service.api.model.ModerationOutput;
import br.com.devmarodrigues.comment_service.api.model.ModerationResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;

@HttpExchange("/api/moderate")
public interface ModerateServiceClient {

    @PostExchange
    ModerationResult moderate(@RequestBody ModerationOutput moderation);
}
