package com.ccn.springai.service;

import com.ccn.springai.AddArticleRequest;
import com.ccn.springai.domain.Article;
import com.ccn.springai.repository.BlogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class BlogService {

    private final BlogRepository blogRepository;

    public Article save(AddArticleRequest request) {
        return blogRepository.save(request.toEntity());
    }


}