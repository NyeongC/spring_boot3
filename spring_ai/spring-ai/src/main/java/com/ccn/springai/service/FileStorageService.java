package com.ccn.springai.service;


import com.ccn.springai.dto.UploadResponse;

public interface FileStorageService {
    UploadResponse store(byte[] bytes, String filename);
}
