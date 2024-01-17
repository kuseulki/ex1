package com.a.exboard5.controller;

import com.a.exboard5.dto.UserAccountDto;
import com.a.exboard5.dto.request.ArticleCommentRequest;
import com.a.exboard5.service.ArticleCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@RequestMapping("/comments")
@Controller
public class ArticleCommentController {

    private final ArticleCommentService articleCommentService;

    // 댓글 등록
    @PostMapping("/new")
    public String postNewArticleComment(ArticleCommentRequest articleCommentRequest) {
        // TODO : 인증 정보 넣어줘야 됨.

        articleCommentService.saveArticleComment(articleCommentRequest.toDto(UserAccountDto.of(
                "testUser", "pw", "test@test.com", null, null

        )));

        return "redirect:/articles" + articleCommentRequest.articleId();
    }

    // 삭제
    @PostMapping("/{commentId}/delete")
    public String deleteArticleCommnet(@PathVariable Long commentId, Long articleId) {
        articleCommentService.deleteArticleComment(commentId);

        return "redirect:/articles/" + articleId;
    }

}

/**
 * 삭제 시, 어느 게시물의 댓글 인지 알아야 됨.
 * 삭제 시에는 필요 없고, 다시 게시글 뷰로 돌아 갈 때 문제가 됨.
 *
 * return "redirect:/articles" + articleCommentRequest.articleId();
 *  -> articleCommentRequest 에서 article id 를 불러서 붙여주면 현재 댓글을 작성한 그 페이지로 redirect
* */