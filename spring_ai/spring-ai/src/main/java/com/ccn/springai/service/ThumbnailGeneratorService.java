package com.ccn.springai.service;


import com.ccn.springai.dto.GeneratorThumbnailRequest;
import com.ccn.springai.dto.GeneratorThumbnailResponse;
import com.ccn.springai.dto.UploadResponse;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.image.Image;
import org.springframework.ai.image.ImageModel;
import org.springframework.ai.image.ImagePrompt;
import org.springframework.ai.image.ImageResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.net.URI;
import java.util.Base64;

@Service
public class ThumbnailGeneratorService {

    private final ImageModel imageModel;
    private final PromptTemplate template;
    private final FileStorageService fileStorageService;
    private final RestClient restClient = RestClient.create();

    public ThumbnailGeneratorService(
            ImageModel imageModel,
            FileStorageService fileStorageService,
            @Value("classpath:prompts/thumbnail-generator.st") Resource promptResource
    ) {
        this.imageModel = imageModel;
        this.fileStorageService = fileStorageService;
        this.template = new PromptTemplate(promptResource);
    }

    public GeneratorThumbnailResponse generateThumbnail(GeneratorThumbnailRequest request) {
        String prompt = template.create(request.toMap()).getContents();

        ImageResponse response = imageModel.call(new ImagePrompt(prompt));
        Image image = response.getResult().getOutput();

        byte[] bytes;

        if (image.getUrl() != null) {
            bytes = restClient.get()
                    .uri(URI.create(image.getUrl()))
                    .retrieve()
                    .body(byte[].class);
        } else if (image.getB64Json() != null) {
            bytes = Base64.getDecoder().decode(image.getB64Json());
        } else {
            throw new IllegalStateException("이미지 응답에 URL과 Base64 데이터가 모두 없습니다.");
        }

        UploadResponse saved = fileStorageService.store(bytes, "thumbnail.png");

        return new GeneratorThumbnailResponse(saved.imageUrl());
    }
}
