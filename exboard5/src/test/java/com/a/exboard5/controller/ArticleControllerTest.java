package com.a.exboard5.controller;

import com.a.exboard5.config.SecurityConfig;
import com.a.exboard5.domain.constant.SearchType;
import com.a.exboard5.dto.ArticleWithCommentsDto;
import com.a.exboard5.dto.UserAccountDto;
import com.a.exboard5.service.ArticleService;
import com.a.exboard5.service.PaginationService;
import com.a.exboard5.util.FormDataEncoder;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.BDDMockito.*;

@DisplayName("View 컨트롤러 - 게시글")
@Import({SecurityConfig.class, FormDataEncoder.class})
@WebMvcTest(ArticleController.class)
class ArticleControllerTest {

    private final MockMvc mvc;
    private final FormDataEncoder formDataEncoder;

    @MockBean private ArticleService articleService;
    @MockBean private PaginationService paginationService;


    public ArticleControllerTest(@Autowired MockMvc mvc, @Autowired FormDataEncoder formDataEncoder) {
        this.mvc = mvc;
        this.formDataEncoder = formDataEncoder;
    }


    @DisplayName("[View][GET] 게시글 리스트 (게시판) 페이지 - 정상 호출 ")
    @Test
    public void givenNothing_whenRequestingArticleView_thenReturnArticlesView() throws Exception {

        // given
        given(articleService.searchArticles(eq(null), eq(null), any(Pageable.class))).willReturn(Page.empty());
        given(paginationService.getPaginationBarNumbers(anyInt(), anyInt())).willReturn(List.of(0, 1, 2, 3, 4));

        mvc.perform(get("/articles"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(view().name("articles/index"))
                .andExpect(model().attributeExists("articles"))
                .andExpect(model().attributeExists("paginationBarNumbers"));

        // then
        then(articleService).should().searchArticles(eq(null), eq(null), any(Pageable.class));
        then(paginationService).should().getPaginationBarNumbers(anyInt(), anyInt());
    }

    @DisplayName("[View][GET] 게시글 리스트 (게시판) 페이지 - 검색 기능 ")
    @Test
    public void givenSearchKeyword_whenSearchingArticleView_thenReturnArticlesView() throws Exception {

        // given
        SearchType searchType = SearchType.TITLE;
        String searchValue = "title";

        given(articleService.searchArticles(eq(searchType), eq(searchValue), any(Pageable.class))).willReturn(Page.empty());
        given(paginationService.getPaginationBarNumbers(anyInt(), anyInt())).willReturn(List.of(0, 1, 2, 3, 4));

        mvc.perform(get("/articles")
                        .queryParam("searchType", searchType.name())
                        .queryParam("searchValue", searchValue)

                )
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(view().name("articles/index"))
                .andExpect(model().attributeExists("articles"))
                .andExpect(model().attributeExists("searchTypes"));

        // then
        then(articleService).should().searchArticles(eq(searchType), eq(searchValue), any(Pageable.class));
        then(paginationService).should().getPaginationBarNumbers(anyInt(), anyInt());
    }

    @DisplayName("[view][GET] 게시글 리스트 (게시판) 페이지 - 페이징, 정렬 기능")
    @Test
    void givenPagingAndSortingParams_whenSearchingArticlesPage_thenReturnsArticlesPage() throws Exception {
        // Given
        String sortName = "title";
        String direction = "desc";
        int pageNumber = 0;
        int pageSize = 5;
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Order.desc(sortName)));
        List<Integer> barNumbers = List.of(1, 2, 3, 4, 5);
        given(articleService.searchArticles(null, null, pageable)).willReturn(Page.empty());
        given(paginationService.getPaginationBarNumbers(pageable.getPageNumber(), Page.empty().getTotalPages())).willReturn(barNumbers);

        // When & Then
        mvc.perform(
                        get("/articles")
                                .queryParam("page", String.valueOf(pageNumber))
                                .queryParam("size", String.valueOf(pageSize))
                                .queryParam("sort", sortName + "," + direction)
                )
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(view().name("articles/index"))
                .andExpect(model().attributeExists("articles"))
                .andExpect(model().attribute("paginationBarNumbers", barNumbers));
        then(articleService).should().searchArticles(null, null, pageable);
        then(paginationService).should().getPaginationBarNumbers(pageable.getPageNumber(), Page.empty().getTotalPages());
    }

//    @Disabled("구현 중")
//    @DisplayName("[View][GET] 게시글 상세 페이지 - 정상 호출 ")
//    @Test
//    public void givenNothing_whenRequestingArticleView_thenReturnArticleView() throws Exception {
//
//        //Given
//        Long articleId = 1L;
//        given(articleService.getArticle(articleId)).willReturn(createArticleWithCommentsDto());
//
//        // When & Then
//        mvc.perform(get("/articles/" + articleId))
//                .andExpect(status().isOk())
//                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
//                .andExpect(view().name("articles/detail"))
//                .andExpect(model().attributeExists("article"))
//                .andExpect(model().attributeExists("articleComments"));
//
//        // then
//        then(articleService).should().getArticle(articleId);
//    }

