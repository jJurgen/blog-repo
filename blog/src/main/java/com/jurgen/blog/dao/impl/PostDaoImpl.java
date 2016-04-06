package com.jurgen.blog.dao.impl;

import com.jurgen.blog.dao.PostDao;
import com.jurgen.blog.domain.Post;
import com.jurgen.blog.domain.User;
import java.util.List;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("postDao")
public class PostDaoImpl extends GenericDaoImpl<Post, Integer> implements PostDao {

    private static Logger logger = LoggerFactory.getLogger(PostDaoImpl.class);

    @Override
    public List<Post> getUsersPosts(User user) {
        Query query = getCurrentSession().createQuery("from Post where author = :author");
        query.setParameter("author", user);
        List<Post> posts = query.list();
        if (user != null) {
            logger.debug("All posts of user: " + user.getUsername() + " obtained. Count: " + posts.size());
        }
        return posts;
    }

    @Override
    public List<Post> getRecentPosts(int amount) {
        Query query = getCurrentSession().createQuery("from Post order by postDate");
        query.setMaxResults(amount);
        List<Post> posts = query.list();
        logger.debug("Recent posts obtained. Count: " + posts.size());
        return posts;
    }

    @Override
    public List<Post> searchByTitle(String req) {
        Query query = getCurrentSession().createQuery("from Post where lower(title) like :request");
        query.setParameter("request", '%' + req.toLowerCase() + '%');
        List<Post> posts = query.list();
        logger.debug("Obtained " + posts.size() + " posts which was found by title: " + req);
        return posts;
    }

    @Override
    public List<Post> searchByContent(String request) {
        Query query = getCurrentSession().createQuery("from Post where lower(content) like :request");
        query.setParameter("request", '%' + request.toLowerCase() + '%');
        List<Post> posts = query.list();
        logger.debug("Obtained " + posts.size() + " posts which was found by content: " + request);
        return posts;
    }

    @Override
    public List<Post> searchByAuthor(String request) {
        Query query = getCurrentSession().createQuery("from Post where lower(author.username) like :request");
        query.setParameter("request", '%' + request.toLowerCase() + '%');
        List<Post> posts = query.list();
        logger.debug("Obtained " + posts.size() + " posts which was found by author: " + request);
        return posts;
    }

    @Override
    public List<Post> searchByComments(String request) {
        String hqlQuery = "from Post as post LEFT JOIN FETCH post.comments as comment where lower(comment.content) like :request";
        Query query = getCurrentSession().createQuery(hqlQuery);
        query.setParameter("request", '%' + request.toLowerCase() + '%');
        List<Post> posts = query.list();
        logger.debug("Obtained " + posts.size() + " posts which was found by comments: " + request);
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
