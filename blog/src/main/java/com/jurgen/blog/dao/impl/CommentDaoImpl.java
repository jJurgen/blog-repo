package com.jurgen.blog.dao.impl;

import com.jurgen.blog.dao.CommentDao;
import com.jurgen.blog.dao.impl.GenericDaoImpl;
import com.jurgen.blog.domain.Comment;
import org.springframework.stereotype.Component;

@Component("commentDao")
public class CommentDaoImpl extends GenericDaoImpl<Comment, Long> implements CommentDao{
    
}
