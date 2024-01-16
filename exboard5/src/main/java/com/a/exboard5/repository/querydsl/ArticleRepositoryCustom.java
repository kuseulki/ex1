package com.a.exboard5.repository.querydsl;

import java.util.List;

public interface ArticleRepositoryCustom {

    List<String> findAllDistinctHashtags();
}
