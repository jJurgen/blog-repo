package com.jurgen.blog.dao.impl;

import com.jurgen.blog.dao.PostDao;
import com.jurgen.blog.domain.Post;
import com.jurgen.blog.domain.User;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("postDao")
public class PostDaoImpl extends GenericDaoImpl<Post, Integer> implements PostDao {
    
    private static Logger logger = LoggerFactory.getLogger(PostDaoImpl.class);
    
    @Override
    public List<Post> getUsersPosts(User user) {
        if (user != null) {
            List<Post> posts = getCurrentSession().createCriteria(type).add(Restrictions.eq("author", user)).list();
            if (posts != null) {
                logger.debug("All posts of user: " + user.getUsername() + " obtained. Count: " + posts.size());
            }
            return posts;
        }
        return null;
    }
    
    @Override
    public List<Post> getRecentPosts(int amount) {
        Criteria c = getCurrentSession().createCriteria(type);
        c.addOrder(Order.desc("postDate"));
        c.addOrder(Order.asc("content"));
        c.setMaxResults(amount);
        List<Post> posts = c.list();
        if (posts != null) {
            logger.debug("Recent posts obtained. Count: " + posts.size());
        }
        return posts;
    }
    
    @Override
    public List<Post> searchByTitle(String req) {
        Criteria c = getCurrentSession().createCriteria(type);
        c.add(Restrictions.like("title", req, MatchMode.ANYWHERE).ignoreCase());
        List<Post> posts = c.list();
        if (posts != null) {
            logger.debug("Obtained " + posts.size() + " posts which was found by title: " + req);
        }
        return posts;
    }
    
    @Override
    public List<Post> searchByContent(String req) {
        Criteria c = getCurrentSession().createCriteria(type);
        c.add(Restrictions.like("content", req, MatchMode.ANYWHERE).ignoreCase());
        List<Post> posts = c.list();
        if (posts != null) {
            logger.debug("Obtained " + posts.size() + " posts which was found by content: " + req);
        }
        return posts;
    }
    
    @Override
    public List<Post> searchByAuthor(String req) {
        Criteria c = getCurrentSession().createCriteria(type, "post");
        c.createAlias("post.author", "auth");
        c.add(Restrictions.like("auth.username", req, MatchMode.ANYWHERE).ignoreCase());
        List<Post> posts = c.list();
        if (posts != null) {
            logger.debug("Obtained " + posts.size() + " posts which was found by author: " + req);
        }
        return posts;
    }
    
    @Override
    public List<Post> searchByComments(String req) {
        Criteria c = getCurrentSession().createCriteria(type, "post");
        c.createCriteria("comments", "comm");
        c.add(Restrictions.like("comm.content", req, MatchMode.ANYWHERE).ignoreCase());
        List<Post> posts = c.list();
        if (posts != null) {
            logger.debug("Obtained " + posts.size() + " posts which was found by comments: " + req);
        }
        return posts;
    }
    
    @Override
    public Post getPostWithJoins(Integer id) {
        Post post = get(id);
        if (post != null) {
            Hibernate.initialize(post.getComments());
            logger.debug("Post with id: " + id + " obtained and initialized with " + post.getComments().size() + " comments");
        }
        return post;
    }
    
}
