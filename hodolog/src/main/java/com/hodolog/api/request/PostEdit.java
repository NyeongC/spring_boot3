package com.hodolog.api.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostEdit {
    
    // 기능이 다른것을 넘길때에는 새로운 클래스를 쓰는게 맞다

    @NotBlank(message = "타이틀을 입력하세요.")
    private String title;
    @NotBlank(message = "콘텐츠를 입력해주세요.")
    private String content;

    @Builder
    public PostEdit(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
