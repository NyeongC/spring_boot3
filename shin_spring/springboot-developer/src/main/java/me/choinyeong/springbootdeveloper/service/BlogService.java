package me.choinyeong.springbootdeveloper.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import me.choinyeong.springbootdeveloper.domain.Article;
import me.choinyeong.springbootdeveloper.dto.ArticleRequest;
import me.choinyeong.springbootdeveloper.dto.UpdateArticleRequest;
import me.choinyeong.springbootdeveloper.repository.BlogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class BlogService {

    @Autowired
    private final BlogRepository blogRepository;

    public Article save(ArticleRequest request){
        return blogRepository.save(request.toEntity());
    }

    public List<Article> findAll() {
        return blogRepository.findAll();
    }

    public Article findById(long id) {
        return blogRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found: " + id));
    }

    public void delete(long id) {
        blogRepository.deleteById(id);
    }

    @Transactional
    public Article update(long id, UpdateArticleRequest request) {

        Article article = blogRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found: " + id));

        article.update(request.getTitle(), request.getContent());

        return article;
    }
}
