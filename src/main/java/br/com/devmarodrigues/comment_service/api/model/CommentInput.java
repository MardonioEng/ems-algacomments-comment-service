package br.com.devmarodrigues.comment_service.api.model;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class CommentInput {

    @NotBlank
    private String text;

    @NotBlank
    private String author;

}
