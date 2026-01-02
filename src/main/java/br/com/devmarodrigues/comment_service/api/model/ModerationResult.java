package br.com.devmarodrigues.comment_service.api.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ModerationResult {

    private Boolean approved;
    private String reason;
}
