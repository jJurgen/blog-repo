package com.jurgen.blog.service;

import com.jurgen.blog.dao.CommentDao;
import com.jurgen.blog.domain.Comment;
import com.jurgen.blog.domain.Post;
import com.jurgen.blog.domain.User;
import java.sql.Timestamp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "commentService")
//@Transactional
public class CommentService {

    @Autowired
    private CommentDao commentDao;

    private static Logger logger = LoggerFactory.getLogger(CommentService.class);

    public CommentService() {
        logger.debug("loggerService created");
    }

    public void removeComment(Comment comment) {
        commentDao.delete(comment);
        logger.debug("comment: " + comment.getContent().substring(0, Math.min(10, comment.getContent().length() - 1)) + "... removed");

    }

    public Comment getComment(Long id) {
        return commentDao.get(id);
    }

    public void addComment(String content, User author, Post post) {
        Comment comment = new Comment();
        comment.setContent(content);
        comment.setAuthor(author);
        comment.setPost(post);
        comment.setCommentDate(new Timestamp(System.currentTimeMillis()));
        logger.debug("creating of comment: " + comment.getContent().substring(0, Math.min(10, comment.getContent().length() - 1)));
        commentDao.create(comment);        
    }

    public CommentDao getCommentDao() {
        return commentDao;
    }

    public void setCommentDao(CommentDao commentDao) {
        this.commentDao = commentDao;
    }

}
