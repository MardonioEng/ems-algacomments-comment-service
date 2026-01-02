package br.com.devmarodrigues.comment_service.api.model;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class ModerationOutput {

    private String text;
    private UUID commentId;
}
