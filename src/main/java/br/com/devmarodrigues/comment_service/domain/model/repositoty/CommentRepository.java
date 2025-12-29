package br.com.devmarodrigues.comment_service.domain.model.repositoty;

import br.com.devmarodrigues.comment_service.domain.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CommentRepository extends JpaRepository<Comment, UUID> {
}
