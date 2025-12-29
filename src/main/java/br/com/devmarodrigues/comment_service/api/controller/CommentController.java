package br.com.devmarodrigues.comment_service.api.controller;

import br.com.devmarodrigues.comment_service.api.model.CommentInput;
import br.com.devmarodrigues.comment_service.api.model.CommentOutput;
import br.com.devmarodrigues.comment_service.domain.model.Comment;
import br.com.devmarodrigues.comment_service.domain.model.repositoty.CommentRepository;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@RestController
@RequestMapping("/api/comments")
public class CommentController {

    private CommentRepository commentRepository;

    @GetMapping("/{commentId}")
    public CommentOutput getDetail(@PathVariable UUID commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return convertToModel(comment);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CommentOutput create(@RequestBody CommentInput commentInput) {
        Comment comment = Comment.builder()
            .id(UUID.randomUUID())
            .text(commentInput.getText())
            .author(commentInput.getAuthor())
            .createdAt(LocalDateTime.now())
            .build();

        comment = commentRepository.saveAndFlush(comment);

        return convertToModel(comment);
    }

    private CommentOutput convertToModel(@NonNull Comment comment) {
        return CommentOutput.builder()
            .text(comment.getText())
            .author(comment.getAuthor())
            .build();
    }

}
