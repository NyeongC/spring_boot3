package com.hodolog.api.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@ToString
@Setter
@Getter
@AllArgsConstructor
@Builder
public class PostCreate {

    @NotBlank
    private String title;

    @NotBlank
    private String content;

}
