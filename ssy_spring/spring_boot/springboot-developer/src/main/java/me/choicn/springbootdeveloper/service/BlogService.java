package me.choicn.springbootdeveloper.service;


import lombok.RequiredArgsConstructor;
import me.choicn.springbootdeveloper.domain.Article;
import me.choicn.springbootdeveloper.dto.AddArticleRequest;
import me.choicn.springbootdeveloper.repository.BlogRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor // final 키워드나 @NotNull 이 붙은 필드로 생성자를 만들어줌
@Service // 빈 등록
public class BlogService {

    private final BlogRepository blogRepository;

    // 블로그 글 추가 메서드
    public Article save(AddArticleRequest request){
        return blogRepository.save(request.toEntity());
    }

    public List<Article> findAll(){
        return blogRepository.findAll();
    }
}
