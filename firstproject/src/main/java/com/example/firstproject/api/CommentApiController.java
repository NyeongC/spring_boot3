package com.example.firstproject.api;

import com.example.firstproject.dto.CommentDto;
import com.example.firstproject.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CommentApiController {
    @Autowired
    private CommentService commentService;
    
    // 1. 댓글 조회
    @GetMapping("/api/articles/{article_id}/comments")
    ResponseEntity<List<CommentDto>> comments(@PathVariable Long article_id){
        // 서비스 위임
        List<CommentDto> dtos = commentService.comments(article_id);
        // 결과 응답
        return ResponseEntity.status(HttpStatus.OK).body(dtos);
    }
    // 2. 댓글 생성
    @PostMapping("/api/articles/{article_id}/comments")
    public ResponseEntity<CommentDto> create(@PathVariable Long article_id, @RequestBody CommentDto dto){
        // 서비스 위임
        CommentDto commentDto = commentService.create(article_id,dto);
        // 결과 응답
        return ResponseEntity.status(HttpStatus.OK).body(commentDto);
    }
    // 3. 댓글 수정
    @PatchMapping("/api/comments/{id}")
    public ResponseEntity<CommentDto> update(@PathVariable Long id, @RequestBody CommentDto dto){
        // 서비스 위임
        CommentDto updatedDto = commentService.update(id,dto);
        // 결과 응답
        return ResponseEntity.status(HttpStatus.OK).body(updatedDto);
    }
    // 4. 댓글 삭제
}
