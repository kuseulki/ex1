package com.a.exboard5.dto;

import java.io.Serializable;


public record ArticleUpdateDto(

        String title,
        String content,
        String hashtag
) {

    public static ArticleUpdateDto of(String title, String content, String hashtag) {
        return new ArticleUpdateDto(title, content, hashtag);
    }
}