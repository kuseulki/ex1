package com.a.exboard5.service;

import com.a.exboard5.domain.Article;
import com.a.exboard5.domain.type.SearchType;
import com.a.exboard5.dto.ArticleCommentDto;
import com.a.exboard5.repository.ArticleCommentRepository;
import com.a.exboard5.repository.ArticleRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;

@DisplayName("비즈니스 로직 - 댓글")
@ExtendWith(MockitoExtension.class)
class ArticleCommentServiceTest {

    @InjectMocks private ArticleCommentService sut;
    @Mock private ArticleCommentRepository articleCommentRepository;
    @Mock private ArticleRepository articleRepository;

    @DisplayName("댓글 ID로 조회 시, 댓글 리스트 반환")
    @Test
    void givenArticleId_whenSearchingArticleComments_thenReturnsArticleComments(){

        //Given
        Long articleId = 1L;
//        given(articleRepository.findById(articleId)).willReturn(Optional.of(
//                Article.of("kk","title", "content", "#hashtag"))
//        );

        //When
        List<ArticleCommentDto> articleComments = sut.searchArticleComment(articleId);

        //Then
        assertThat(articleComments).isNotNull();
        then(articleRepository).should().findById(articleId);
    }

    @DisplayName("댓글 입력 시, 댓글 저장")
    @Test
    void givenArticleComments_whenSearchingArticleComments_thenReturnsArticleComments(){

        //Given
        Long articleId = 1L;
//        given(articleRepository.findById(articleId)).willReturn(Optional.of(
//                Article.of("kk","title", "content", "#hashtag"))
//        );

        //When
        List<ArticleCommentDto> articleComments = sut.searchArticleComment(articleId);

        //Then
        assertThat(articleComments).isNotNull();
        then(articleRepository).should().findById(articleId);
    }

    @DisplayName("댓글 입력 시, 댓글 삭제") // 작성해야됨.
    @Test
    void givenArticleComments_whenSearchingArticleComments_thenReturnsArticleCommentsDelete(){

        //Given

        //When

        //Then

    }

}