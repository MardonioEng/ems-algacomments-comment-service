package br.com.devmarodrigues.comment_service.api.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Data
public class CommentOutput {

    private UUID id;
    private String text;
    private String author;
    private LocalDateTime createdAt;
}