    @Disabled("구현 중")
    @DisplayName("[View][GET] 게시글 검색 전용 페이지 - 정상 호출 ")
    @Test
    public void givenNothing_whenRequestingArticleSearchView_thenReturnArticleSearchView() throws Exception {

        //Given

        // When & Then
        mvc.perform(get("/articles/search"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(view().name("articles/search"));

    }

    @DisplayName("[View][GET] 게시글 해시태그 검색 페이지 - 정상 호출, 해시태그 입력x")
    @Test
    public void givenNothing_whenRequestingArticleSearchHashtagView_thenReturnsArticleSearchHashtagView() throws Exception {
        // Given
        List<String> hashtags = List.of("#java", "#spring", "#boot");
        given(articleService.searchArticlesViaHashtag(eq(null), any(Pageable.class))).willReturn(Page.empty());
        given(articleService.getHashtags()).willReturn(hashtags);
        given(paginationService.getPaginationBarNumbers(anyInt(), anyInt())).willReturn(List.of(1, 2, 3, 4, 5));

        // When & Then
        mvc.perform(get("/articles/search-hashtag"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(view().name("articles/search-hashtag"))
                .andExpect(model().attribute("articles", Page.empty()))
                .andExpect(model().attribute("hashtags", hashtags))
                .andExpect(model().attributeExists("paginationBarNumbers"))
                .andExpect(model().attribute("searchType", SearchType.HASHTAG));
        then(articleService).should().searchArticlesViaHashtag(eq(null), any(Pageable.class));
        then(articleService).should().getHashtags();
        then(paginationService).should().getPaginationBarNumbers(anyInt(), anyInt());
    }

    @DisplayName("[View][GET] 게시글 해시태그 검색 페이지 - 정상 호출, 해시태그 입력o")
    @Test
    public void givenHashtag_whenRequestingArticleSearchHashtagView_thenReturnsArticleSearchHashtagView() throws Exception {
        // Given
        String hashtag = "#java";
        List<String> hashtags = List.of("#java", "#spring", "#boot");
        given(articleService.searchArticlesViaHashtag(eq(hashtag), any(Pageable.class))).willReturn(Page.empty());
        given(articleService.getHashtags()).willReturn(hashtags);
        given(paginationService.getPaginationBarNumbers(anyInt(), anyInt())).willReturn(List.of(1, 2, 3, 4, 5));

        // When & Then
        mvc.perform(
                        get("/articles/search-hashtag")
                                .queryParam("searchValue", hashtag)
                )
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(view().name("articles/search-hashtag"))
                .andExpect(model().attribute("articles", Page.empty()))
                .andExpect(model().attribute("hashtags", hashtags))
                .andExpect(model().attributeExists("paginationBarNumbers"))
                .andExpect(model().attribute("searchType", SearchType.HASHTAG));
        then(articleService).should().searchArticlesViaHashtag(eq(hashtag), any(Pageable.class));
        then(articleService).should().getHashtags();
        then(paginationService).should().getPaginationBarNumbers(anyInt(), anyInt());
    }


    private ArticleWithCommentsDto createArticleWithCommentsDto(){
        return ArticleWithCommentsDto.of(
                1L,
                createUserAccountDto(),
                Set.of(),
                "title",
                "content",
                "#java",
                LocalDateTime.now(),
                "kk",
                LocalDateTime.now(),
                "kk"
        );
    }

    private UserAccountDto createUserAccountDto(){
        return UserAccountDto.of(
                "kk",
                "pw",
                "kk@mail.com",
                "name_kk",
                "memo",
                LocalDateTime.now(),
                "kk",
                LocalDateTime.now(),
                "kk"
        );
    }

}