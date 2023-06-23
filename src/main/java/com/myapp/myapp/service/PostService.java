package com.myapp.myapp.service;

import com.myapp.myapp.payload.PostDto;
import com.myapp.myapp.payload.PostResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface PostService {

    PostDto createPost(PostDto postDto);

    PostResponse getAllPost(int pageNo, int pageSize, String sortBy, String sortDir);


    PostDto getPostById(long id);

    PostDto updatePost(PostDto postDto, long id);

    void deletePostByID(long id);
}
