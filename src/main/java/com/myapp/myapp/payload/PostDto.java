package com.myapp.myapp.payload;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class PostDto {


    private long id;
    @NotEmpty
    @Size(min = 3 , message = "Post description should have at least 3 characters")
    private String title;
    @NotEmpty
    @Size(min = 10, message = "Post title should have at least 10 character")
    private String description;
    private String content;
}
