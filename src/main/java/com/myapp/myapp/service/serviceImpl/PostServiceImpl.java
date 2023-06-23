package com.myapp.myapp.service.serviceImpl;

import com.myapp.myapp.entities.Post;
import com.myapp.myapp.exception.ResourceNotFoundException;
import com.myapp.myapp.payload.PostDto;
import com.myapp.myapp.payload.PostResponse;
import com.myapp.myapp.repositories.PostRepository;
import com.myapp.myapp.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    private PostRepository postRepo;
    private ModelMapper mapper;

    public PostServiceImpl(PostRepository postRepo ,ModelMapper mapper) {

        this.postRepo = postRepo;
        this.mapper = mapper;
    }

    @Override
    public PostDto createPost(PostDto postDto) {
        Post post = mapToEntity(postDto);

        Post newPost = postRepo.save(post);

        PostDto dto = mapToDto(newPost);
        return dto;
    }


    @Override
    public PostResponse getAllPost(int pageNo, int pageSize,String sortBy, String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ?
                Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        PageRequest pageable = PageRequest.of(pageNo,pageSize, sort);


        Page<Post> content = postRepo.findAll(pageable);
        //to convert page to list ---->
        List<Post> posts = content.getContent();

        List<PostDto> dto = posts.stream().map(post -> mapToDto(post)).collect(Collectors.toList());

        PostResponse postResponse = new PostResponse();
        postResponse.setContent(dto);
        postResponse.setPageSize(content.getSize());
        postResponse.setTotalPages(content.getTotalPages());
        postResponse.setPageNo(content.getNumber());
        postResponse.setLast(content.isLast());
        postResponse.setTotalElement((int)content.getTotalElements());

        return postResponse;
    }

    @Override
    public PostDto getPostById(long id) {
        Post post = postRepo.findById(id).orElseThrow(
                ()->new ResourceNotFoundException("Post","Id", id)
        );
        return mapToDto(post);
    }

    @Override
    public PostDto updatePost(PostDto postDto, long id) {
        Post post = postRepo.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("Post", "Id", id)
        );
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());

        Post updatedPost = postRepo.save(post);
        return mapToDto(updatedPost);
    }

    @Override
    public void deletePostByID(long id) {
        Post post = postRepo.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Post", "Id", id)
        );
        postRepo.deleteById(id);
    }

    PostDto mapToDto(Post post) {
        PostDto postDto = mapper.map(post, PostDto.class); //using mapper class we convert easily

//        PostDto dto = new PostDto();
//        dto.setId(post.getId());
//        dto.setTitle(post.getTitle());
//        dto.setDescription(post.getDescription());
//        dto.setContent(post.getContent());
        return postDto;
    }

    Post mapToEntity(PostDto postDto){
        Post post = mapper.map(postDto, Post.class);
//        Post  post = new Post();
//        post.setTitle(postDto.getTitle());
//        post.setDescription(postDto.getDescription());
//        post.setContent(postDto.getContent());
        return post;
    }
}
