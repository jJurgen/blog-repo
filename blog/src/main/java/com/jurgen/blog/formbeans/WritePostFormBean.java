package com.jurgen.blog.formbeans;

import javax.validation.constraints.Size;

public class WritePostFormBean {

    @Size(min = 3, max = 200, message = "-length of title must be from 3 to 200 characters")
    private String title;

    @Size(min = 3, max = 3000, message = "-length of post must be from 3 to 3000 characters")
    private String content;

    private Long postId;

    public WritePostFormBean() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

}
