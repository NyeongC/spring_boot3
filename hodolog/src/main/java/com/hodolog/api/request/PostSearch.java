package com.hodolog.api.request;

import lombok.Builder;

import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class PostSearch {

    @Builder.Default
    private Integer page = 1;

    @Builder.Default
    private Integer size = 10;

    public long getOffset() {
        return  (long)(Math.max(page, 1) - 1) * size;
    }
}
