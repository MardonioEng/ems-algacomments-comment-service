package br.com.devmarodrigues.comment_service.api.model;

import lombok.AllArgsConstructor;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@AllArgsConstructor
public class CommentOutput {

    private UUID id;
    private String text;
    private String author;
    private LocalDateTime createdAt;
}
