package me.choinyeong.springbootdeveloper.controller;

import lombok.RequiredArgsConstructor;
import me.choinyeong.springbootdeveloper.domain.Article;
import me.choinyeong.springbootdeveloper.dto.ArticleRequest;
import me.choinyeong.springbootdeveloper.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class BlogApiController {

    @Autowired
    private final BlogService blogService;

    @PostMapping("/api/articles")
    public ResponseEntity<Article> save(@RequestBody ArticleRequest request) {
        Article savedArticle = blogService.save(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(savedArticle);
    }
}
