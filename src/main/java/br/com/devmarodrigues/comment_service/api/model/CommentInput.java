package br.com.devmarodrigues.comment_service.api.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class CommentInput {

    private String text;
    private String author;

}
