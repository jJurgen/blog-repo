package com.jurgen.blog.formbeans;

import javax.validation.constraints.Size;

public class AddCommentFormBean {

    @Size(min = 1, max = 500, message = "-length of comment must be from 1 to 500 characters")
    private String comment;

    private Long postId;

    public AddCommentFormBean() {
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }
}
