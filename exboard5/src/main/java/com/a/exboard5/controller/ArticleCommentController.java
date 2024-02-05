package com.a.exboard5.controller;

import com.a.exboard5.dto.request.ArticleCommentRequest;
import com.a.exboard5.dto.security.BoardPrincipal;
import com.a.exboard5.service.ArticleCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@RequestMapping("/comments")
@Controller
public class ArticleCommentController {

    private final ArticleCommentService articleCommentService;

    @PostMapping("/new")
    public String postNewArticleComment(
            @AuthenticationPrincipal BoardPrincipal boardPrincipal,
            ArticleCommentRequest articleCommentRequest
    ) {
        articleCommentService.saveArticleComment(articleCommentRequest.toDto(boardPrincipal.toDto()));

        return "redirect:/articles/" + articleCommentRequest.articleId();
    }

    @PostMapping("/{commentId}/delete")
    public String deleteArticleComment(
            @PathVariable Long commentId,
            @AuthenticationPrincipal BoardPrincipal boardPrincipal,
            Long articleId
    ) {
        articleCommentService.deleteArticleComment(commentId, boardPrincipal.getUsername());

        return "redirect:/articles/" + articleId;
    }

}

/**
 * 삭제 시, 어느 게시물의 댓글 인지 알아야 됨.
 * 삭제 시에는 필요 없고, 다시 게시글 뷰로 돌아 갈 때 문제가 됨.
 * <p>
 * return "redirect:/articles" + articleCommentRequest.articleId();
 * -> articleCommentRequest 에서 article id 를 불러서 붙여주면 현재 댓글을 작성한 그 페이지로 redirect
 */