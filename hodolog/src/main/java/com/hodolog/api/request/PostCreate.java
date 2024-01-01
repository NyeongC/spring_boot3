package com.hodolog.api.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Setter;
import lombok.ToString;

@ToString
@Setter
public class PostCreate {

    @NotBlank
    private String title;

    @NotBlank
    private String content;

}
