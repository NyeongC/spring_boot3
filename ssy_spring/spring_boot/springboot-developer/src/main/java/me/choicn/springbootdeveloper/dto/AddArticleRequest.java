package me.choicn.springbootdeveloper.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.choicn.springbootdeveloper.domain.Article;

@NoArgsConstructor // 기본 생성자
@AllArgsConstructor // 모든 필드 값을 받는 생성자 
@Getter
public class AddArticleRequest {
    
    private String title;
    private String content;
    
    public Article toEntity() { // DTO를 엔티티로 만들어주는 메서드
        return Article.builder()
                .title(title)
                .content(content)
                .build();
    }
}
