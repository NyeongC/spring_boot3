package me.choinyeong.springbootdeveloper.service;

import lombok.RequiredArgsConstructor;
import me.choinyeong.springbootdeveloper.domain.Article;
import me.choinyeong.springbootdeveloper.dto.ArticleRequest;
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
}
