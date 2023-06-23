package com.myapp.myapp.service;

import com.myapp.myapp.payload.CommentDto;

import java.util.List;

public interface CommentService {

    CommentDto createComment(long postId, CommentDto commentDto);

    List<CommentDto> getCommentsByPostId(long postId);
    CommentDto getCommentById(Long postId, Long commentId);

    CommentDto updateComment(long postId, long id, CommentDto commentDto);

    void deleteComent(long postId, long id);
}
