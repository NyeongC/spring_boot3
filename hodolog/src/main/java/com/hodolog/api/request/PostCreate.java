package com.hodolog.api.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Setter
@Getter
@AllArgsConstructor
public class PostCreate {

    @NotBlank
    private String title;

    @NotBlank
    private String content;

}
