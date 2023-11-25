package com.a.exboard5.dto;

import com.a.exboard5.domain.Article;
import com.a.exboard5.domain.ArticleComment;

import java.io.Serializable;
import java.time.LocalDateTime;

public record ArticleCommentDto(

        Long id,
        Long articleId,
        UserAccountDto userAccountDto,
        String content,
        LocalDateTime createdAt,
        String createdBy,
        LocalDateTime modifiedAt,
        String modifiedBy
) {
    public static ArticleCommentDto of(Long id, Long articleId, UserAccountDto userAccountDto, String content, LocalDateTime createdAt, String createdBy, LocalDateTime modifiedAt, String modifiedBy) {
        return new ArticleCommentDto(id, articleId, userAccountDto, content, createdAt, createdBy, modifiedAt, modifiedBy);
    }

    // entity -> dto
    public static ArticleCommentDto from(ArticleComment entity) {
        return new ArticleCommentDto(
                entity.getId(),
                entity.getArticle().getId(),
                UserAccountDto.from(entity.getUserAccount()),
                entity.getContent(),
                entity.getCreatedAt(),
                entity.getCreatedBy(),
                entity.getModifiedAt(),
                entity.getModifiedBy()
        );
    }

    // dto -> entity
    public ArticleComment toEntity(Article entity) {
        return ArticleComment.of(
                entity,
                userAccountDto.toEntity(),
                content
        );
    }
}