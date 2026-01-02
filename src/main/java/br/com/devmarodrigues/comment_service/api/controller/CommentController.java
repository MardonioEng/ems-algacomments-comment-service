package br.com.devmarodrigues.comment_service.api.controller;

import br.com.devmarodrigues.comment_service.api.client.ModerateServiceClient;
import br.com.devmarodrigues.comment_service.api.config.web.customExceptions.ModerateServiceClientBadGatewayException;
import br.com.devmarodrigues.comment_service.api.model.CommentInput;
import br.com.devmarodrigues.comment_service.api.model.CommentOutput;
import br.com.devmarodrigues.comment_service.api.model.ModerationOutput;
import br.com.devmarodrigues.comment_service.api.model.ModerationResult;
import br.com.devmarodrigues.comment_service.domain.model.Comment;
import br.com.devmarodrigues.comment_service.domain.model.repositoty.CommentRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.NonNull;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@RestController
@RequestMapping("/api/comments")
public class CommentController {

    private static Logger logger = LogManager.getLogger(CommentController.class);

    private final CommentRepository commentRepository;
    private final ModerateServiceClient commentServiceClient;

    @GetMapping
    public Page<CommentOutput> getAll(@PageableDefault Pageable pageable) {
        Page<Comment> comments = commentRepository.findAll(pageable);
        return comments.map(this::convertToModel);
    }

    @GetMapping("/{commentId}")
    public CommentOutput getDetail(@PathVariable UUID commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return convertToModel(comment);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<CommentOutput> create(@RequestBody @Valid CommentInput commentInput) throws ModerateServiceClientBadGatewayException {

        UUID commentId = UUID.randomUUID();

        ModerationResult result = commentServiceClient.moderate(ModerationOutput
            .builder()
            .commentId(commentId)
            .text(commentInput.getText())
            .build());

        logger.info(String.format("Result recived: %s", result.getApproved()));

        if (result.getApproved()) {

            Comment comment = Comment.builder()
                    .id(commentId)
                    .text(commentInput.getText())
                    .author(commentInput.getAuthor())
                    .createdAt(LocalDateTime.now())
                    .build();

            comment = commentRepository.saveAndFlush(comment);

            return new ResponseEntity<>(convertToModel(comment), HttpStatus.CREATED);
        }

        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_CONTENT).build();
    }

    private CommentOutput convertToModel(@NonNull Comment comment) {
        return CommentOutput.builder()
            .id(comment.getId())
            .text(comment.getText())
            .author(comment.getAuthor())
            .createdAt(comment.getCreatedAt())
            .build();
    }
}
