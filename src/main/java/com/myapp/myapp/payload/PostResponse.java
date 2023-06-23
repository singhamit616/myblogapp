package com.myapp.myapp.payload;

import com.myapp.myapp.entities.Post;
import lombok.Data;

import java.util.List;

@Data
public class PostResponse {

    private List<PostDto> content;
    private int pageNo;
    private int pageSize;
    private int totalElement;
    private int totalPages;
    private boolean isLast;

}
