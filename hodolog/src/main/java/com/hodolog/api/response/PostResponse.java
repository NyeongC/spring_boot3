package com.hodolog.api.response;

import lombok.Builder;
import lombok.Getter;


@Getter
public class PostResponse {

    private Long id;
    private String title;
    private String content;

    @Builder
    public PostResponse(Long id, String title, String content) {
        this.id = id;
        this.title = title.substring(0,Math.min(title.length(),10)); // 클라이언트 요구사항 응답객체 10자 제한
        this.content = content;
    }
}
